package fitVenture.backend.utils;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Current_Date {
    public static String getDateToday(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
