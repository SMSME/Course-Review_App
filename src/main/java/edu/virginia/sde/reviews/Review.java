package edu.virginia.sde.reviews;

import java.sql.Time;
import java.sql.Timestamp;

public class Review {
    private Course course;
    private int rating;
    private Timestamp timestamp;
    private String comment;

    public Review(Course course, int rating, Timestamp timestamp, String comment) {
        this.course = course;
        this.rating = rating;
        this.timestamp = timestamp;
        this.comment = comment;
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

    public Boolean hasComment() {
        if (comment.equals("")) {
            return false;
        }
        return true;
    }
}
