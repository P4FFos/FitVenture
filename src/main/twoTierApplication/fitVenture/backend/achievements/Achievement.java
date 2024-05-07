package fitVenture.backend.achievements;

public class Achievement {
    private String description;
    private double completionRequirement;
    private boolean completionStatus;

    public Achievement(String description, double completionRequirement){
        this.description = description;
        this.completionRequirement = completionRequirement;
        this.completionStatus = false; // The achievements will be false at default
    }

    public String getDescription(){
        return this.description;
    }
    
    public double getCompletionRequirement(){
        return this.completionRequirement;
    }
    
    public boolean getCompletionStatus(){
        return this.completionStatus;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setCompletionRequirement(double completionRequirement){
        this.completionRequirement = completionRequirement;
    }
    
    public void setCompletionStatus(boolean completionStatus){
        this.completionStatus = completionStatus;
    }
    
    public boolean checkIfRequirementsAreMet(double steps){ // If the requirements are met -> Set this achivement as complete
        if (steps >= this.completionRequirement){
            this.completionStatus = true;
            return true;
        }
        else return false;
    }
    
}
