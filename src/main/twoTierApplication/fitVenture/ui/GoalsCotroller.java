package fitVenture.ui;

import fitVenture.backend.goal.WeightGoal;
import fitVenture.backend.utils.Current_Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;

public class GoalsCotroller {

    @FXML
    private TextField addWeightgoal;

    private String key;


    // this method adds the created goal to the hashMap of the user
    public void addGoal(ActionEvent event) throws Exception {
         // this is where the goal object is saved to the map. key is todays date including seconds ans minutes .

        key =Current_Date.getDateToday(new Date());
       if(addWeightgoal.getText()!=null){ //checking if the weight goal exists if not, the goal is added
         try {
           int value = Integer.parseInt(addWeightgoal.getText());
           WeightGoal weightGoal = new WeightGoal(value,0); // the progress always starts from zero
           FitVentureStart.currentUser.addWeightGoal(key,weightGoal);
         } catch (Exception e){
           System.out.println("the entered value is not a number ");
         }

       }

       // loading the weightLossGoal.fxm
        FXMLLoader loader = new FXMLLoader(getClass().getResource("weightLossGoal.fxml"));
        Parent root = loader.load();

        // instantiating weightLossGoalCongtoller for the use os progressBar creation and display inside of weightLossGoal page
        WeightLossGoalContoller weightLossGoalContoller = loader.getController();
        weightLossGoalContoller.progressBarProgress(key);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }





  }
