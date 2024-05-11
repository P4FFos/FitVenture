package fitVenture.backend.stats;

public class RaceStats extends Stats{
    // raceStats class attributes
    private String startTime;
    private String endTime;
    private String raceDuration;

    // Empty constructor used by Jackson for Json deserializing,
    // every time we run the application, the user object is created with the empty constructor
    public RaceStats() {
    }

    // Race stats class constructor
    public RaceStats(String startTime, String endTime, String raceDuration, String distance, String steps, String calories) {
        super(steps, distance, calories);
        this.startTime = startTime;
        this.endTime = endTime;
        this.raceDuration = raceDuration;
    }

    //#region RaceStats Class Get Methods
    public String getStartTime(){
        return this.startTime;
    }

    public String getEndTime(){
        return this.endTime;
    }

    public String getRaceDuration(){
        return this.raceDuration;
    }
    //#endregion

    //#region RaceStats Class Set Methods
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setRaceDuration(String raceDuration){
        this.raceDuration = raceDuration;
    }
    //#endregion
}
