package fitVenture.backend.achievements;

public class Achievement {
    private String description;
    private double completionRequirement;
    private boolean completionStatus;

    //#region Constructor
    public Achievement(String description, double completionRequirement){
        this.description = description;
        this.completionRequirement = completionRequirement;
        this.completionStatus = false; // The achievements will be false at default
    }
    //#endregion

    //#region getters
    public String getDescription(){
        return this.description;
    }
    
    public double getCompletionRequirement(){
        return this.completionRequirement;
    }
    
    public boolean getCompletionStatus(){
        return this.completionStatus;
    }
    //#endregion
    
    //#region setters
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setCompletionRequirement(double completionRequirement){
        this.completionRequirement = completionRequirement;
    }
    
    public void setCompletionStatus(boolean completionStatus){
        this.completionStatus = completionStatus;
    }
    //#endregion

    
    public boolean checkIfRequirementsAreMet(double stat){ // If the requirements are met -> Set this achievement as complete
        if (stat >= this.completionRequirement){
            this.completionStatus = true;
            return true;
        }
        else return false;
    }
    
}
