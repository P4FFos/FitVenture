package fitVenture.backend;

import org.eclipse.paho.client.mqttv3.*;
import fitVenture.ui.FitVentureStart;


public class MQTTPublisher {

    private static final String broker = "tcp://broker.hivemq.com:1883";
    private static final String clientId = "JavaPublisher";
    private static final String weightHeightTopic = "fitVenture/application/weight&height";

    //Get the currently logged in users weight and height
    private static final String userWeight = FitVentureStart.currentUser.getWeight();
    private static final String userHeight = FitVentureStart.currentUser.getHeight();

    //Put them in a JSON format for the payload.
    private static final String userWeightAndHeight = "{\"userWeight\": "  + userWeight + "," + " \"userHeight\": " + userHeight + "}";

    public MQTTPublisher() {
        try {
            MqttClient client = new MqttClient(broker, clientId);
            client.connect();
            System.out.println("MQTTPublisher has been connected!");


            //Publish the payload as bytes to the topic.
            client.publish(weightHeightTopic, userWeightAndHeight.getBytes(), 0, false);
            System.out.println("User Weight and Height has been published!");
            System.out.println(userWeightAndHeight);
            
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}