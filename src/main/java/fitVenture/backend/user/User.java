package fitVenture.backend.user;

import java.util.Map;

public class User {
    // user class attributes
    private String username;
    private String password;
    public double weight;
    public double height;
    private String name;

    // Empty constructor used by Jackson for Json deserializing
    public User(){}

    // user class constructor
    public User(String username, String password, String weight, String height, String name) {
        this.username = username;
        this.password = password;
        this.weight = Double.parseDouble(weight);
        this.height = Double.parseDouble(height);
        this.name = name;
    }


    // user class get methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getWeight() {return weight;}

    public double getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    // user class set methods
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }
}
