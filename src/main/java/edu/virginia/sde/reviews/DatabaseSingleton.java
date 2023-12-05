package edu.virginia.sde.reviews;

import org.sqlite.SQLiteException;

import java.sql.SQLException;

public class DatabaseSingleton {
    private static DatabaseSingleton instance;

    private DatabaseDriver databaseDriver;
    private DatabaseSingleton(DatabaseDriver driver) {
        this.databaseDriver = driver;
    }

    public static DatabaseDriver getInstance() {
        if (instance == null) {
            DatabaseDriver driver = new DatabaseDriver("CruddyCoursework.sqlite");
            instance = new DatabaseSingleton(driver);
        }

        try {
            if (!instance.databaseDriver.isConnected()) {
                instance.databaseDriver.connect();
            }
        } catch(SQLException e) {
            return null;
        }

        return instance.databaseDriver;
    }
}
