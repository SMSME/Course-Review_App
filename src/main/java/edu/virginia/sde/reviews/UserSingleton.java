package edu.virginia.sde.reviews;

import java.sql.SQLException;

public class UserSingleton {
    private final User user;
    private static UserSingleton singleton;

    private UserSingleton(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        // If a singleton exists, it means a user is logged in
        return singleton != null;
    }

    public static User login(String username, String password) {
        DatabaseDriver driver = DatabaseSingleton.getInstance();
        // Create the singleton if the login exists
        try {
            if (isValid(username, password)) {
                User newUser = new User(username);
                singleton = new UserSingleton(newUser);
                return newUser;
            }
            else if (driver.getPassword(username)==null){
                throw new IllegalArgumentException("User not found");
            }
            else if(!password.equals(driver.getPassword(username))){
                throw new IllegalStateException("Password is incorrect");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("An error something something");
        }
        return null;
    }

    public static User createUser(String username, String password) {
        DatabaseDriver driver = DatabaseSingleton.getInstance();
        try {
            driver.connect();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try{
            if(!username.isEmpty() && password.length()>=8){
                if(driver.getPassword(username)!=null){
                    throw new IllegalArgumentException("User already exists");
                }
                else{
                    driver.addUser(username, password);
                    driver.commit();
                    driver.disconnect();
                }
            }
            else{
                if(password.length()<8){
                    throw new IllegalStateException("Password must be at least 8 characters");
                }
            }
            driver.disconnect();
            return null;
        } catch (SQLException e){
            throw new IllegalArgumentException("Error occurred creating user.");
        }
    }

    public static void logout() {
        // Logout
        singleton = null;
    }


    public static User getCurrentUser() {
        if (singleton != null)
            return singleton.user;
        // Return null, or throw an error otherwise
        return null;
    }
    private static boolean isValid(String username, String password) throws SQLException {
        DatabaseDriver driver = DatabaseSingleton.getInstance();
//      driver.connect();
        //If the user has a password
        if(driver.getPassword(username)!=null){
            return password.equals(driver.getPassword(username));
        }
        return false;
    }
}
