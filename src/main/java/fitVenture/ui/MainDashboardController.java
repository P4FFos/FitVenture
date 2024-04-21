package fitVenture.ui;

import fitVenture.backend.stats.Stats;
import fitVenture.backend.user.User;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class MainDashboardController {

    private CategoryAxis xAxis; // xAxis of the chart
    private NumberAxis yAxis; // Yaxis of the chart
    private BarChart barChart; //Barchart object

    @FXML
    private BorderPane borderPane; // refelance to the BorderPane in the fxml
    private ArrayList immutableList; // list of values for Xaxis of the graph
    private ObservableList observableList; // Obsevable referlance for observable object

    private ArrayList<Integer> caloriesList; // a list to hold calorie values
    private ArrayList <Integer> distanceList; // a list to hold distance values

    public void openUserProfile(ActionEvent event) throws IOException { // method to be called if the user clicks on the userProfile button
        //loads MainDashboardScene one user pressed login button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileScene.fxml"));
        Parent root = loader.load(); // loading the userProfileScene.fxml

        UserProfileController userProfileController = loader.getController(); // getting user profile controller object
        userProfileController.showData(); // calling the showdata method of showData method

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // getting the stage
        Scene scene = new Scene(root); // adding the parent to the scene
        stage.setScene(scene); // adding scene to the stage
        stage.show();// showing the stage
    }


    public void showChart(int size) throws IOException { // show chart method
        if (size == 24) {
            dayChart(); // checks and if the value is 24, it call dayChart which is responsible for 24 hours chart
        }
        if (size == 7) {
            weekChart(); // checks and if the value is 7, it call weekChart which is responsible for 7 dayChart
        }
        if (size == 31) {
            monthChart(); // checks and if the value is 31, it call monthChart which is responsible for 31 daysChart
        }
    }

    public void dayChart() { // dayChart is resposible to display 24 hours chart
        xAxis = new CategoryAxis(); // create object of CategoryAxis which is x axis of the graph
        xAxis.setLabel("Hours"); // setting label to 24 hours

        yAxis = new NumberAxis(); // create object of NumberAxis which is Yaxis of the graph
        yAxis.setLabel("Steps"); // setting the label

        List numbersList = List.of(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23"
        ); // a list of values to be used for Xaxis.

        observableList = FXCollections.observableList(numbersList); // creating the observable object
        xAxis.setCategories(observableList); // setting the observable to the Xaxis

        ArrayList list = getData(24); // getting data for 24 hours
        barChart = new BarChart(xAxis, yAxis); // creation of barChart object
        addData(list, 0); // addition of the 24 hours data to the chart

        barChart.setMaxHeight(800); // setting the maxHeight of the chart
        barChart.setMaxWidth(1200); // setting the maxWidth of the chart
        borderPane.setCenter(barChart); // setting the barchart to the center of the borderPane

    }

    public void monthChart() {
        xAxis = new CategoryAxis();// create object of CategoryAxis which is x axis of the graph
        xAxis.setLabel("Weeks"); //setting the label

        yAxis = new NumberAxis(); //create object of NumberAxis which is Yaxis of the graph
        yAxis.setLabel("Steps");// setting the label

        List numbersList = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        ); // a list of values to be used for Xaxis.

        observableList = FXCollections.observableList(numbersList); // creating the observable object
        xAxis.setCategories(observableList); // setting the observable to the Xaxis

        ArrayList list = getData(31); // getting data for 31 days
        barChart = new BarChart(xAxis, yAxis); // creation of chart object
        addData(list, 1); // adding data to the chart. 1 int value that is being parsed, is the indicator to show that the chart will count from 1 not zero.

        barChart.setMaxHeight(800); // setting the maxHeight of the barChart
        barChart.setMaxWidth(1200); // setting the maxWidth of the barchart
        borderPane.setCenter(barChart); // setting the barchart in the center of the borderPane
    }

    public void weekChart() { // a method that is responcible for weekchart
        xAxis = new CategoryAxis(); // create object of CategoryAxis which is x axis of the graph
        xAxis.setLabel("Days"); //setting the label

        yAxis = new NumberAxis();//create object of NumberAxis which is Yaxis of the graph
        yAxis.setLabel("Steps");//setting the label

        List numbersList = List.of(
                "1", "2", "3", "4", "5", "6", "7"
        ); // a list of values to be used for Xaxis.
        observableList = FXCollections.observableList(numbersList); // creating observable object
        xAxis.setCategories(observableList); // setting the observable to the Xaxis

        ArrayList list = getData(7); // getting data for 7 days
        barChart = new BarChart(xAxis, yAxis); // creating the barChart object
        addData(list, 1); // adding data to the chart. 1 int value that is being parsed, is the indicator to show that the chart will count from 1 not zero.

        barChart.setMaxHeight(800); // setting the value of maxheight of barchart
        barChart.setMaxWidth(1200); // setting the maxWidth of the barChart
        borderPane.setCenter(barChart); // setting the barChart in the center of the borderPane
    }

    public void addData(ArrayList<Integer> list, int start) { // methos that is responcible for adding data
        // creation of XYChart that is userd by barChart to map x and y axis of the graph
        XYChart.Series<String, Number> steps = new XYChart.Series<>();  // XYchart object for steps
        steps.setName("Steps/Time");

        XYChart.Series<String, Number> calories = new XYChart.Series<>(); // XYchart for calories
        calories.setName("Calories/Time");
        XYChart.Series<String, Number> distance = new XYChart.Series<>(); // XYchart object for distance
        distance.setName("Distance/Time");

        int sizeOfList = list.size(); // length of the list containing data. the list is parsed
        int avoidNullPointer = 0; // this value helps in avoiding a nullPointerExeption that can be caused by some graphs starting from 0 and others from 1

        if (start > 0) { // checking if the charts starts from 1 or zero
            sizeOfList = start + sizeOfList; // making sure that the last value in the list is reached
            avoidNullPointer = start; // setting the starting position of the chart
        }
        for (int i = start; i < sizeOfList; i++) { // a loop that goes from start to the length of the list the add x and y values to XYcharts.
            steps.getData().add(new XYChart.Data<>(String.valueOf(i), list.get(i - avoidNullPointer)));
            calories.getData().add(new XYChart.Data<>(String.valueOf(i),caloriesList.get(i-avoidNullPointer)));
            distance.getData().add(new XYChart.Data<>(String.valueOf(i),distanceList.get(i-avoidNullPointer)));
        }

        barChart.getData().addAll(steps,calories,distance); // adding the XYcahrts values to the barchart
    }

    public ArrayList getData(int size) { // this is the method responsible for retrieving data for charts
        if (size == 24) { // choice for day data.
            return getDayData();
        }
        if (size == 31) {
            // add a method to get data for a month
            return getMonthData();

        }
        if (size == 7) {
            // add a method for a week data
            return getWeekData();
        }
        return null;
    }

    public ArrayList getDayData() { // getting dayData
        // getting the object user who signed in
        User currentUser = FitVentureStart.currentUser;
        HashMap<String, Stats> mapOfStats = currentUser.getStats(); // retrieving their stats
        Integer[] caloriesArray = new Integer[24];
        Integer[] distanceArray = new Integer[24];

        Integer[] myList = new Integer[24];

        mapOfStats.forEach((k, v) -> { // looping through all stats
            if (k.substring(0, 10).toLowerCase().equals(Current_Date.getDateToday(new Date()).substring(0, 10))) { // check if the dateKeyValue is equal to today's date.
                int currentHour = Integer.parseInt(k.substring(11, 13)); // take the hour of that day as integer
                Stats stat = v; // assign stat to the current stat Object
                Integer steps = Integer.parseInt(stat.getSteps());
                Integer calories = Integer.parseInt(stat.getCalories());
                Integer distance = Integer.parseInt(stat.getDistance());

                // checking if there were other values that were set to the current our
                // then updates the value
                if (myList[currentHour] != null) {
                    Integer updatedSteps = myList[currentHour] + steps;
                    myList[currentHour] = updatedSteps;
                    Integer updatedCalories = caloriesArray[currentHour] +  calories;
                    caloriesArray[currentHour] = updatedCalories;
                    Integer updatedDistance= distanceArray[currentHour] + distance;
                    distanceArray[currentHour] = updatedDistance;
                } else {
                    // if nothing were regestered for the particular hour, save the values in the hour.
                    myList[currentHour] = steps;
                    caloriesArray[currentHour] = calories;
                    distanceArray[currentHour] = distance;
                }
            }
        });
        // this is to remove a null value from the list. Null values are causing issues in the chart.
        ArrayList <Integer> emptyList = new ArrayList<>();
        caloriesList = new ArrayList<>();
        distanceList = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            if (myList[i] != null) {
                emptyList.add(myList[i]);
                caloriesList.add(caloriesArray[i]);
                distanceList.add(distanceArray[i]);
            } else {
                // adding o to the index that holds null values
                emptyList.add(0);
                caloriesList.add(0);
                distanceList.add(0);
            }
        }
        return emptyList;
    }

    public ArrayList getWeekData() { // this method is responsible for getting weekData
        // getting stats of the current user
        HashMap<String, Stats> mapOfStats = FitVentureStart.currentUser.getStats();
        Integer[] myList = new Integer[7];
        Integer[] caloriesArray = new Integer[7];
        Integer[] distanceArray = new Integer[7];

        int totalDays = 7;
        int currentDate = Current_Date.getDateTodayAsInteger(); // getting todays date as an integer

        mapOfStats.forEach((k, v) -> { // looping though evert stat in the map

            int anotherDate = Current_Date.getIntegerOfSpecificDate(k); // getting the dateKey for the map as an integer

            int difference = currentDate - anotherDate; // the difference between today and when the date was created'
            System.out.println(currentDate);
            System.out.println(anotherDate);
            // referencing values of current stat in the loop
            Stats stat = v;
            Integer steps = Integer.parseInt(stat.getSteps());
            Integer calories = Integer.parseInt(stat.getCalories());
            Integer distance = Integer.parseInt(stat.getDistance());

            // checking if there has been less than a week since the stat object was created
            if (difference < totalDays) {
                int index = Math.abs(difference - 6);
                if (myList[index] != null) { // chicking if there were saved data on that day to update
                   // updating data if they exist
                    Integer updatedSteps = myList[index] + steps;
                    myList[index] = updatedSteps;
                    Integer updatedCalories = caloriesArray[index] + calories;
                    caloriesArray[index]= updatedCalories;
                    Integer updatedDistance = distanceArray[index] + distance;
                    distanceArray[index]= updatedDistance;


                } else {
                    // adding data if there is nothing to update
                    myList[index] = steps;
                    caloriesArray[index] = steps;
                    distanceArray[index] = distance;

                }
            }
        });

        // this is to remove a null value from the list. Null values are causing issues in the chart.
        ArrayList<Integer> emptyList = new ArrayList<>();
        caloriesList = new ArrayList<>();
        distanceList = new ArrayList<>();

        for (int i = 0; i < totalDays; i++) {
            if (myList[i] != null) {
                emptyList.add(myList[i]);
                caloriesList.add(caloriesArray[i]);
                distanceList.add(distanceArray[i]);
            } else {
                // adding o to the index that holds null values
                emptyList.add(0);
                caloriesList.add(0);
                distanceList.add(0);
            }

        }
        return emptyList;

    }

    public ArrayList getMonthData() { // this method is responcible for getting monthData
        // getting the map of stats for the current user
        HashMap<String, Stats> mapOfStats = FitVentureStart.currentUser.getStats();
        Integer[] myList = new Integer[31];
        Integer [] caloriesArray = new Integer[31];
        Integer[] distanceArray = new Integer[31];

        int totalDays = 31; // days of a month
        int todaysDate = Current_Date.getDateTodayAsInteger();
        mapOfStats.forEach((k, v) -> { // looping though the map of stats
            int anotherDate = Current_Date.getIntegerOfSpecificDate(k);
            int currentday = Current_Date.getDay(anotherDate);

            if( (todaysDate-anotherDate) < 102){ // chcking if we are in the same year
                if (currentday <= totalDays) { // checking if the day is less than 31
                    int index = 0;
                    index = currentday-1; // making indexes corrensond to the way arrays are indexed. from 0. not 1
                    // referencing all values of a stats
                    Stats stat = v;
                    Integer steps = Integer.parseInt(stat.getSteps());
                    Integer calories = Integer.parseInt(stat.getCalories());
                    Integer distance = Integer.parseInt(stat.getDistance());

                     // checking if the date is had values saved and updating these values
                    if (myList[index] != null) {
                        Integer updatedSteps = myList[index] + steps;
                        myList[index] = updatedSteps;
                        Integer updatedCalories = caloriesArray[index] + calories;
                        caloriesArray[index] = updatedCalories;
                        Integer updatedDistance = distanceArray[index] + distance;
                        distanceArray[index] = updatedDistance;
                    } else {
                        // saving values if nothing was saved before
                        myList[index] = steps;
                        caloriesArray[index] = calories;
                        distanceArray[index] = distance;
                    }
                }

            }


        });

        // this is to remove a null value from the list. Null values are causing issues in the chart.
        ArrayList <Integer> nonNullArrayOfSteps = new ArrayList<>();
       caloriesList = new ArrayList<>();
       distanceList = new ArrayList<>();


        for (int i = 0; i < totalDays; i++) {
            if (myList[i] != null) {
                nonNullArrayOfSteps.add(myList[i]);
                caloriesList.add(caloriesArray[i]);
                distanceList.add(distanceArray[i]);
            } else {
                // adding 0 to the indexes that holds null values
                nonNullArrayOfSteps.add(0);
                caloriesList.add(0);
                distanceList.add(0);
            }
        }
        return nonNullArrayOfSteps;
    }



    public void dayChoice() throws Exception { // when the user clicks on the daychart button
        showChart(24);
    }

    public void weekChoice() throws Exception { // when user clicks on the weekChart button
        showChart(7);
    }

    public void monthChoice() throws Exception { // when user clicks on the monthChart button
        showChart(31);
    }
}