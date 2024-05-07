package fitVenture.backend.tempAndHum;

public class TempHumidityData {
    // initialise instance of TempHumidityData to null
    private static TempHumidityData instanceOfTempAndHumData = null;

    // Attributes to store temperature and humidity
    private double temperature;
    private double humidity;

    // constructor to for temperature and humidity data class
    private TempHumidityData() {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    // Method to get instance of TempHumidityData class used in the MQTTSubscriber and MainDashboard classes
    public static TempHumidityData getInstance() {
        if (instanceOfTempAndHumData == null) {
            instanceOfTempAndHumData = new TempHumidityData();
        }
        return instanceOfTempAndHumData;
    }

    // get method for temperature
    public double getTemperature() {
        return temperature;
    }

    // set method for temperature
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    // get method for humidity
    public double getHumidity() {
        return humidity;
    }

    // set method for humidity
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}