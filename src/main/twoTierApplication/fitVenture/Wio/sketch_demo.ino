#include <Wire.h>
#include <MMA7660.h>
#include <PubSubClient.h>
#include <TFT_eSPI.h>
#include <rpcWiFi.h>
#include <ArduinoJson.h>
#include <DHT.h>

//Pins
#define button 0
#define DHTPIN 0
#define DHTTYPE DHT11

// Constants for step detection and calorie calculation
const float STEP_THRESHOLD = 1.0;
const int MIN_STEP_INTERVAL = 500;
const float CALORIES_PER_STEP = 0.05;

// Constants for activity suggestions
const int NUM_SUGGESTIONS = 3;
const char* HOT_SUGGESTIONS[NUM_SUGGESTIONS] = {"Swimming", "Workout indoors", "Yoga"};
const char* SUNNY_SUGGESTIONS[NUM_SUGGESTIONS] = {"Basketball", "Football", "Cycling"};
const char* COLD_SUGGESTIONS[NUM_SUGGESTIONS] = {"Walk", "Hiking", "Jogging"};

// Global variables
int stepCount = 0;
bool isMoving = false;
bool lastConnectionStatus = false;
unsigned long lastStepTime = 0;
byte recieved_payload[128];
float userHeight;
float userWeight;
float strideLength;
unsigned long lastActionTime = 0;

// Variables for race
bool isRaceStarted = false;
unsigned long raceStartTime = 0;
unsigned long raceEndTime = 0;
byte buttonPosition = 0;
unsigned long lastDisplayTime = 0;
const int DEBOUNCE_DELAY = 50;
byte lastButtonState = HIGH;
unsigned long lastDebounceTime = 0;
byte buttonState = HIGH;

const unsigned long MQTT_RETRY_INTERVAL = 5000;
const unsigned long WIFI_RETRY_INTERVAL = 5000;
unsigned long WEATHER_SUGGESTIONS_INTERVAL = 10; // Three hours. the 10 seconds value is to just give the user a lillte
 //bit of time to look at the arduino. 3 hours are equivalent to 10800000 Millseconds. I did not assign the value here because
 //it would require the method to be called in the setup.
  float temperature, humidity;

// WiFi and MQTT broker configuration
char ssid[] = "ASUS_68";
const char* password = "Guricusub1007";
const char* server = "broker.hivemq.com";
const char* mainTopic = "fitVenture/sensor/accelerometer/data";
const char* raceTopic = "fitVenture/sensor/accelerometer/raceData";
const char* weightAndHeightTopic = "fitVenture/application/weight&height";
const int port = 1883;

MMA7660 accel;
TFT_eSPI tft;
WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);
DHT dht(DHTPIN, DHTTYPE);

void setup() {

  tft.begin(); // Initialize the TFT display
  tft.setRotation(3);
  Serial.begin(9600);

  Wire.begin();
  // Initialize the accelerometer
  initAccelerometer();

  // Connect to Wi-Fi
  connectWiFi();
  mqttClient.setServer(server, port);
  reconnectMQTT();
  //Subscribe to the user weight and height topic
  mqttClient.subscribe(weightAndHeightTopic);
  mqttClient.setCallback(getUserWeightAndHeight);


  // Initialize button as an input pin
  // INPUT_PULLUP used as configurator for the botton, when the button is not pressed it will be HIGH
  // when the button is pressed it will be LOW
  pinMode(button, INPUT_PULLUP);
  dht.begin();
}

