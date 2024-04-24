#include <Wire.h>
#include <MMA7660.h>
#include <PubSubClient.h>
#include <TFT_eSPI.h>
#include <rpcWiFi.h>

// Constants for step detection and calorie calculation
const float STEP_THRESHOLD = 1.0;
const int MIN_STEP_INTERVAL = 500;
const float CALORIES_PER_STEP = 0.05;

// Global variables
int stepCount = 0;
bool isMoving = false;
unsigned long lastStepTime = 0;
float userHeight = 195.0;
float strideLength;
unsigned long lastActionTime = 0;

// Variables for race
bool isRaceStarted = false;
unsigned long raceStartTime = 0;
unsigned long raceEndTime = 0;
int buttonPosition = 0;

const unsigned long MQTT_RETRY_INTERVAL = 5000;
const unsigned long WIFI_RETRY_INTERVAL = 5000;

// WiFi and MQTT broker configuration
 char ssid[] = "Alex";
 const char* password = "QazP1234";
 const char* server = "broker.hivemq.com";
 const char* mainTopic = "fitVenture/sensor/accelerometer/data";
 const char* raceTopic = "fitVenture/sensor/accelerometer/raceData";
 const int port = 1883;

MMA7660 accel;
TFT_eSPI tft;
WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);

void setup() {

  tft.begin(); // Initialize the TFT display
  tft.setRotation(3);
  Serial.begin(9600);

  Wire.begin();
  // Initialize the accelerometer
  initAccelerometer();

  // Calculate stride length based on user's height
  calculateStrideLength();

  // Connect to Wi-Fi
  connectWiFi();
  mqttClient.setServer(server, port);
  reconnectMQTT();
}

void loop() {
  unsigned long currentTime = millis();

  // Read accelerometer data
  float x, y, z;
  bool success = accel.getAcceleration(&x, &y, &z);

  // Read if button is pressed
  buttonPosition = digitalRead(2);

  if (success) {
    // Calculate magnitude of acceleration vector
    float magnitude = sqrt(x * x + y * y + z * z);

    // Check if magnitude exceeds threshold
    if (magnitude > STEP_THRESHOLD) {
      // Start counting steps only if previously not moving
      if (!isMoving && (currentTime - lastStepTime > MIN_STEP_INTERVAL)) {
        stepCount++;
        lastStepTime = currentTime;
        publishData();
        Serial.println("Step detected and published.");
      } else if (buttonPosition == HIGH) {
        // Start the race
        isRaceStarted = true;
        raceStartTime = millis();
        } else {
        // Stop the race
        isRaceStarted = false;
        raceEndTime = millis();

        // Publish race data
        publishRaceData();
        }
      isMoving = true;
    } else {
      // Stop counting steps if previously moving
      isMoving = false;
    }
  } else {
    Serial.println("Failed to read accelerometer data.");
  }

    tft.fillScreen(TFT_BLACK); // Clear the screen
    tft.setCursor(0, 0); // Set the cursor position
    tft.setTextColor(TFT_WHITE); // Set text color
    tft.setTextSize(2); // Set text size
  

  // Maintain MQTT connection
  if (!mqttClient.connected()) {
    if (currentTime - lastActionTime > MQTT_RETRY_INTERVAL) {
      reconnectMQTT();
      lastActionTime = currentTime;
      Serial.println("Reconnecting to MQTT broker...");
    }
  }
  mqttClient.loop();

  // Non-blocking delay
  if (currentTime - lastActionTime > 500) {
    delay(500);
    lastActionTime = currentTime;
  }
}

void initAccelerometer() {
  accel.init();
  accel.setMode(MMA7660_STAND_BY);
  accel.setSampleRate(6);
  accel.setMode(MMA7660_ACTIVE);
}

void calculateStrideLength() {
  strideLength = 0.415 * userHeight;
}

void connectWiFi() {
  Serial.println("Connecting to Wi-Fi...");
  WiFi.begin(ssid, password);
  delay(5000); // Wait for connection to establish
  unsigned long startTime = millis();
  while (WiFi.status() != WL_CONNECTED) {
    if (millis() - startTime > WIFI_RETRY_INTERVAL) {
      Serial.println("Failed to connect to Wi-Fi...");
      return;
    }
    delay(500);
  }
  Serial.println("Connected to Wi-Fi");
}

void reconnectMQTT() {
  Serial.println("Trying MQTT connection...");
  String clientId = "wio";
  if (mqttClient.connect(clientId.c_str())) {
    Serial.println("Connected to MQTT broker");
  } else {
    Serial.print("Failed to connect to MQTT broker, rc=");
    Serial.println(mqttClient.state());
  }
}

void publishData() {
  // Calculate distance traveled based on steps taken in meters
  float distance = stepCount * strideLength / 100.0;
  
  // Calculate calories burned based on steps taken
  float caloriesBurned = stepCount * CALORIES_PER_STEP;

  // Publish step count, distance, and calorie data to MQTT topics
  char payload[100];
  snprintf(payload, sizeof(payload), "{\"distance\": %.2f, \"steps\": %d, \"calories\": %.2f}", distance, stepCount, caloriesBurned);
  tft.printf("Distance: %.2f\nStep Count: %d\nCalories Burned: %.2f", distance, stepCount, caloriesBurned);
  delay(500);
  mqttClient.publish(mainTopic, payload);
  Serial.println("Data published to MQTT topics.");
}

void publishRaceData() {
  // Calculate distance traveled based on steps taken in meters
  float distance = stepCount * strideLength / 100.0;

  // Calculate calories burned based on steps taken
  float caloriesBurned = stepCount * CALORIES_PER_STEP;

  char payload[100];
  snprintf(payload, sizeof(payload), "{\"start\": %lu, \"end\": %lu, \"distance\": %.2f, \"steps\": %d, \"calories\": %.2f}", raceStartTime, raceEndTime, distance, stepCount, caloriesBurned);
  mqttClient.publish(raceTopic, payload);
  tft.printf("Race finished!");
  Serial.println("Race data published to the MQTT topic.");
}