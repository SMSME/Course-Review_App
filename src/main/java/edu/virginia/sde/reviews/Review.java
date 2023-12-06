package edu.virginia.sde.reviews;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class Review {
    private Course course;
    private int rating;
    private Timestamp timestamp;
    private String comment;
    private String user;

    public Review(Course course, int rating, Timestamp timestamp, String comment, String user) {
        this.course = course;
        this.rating = rating;
        this.timestamp = timestamp;
        this.comment = comment;
        this.user = user;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getRating() {
        return rating;
    }
    public void setTimeStamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setComment(String string) {
        this.comment = string;
    }
    public String getComment() {
        return comment;
    }
    public void setUser(String user) { this.user = user; }
    public String getUser() { return user; }
    public IntegerProperty getDisplayRating() {
        return new SimpleIntegerProperty(rating);
    }
    public StringProperty getDisplayComment() {
        return new SimpleStringProperty(comment);
    }
    public void setCourse(Course course) {this.course = course; }

    public Course getCourse() { return course; }


    @Override
    public String toString(){
        int truncate = comment.length();
        String end = "";
        int titleTruncate = course.getCourseTitle().length();
        String titleEnd = "";
        if(course.getCourseTitle().length()>20){
            titleTruncate = 20;
            titleEnd = "...";
        }
        if (comment.length() > 29){
            truncate = 29;
            end = "...";
        }
        return String.format(" %-31s %-32s %-25s %-50s %-10s",
                course.getCourseSubject().toUpperCase(),
                course.getCourseNumber(),
                rating,
                course.getCourseTitle().substring(0, titleTruncate)+titleEnd,
                comment.substring(0, truncate) + end);
    }

    public Boolean hasComment() {
        if (comment.isEmpty()) {
            return false;
        }
        return true;
    }
}