void loop() {
  // read the state of the button
  int reading = digitalRead(button);

  if (reading != lastButtonState) {
    // reset the debouncing timer
    lastDebounceTime = millis();
  }

  if ((millis() - lastDebounceTime) > DEBOUNCE_DELAY) {
    // if the button state has changed:
    if (reading != buttonPosition) {
      buttonPosition = reading;

      // only toggle the race if the new button state is LOW
      if (buttonPosition == LOW) {
        Serial.println("Button pressed");
        isRaceStarted = !isRaceStarted;
        if (isRaceStarted) {
          stepCount++;
          raceStartTime = millis();
          displayRaceData();
        } else {
          raceEndTime = millis();
          // Publish race data
          publishRaceData();
          Serial.println("Race finished and data published.");
        }
      }
    }
  }

  lastButtonState = reading;
  unsigned long currentTime = millis();

  // Read accelerometer data
  float x, y, z;
  bool success = accel.getAcceleration(&x, &y, &z);

  if (success) {
    // Calculate magnitude of acceleration vector
    float magnitude = sqrt(x * x + y * y + z * z);

    // Check if magnitude exceeds threshold
    if (magnitude > STEP_THRESHOLD) {
      // Start counting steps only if previously not moving
      if (!isMoving && (currentTime - lastStepTime > MIN_STEP_INTERVAL)) {
        stepCount = 1;
        lastStepTime = currentTime;
        publishData();
        Serial.println("Step detected and published.");
      }
      isMoving = true;
    } else {
      // Stop counting steps if previously moving
      isMoving = false;
    }
  } else {
    Serial.println("Failed to read accelerometer data.");
  }

  /*tft.fillScreen(TFT_BLACK); // Clear the screen
  tft.setCursor(0, 150); // Set the cursor position
  tft.setTextColor(TFT_GREEN); // Set text color
  tft.setTextSize(2); // Set text size */


  getTemperatureAndHumidity(&temperature, &humidity);
  // after getting, we publish the temphumidity data to the app
  publishTemperatureAndHumidity(temperature, humidity);
  displayTemperatureAndHumidity(temperature, humidity);

  // Suggest activity based on weather conditions
    if( millis()>=WEATHER_SUGGESTIONS_INTERVAL )
    {
       displayActivitySuggestion(temperature, humidity);
       delay(500); // give the user time to see the message.
       WEATHER_SUGGESTIONS_INTERVAL+=10800000;
    }

  // Maintain MQTT connection
  if (!mqttClient.connected()) {
    if (currentTime - lastActionTime > MQTT_RETRY_INTERVAL) {
      reconnectMQTT();
      lastActionTime = currentTime;
      Serial.println("Reconnecting to MQTT broker...");
    }
  }
  mqttClient.loop();

  // Send signal status to client
  publishSignal();

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

void displayRaceData() {
  if (isRaceStarted) {
    // Calculate race duration
    unsigned long raceDuration = millis() - raceStartTime;

    // Calculate distance traveled based on steps taken in meters
    float distance = stepCount * strideLength / 100.0;

    // Calculate calories burned based on steps taken
    float caloriesBurned = stepCount * CALORIES_PER_STEP;
    tft.fillScreen(TFT_BLACK); // Clear the screen
    tft.setCursor(0, 0); // Set the cursor position
    tft.setTextColor(TFT_WHITE); // Set text color
    tft.setTextSize(2); // Set text size

    // Display the race duration, steps, calories, and distance
    tft.printf("Race Duration: %lu\nDistance: %.2f\nStep Count: %d\nCalories Burned: %.2f", raceDuration, distance, stepCount, caloriesBurned);
  }
}

void calculateStrideLength() {
  strideLength = 0.415 * userHeight;
}

//When the message gets recieved from the MQTTPublisher in the Java application, the setCallback method will call this method.
void getUserWeightAndHeight(char* topic, byte* payload, unsigned int length) {
  Serial.println("Payload with user height and weight has been received!");

  for (unsigned int i = 0; i < length; i++) {
    Serial.write(payload[i]);
  }
  Serial.println(); // Print newline for formatting
  //Copy the payload to be able to change the global variables later
  memcpy(recieved_payload, payload, length);

  setUserWeightAndHeight();
}

void setUserWeightAndHeight() {
  //Create a json document to put the deserialized payload inside of.
  StaticJsonDocument<100> jsonDoc;
  DeserializationError error = deserializeJson(jsonDoc, recieved_payload);
  if (error) {
    Serial.print("The payload could not be deserialized.");
    Serial.println(error.f_str());
    return;
  }

  //Assign the global variables to the value assigned to the meta-data
  userWeight = jsonDoc["userWeight"];
  userHeight = jsonDoc["userHeight"];

  Serial.println("User weight and height have been updated!");

  // Calculate stride length based on user's height
  calculateStrideLength();
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

/* This method will send the status of the mqtt connection to client,
whether success or failure */
void publishSignal() {
  // Get current MQTT connection status
  bool currentConnectionStatus = mqttClient.connected();

  // If client is connected and connection status has changed since
  if (currentConnectionStatus && !lastConnectionStatus) {
    // send signal confirmation to client
    mqttClient.publish("fitVenture/application/signal", "MQTT Connection Successful");
    // Update last connection status
    lastConnectionStatus = true;
  }
  // If client is not connected and connection status has changed since
  else if (!currentConnectionStatus && lastConnectionStatus) {
    mqttClient.publish("fitVenture/application/signal", "MQTT Connection Failed");
    // Update last connection status
    lastConnectionStatus = false;
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

  // Calculate race duration in seconds
  unsigned long raceDuration = (raceEndTime - raceStartTime) / 1000;

  // Publish step count, distance, and calorie data to MQTT topics
  char payload[200];
  snprintf(payload, sizeof(payload), "{\"StartTime\": %lu, \"EndTime\": %lu, \"Distance\": %.2f, \"Steps\": %d, \"Calories\": %.2f}", raceStartTime, raceEndTime, distance, stepCount, caloriesBurned);
  mqttClient.publish(raceTopic, payload);
  tft.printf("Race finished!");
  Serial.println("Race data published to the MQTT topic.");
}

void getTemperatureAndHumidity(float* temperature, float* humidity) {

   float temp= dht.readTemperature();
   float humi= dht.readHumidity();
   if(!isnan(temp) && !isnan(humi))
   {
     *temperature = temp; // read temperature in Celsius
     *humidity = humi; // read humidity
   }
}

// publishing tempHumidity data
void publishTempAndHumidity(float temperature, float humidity) {
  char payload[100];
  snprintf(payload, sizeof(payload), "{\"temperature\": %.2f, \"humidity\": %.2f}", temperature, humidity);
  mqttClient.publish("fitVenture/sensor/tempHumidity", payload);
  Serial.println("Temperature and humidity data published.");
}

void displayTemperatureAndHumidity(float temperature, float humidity) {
  tft.fillScreen(TFT_BLACK); // Clear the screen
  tft.setCursor(0, 0); // Set the cursor position
  tft.setTextColor(TFT_WHITE); // Set text color
  tft.setTextSize(2); // Set text size
  tft.printf("Temperature: %.2f C\nHumidity: %.2f%%", temperature, humidity);
  delay(500); // give the user, time to see the message
}

void displayActivitySuggestion(float temperature, float humidity) {
  String activity;
  // Check if it's hot and humid
  if (temperature >= 20 && humidity > 35) {
    activity = HOT_SUGGESTIONS[random(0, NUM_SUGGESTIONS)];
    // Check if it's sunny and not too humid
  } else if (temperature < 20 && temperature >= 10 && humidity < 35) {
    activity = SUNNY_SUGGESTIONS[random(0, NUM_SUGGESTIONS)];
    //if none, it's cold
  } else {
    activity = COLD_SUGGESTIONS[random(0, NUM_SUGGESTIONS)];
  }
  tft.setCursor(0, 50);
  tft.setTextColor(TFT_YELLOW);
  tft.setTextSize(2);
  tft.printf("Hey you! \nLet's go: %s", activity.c_str());
}