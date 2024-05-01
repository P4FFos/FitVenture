package fitVenture.backend.stats;

public class RaceStats extends Stats{
    private String startTime;
    private String endTime;
    private String raceDuration;

    // Empty constructor used by Jackson for Json deserializing
    public RaceStats() {
    }

    // Race stats class constructor
    public RaceStats(String steps, String distance, String calories, String startTime, String endTime, String raceDuration){
        super(steps, distance, calories);
        this.startTime = startTime;
        this.endTime = endTime;
        this.raceDuration = raceDuration;
    }

    // get methods for raceStats
    public String getStartTime(){
        return this.startTime;
    }

    public String getEndTime(){
        return this.endTime;
    }

    public String getRaceDuration(){
        return this.raceDuration;
    }

    // set methods for raceStats
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setRaceDuration(String raceDuration){
        this.raceDuration = raceDuration;
    }

}
