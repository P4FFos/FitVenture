package fitVenture.ui;

import fitVenture.backend.stats.Stats;
import fitVenture.backend.utils.Current_Date;
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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class MainDashboardController {

    private CategoryAxis xAxis; // xAxis of the chart
    private NumberAxis yAxis; // Yaxis of the chart
    private BarChart barChart; //Barchart object

    private String choiceOfGraph = "";

    @FXML
    private BorderPane borderPane; // reference to the BorderPane in the fxml
    private ObservableList observableList; // Observable reference for observable object

    @FXML
    private Button updateCharButton;

    private ArrayList<Double> caloriesList; // a list to hold calorie values
    private ArrayList<Double> distanceList; // a list to hold distance values

    public void updateChartButton(MouseEvent event){
        showChart();
        System.out.println("Chart Updated");
    }

    public void openUserProfile(ActionEvent event) throws IOException { // method to be called if the user clicks on the userProfile button
        //loads MainDashboardScene one user pressed login button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileScene.fxml"));
        Parent root = loader.load(); // loading the userProfileScene.fxml

        UserProfileController userProfileController = loader.getController(); // getting user profile controller object
        userProfileController.showData(); // calling the showData method of showData method

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // getting the stage
        Scene scene = new Scene(root); // adding the parent to the scene
        stage.setScene(scene); // adding scene to the stage
        stage.show(); // showing the stage
    }
    public void openChallengesPage(ActionEvent event) throws IOException{
        //loads ChallengesScene once user pressed the "Challenges" button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChallengesScene.fxml"));
        Parent root = loader.load(); // loading the ChallengesScene.fxml

        ChallengesController challengesController = loader.getController(); // getting challenges controller object
        challengesController.showChallenges(); // calling the showData method of challengesController

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // getting the stage
        Scene scene = new Scene(root); // adding the parent to the scene
        stage.setScene(scene); // adding scene to the stage
        stage.show(); // showing the stage
    }

    public void dayChart() { // dayChart is responsible to display 24 hours chart
        xAxis = new CategoryAxis(); // create object of CategoryAxis which is XAxis of the graph
        xAxis.setLabel("Hours"); // setting label to 24 hours

        yAxis = new NumberAxis(); // create object of NumberAxis which is Yaxis of the graph
        yAxis.setLabel("Steps"); // setting the label

        List numbersList = List.of(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23"
        ); // a list of values to be used for XAxis.

        observableList = FXCollections.observableList(numbersList); // creating the observable object
        xAxis.setCategories(observableList); // setting the observable to the XAxis

        ArrayList dayList = getDayData(); // getting data for 24 hours
        barChart = new BarChart(xAxis, yAxis); // creation of barChart object
        addData(dayList, 0); // addition of the 24 hours data to the chart

        barChart.setMaxHeight(800); // setting the maxHeight of the chart
        barChart.setMaxWidth(1200); // setting the maxWidth of the chart
        borderPane.setCenter(barChart); // setting the barchart to the center of the borderPane
    }

    public void weekChart() { // a method that is responsible for weekChart
        xAxis = new CategoryAxis(); // create object of CategoryAxis which is XAxis of the graph
        xAxis.setLabel("Days"); //setting the label

        yAxis = new NumberAxis();//create object of NumberAxis which is Yaxis of the graph
        yAxis.setLabel("Steps");//setting the label

        List numbersList = List.of(
                "1", "2", "3", "4", "5", "6", "7"
        ); // a list of values to be used for XAxis.

        observableList = FXCollections.observableList(numbersList); // creating observable object
        xAxis.setCategories(observableList); // setting the observable to the XAxis

        ArrayList weekList = getWeekData(); // getting data for 7 days
        barChart = new BarChart(xAxis, yAxis); // creating the barChart object
        addData(weekList, 1); // adding data to the chart. 1 int value that is being parsed, is the indicator to show that the chart will count from 1 not zero.

        barChart.setMaxHeight(800); // setting the value of maxHeight of barchart
        barChart.setMaxWidth(1200); // setting the maxWidth of the barChart
        borderPane.setCenter(barChart); // setting the barChart in the center of the borderPane
    }

    public void monthChart() {
        xAxis = new CategoryAxis();// create object of CategoryAxis which is XAxis of the graph
        xAxis.setLabel("Weeks"); //setting the label

        yAxis = new NumberAxis(); //create object of NumberAxis which is Yaxis of the graph
        yAxis.setLabel("Steps");// setting the label

        List numbersList = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        ); // a list of values to be used for XAxis.

        observableList = FXCollections.observableList(numbersList); // creating the observable object
        xAxis.setCategories(observableList); // setting the observable to the XAxis

        ArrayList monthList = getMonthData(); // getting data for 31 days
        barChart = new BarChart(xAxis, yAxis); // creation of chart object
        addData(monthList, 1); // adding data to the chart. 1 int value that is being parsed, is the indicator to show that the chart will count from 1 not zero.

        barChart.setMaxHeight(800); // setting the maxHeight of the barChart
        barChart.setMaxWidth(1200); // setting the maxWidth of the barchart
        borderPane.setCenter(barChart); // setting the barchart in the center of the borderPane
    }

    public void showChart(){

        switch (choiceOfGraph) {
            case "daily":
                dayChart();
                break;

            case "weekly":
                weekChart();
                break;

            case "monthly":
                monthChart();
                break;

            default:
                dayChart();
                break;
        }
    }

    public void addData(ArrayList<Integer> list, int startDay) { // methods that is responsible for adding data
        // creation of XYChart that is used by barChart to map x and y Axis of the graph
        XYChart.Series<String, Number> steps = new XYChart.Series<>();  // XYChart object for steps
        steps.setName("Steps/Time");

        XYChart.Series<String, Number> distance = new XYChart.Series<>(); // XYChart object for distance
        distance.setName("Distance/Time");

        XYChart.Series<String, Number> calories = new XYChart.Series<>(); // XYChart for calories
        calories.setName("Calories/Time");

        int sizeOfList = list.size(); // length of the list containing data. the list is parsed
        int avoidNullPointer = 0; // this value helps in avoiding a nullPointerException that can be caused by some graphs starting from 0 and others from 1

        if (startDay > 0) { // checking if the charts starts from 1 or zero
            sizeOfList = startDay + sizeOfList; // making sure that the last value in the list is reached
            avoidNullPointer = startDay; // setting the starting position of the chart
        }
        for (int i = startDay; i < sizeOfList; i++) { // a loop that goes from start to the length of the list the add x and y values to XYCharts.
            steps.getData().add(new XYChart.Data<>(String.valueOf(i), list.get(i - avoidNullPointer)));
            calories.getData().add(new XYChart.Data<>(String.valueOf(i), caloriesList.get(i - avoidNullPointer)));
            distance.getData().add(new XYChart.Data<>(String.valueOf(i), distanceList.get(i - avoidNullPointer)));
        }

        barChart.getData().addAll(steps, calories, distance); // adding the XYCharts values to the barchart
    }

    public ArrayList getDayData() { // getting dayData
        // getting the object user who signed in
        HashMap<String, Stats> mapOfStats = FitVentureStart.currentUser.getStatsMap(); // retrieving their stats
        Integer[] stepsArray = new Integer[24];
        Double[] caloriesArray = new Double[24];
        Double[] distanceArray = new Double[24];

        mapOfStats.forEach((key, value) -> { // looping through all stats
            if (key.substring(0, 10).toLowerCase().equals(Current_Date.getDateToday(new Date()).substring(0, 10))) { // check if the dateKeyValue is equal to today's date.
                int currentHour = Integer.parseInt(key.substring(11, 13)); // take the hour of that day as integer
                Integer steps = Integer.parseInt(value.getSteps());
                double calories = Double.parseDouble(value.getCalories());
                double distance = Double.parseDouble(value.getDistance());

                // checking if there were other values that were set to the current our
                // then updates the value
                if (stepsArray[currentHour] != null) {
                    Integer updatedSteps = stepsArray[currentHour] + steps;
                    stepsArray[currentHour] = updatedSteps;
                    double updatedCalories = caloriesArray[currentHour] + calories;
                    caloriesArray[currentHour] = updatedCalories;
                    double updatedDistance = distanceArray[currentHour] + distance;
                    distanceArray[currentHour] = updatedDistance;
                } else {
                    // if nothing were registered for the particular hour, save the values in the hour.
                    stepsArray[currentHour] = steps;
                    caloriesArray[currentHour] = calories;
                    distanceArray[currentHour] = distance;
                }
            }
        });

        // this is to remove a null value from the list. Null values are causing issues in the chart.
        ArrayList<Integer> emptyList = new ArrayList<>();
        caloriesList = new ArrayList<>();
        distanceList = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            if (stepsArray[i] != null) {
                emptyList.add(stepsArray[i]);
                caloriesList.add(caloriesArray[i]);
                distanceList.add(distanceArray[i]);
            } else {
                // adding o to the index that holds null values
                emptyList.add(0);
                caloriesList.add(0.0);
                distanceList.add(0.0);
            }
        }
        return emptyList;
    }

    public ArrayList getWeekData() { // this method is responsible for getting weekData
        // getting stats of the current user
        HashMap<String, Stats> mapOfStats = FitVentureStart.currentUser.getStatsMap();
        Integer[] stepsArray = new Integer[7];
        double[] caloriesArray = new double[7];
        double[] distanceArray = new double[7];

        int totalDays = 7;
        int currentDate = Current_Date.getDateTodayAsInteger(); // getting today's date as an integer

        mapOfStats.forEach((key, value) -> { // looping though evert stat in the map
            int anotherDate = Current_Date.getIntegerOfSpecificDate(key); // getting the dateKey for the map as an integer
            int difference = currentDate - anotherDate; // the difference between today and when the date was created

            // referencing values of current stat in the loop
            Integer steps = Integer.parseInt(value.getSteps());
            double calories = Double.parseDouble(value.getCalories());
            double distance = Double.parseDouble(value.getDistance());

            // checking if there has been less than a week since the stat object was created
            if (difference < totalDays) {
                int index = Math.abs(difference - 6);
                if (stepsArray[index] != null) { // checking if there were saved data on that day to update
                    // updating data if they exist
                    int updatedSteps = stepsArray[index] + steps;
                    stepsArray[index] = updatedSteps;

                    double updatedCalories = caloriesArray[index] + calories;
                    caloriesArray[index] = updatedCalories;

                    double updatedDistance = distanceArray[index] + distance;
                    distanceArray[index] = updatedDistance;
                } else {
                    // adding data if there is nothing to update
                    stepsArray[index] = steps;
                    caloriesArray[index] = calories;
                    distanceArray[index] = distance;
                }
            }
        });

        // this is to remove a null value from the list. Null values are causing issues in the chart.
        ArrayList<Integer> emptyList = new ArrayList<>();
        caloriesList = new ArrayList<>();
        distanceList = new ArrayList<>();

        for (int i = 0; i < totalDays; i++) {
            if (stepsArray[i] != null) {
                emptyList.add(stepsArray[i]);
                caloriesList.add(caloriesArray[i]);
                distanceList.add(distanceArray[i]);
            } else {
                // adding o to the index that holds null values
                emptyList.add(0);
                caloriesList.add(0.0);
                distanceList.add(0.0);
            }
        }
        return emptyList;
    }

    public ArrayList getMonthData() { // this method is responsible for getting monthData
        // getting the map of stats for the current user
        HashMap<String, Stats> mapOfStats = FitVentureStart.currentUser.getStatsMap();
        Integer[] stepsArray = new Integer[31];
        double[] caloriesArray = new double[31];
        double[] distanceArray = new double[31];

        int totalDays = 31; // total days of a month
        int dateToday = Current_Date.getDateTodayAsInteger();

        mapOfStats.forEach((key, value) -> { // looping though the map of stats
            int anotherDate = Current_Date.getIntegerOfSpecificDate(key);
            int currentDay = Current_Date.getDay(anotherDate);


            if ((dateToday - anotherDate) < (totalDays -1)) { // checking if the day is less than 31
                int index = 0;
                index = currentDay - 1; // making indexes correspond to the way arrays are indexed. from 0. not 1

                // referencing all values of a stats
                Integer steps = Integer.parseInt(value.getSteps());
                double calories = Double.parseDouble(value.getCalories());
                double distance = Double.parseDouble(value.getDistance());

                // checking if the date has values saved and updating these values
                if (stepsArray[index] != null) {
                    Integer updatedSteps = stepsArray[index] + steps;
                    stepsArray[index] = updatedSteps;

                    double updatedCalories = caloriesArray[index] + calories;
                    caloriesArray[index] = updatedCalories;

                    double updatedDistance = distanceArray[index] + distance;
                    distanceArray[index] = updatedDistance;
                } else {
                    // saving values if nothing was saved before
                    stepsArray[index] = steps;
                    caloriesArray[index] = calories;
                    distanceArray[index] = distance;
                }
            }
        });

        // this is to remove a null value from the list. Null values are causing issues in the chart.
        ArrayList<Integer> nonNullArrayOfSteps = new ArrayList<>();
        caloriesList = new ArrayList<>();
        distanceList = new ArrayList<>();

        for (int i = 0; i < totalDays; i++) {
            if (stepsArray[i] != null) {
                nonNullArrayOfSteps.add(stepsArray[i]);
                caloriesList.add(caloriesArray[i]);
                distanceList.add(distanceArray[i]);
            } else {
                // adding 0 to the indexes that holds null values
                nonNullArrayOfSteps.add(0);
                caloriesList.add(0.0);
                distanceList.add(0.0);
            }
        }
        return nonNullArrayOfSteps;
    }

    public void dayChoice() throws Exception { // when the user clicks on the dayChart button
        choiceOfGraph = "daily";
        showChart();
    }

    public void weekChoice() throws Exception { // when user clicks on the weekChart button
        choiceOfGraph = "weekly";
        showChart();
    }

    public void monthChoice() throws Exception { // when user clicks on the monthChart button
        choiceOfGraph = "monthly";
        showChart();
    }

    public void displayGoals(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GoalsScene.fxml"));
        Parent root = loader.load();
        GoalsController goalsController = loader.getController();
        goalsController.viewAWeightgoalsinProgress();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}