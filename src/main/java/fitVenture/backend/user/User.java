package fitVenture.backend;

public class User {
    private String username;
    private String password;
    private String weight;
    private String height;

    public User(String username, String password, String weight, String height) {
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
    }

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
