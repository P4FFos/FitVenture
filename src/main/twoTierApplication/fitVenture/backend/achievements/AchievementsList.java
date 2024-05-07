package fitVenture.backend.achievements;

import java.util.HashMap;
import java.util.Map;

public class AchievementsList {
    private HashMap<String, Achievement> uncompletedAchievements;
    private HashMap<String, Achievement> completedAchievements;
    
    //#region Constructor
    public AchievementsList(double distance, double calories){
        uncompletedAchievements = new HashMap<>();
        // Insert all achievements
        uncompletedAchievements.put("Burj Khalifa", new Achievement("""
        Congratulations, you have walked the distance equivalent to 
        the height of the Burj Khalifa. Initially the building was supposed 
        to be 705 meters tall, but the building plans were later changed to add more floors.""", 828, "Distance"));

        checkCompletedAchievements(distance, calories);
    }
    //#endregion
    
    //#region Getters
    public String getDescription(String achievement){
        if (uncompletedAchievements.containsKey(achievement)) return this.uncompletedAchievements.get(achievement).getDescription();
        else return this.completedAchievements.get(achievement).getDescription();
    }
    
    public double getCompletionRequirement(String achievement){
        if (uncompletedAchievements.containsKey(achievement)) return this.uncompletedAchievements.get(achievement).getCompletionRequirement();
        else return this.completedAchievements.get(achievement).getCompletionRequirement();
    }
    
    public boolean getCompletionStatus(String achievement){
        if (uncompletedAchievements.containsKey(achievement)) return this.uncompletedAchievements.get(achievement).getCompletionStatus();
        else return this.completedAchievements.get(achievement).getCompletionStatus();
    }

    public HashMap<String, Achievement> getUncompletedAchievements(){
        return uncompletedAchievements;
    }

    public HashMap<String, Achievement> getCompletedAchievements(){
        return completedAchievements;
    }

    public HashMap<String, Achievement> getAllAchievements(){
        HashMap<String, Achievement> allAchievements = new HashMap<>();
        allAchievements.putAll(uncompletedAchievements);
        allAchievements.putAll(completedAchievements);
        return allAchievements;
    }

    //#endregion

    //#region Setters
    public void setDescription(String achievement, String description){
        if (uncompletedAchievements.containsKey(achievement)) this.uncompletedAchievements.get(achievement).setDescription(description);
        else this.completedAchievements.get(achievement).setDescription(description);
    }
    
    public void setCompletionRequirement(String achievement, double completionRequirement){
        if (uncompletedAchievements.containsKey(achievement)) this.uncompletedAchievements.get(achievement).setCompletionRequirement(completionRequirement);
        else this.completedAchievements.get(achievement).setCompletionRequirement(completionRequirement);
    }
    
    public void setCompletionStatus(String achievement, boolean completionStatus){
        if (uncompletedAchievements.containsKey(achievement)) this.uncompletedAchievements.get(achievement).setCompletionStatus(completionStatus);
        else this.completedAchievements.get(achievement).setCompletionStatus(completionStatus);
    }
    //#endregion

    public void checkCompletedAchievements(double distance, double calories){
        for (Map.Entry<String, Achievement> entry : uncompletedAchievements.entrySet()){
            Achievement achievement = entry.getValue();
            String name = entry.getKey();
            if (achievement.getTag().equals("Distance") && achievement.checkIfRequirementsAreMet(distance)){
                this.completedAchievements.put(name, achievement);
                this.uncompletedAchievements.remove(name);
            }
            else if (achievement.getTag().equals("Calories") && achievement.checkIfRequirementsAreMet(calories)){
                this.completedAchievements.put(name, achievement);
                this.uncompletedAchievements.remove(name);
            }
        }
    }

    public boolean checkIfRequirementsAreMet(String achievement, double stat){ // If the requirements are met -> Set this achivement as complete
        if (uncompletedAchievements.containsKey(achievement)) return this.uncompletedAchievements.get(achievement).checkIfRequirementsAreMet(stat);
        else return this.completedAchievements.get(achievement).checkIfRequirementsAreMet(stat);
    }
}
