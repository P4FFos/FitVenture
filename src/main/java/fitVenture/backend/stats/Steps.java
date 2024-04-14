package fitVenture.backend.stats;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Steps {
    
    private static String getDateToday(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
