package fitVenture.backend.utils;

import java.util.*;
import java.text.SimpleDateFormat;

public class Current_Date {
    public static String getDateToday(Date date) {
        // Returns the current date in the format "1999 01 11 12:00"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        return dateFormat.format(date);
    }

    public static int getDateTodayAsInteger(){
        String todayDate= getDateToday(new Date());
        String newDate="";

        Set <Integer> setOfLine= new HashSet<Integer>();
        setOfLine.add(4);
        setOfLine.add(7);
        setOfLine.add(10);

        for(int i =0; i< 10 ; i++){
            if(!setOfLine.contains(i)){
                char chr= todayDate.charAt(i);
                newDate= newDate + chr;
            }
        }

        return Integer.parseInt(newDate);
    }

    public static int getIntegerOfSpecificDate(String date){

        String newDate="";

        Set <Integer> setOfLine= new HashSet<Integer>();
        setOfLine.add(4);
        setOfLine.add(7);
        setOfLine.add(10);

        for(int i =0; i< 10 ; i++){
            if(!setOfLine.contains(i)){
                char chr= date.charAt(i);
                newDate= newDate + chr;
            }
        }

        int number = Integer.parseInt(newDate);

        return number;
    }

    public static int getDay(int date){
        String stringDate = String.valueOf(date); // Turn the date to String
        char firstDigit = stringDate.charAt(stringDate.length()-1);
        char secondDigit = stringDate.charAt(stringDate.length()-2);
        String sDay = String.valueOf(secondDigit) + String.valueOf(firstDigit); // Combining the digits to a two-digit number
        int day = Integer.parseInt(sDay); // Parse it to int
        return day;
    }   


}
