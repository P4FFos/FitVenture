package fitVenture;

import fitVenture.backend.utils.Current_Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrentDateTest { 

    @Test
    @DisplayName("Tests if Current_Date.getIntegerOfSpecificDate method handles different date inputs.")
    public void getIntegerOfSpecificDateTest() {
        assertEquals(20041215, Current_Date.getIntegerOfSpecificDate("2004-12-15 16"), "The method should convert the string date into an integer format.");
        assertEquals(20041212, Current_Date.getIntegerOfSpecificDate("2004-12-12 16"), "The method should convert the string date into an integer format.");

        //Expecting failure
        Throwable exceptionIntSpecDate = assertThrows(IllegalArgumentException.class, ()-> Current_Date.getIntegerOfSpecificDate("Not a date"));
        assertEquals("The method should not allow any symbols or characters that are not integers(1, 2, 3, ...) and dashes (-).", exceptionIntSpecDate.getMessage());
        
        Throwable exceptionNonCompleteFormat = assertThrows(StringIndexOutOfBoundsException.class, ()-> Current_Date.getIntegerOfSpecificDate("2004"));
        assertEquals("A complete date format has to be provided.", exceptionNonCompleteFormat.getMessage());


    }

    @Test
    @DisplayName("Tests if Current_Date.getDay method handles different inputs for the day")
    public void getDayTest() {
        Throwable exception = assertThrows(StringIndexOutOfBoundsException.class, ()-> Current_Date.getDay(2004));
        assertEquals("A complete date format has to be provided.", exception.getMessage());
    }

}
