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

    // Method to get instance of TempHumidityData class
    // if the object would be null, it would create a new instance of TempHumidityData
    // this is needed because once we get the data from the sensor,
    // we need to store it in the instance of TempHumidityData,
    // and display it on the MainDashboard scene
    public static TempHumidityData getInstance() {
        if (instanceOfTempAndHumData == null) {
            instanceOfTempAndHumData = new TempHumidityData();
        }
        return instanceOfTempAndHumData;
    }

    //#region TempHumidityData Class Get Methods
    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
    //#endregion

    //#region TempHumidityData Class Set Methods
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
    //#endregion
}