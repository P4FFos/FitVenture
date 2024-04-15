package fitVenture.ui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
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
   private Label day;
   @FXML
   private Label week;
   @FXML
   Label month;
   @FXML
   private BorderPane chartPane;

   ArrayList list;

   ArrayList immutableList;

   ObservableList observableList;


    public void showChart () throws IOException {

        xAxis = new CategoryAxis();
        xAxis.setLabel("Days");

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















       list = getData(24); // this methos is for retrival of steps and time stamp from database.



        // Create an ArrayList from the immutable list





        linechart = new BarChart(xAxis,yAxis);
        addData(list);
       // addChartToLayout();





        linechart.setMaxHeight(800);
        linechart.setMaxWidth(1200);


        chartPane.setCenter(linechart);



    }

    private void setXaisValues(int start, int end, int range, String label){


        // the range, start and end are choose depending on user's choise
        // the label is also choosen dependinng on the choice of the user
        // (their would be a collection containing on of users choice)
    }

    private void setYaxisValues(int start, int end, int range, String label){



        // here we retrieve data from the user in a collection
        // check to see the range as it was to the xaxis
        // the label is always the same

    }

    public void addData(ArrayList<Integer> list) {


        // this method will be adding dates and steps to the chart.
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (int i = 0; i < list.size(); i++) {

           series.getData().add(
                   new XYChart.Data<>(String.valueOf(i), list.get(i)));
        }

        linechart.getData().add(series);

    }

    public void addChartToChartPane(){

    }

    public ArrayList getData(int size){
        // this method will retrive an arrayList containing information
        // about users steps and time stump.
        ArrayList mylist = new ArrayList<>();
        Random random = new Random();
        for(int i =0; i<size; i++){
            mylist.add(random.nextInt(0,100));
            System.out.println(mylist.get(i));
        }
        return mylist;

    }

    public void userChoiceWeek(){



    }
    public void userChiceMonth(){

    }


    public void addXYToChart(){

    }

    private void addChartToLayout(){

    }







}





// I will need a way to add all steps that was made during the current hour.
// the same thing for the week
// the same thing for the month.









