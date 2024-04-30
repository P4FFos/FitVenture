package fitVenture.backend;

import fitVenture.backend.stats.Stats;
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
    private static final String mainTopic = "fitVenture/sensor/accelerometer/data";
    private static final String raceTopic = "fitVenture/sensor/accelerometer/raceData";

    // Variable to store the last received message
    private Stats lastReceivedMessage;
    private RaceStats lastReceivedRaceMessage;

    // Method to subscribe to a topic
    public MQTTSubscriber() {
        try {
            // Initialise MQTT client and connect to broker using broker and client id
            MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connection = new MqttConnectOptions();
            client.connect(connection);
            System.out.println("Connected");

            // Defines what will happen when the connection is lost, message is arrived and delivery is completed
            // This syntax is required by the library which we are using for the MQTT broker
            client.setCallback(new MqttCallback() {
                // Method to show in case connection is lost
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection is lost");
                }

                // Method to save message from the MQTT broker into the JSON file
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message is arrived");
                    ObjectMapper mapper = new ObjectMapper();
                    lastReceivedMessage = mapper.readValue(message.toString(), Stats.class);
                    if (topic.equals(mainTopic)) {
                        FitVentureStart.fitVenture.saveStatsData(
                                lastReceivedMessage.getDistance(), lastReceivedMessage.getSteps(),
                                lastReceivedMessage.getCalories(),
                                FitVentureStart.currentUser.getUsername()
                        );
                        FileHandler.jsonSerializer(FitVentureStart.jsonPath, FitVentureStart.fitVenture);
                        System.out.println("Data saved");
                    } else if (topic.equals(raceTopic)) {
                        FitVentureStart.fitVenture.saveRaceStatsData(
                                lastReceivedRaceMessage.getStartTime(), lastReceivedRaceMessage.getEndTime(),
                                lastReceivedRaceMessage.getRaceDuration(),
                                lastReceivedMessage.getDistance(),
                                lastReceivedRaceMessage.getSteps(), lastReceivedRaceMessage.getCalories(),
                                FitVentureStart.currentUser.getUsername()
                        );
                        FileHandler.jsonSerializer(FitVentureStart.jsonPath, FitVentureStart.fitVenture);
                        System.out.println("Race data saved");
                    }
                }

                // Method to see that delivery is completed
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Complete");
                }
            });
            client.subscribe(mainTopic);
            client.subscribe(raceTopic);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }
}