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
        String newDate = "";
        Set<Integer> setOfLine = new HashSet<Integer>();
        setOfLine.add(4);
        setOfLine.add(7);
        setOfLine.add(10);

        for (int i = 0; i < 10; i++) {
            if (!setOfLine.contains(i)) {
                char chr = date.charAt(i);
                newDate = newDate + chr;
            }
        }

        int number = Integer.parseInt(newDate);
        return number;
    }

    // Returns today's date as an integer
    public static int getDateTodayAsInteger() {
        return getIntegerOfSpecificDate(getDateToday(new Date()));
    }

    // Returns day from a specific integer date
    public static int getDay(int date) {
        String stringDate = String.valueOf(date); // Turn the date to String
        String days = stringDate.substring(6, 8); // Gets the days (only works with the format that starts with "yyyy-MM-dd" in Integer format)
        int day = Integer.parseInt(days);
        return day;
    }
}
