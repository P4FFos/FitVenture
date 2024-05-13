package fitVenture.ui;

import fitVenture.backend.FitVenture;
import fitVenture.backend.goal.RunningGoal;
import fitVenture.backend.goal.WeightGoal;
import fitVenture.backend.utils.Current_Date;
import fitVenture.backend.utils.FileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static javafx.scene.paint.Color.rgb;

public class GoalsController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    private CategoryAxis xAxis; // xAxis of the chart
    private NumberAxis yAxis; // yAxis of the chart
    private BarChart barChart; // Barchart initialisation
    private ArrayList<Integer> runGoalList; // holds runGoals
    private String choiceOfGraph = "";

    @FXML
    private BorderPane borderPane; // reference to the BorderPane in the fxml
    @FXML
    private ObservableList observableList; // Observable reference for observable object
    @FXML
    private TextField userWeightGoalValue;// text field where user inputs Weight Goal in KG
    @FXML
    private TextField userRunGoalValue;// text field where user inputs Run Goal in KM
    @FXML
    private VBox runVBoxContainer; // VBox with all existing running goals
    @FXML
    private VBox weightVBoxContainer;//VBox with all existing weight goals
    @FXML
    private Label weightGoalCounter; // Label to display the number of completed weight goals
    @FXML
    private Label runGoalCounter; // Label to display the number of completed run goals

    private ArrayList<HBox> weightGoalArrayList;
    private ArrayList<String> listOfWeightKeys;
    private ArrayList<HBox> runGoalArrayList;
    private ArrayList<String> listOfRunKeys;

    // to get the weight goals of the user
    private HashMap<String, WeightGoal> weightGoalHashMap = FitVentureStart.currentUser.getWeightGoal();
    private HashMap<String, RunningGoal> runGoalHashMap = FitVentureStart.currentUser.getRunningGoal();

    // FitVenture object used for JSON serializer
    FitVenture fitVenture = FitVentureStart.fitVenture;

    // this method adds the created goal to the hashMap of the user
    public void addWeightGoal(ActionEvent event) throws Exception {
        // gets date today to assign it to the new goal
        String goalCreationDate = Current_Date.getDateToday(new Date());

        // checks if the entered value is not null
        // saves new goal into the HashMap
        if (userWeightGoalValue.getText() != null) {
            try {
                double weightGoalInKg = Double.parseDouble(userWeightGoalValue.getText());
                WeightGoal weightGoal = new WeightGoal(weightGoalInKg);
                FitVentureStart.currentUser.addWeightGoal(goalCreationDate, weightGoal);
                FileHandler.jsonSerializer(FitVentureStart.jsonPath, fitVenture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // call the method to see all goals
        viewWeightGoalsInProgress();
    }

    public void addRunGoal(ActionEvent event) throws Exception {
        // gets date today to assign it to the new goal

        String goalCreationDate = Current_Date.getDateToday(new Date());
        // checks if the entered value is not null
        // saves new goal into the HashMap
        if (userRunGoalValue.getText() != null) {
            try {
                double runGoalInKM = Double.parseDouble(userRunGoalValue.getText());
                RunningGoal runningGoal = new RunningGoal(runGoalInKM);
                FitVentureStart.currentUser.addRunGoal(goalCreationDate, runningGoal);
                FileHandler.jsonSerializer(FitVentureStart.jsonPath, fitVenture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        viewRunGoalsInProgress();
    }

    // the method is responsible for showing progress to the user in the progressBar
    public void viewWeightGoalsInProgress() {
        weightVBoxContainer.getChildren().clear(); // Clear all objects from the container
        weightGoalArrayList = new ArrayList<>();
        listOfWeightKeys = new ArrayList<>();

        weightGoalHashMap.forEach((goalCreationDate, goalValue) -> { // for each Weight object do the fallowing
            double goal = goalValue.getGoalInCalories();
            double progressToGoal = FitVentureStart.currentUser.getTotalBurnedCalories(goalCreationDate);

            if (goal > progressToGoal) { // check if the goal was completed
                // settings for progressBar for the user
                ProgressBar weightProgressBar = new ProgressBar();
                weightProgressBar.setPrefWidth(330);
                weightProgressBar.setPrefHeight(50);
                weightProgressBar.setStyle("-fx-accent: #a9a9df;");

                //settings for the goalLabel
                Label weightGoalInCaloriesLabel = new Label();
                weightGoalInCaloriesLabel.setPrefHeight(50);
                weightGoalInCaloriesLabel.setStyle("-fx-font-size: 15px;");
                weightGoalInCaloriesLabel.setPrefWidth(220);
                weightGoalInCaloriesLabel.setText("You burned: " + progressToGoal + " KCal");

                //settings for the progressLabel
                Label weightProgressInCaloriesLabel = new Label();
                weightProgressInCaloriesLabel.setPrefHeight(50);
                weightProgressInCaloriesLabel.setPrefWidth(220);
                weightProgressInCaloriesLabel.setStyle("-fx-font-size: 15px;");
                weightProgressInCaloriesLabel.setText(" The goal is: " + goal + " KCal");

                // Setting foe the HBox that holds the progressbar and both Labels
                HBox hBoxWeight = new HBox();
                hBoxWeight.setPrefWidth(700);
                hBoxWeight.setSpacing(10);

                weightProgressBar.setProgress(progressToGoal / goal); // set the value of the progressbar
                hBoxWeight.getChildren().addAll(weightProgressBar, weightGoalInCaloriesLabel, weightProgressInCaloriesLabel); // add three objects to the hBox
                sortWeightArray(hBoxWeight, goalCreationDate); // Making sure that the current goal in progress (bar) is always on the top of the list of other bars
            }
        });
        weightVBoxContainer.getChildren().addAll(weightGoalArrayList);// add everything to the container that is reserved a space in the fxml file
        weightGoalCounter.setText("You have completed " + doneWeightGoalCounter() +" weightloss goals."); //Displays the number of completed weight goals
    }

    // the method is responsible for showing progress to the user in the progressBar
    public void viewRunGoalsInProgress() {
        runVBoxContainer.getChildren().clear(); // Clear all objects from the container
        runGoalArrayList = new ArrayList<>();
        listOfRunKeys = new ArrayList<>();

        runGoalHashMap.forEach((goalCreationDate, goalValue) -> { // for each Run object do the following
            double goal = goalValue.getRunGoalInM();
            double progressToGoal = FitVentureStart.currentUser.getTotalRanDistance(goalCreationDate);

            if (goal > progressToGoal) { // check if the goal was completed
                // settings for progressBar for the user
                ProgressBar runProgressBar = new ProgressBar();
                runProgressBar.setPrefWidth(330);
                runProgressBar.setPrefHeight(50);
                runProgressBar.setStyle("-fx-accent: #a9a9df;");

                //settings for the runGoalLabel
                Label runGoalInMetersLabel = new Label();
                runGoalInMetersLabel.setPrefHeight(50);
                runGoalInMetersLabel.setStyle("-fx-font-size: 15px;");
                runGoalInMetersLabel.setPrefWidth(220);
                runGoalInMetersLabel.setText("You ran: " + progressToGoal + " M");

                //settings for the progressLabel
                Label runProgressInMetersLabel = new Label();
                runProgressInMetersLabel.setPrefHeight(50);
                runProgressInMetersLabel.setPrefWidth(220);
                runProgressInMetersLabel.setStyle("-fx-font-size: 15px;");
                runProgressInMetersLabel.setText(" The goal is: " + goal + " M");
                // Setting foe the HBox that holds the progressbar and both Labels
                HBox hBoxRun = new HBox();
                hBoxRun.setPrefWidth(700);
                hBoxRun.setSpacing(10);

                runProgressBar.setProgress(progressToGoal / goal); // set the value of the progressbar
                hBoxRun.getChildren().addAll(runProgressBar, runGoalInMetersLabel, runProgressInMetersLabel); // add three objects to the hBox
                sortRunArray(hBoxRun, goalCreationDate); // Making sure that the current goal in progress (bar) is always on the top of the list of other bars
            }
        });
        runVBoxContainer.getChildren().addAll(runGoalArrayList);// add everything to the container that is reserved a space in the fxml file
        runGoalCounter.setText("You have completed " + doneRunGoalCounter() +" running goals."); //Displays the number of completed run goals
    }

    // This method is responsible for sorting HBox objects by date
    private void sortWeightArray(HBox hBoxWeight, String goalCreationDate) {
        // If the list is empty, simply add the HBox and its key
        if (weightGoalArrayList.isEmpty()) {
            weightGoalArrayList.add(hBoxWeight);
            listOfWeightKeys.add(goalCreationDate);
        } else {
            int lastIndex = listOfWeightKeys.size() - 1;
            int currentGoalKey = Current_Date.getIntegerOfSpecificDate(listOfWeightKeys.get(lastIndex));
            int newGoalKey = Current_Date.getIntegerOfSpecificDate(goalCreationDate);

            // If the new key is greater than the current last key,
            // add the HBox and its key at the end
            if (newGoalKey > currentGoalKey) {

                weightGoalArrayList.add(hBoxWeight);
                listOfWeightKeys.add(goalCreationDate);
            } else {
                // Otherwise, find the correct position to insert the new key
                int indexToInsert = 0;
                while (indexToInsert <= lastIndex &&
                        Current_Date.getIntegerOfSpecificDate(listOfWeightKeys.get(indexToInsert)) < newGoalKey) {
                    indexToInsert++;
                }

                // Insert the HBox and its key at the correct position
                weightGoalArrayList.add(indexToInsert, hBoxWeight);
                listOfWeightKeys.add(indexToInsert, goalCreationDate);
            }
        }
    }

    // This method is responsible for sorting HBox objects by date
    private void sortRunArray(HBox hBoxRun, String goalCreationDate) {
        // If the list is empty, simply add the HBox and its key
        if (runGoalArrayList.isEmpty()) {
            runGoalArrayList.add(hBoxRun);
            listOfRunKeys.add(goalCreationDate);
        } else {
            int lastIndex = listOfRunKeys.size() - 1;
            int currentGoalKey = Current_Date.getIntegerOfSpecificDate(listOfRunKeys.get(lastIndex));
            int newGoalKey = Current_Date.getIntegerOfSpecificDate(goalCreationDate);

            // If the new key is greater than the current last key,
            // add the HBox and its key at the end
            if (newGoalKey > currentGoalKey) {

                runGoalArrayList.add(hBoxRun);
                listOfRunKeys.add(goalCreationDate);
            } else {
                // Otherwise, find the correct position to insert the new key
                int indexToInsert = 0;
                while (indexToInsert <= lastIndex &&
                        Current_Date.getIntegerOfSpecificDate(listOfRunKeys.get(indexToInsert)) < newGoalKey) {
                    indexToInsert++;
                }

                // Insert the HBox and its key at the correct position
                runGoalArrayList.add(indexToInsert, hBoxRun);
                listOfWeightKeys.add(indexToInsert, goalCreationDate);
            }
        }
    }

    // method to count the number of completed weight goals
    public int doneWeightGoalCounter() {
        int weightGoalCounter = 0; //Initialises goal counter
        for (Map.Entry<String, WeightGoal> entry : weightGoalHashMap.entrySet()) { //Iterates through the weight goals
            double goal = entry.getValue().getGoalInCalories(); //Gets the goal value
            double progressToGoal = FitVentureStart.currentUser.getTotalBurnedCalories(entry.getKey()); //Gets the progress to the goal
            if (goal <= progressToGoal) { //Checks if the goal is completed
                weightGoalCounter++;
            }
        }
        return weightGoalCounter;
    }

    // method to count the number of completed run goals
    public int doneRunGoalCounter() {
        int runGoalCounter = 0; //Initialises goal counter
        for (Map.Entry<String, RunningGoal> entry : runGoalHashMap.entrySet()) { //Iterates through the run goals
            double goal = entry.getValue().getRunGoalInM(); //Gets the goal value
            double progressToGoal = FitVentureStart.currentUser.getTotalRanDistance(entry.getKey()); //Gets the progress to the goal
            if (goal <= progressToGoal) { //Checks if the goal is completed
                runGoalCounter++;
            }
        }
        return runGoalCounter;
    }

    // button to return back to the MainDashboard
    public void returnBackToMain(ActionEvent event) throws IOException {
        // loads LoginRegistrationScene once user pressed the "return back" button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDashboardScene.fxml"));
        root = loader.load();

        MainDashboardController mainDashboardController = loader.getController();
        mainDashboardController.showChart();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // the method shows completed goals during the week
    public void weekChart() {
        xAxis = new CategoryAxis(); // create object of CategoryAxis which is XAxis of the graph
        xAxis.setLabel("Days"); // setting the label on the X side

        yAxis = new NumberAxis();// create object of NumberAxis which is Yaxis of the graph
        yAxis.setLabel("Goals");// setting the label on the Y side

        // a list of values to be used for XAxis.
        List numbersList = List.of(
                "1", "2", "3", "4", "5", "6", "7"
        );

        observableList = FXCollections.observableList(numbersList); // create observable object
        xAxis.setCategories(observableList); // set the observable to the XAxis

        ArrayList weekList = getWeekData(); // get week data for completed goals
        barChart = new BarChart(xAxis, yAxis); // create the barChart object
        addData(weekList, 1); // add data to the chart

        barChart.setMaxHeight(935); // set the value of maxHeight of barchart, since it's in the borderPane
        barChart.setMaxWidth(1867); // set the maxWidth of the barChart, since it's in the borderPane
        borderPane.setCenter(barChart); // set the barchart to the center of the borderPane
    }

    public void monthChart() {
        xAxis = new CategoryAxis();// create object of CategoryAxis which is XAxis of the graph
        xAxis.setLabel("Days"); // setting the label on the X side

        yAxis = new NumberAxis(); //create object of NumberAxis which is Yaxis of the graph
        yAxis.setLabel("Goals"); // setting the label on the Y side

        List numbersList = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        ); // a list of values to be used for XAxis.

        observableList = FXCollections.observableList(numbersList); // create the observable object
        xAxis.setCategories(observableList); // set the observable to the XAxis

        ArrayList monthList = getMonthData(); // get data for the month period
        barChart = new BarChart(xAxis, yAxis); // create barChart
        addData(monthList, 1); // add data to the chart

        barChart.setMaxHeight(935); // set the maxHeight of the barChart, since it's in the borderPane
        barChart.setMaxWidth(1867); // set the maxWidth of the barchart, since it's in the borderPane
        borderPane.setCenter(barChart); // set the barchart in the center of the borderPane
    }

    // calls different methods for charts based on the user choose
    public void showChart() {
        switch (choiceOfGraph) {
            case "weekly":
                weekChart();
                break;
            case "monthly":
                monthChart();
                break;
            default:
                weekChart();
                break;
        }
    }

    // method to add data to the chart
    public void addData(ArrayList<Integer> list, int startDay) {
        // creation of XYChart that is used by barChart to map x and y Axis of the graph
        XYChart.Series<String, Number> weightGoal = new XYChart.Series<>();  // XYChart object for weightGoal
        weightGoal.setName("WeightGoal/Time");

        XYChart.Series<String, Number> runGoal = new XYChart.Series<>(); // XYChart object for runGoal
        runGoal.setName("RunGoal/Time");

        int sizeOfList = list.size(); // length of the list which contains data
        int avoidNullPointer = 0; // this value helps in avoiding a nullPointerException that can be caused by some graphs starting from 0 and others from 1

        if (startDay > 0) { // checking if the charts starts from 1 or zero
            sizeOfList = startDay + sizeOfList; // making sure that the last value in the list is reached
            avoidNullPointer = startDay; // setting the starting position of the chart
        }
        for (int i = startDay; i < sizeOfList; i++) { // a loop that goes from start to the length of the list the add x and y values to XYCharts.
            weightGoal.getData().add(new XYChart.Data<>(String.valueOf(i), list.get(i - avoidNullPointer)));
            runGoal.getData().add(new XYChart.Data<>(String.valueOf(i), runGoalList.get(i - avoidNullPointer)));
        }

        barChart.getData().addAll(weightGoal, runGoal); // adding the XYCharts values to the barchart
    }

    // method to display week data on the chart
    public ArrayList getWeekData() {
        // to store weight goals of the user
        HashMap<String, WeightGoal> mapOfWeightGoals = FitVentureStart.currentUser.getWeightGoal();
        // to store run goals of the user
        HashMap<String, RunningGoal> mapOfRunGoals = FitVentureStart.currentUser.getRunningGoal();

        // array of integers to store the progress of weight goals of the user
        Integer[] weightArray = new Integer[7];
        for (int i = 0; i < weightArray.length; i++) {
            weightArray[i] = 0;
        }

        // array of integers to store the progress of run goals of the user
        Integer[] runArray = new Integer[7];
        for (int i = 0; i < runArray.length; i++) {
            runArray[i] = 0;
        }

        int totalDays = 7;
        int currentDate = Current_Date.getDateTodayAsInteger(); // getting today's date as an integer

        // goes through each weight goal of the user and checks if its completed
        mapOfWeightGoals.forEach((goalCreationDate, goalValue) -> {
            double goal = goalValue.getGoalInCalories(); // get the goal value
            // get total calories burned from a specific data to check later if goal is completed
            double doneProgress = FitVentureStart.currentUser.getTotalBurnedCalories(goalCreationDate);

            if (goal <= doneProgress) { // checks if the goal was completed
                int daysFromCompletion = currentDate - Current_Date.getIntegerOfSpecificDate(goalCreationDate);
                if (daysFromCompletion < totalDays) { // checks if the completion was within 7 days
                    weightArray[daysFromCompletion] += 1; // add the progress to the array
                }
            }
        });

        // goes through each run goal of the user and checks if its completed
        mapOfRunGoals.forEach((goalCreationDate, goalValue) -> {
            double goal = goalValue.getRunGoalInM(); // get the goal value
            // get total distance ran from a specific data to check later if goal is completed
            double doneProgress = FitVentureStart.currentUser.getTotalRanDistance(goalCreationDate);

            if (goal <= doneProgress) { // checks if the goal was completed
                int daysFromCompletion = currentDate - Current_Date.getIntegerOfSpecificDate(goalCreationDate);
                if (daysFromCompletion < totalDays) { // checks if the completion was in last 7 days
                    runArray[daysFromCompletion] += 1; // add the progress to the array
                }
            }
        });

        // this is to remove a null value from the list.
        ArrayList<Integer> emptyListForWeightArray = new ArrayList<>();
        runGoalList = new ArrayList<>();

        for (int i = 0; i < totalDays; i++) {
            if (weightArray[i] != null) {
                emptyListForWeightArray.add(weightArray[i]);
                runGoalList.add(runArray[i]);
            } else {
                emptyListForWeightArray.add(0);
                runGoalList.add(0);
            }
        }
        return emptyListForWeightArray;
    }

    // method to display month data on the chart
    public ArrayList getMonthData() {
        // to store weight goals of the user
        HashMap<String, WeightGoal> mapOfWeightGoals = FitVentureStart.currentUser.getWeightGoal();
        // to store run goals of the user
        HashMap<String, RunningGoal> mapOfRunGoals = FitVentureStart.currentUser.getRunningGoal();

        // array of integers to store the progress of weight goals of the user
        Integer[] weightArray = new Integer[31];
        for (int i = 0; i < weightArray.length; i++) {
            weightArray[i] = 0;
        }

        // array of integers to store the progress of run goals of the user
        Integer[] runArray = new Integer[31];
        for (int i = 0; i < runArray.length; i++) {
            runArray[i] = 0;
        }

        int totalDays = 7;
        int currentDate = Current_Date.getDateTodayAsInteger(); // getting today's date as an integer

        // goes through each weight goal of the user and checks if its completed
        mapOfWeightGoals.forEach((goalCreationDate, goalValue) -> {
            double goal = goalValue.getGoalInCalories(); // get the goal value
            // get total calories burned from a specific data to check later if goal is completed
            double doneProgress = FitVentureStart.currentUser.getTotalBurnedCalories(goalCreationDate);

            if (goal <= doneProgress) { // checks if the goal was completed
                int daysFromCompletion = currentDate - Current_Date.getIntegerOfSpecificDate(goalCreationDate);
                if (daysFromCompletion < totalDays) { // checks if the completion was within 31 days
                    weightArray[daysFromCompletion] += 1; // add the progress to the array
                }
            }
        });

        // goes through each run goal of the user and checks if its completed
        mapOfRunGoals.forEach((goalCreationDate, goalValue) -> {
            double goal = goalValue.getRunGoalInM(); // get the goal value
            // get total distance ran from a specific data to check later if goal is completed
            double doneProgress = FitVentureStart.currentUser.getTotalRanDistance(goalCreationDate);

            if (goal <= doneProgress) { // checks if the goal was completed
                int daysFromCompletion = currentDate - Current_Date.getIntegerOfSpecificDate(goalCreationDate);
                if (daysFromCompletion < totalDays) { // checks if the completion was within 31 days
                    runArray[daysFromCompletion] += 1; // add the progress to the array
                }
            }
        });

        ArrayList<Integer> emptyListForWeightGoal = new ArrayList<>();
        runGoalList = new ArrayList<>();

        for (int i = 0; i < totalDays; i++) {
            if (weightArray[i] != null) {
                emptyListForWeightGoal.add(weightArray[i]);
                runGoalList.add(runArray[i]);
            } else {
                emptyListForWeightGoal.add(0);
                runGoalList.add(0);
            }
        }
        return emptyListForWeightGoal;
    }

    // display week chart when user clicks on the Week Chart button
    public void openWeekChart() throws Exception {
        choiceOfGraph = "weekly";
        showChart();
    }

    // display month chart when user clicks on the Month Chart button
    public void openMonthChart() throws Exception {
        choiceOfGraph = "monthly";
        showChart();
    }

    // update the chart when user clicks on the Update Chart button
    public void updateChart(MouseEvent event) {
        showChart();
    }
}
