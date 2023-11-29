package edu.virginia.sde.reviews;

public class DatabaseSingleton {
    private static DatabaseSingleton instance;

    private DatabaseDriver databaseDriver;
    private DatabaseSingleton(DatabaseDriver driver) {
        this.databaseDriver = driver;
    }

    public static DatabaseDriver getInstance() {
        if (instance != null) {
            DatabaseDriver driver = new DatabaseDriver("CruddyCoursework.sqlite");
            instance = new DatabaseSingleton(driver);
        }

        return instance.databaseDriver;
    }
}
