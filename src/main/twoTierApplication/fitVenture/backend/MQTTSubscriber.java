package fitVenture.backend;

import fitVenture.backend.stats.Stats;
import fitVenture.backend.tempAndHum.TempHumidityData;
import fitVenture.backend.stats.RaceStats;
import fitVenture.backend.utils.FileHandler;
import fitVenture.ui.FitVentureStart;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MQTTSubscriber {
    // Attributes to initialise MQTT broker, client id and topic
    private static final String broker = "tcp://broker.hivemq.com:1883";
    private static final String clientId = "ClientID1";
    // Topic for usual accelerometer data
    private static final String mainTopic = "fitVenture/sensor/accelerometer/data";
    // Topic for race data
    private static final String raceTopic = "fitVenture/sensor/accelerometer/raceData";
    // Topic for temperature and humidity data
    private static final String tempAndHumTopic = "fitVenture/sensor/tempHumidity";

    // Variables to store the last received message
    private Stats lastReceivedMessage;
    private RaceStats lastReceivedRaceMessage;
    private TempHumidityData lastReceivedtempHumidityData;

    // Method to subscribe to a topic
    public MQTTSubscriber() {
        try {
            // Initialise MQTT client and connect to broker using broker and client id
            MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connection = new MqttConnectOptions();
            client.connect(connection);
            System.out.println("Connected");

            // Defines what will happen when the connection is lost, message is arrived and delivery is completed
            client.setCallback(new MqttCallback() {
                // Method to show in case connection is lost
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection is lost");
                }

                // Method to save message from the MQTT broker into the JSON file
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message is arrived");
                    ObjectMapper mapper = new ObjectMapper();
                    // check from which topic the message is arrived
                    if (topic.equals(mainTopic)) {
                        lastReceivedMessage = mapper.readValue(message.toString(), Stats.class);
                        // save the data into the HashMap
                        FitVentureStart.fitVenture.saveStatsData(
                                lastReceivedMessage.getDistance(), lastReceivedMessage.getSteps(),
                                lastReceivedMessage.getCalories(),
                                FitVentureStart.currentUser.getUsername()
                        );
                        // save the data into the JSON file
                        FileHandler.jsonSerializer(FitVentureStart.jsonPath, FitVentureStart.fitVenture);
                        System.out.println("Data saved");
                    } else if (topic.equals(raceTopic)) {
                        lastReceivedRaceMessage = mapper.readValue(message.toString(), RaceStats.class);
                        // save the data into the HashMap
                        FitVentureStart.fitVenture.saveRaceStatsData(
                                lastReceivedRaceMessage.getStartTime(), lastReceivedRaceMessage.getEndTime(),
                                lastReceivedRaceMessage.getRaceDuration(),
                                lastReceivedMessage.getDistance(),
                                lastReceivedRaceMessage.getSteps(), lastReceivedRaceMessage.getCalories(),
                                FitVentureStart.currentUser.getUsername()
                        );
                        // save the data into the JSON file
                        FileHandler.jsonSerializer(FitVentureStart.jsonPath, FitVentureStart.fitVenture);
                        System.out.println("Race data saved");
                    } else if (topic.equals(tempAndHumTopic)) {
                        lastReceivedtempHumidityData = mapper.readValue(message.toString(), TempHumidityData.class);
                        // create an instance of TempHumidityData class and set the temperature and humidity values to the values from the message
                        TempHumidityData tempHumidityData = TempHumidityData.getInstance();
                        tempHumidityData.setTemperature(lastReceivedtempHumidityData.getTemperature());
                        tempHumidityData.setHumidity(lastReceivedtempHumidityData.getHumidity());
                    }
                }

                // Method to see that delivery is completed
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Complete");
                }
            });
            // Subscribe to different topics to receive message
            client.subscribe(mainTopic);
            client.subscribe(raceTopic);
            client.subscribe(tempAndHumTopic);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }
}