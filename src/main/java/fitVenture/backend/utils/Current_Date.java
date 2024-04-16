package fitVenture.backend.utils;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Current_Date {
    public static String getDateToday(Date date) {
        // Returns the current date in the format "1999 01 11 12:00"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH:00");
        return dateFormat.format(date);
    }
}
