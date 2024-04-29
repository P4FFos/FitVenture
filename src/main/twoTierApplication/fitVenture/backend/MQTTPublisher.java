package fitVenture.backend;

import org.eclipse.paho.client.mqttv3.*;
import fitVenture.ui.FitVentureStart;


public class MQTTPublisher {
    private static final String broker = "tcp://broker.hivemq.com:1883";
    private static final String clientId = "JavaPublisher";
    private static final String weightHeightTopic = "fitVenture/sensor/accelerometer/weight&height";

    private static final String userWeight = FitVentureStart.currentUser.getWeight();
    private static final String userHeight = FitVentureStart.currentUser.getHeight();

    //private static final String[] userWeightAndHeight = {userWeight, userHeight};

    public MQTTPublisher() {
        try {
            MqttClient client = new MqttClient(broker, clientId);
            client.connect();
            System.out.println("MQTTPublisher has been connected!");



            client.publish(weightHeightTopic, userHeight.getBytes(), 0, false);
            System.out.println("User Height has been published!");
            client.publish(weightHeightTopic, userWeight.getBytes(), 0, false);
            System.out.println("User Weight has been published!");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}