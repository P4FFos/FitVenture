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

import java.io.IOException;
import java.util.*;

public class MainDashboardController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart linechart;
    @FXML
    private Label dayUserChoice;
    @FXML
    private Label weekUserChoice;
    @FXML
    Label monthUserChoice;
    @FXML
    private BorderPane chartPane;

    ArrayList list;

    ArrayList immutableList;

    ObservableList observableList;

    public void openUserProfile(ActionEvent event) throws IOException {
        //loads MainDashboardScene one user pressed login button
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserProfileScene.fxml"));
        root = loader.load();

        //calls method showData in the userProfile, once user is logged in
        UserProfileController userProfileController = loader.getController();
        userProfileController.showData();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public void showChart() throws IOException {
        dayChart(); // this chart is based on actual time stamp of when the data was saved
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

        list = getData(24);
        linechart = new BarChart(xAxis, yAxis);
        addData(list, 0);

        linechart.setMaxHeight(800);
        linechart.setMaxWidth(1200);
        chartPane.setCenter(linechart);

    }

    public void monthChart() {

        xAxis = new CategoryAxis();
        xAxis.setLabel("Weeks");

        yAxis = new NumberAxis();
        yAxis.setLabel("Steps");

        List numbersList = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23","24","25","26","27","28","29","30","31"
        );

        immutableList = new ArrayList<>(List.copyOf(numbersList));

        observableList = FXCollections.observableList(immutableList);
        xAxis.setCategories(observableList);

        list = getData(30);
        linechart = new BarChart(xAxis, yAxis);
        addData(list, 1);

        linechart.setMaxHeight(800);
        linechart.setMaxWidth(1200);
        chartPane.setCenter(linechart);

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

        list = getData(7);
        linechart = new BarChart(xAxis, yAxis);
        addData(list, 1);

        linechart.setMaxHeight(800);
        linechart.setMaxWidth(1200);
        chartPane.setCenter(linechart);

    }


    public void addData(ArrayList<Integer> list, int start) {
        // this method will be adding dates and steps to the chart.
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Steps/Time");

        Random random = new Random();
        int sizeOfList = list.size();
        int avoidNullPointer = 0;

        if (start > 0) {
            sizeOfList = start + sizeOfList;
            avoidNullPointer = start;
        }

        for (int i = start; i < sizeOfList; i++) {

            series.getData().add(
                    new XYChart.Data<>(String.valueOf(i), list.get(i - avoidNullPointer)));
        }

        linechart.getData().add(series);

    }

    public void addChartToChartPane() {

    }

    public ArrayList getData(int size) { // this method will add up all steps to one hour/ day or a month depending on a string that determines the choice for day/ week ....
        // this method will retrive an arrayList containing information
        // about users steps and time stump.
        ArrayList mylist = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            mylist.add(random.nextInt(0, 100));
            System.out.println(mylist.get(i));
        }
        return mylist;
    }
    public void getThedata(int size){
        ArrayList list = new ArrayList();
        User currentUser = FitVentureStart.currentUser;
        HashMap < String,Stats> mapOfStats = currentUser.getStats();

        if(size == 24 ){
            int currentHour = Integer.parseInt(Current_Date.getDateToday(new Date()).substring(11,13));
            try {
                mapOfStats.forEach((k,v) -> {
                    Stats stat = v;
                    double steps= Integer.parseInt(stat.getSteps());

                    if(list.get(currentHour)!= null){
                        double stepsInCurrentHour = (Double) list.get(currentHour)+ steps;

                    } else{
                        list.add(currentHour,steps);
                    }


                });
            } catch (Exception e){
                System.out.println(e.getMessage()+"some thing is wrong in creating data for charts");
            }

        }









    }

    public void dayChoice() {
        dayChart();
    }

    public void weekChoice() {
        weekChart();
    }

    public void monthChoice() {
        monthChart();
    }
}