package edu.virginia.sde.reviews;

import java.sql.*;
import java.util.*;
import java.util.function.LongUnaryOperator;

public class DatabaseDriver {
    private final String sqliteFilename;
    private Connection connection;

    public DatabaseDriver (String sqlListDatabaseFilename) {
        this.sqliteFilename = sqlListDatabaseFilename;
    }

    /**
     * Connect to a SQLite Database. This turns out Foreign Key enforcement, and disables auto-commits
     * @throws SQLException
     */
    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            throw new IllegalStateException("The connection is already opened");
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + sqliteFilename);
        //the next line enables foreign key enforcement - do not delete/comment out
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        //the next line disables auto-commit - do not delete/comment out
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    public void createTables() throws SQLException {
        //TODO: implement
        String createUsers = "CREATE TABLE IF NOT EXISTS Users (id TEXT PRIMARY KEY, Password TEXT NOT NULL)";
        String createCourses = "CREATE TABLE IF NOT EXISTS Courses (id INTEGER PRIMARY KEY, Subject TEXT NOT NULL, Number INTEGER NOT NULL, Title TEXT NOT NULL)";
        String createReviews = "CREATE TABLE IF NOT EXISTS Reviews (INTEGER CourseID REFERENCES Courses(id) ON DELETE CASCADE, UserID TEXT REFERENCES Users(id) ON DELETE CASCADE, Rating INTEGER NOT NULL, Review TEXT, Timestamp TEXT NOT NULL)";
        Statement statement = connection.createStatement();

        statement.execute(createUsers);
        statement.execute(createCourses);
        statement.execute(createReviews);

        statement.close();
    }

    public String getPassword(String user) throws SQLException {
        String find = "SELECT * FROM Users WHERE id = ?";

        PreparedStatement prepStatement = connection.prepareStatement(find);
        prepStatement.setString(1, user);
        ResultSet rs = prepStatement.executeQuery();
        if(rs.next()) {
            String password = rs.getString("Password");
            prepStatement.close();
            rs.close();
            return password;
        }
        prepStatement.close();
        rs.close();
        return null;
    }
    public void addUser(String user, String pass) throws SQLException {
        //TODO: implement
        String insertQuery = "INSERT INTO Users (id, Password) VALUES (?, ?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
        if(getPassword(user) == null) {
            insertStmt.setString(1, user);
            insertStmt.setString(2, pass);
            insertStmt.executeUpdate();
        }
        insertStmt.close();
    }
    public void clearTables() throws SQLException {
        //TODO: implement
        String delRoute = "DELETE FROM Users";
        String delBus = "DELETE FROM Courses";
        String delStops = "DELETE FROM Reviews";

        Statement statement = connection.createStatement();
        statement.execute(delRoute);
        statement.execute(delBus);
        statement.execute(delStops);

        statement.close();

    }
    public static void main(String[] args) throws SQLException {
        DatabaseDriver d = new DatabaseDriver("CruddyCoursework.sqlite");
        d.connect();
        d.clearTables();
        d.createTables();
        d.addUser("jinwookim", "password1");
        d.addUser("smatt", "password2");
        d.addUser("vineelk", "password3");
        d.addUser("jamtran", "password4");
        d.commit();
        d.disconnect();
    }
}