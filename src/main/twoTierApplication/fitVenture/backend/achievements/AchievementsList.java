package fitVenture.backend.achievements;

import java.util.HashMap;
import java.util.Map;

public class AchievementsList {
    private HashMap<String, Achievement> uncompletedAchievements;
    private HashMap<String, Achievement> completedAchievements;
    
    //#region Constructor
    public AchievementsList(){
        uncompletedAchievements = new HashMap<>();
        completedAchievements = new HashMap<>();
        // Insert all achievements
        uncompletedAchievements.put("Burj Khalifa", new Achievement("""
        Congratulations, you have walked the distance equivalent to 
        the height of the Burj Khalifa. Initially the building was supposed 
        to be 705 meters tall, but the building plans were later changed to add more floors.""", 828, "Distance"));
        uncompletedAchievements.put("Great Pyramid Of Giza", new Achievement(""" 
            Congratulations, you have walked the distance equivalent to the height of the Pyramid of Giza. 
            Due to the removal of the limestone casing, the height of the pyramids has decreased 
            from 146.6 to 138.5 meters! ”
            """, 146000.6, "Distance"));
        uncompletedAchievements.put("Mount Everest", new Achievement(""" 
            Congratulations, you have walked the distance equivalent to the height of Mount Everest! 
            As the largest mountain in the world, it has many visitors wanting to reach its peak.
        """, 146000.6, "Distance"));
        uncompletedAchievements.put("Twisting Torso", new Achievement(""" 
            Congratulations, you have walked the distance equivalent to the height of the Turning Torso. 
            The Turning Torso was based on a white marble sculpture by Calatrava that was based on 
            the form of a twisting human being!
        """, 190, "Distance"));
        uncompletedAchievements.put("Small Dog", new Achievement(""" 
            Congratulations, you have lost the same amount of calories a small dog consumes daily! 
            Contrary to the rest of the animal kingdom, smaller dogs live longer than bigger ones!
        """, 350, "Calories"));
        uncompletedAchievements.put("Bald Eagle", new Achievement(""" 
            Congratulations, you have lost the same amount of calories a bald eagle consumes daily! 
            Contrary to its name, bald eagles are not actually bald!
        """, 550, "Calories"));
        uncompletedAchievements.put("Cheetah", new Achievement(""" 
            Congratulations, you have lost the same amount of calories a cheetah consumes daily! 
            Cheetahs are the fastest land animal in the world and can reach up to 104km/h when chasing
            their prey!
        """, 2200, "Calories"));


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
        HashMap<String, Achievement> temporaryHashMap = new HashMap<>(); //Create a temporary Hashmap to prevent the for each loop from breaking if a modification is made to the uncompletedAchievements Hashmap 
        temporaryHashMap.putAll(this.uncompletedAchievements);
        if (!uncompletedAchievements.isEmpty()){
            for (Map.Entry<String, Achievement> entry : temporaryHashMap.entrySet()){
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
    }
}
