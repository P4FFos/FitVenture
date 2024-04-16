package fitVenture.ui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javafx.collections.FXCollections.observableList;


public class StepsChartContoller {

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


    public void showChart () throws IOException {
       // dayChart(); // this chart is based on actual time stamp of when the data was saved
        monthChart();


    }


    public void dayChart (){


        xAxis = new CategoryAxis();
        xAxis.setLabel("Hours");

        yAxis = new NumberAxis();
        yAxis.setLabel("Steps");

        List  numbersList = List.of(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23"
        );

        immutableList = new ArrayList<>(List.copyOf(numbersList));

        observableList= FXCollections.observableList(immutableList);
        xAxis.setCategories(observableList);

        list = getData(24);
        linechart = new BarChart(xAxis,yAxis);
        addData(list,0);

        linechart.setMaxHeight(800);
        linechart.setMaxWidth(1200);
        chartPane.setCenter(linechart);

    }

    public void monthChart(){

        xAxis = new CategoryAxis();
        xAxis.setLabel("Weeks");

        yAxis = new NumberAxis();
        yAxis.setLabel("Steps");

        List  numbersList = List.of("1", "2", "3", "4"
        );

        immutableList = new ArrayList<>(List.copyOf(numbersList));

        observableList= FXCollections.observableList(immutableList);
        xAxis.setCategories(observableList);

        list = getData(4);
        linechart = new BarChart(xAxis,yAxis);
        addData(list, 1);

        linechart.setMaxHeight(800);
        linechart.setMaxWidth(1200);
        chartPane.setCenter(linechart);

    }

    public void weekChart(){

        xAxis = new CategoryAxis();
        xAxis.setLabel("Days");

        yAxis = new NumberAxis();
        yAxis.setLabel("Steps");

        List  numbersList = List.of(
                "1", "2", "3", "4", "5", "6", "7"
        );

        immutableList = new ArrayList<>(List.copyOf(numbersList));

        observableList= FXCollections.observableList(immutableList);
        xAxis.setCategories(observableList);

        list = getData(7);
        linechart = new BarChart(xAxis,yAxis);
        addData(list, 1);

        linechart.setMaxHeight(800);
        linechart.setMaxWidth(1200);
        chartPane.setCenter(linechart);

    }


    public void addData(ArrayList<Integer> list, int start) {


        // this method will be adding dates and steps to the chart.
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Random random = new Random();
        int sizeOfList =list.size();
        int avoidNullPointer=0;

        if(start > 0 ){
            sizeOfList = start+sizeOfList;
            avoidNullPointer=start;
        }

        for (int i = start; i < sizeOfList ; i++) {

           series.getData().add(
                   new XYChart.Data<>(String.valueOf(i), list.get(i-avoidNullPointer)));
        }

        linechart.getData().add(series);

    }

    public void addChartToChartPane(){

    }

    public ArrayList getData(int size){ // this method will add up all steps to one hour/ day or a month depending on a string that determines the choice for day/ week ....
        // this method will retrive an arrayList containing information
        // about users steps and time stump.
        ArrayList mylist = new ArrayList<>();
        Random random = new Random();
        for(int i =0; i<size; i++){
            mylist.add(random.nextInt(0,100));
            System.out.println( mylist.get(i));
        }
        return mylist;

    }

    public void dayChoice(){
        dayChart();
    }


















}





// I will need a way to add all steps that was made during the current hour.
// the same thing for the week
// the same thing for the month.









