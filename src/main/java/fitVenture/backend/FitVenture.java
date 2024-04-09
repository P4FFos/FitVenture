package fitVenture;

import java.util.HashMap;

public class FitVenture {
    HashMap<String, User> users;

    public FitVenture() {
        users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public boolean register(String username, String password, String weight, String height) throws RegistrationException {
        if (users.containsKey(username)) {
            return false;
        } else if (!username.equals(null) && !password.equals(null)) {
            User user = new User(username, password, weight, height);
            addUser(user);
            return true;
        } else {
            return false;
        }
    }
}