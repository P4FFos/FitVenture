package fitVenture.backend.user;

public class User {
    // user class attributes
    private String username;
    private String password;
    private String weight;
    private String height;

    // Bare constructor used by Jackson-Databind for Json deserializing
    public User(){

    }

    // user class constructor
    public User(String username, String password, String weight, String height) {
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
    }

    // user class getter methods
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
}
