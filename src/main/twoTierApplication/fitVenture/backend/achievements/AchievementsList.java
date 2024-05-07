package fitVenture.backend.achievements;

import java.util.HashMap;

public class AchievementsList {
    private HashMap<String, Achievement> listOfAchievements;

    public AchievementsList(){
        listOfAchievements = new HashMap<>();
    }

    public String getDescription(String achievement){
        return this.listOfAchievements.get(achievement).getDescription();
    }
    
    public double getCompletionRequirement(String achievement){
        return this.listOfAchievements.get(achievement).getCompletionRequirement();
    }
    
    public boolean getCompletionStatus(String achievement){
        return this.listOfAchievements.get(achievement).getCompletionStatus();
    }
    
    public void setDescription(String achievement, String description){
        this.listOfAchievements.get(achievement).setDescription(description);
    }
    
    public void setCompletionRequirement(String achievement, double completionRequirement){
        this.listOfAchievements.get(achievement).setCompletionRequirement(completionRequirement);
    }
    
    public void setCompletionStatus(String achievement, boolean completionStatus){
        this.listOfAchievements.get(achievement).setCompletionStatus(completionStatus);
    }
    
    public boolean checkIfRequirementsAreMet(String achievement, double steps){ // If the requirements are met -> Set this achivement as complete
        return this.listOfAchievements.get(achievement).checkIfRequirementsAreMet(steps);
    }
}
