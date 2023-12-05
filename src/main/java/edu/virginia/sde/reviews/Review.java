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
        if(comment.length()<42){
            return String.format("%" + 28 + "s %-20s %-19d %-19d %-18s %-1s", "",
                    course.getCourseSubject().toUpperCase(),
                    course.getCourseNumber(),
                    rating,
                    course.getCourseTitle(),
                    comment);
        }
        return String.format("%" + 28 + "s" + "%-20s" + " %-19d" + " %-19d" + " %-20s" + " %-1s", "",
                course.getCourseSubject().toUpperCase(),
                course.getCourseNumber(),
                rating,
                course.getCourseTitle(),
                comment.substring(0,42) + "...");
    }
    public Boolean hasComment() {
        if (comment.isEmpty()) {
            return false;
        }
        return true;
    }
}
