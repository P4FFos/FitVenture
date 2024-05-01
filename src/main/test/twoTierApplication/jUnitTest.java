package twoTierApplication;

import fitVenture.backend.utils.Current_Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class jUnitTest { 

    @Test
    public void testTimeFormat() {
        assertEquals(20041215, Current_Date.getIntegerOfSpecificDate("2004-12-15 16"));
    }
}
