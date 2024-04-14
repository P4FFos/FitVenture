package fitVenture.backend.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {
    // user class attributes
    private String username;
    private String password;
    private String weight;
    private String height;
    private String name;
    private Map<String, Integer> hashMap;

    // Empty constructor used by Jackson for Json deserializing
    public User(){}

    // user class constructor
    public User(String username, String password, String weight, String height, String name) {
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.name = name;
        this.hashMap = new HashMap<>();
    }

    // user class get methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public int getSteps(String date) {
        return hashMap.get(date);
    }
}
