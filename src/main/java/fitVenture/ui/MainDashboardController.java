package fitVenture.ui;

import fitVenture.backend.FitVenture;
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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.plaf.ColorUIResource;
import java.io.IOException;
import java.util.*;

public class MainDashboardController {

    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart lineChart;

    @FXML
    private BorderPane chartPane;

    private ArrayList immutableList;
    private ObservableList observableList;

    public void openUserProfile(ActionEvent event) throws IOException {
        //loads MainDashboardScene one user pressed login button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileScene.fxml"));
        Parent root = loader.load();
        this.showChart(24);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void showChart(int size) throws IOException {
        if (size == 24) {
            dayChart(); // this chart is based on actual time stamp of when the data was saved
        }
        if (size == 7) {
            weekChart();
        }
        if (size == 31) {
            monthChart();
        }
    }

    public void dayChart() {
        xAxis = new CategoryAxis();
        xAxis.setLabel("Hours");

        yAxis = new NumberAxis();
        yAxis.setLabel("Steps");

        List numbersList = List.of(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23"
        );

        immutableList = new ArrayList<>(List.copyOf(numbersList));

        observableList = FXCollections.observableList(immutableList);
        xAxis.setCategories(observableList);

        ArrayList list = getData(24);
        lineChart = new BarChart(xAxis, yAxis);
        addData(list, 0);

        lineChart.setMaxHeight(800);
        lineChart.setMaxWidth(1200);
        chartPane.setCenter(lineChart);

    }

    public void monthChart() {
        xAxis = new CategoryAxis();
        xAxis.setLabel("Weeks");

        yAxis = new NumberAxis();
        yAxis.setLabel("Steps");

        List numbersList = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        );

        immutableList = new ArrayList<>(List.copyOf(numbersList));

        observableList = FXCollections.observableList(immutableList);
        xAxis.setCategories(observableList);

        ArrayList list = getData(31);
        lineChart = new BarChart(xAxis, yAxis);
        addData(list, 1);

        lineChart.setMaxHeight(800);
        lineChart.setMaxWidth(1200);
        chartPane.setCenter(lineChart);
    }

    public void weekChart() {
        xAxis = new CategoryAxis();
        xAxis.setLabel("Days");

        yAxis = new NumberAxis();
        yAxis.setLabel("Steps");

        List numbersList = List.of(
                "1", "2", "3", "4", "5", "6", "7"
        );

        immutableList = new ArrayList<>(List.copyOf(numbersList));

        observableList = FXCollections.observableList(immutableList);
        xAxis.setCategories(observableList);

        ArrayList list = getData(7);
        lineChart = new BarChart(xAxis, yAxis);
        addData(list, 1);

        lineChart.setMaxHeight(800);
        lineChart.setMaxWidth(1200);
        chartPane.setCenter(lineChart);
    }

    public void addData(ArrayList<Integer> list, int start) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Steps/Time");

        int sizeOfList = list.size();
        int avoidNullPointer = 0;

        if (start > 0) {
            sizeOfList = start + sizeOfList;
            avoidNullPointer = start;
        }
        for (int i = start; i < sizeOfList; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i), list.get(i - avoidNullPointer)));
        }

        lineChart.getData().add(series);
    }

    public ArrayList getData(int size) {
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

    public ArrayList getDayData() {
        User currentUser = FitVentureStart.currentUser;
        HashMap<String, Stats> mapOfStats = currentUser.getStats();

        Integer[] myList = new Integer[24];

        mapOfStats.forEach((k, v) -> {
            if (k.substring(0, 10).toLowerCase().equals(Current_Date.getDateToday(new Date()).substring(0, 10))) {
                int currentHour = Integer.parseInt(k.substring(11, 13));
                Stats stat = v;
                Integer steps = Integer.parseInt(stat.getSteps());
                if (myList[currentHour] != null) {
                    Integer updatedSteps = myList[currentHour] + steps;
                    myList[currentHour] = updatedSteps;
                } else {
                    myList[currentHour] = steps;
                }
            }
        });

        ArrayList emptyList = new ArrayList<>(); // this is to remove a null value from the list. Null values are causing issues in the chart.

        for (int i = 0; i < 24; i++) {
            if (myList[i] != null) {
                emptyList.add(myList[i]);
            } else {
                emptyList.add(0);
            }
        }
        return emptyList;
    }

    public ArrayList getWeekData() {
        HashMap<String, Stats> mapOfStats = FitVentureStart.currentUser.getStats();
        Integer[] myList = new Integer[7];
        int week = 7;
        int currentDate = Current_Date.getDateTodayAsInteger();

        mapOfStats.forEach((k, v) -> {

            int anotherDate = Current_Date.getIntegerOfSpecificDate(k);

            int difference = currentDate - anotherDate;
            Stats stat = v;
            Integer steps = Integer.parseInt(stat.getSteps());

            if (difference < week && difference > 0) {

                if (myList[difference] != null) {
                    Integer updatedSteps = myList[difference] + steps;
                    myList[difference] = updatedSteps;


                } else {
                    myList[difference] = steps;

                }
            }
        });

        ArrayList emptyList = new ArrayList<>(); // this is to remove a null value from the list. Null values are causing issues in the chart.

        for (int i = 0; i < week; i++) {
            if (myList[i] != null) {
                emptyList.add(myList[i]);
            } else {
                emptyList.add(0);
            }

        }
        return emptyList;

    }

    public ArrayList getMonthData() {
        HashMap<String, Stats> mapOfStats = FitVentureStart.currentUser.getStats();
        Integer[] myList = new Integer[31];
        int month = 31;
        int currentDate = Current_Date.getDateTodayAsInteger();

        mapOfStats.forEach((k, v) -> {
            int anotherDate = Current_Date.getIntegerOfSpecificDate(k);
            System.out.println(anotherDate);
            int difference = currentDate - anotherDate;

            if (difference <= 100) {
                int index = 0;
                if (difference > month) {
                    int indexDiff = (difference - 7) % month;
                    index = Math.abs(indexDiff);
                } else if (difference >= 0) {
                    index = difference % month;
                }
                System.out.println(index);
                Stats stat = v;
                Integer steps = Integer.parseInt(stat.getSteps());

                if (myList[index] != null) {
                    Integer updatedSteps = myList[index] + steps;
                    myList[index] = updatedSteps;
                } else {
                    myList[index] = steps;
                }
            }
        });

        ArrayList emptyList = new ArrayList<>(); // this is to remove a null value from the list. Null values are causing issues in the chart.

        for (int i = 0; i < month; i++) {
            if (myList[i] != null) {
                emptyList.add(myList[i]);
            } else {
                emptyList.add(0);
            }
        }
        return emptyList;
    }


    public void dayChoice() throws Exception {
        showChart(24);
    }

    public void weekChoice() throws Exception {
        showChart(7);
    }

    public void monthChoice() throws Exception {
        showChart(31);
    }
}