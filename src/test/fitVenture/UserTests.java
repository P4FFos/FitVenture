package fitVenture;

import fitVenture.backend.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; 

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {
    
    @Test
    @DisplayName("Tests if Current_Date.getIntegerOfSpecificDate method handles different date inputs.")
    public void shouldCreateValidUser() {
        User user = new User("Admin", "Admin1", "50", "168", "David");
        assertEquals("Admin", user.getUsername());
        assertEquals("Admin1", user.getPassword());
        assertEquals("50", user.getWeight());
        assertEquals("168", user.getHeight());
        assertEquals("David", user.getName());
        assertTrue(user.getStats().isEmpty());
    }
}