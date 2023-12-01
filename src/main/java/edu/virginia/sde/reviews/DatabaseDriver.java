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

    public boolean courseExists(Course course) throws SQLException {
        String findCourses = "SELECT * FROM Courses WHERE (Subject, Number, Title) = (?, ?, ?)";
        PreparedStatement prepStatement = connection.prepareStatement(findCourses);
        prepStatement.setString(1, course.getCourseSubject());
        prepStatement.setInt(2, course.getCourseNumber());
        prepStatement.setString(3, course.getCourseTitle());
        ResultSet rs = prepStatement.executeQuery();

        if(rs.next()) {
            rs.close();
            prepStatement.close();
            return true;
        }
        rs.close();
        prepStatement.close();
        return false;
    }
    public void addCourse(Course course) throws SQLException {
        //TODO: implement
        String insertQuery = "INSERT INTO Courses (Subject, Number, Title) VALUES (?, ?, ?)";
        PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
        if(!courseExists(course)) {
            insertStmt.setString(1, course.getCourseSubject());
            insertStmt.setInt(2, course.getCourseNumber());
            insertStmt.setString(3, course.getCourseTitle());
            insertStmt.executeUpdate();
        }
        insertStmt.close();
    }

//    public void addReview(String user, String pass) throws SQLException {
//        //TODO: implement
//        String insertQuery = "INSERT INTO Users (id, Password) VALUES (?, ?)";
//        PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
//        if(getPassword(user) == null) {
//            insertStmt.setString(1, user);
//            insertStmt.setString(2, pass);
//            insertStmt.executeUpdate();
//        }
//        insertStmt.close();
//    }

    public List<Course> getCoursesByName(String search) throws SQLException{
        String findCourses = "SELECT * FROM Courses WHERE Title LIKE ? COLLATE NOCASE";
        List<Course> c = new ArrayList<>();
        PreparedStatement prepStatement = connection.prepareStatement(findCourses);
        prepStatement.setString(1, "%"+search+"%");
        ResultSet rs = prepStatement.executeQuery();

        while(rs.next()) {
            String subject = rs.getString("Subject");
            int number = rs.getInt("Number");
            String title = rs.getString("Title");

            Course temp = new Course(subject, number, title);
            c.add(temp);
        }
        rs.close();
        prepStatement.close();
        return c;
    }
    public List<Course> getCoursesBySubject(String search) throws SQLException {
        String findCourses = "SELECT * FROM Courses WHERE Subject LIKE ? COLLATE NOCASE";
        List<Course> c = new ArrayList<>();
        PreparedStatement prepStatement = connection.prepareStatement(findCourses);
        prepStatement.setString(1, "%"+search+"%");
        ResultSet rs = prepStatement.executeQuery();

        while(rs.next()) {
            String subject = rs.getString("Subject");
            int number = rs.getInt("Number");
            String title = rs.getString("Title");

            Course temp = new Course(subject, number, title);
            c.add(temp);
        }
        rs.close();
        prepStatement.close();
        return c;
    }
    public List<Course> getCoursesByNumber(int num) throws SQLException {
        String findCourses = "SELECT * FROM Courses WHERE Number = ?";
        List<Course> c = new ArrayList<>();
        PreparedStatement prepStatement = connection.prepareStatement(findCourses);
        prepStatement.setInt(1, num);
        ResultSet rs = prepStatement.executeQuery();

        while(rs.next()) {
            String subject = rs.getString("Subject");
            int number = rs.getInt("Number");
            String title = rs.getString("Title");

            Course temp = new Course(subject, number, title);
            c.add(temp);
        }
        rs.close();
        prepStatement.close();
        return c;
    }

//    public List<Review> getReviewsFromUser(String user) throws SQLException{
//        String findReviews = "SELECT * FROM Reviews WHERE UserID = ?";
//        String findCourse = "SELECT * FROM Courses WHERE id = ?";
//        List<Review> r = new ArrayList<>();
//        PreparedStatement prepReviewStatement = connection.prepareStatement(findReviews);
//        PreparedStatement prepCourseStatement = connection.prepareStatement(findCourse);
//        prepReviewStatement.setString(1, user);
//        ResultSet rs = prepReviewStatement.executeQuery();
//
////    CourseID, UserID, Rating, Review, Timestamp
//        while(rs.next()) {
//            String subject = rs.getString("CourseID");
//            int number = rs.getInt("Number");
//            String title = rs.getString("Title");
//            prepCourseStatement.setInt(1, rs.getInt("id"));
//            ResultSet rs2 =
//                    Course temp = new Course(subject, number, title);
//            c.add(temp);
//        }
//        rs.close();
//        prepStatement.close();
//        return c;
//    }
//    public void deleteReview(Review review) {
//
//    }
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
        d.createTables();
//        d.addUser("jinwookim", "password1");
//        d.addUser("smatt", "password2");
//        d.addUser("vineelk", "password3");
//        d.addUser("jamtran", "password4");
//        System.out.println(d.getPassword("jamtran"));
        Course temp = new Course("CS", 3100, "Data Structures 2");
        d.addCourse(temp);
        d.commit();
        d.disconnect();
    }
}