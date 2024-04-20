package fitVenture.backend;

import fitVenture.backend.stats.Stats;
import fitVenture.backend.utils.FileHandler;
import fitVenture.ui.FitVentureStart;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MQTTSubscriber {
    // attributes to initialise MQTT broker, client id and topic
    private static final String broker = "";
    private static final String clientId = "";
    private static final String topic = "";

    // method to subscribe to a topic
    public MQTTSubscriber() {
        try {
            // initialise MQTT client and connect to broker using broker and client id
            MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions connection = new MqttConnectOptions();
            client.connect(connection);
            System.out.println("Connected");

            // defines what will happen when the connection is lost, message is arrived and delivery is completed
            // this syntax is required by the library which we are using for the MQTT broker
            client.setCallback(new MqttCallback() {
                // method to show in case connection is lost
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection is lost");
                }

                // method save data from the terminal into the JSON file
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message is arrived");
                    if ("".equals(topic)) {
                        ObjectMapper mapper = new ObjectMapper();
                        Stats statsData = mapper.readValue(message.toString(), Stats.class);
                        FitVentureStart.fitVenture.saveStatsData(statsData.getDistance(), statsData.getSteps(), statsData.getCalories(), FitVentureStart.currentUser.getUsername());
                        FileHandler.jsonSerializer(FitVentureStart.jsonPath, FitVentureStart.fitVenture);
                    }
                }

                // method to see that delivery is completed
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Complete");
                }
            });
            client.subscribe(topic);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }
}