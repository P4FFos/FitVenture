package fitVenture.backend.user;

import fitVenture.backend.stats.Stats;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class User {
    // user class attributes
    private String username;
    private String password;
    private String weight;
    private String height;
    private String name;
    private HashMap<String, Stats> stats;

    // Empty constructor used by Jackson for Json deserializing
    public User() {
    }

    // user class constructor
    public User(String username, String password, String weight, String height, String name) {
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.name = name;
        this.stats = new HashMap<>();
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

    public HashMap<String, Stats> getStats() {
        return stats;
    }

    // user class set methods
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStats(HashMap<String, Stats> stats) {
        this.stats = stats;
    }

    public void addStats(String newDate, Stats stats) {
        this.stats.put(newDate, stats);
    }

}
