package fitVenture.backend.user;

import java.util.Map;

public class User {
    // user class attributes
    private String username;
    private String password;
    private String weight;
    private String height;
    private String name;

    // Empty constructor used by Jackson for Json deserializing
    public User(){}

    // user class constructor
    public User(String username, String password, String weight, String height, String name) {
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.name = name;
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
}
