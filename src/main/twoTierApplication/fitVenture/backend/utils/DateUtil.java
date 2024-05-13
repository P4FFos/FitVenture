package fitVenture.backend.utils;

import java.util.*;
import java.text.SimpleDateFormat;

public class DateUtil {
    public static String getDateToday(Date date) {
        // Returns the current date in the format "1999-01-11 12" -> "Year-Month-Day Hour"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        return dateFormat.format(date);
    }

    // Returns specific date as an integer
    public static int getIntegerOfSpecificDate(String date) {
        Set<Character> allowedCharacters = new HashSet<>(Arrays.asList(' ','-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
        String newDate = "";

        try {
            // 10 represents the amount of characters in the date format "yyyy-MM-dd"
            for (int i = 0; i < 10; i++) {
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
        String stringDate = String.valueOf(date); // Turn the date to String
        String days = stringDate.substring(6, 8); // Gets the days (only works with the format that starts with "yyyy-MM-dd" in Integer format)
        int day = Integer.parseInt(days);
        return day;
    }
<<<<<<< HEAD

    // Returns year from a specific integer date
    public static int getYear(int date) {
        String stringDate = String.valueOf(date); // Turn the date to String
        if (stringDate.length() != 10) {
            throw new StringIndexOutOfBoundsException("A complete date format has to be provided.");
        }
        String years = stringDate.substring(0, 4); // Gets the days (only works with the format that starts with "yyyy-MM-dd" in Integer format)
        int year = Integer.parseInt(years);
        return year;
    }

    public static int getWeek(int date){ // This method gets the week of the year from provided date
        if (!String.valueOf(date).equals("10")) {
            throw new StringIndexOutOfBoundsException("A complete date format has to be provided.");
        }

        Calendar calendar = Calendar.getInstance();
        String stringDate = String.valueOf(date);

        int year = Integer.parseInt(stringDate.substring(0, 4));
        int month = Integer.parseInt(stringDate.substring(4,6)) - 1; // We subtract with one because the month field is zero-based in the Calendar class.
        int day = Integer.parseInt(stringDate.substring(6, 8));
        
        calendar.set(year, month, day);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getWeekday(int date){ // This method gets the week of the year from provided date
        if (!String.valueOf(date).equals("10")) {
            throw new StringIndexOutOfBoundsException("A complete date format has to be provided.");
        }

        Calendar calendar = Calendar.getInstance();
        String stringDate = String.valueOf(date);

        int year = Integer.parseInt(stringDate.substring(0, 4));
        int month = Integer.parseInt(stringDate.substring(4,6)) - 1; // We subtract with one because the month field is zero-based in the Calendar class.
        int day = Integer.parseInt(stringDate.substring(6, 8));

        calendar.set(year, month, day);
        return calendar.get(Calendar.DAY_OF_WEEK); // Returns an integer from 1-7 (Monday to Sunday)
    }
}
