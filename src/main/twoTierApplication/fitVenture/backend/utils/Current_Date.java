package fitVenture.backend.utils;

import java.util.*;
import java.text.SimpleDateFormat;

public class Current_Date {
    public static String getDateToday(Date date) {
        // Returns the current date in the format "1999-01-11 12" -> "Year-Month-Day Hour"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        return dateFormat.format(date);
    }

    // Returns specific date as an integer
    public static int getIntegerOfSpecificDate(String date) {
        Set<Character> allowedCharacters = new HashSet<>(Arrays.asList(' ','-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
        String newDate = "";
        /*Set<Integer> setOfLine = new HashSet<Integer>();
        setOfLine.add(4);
        setOfLine.add(7);
        setOfLine.add(10);

        for (int i = 0; i < 10; i++) {
            if (!setOfLine.contains(i)) {
                char chr = date.charAt(i);
                newDate = newDate + chr;
            }
        }
        */
        
        if (date.length() != 13) {
            throw new StringIndexOutOfBoundsException("A complete date format has to be provided.");
        }

        try {
            for (int i = 0; i < date.length(); i++) {
                char chr = date.charAt(i);
                if (!allowedCharacters.contains(chr)) {
                    throw new IllegalArgumentException("The method should not allow any symbols or characters that are not integers(1, 2, 3, ...) and dashes (-).");
                } 
                // Skip over spaces and dashes so that newDate can be parsed into an integer later
                if (chr != ' ' && chr != '-'){ 
                    newDate += chr;
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }


        int dateInInt = Integer.parseInt(newDate);
        return dateInInt;
    }

    // Returns today's date as an integer
    public static int getDateTodayAsInteger() {
        return getIntegerOfSpecificDate(getDateToday(new Date()));
    }

    // Returns day from a specific integer date
    public static int getDay(int date) {
        if (!String.valueOf(date).equals("10")) {
            throw new StringIndexOutOfBoundsException("A complete date format has to be provided.");
        }
        String stringDate = String.valueOf(date); // Turn the date to String
        String days = stringDate.substring(6, 8); // Gets the days (only works with the format that starts with "yyyy-MM-dd" in Integer format)
        int day = Integer.parseInt(days);
        return day;
    }
}
