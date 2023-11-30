package edu.virginia.sde.reviews;

import java.sql.Time;
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
    public void setCourse(Course course) { this.course = course; }
    public Course getCourse() { return course; }
    public void setUser(String user) { this.user = user; }
    public String getUser() { return user; }

    public Boolean hasComment() {
        if (comment.equals("")) {
            return false;
        }
        return true;
    }
}
