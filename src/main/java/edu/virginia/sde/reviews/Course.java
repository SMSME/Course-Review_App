package edu.virginia.sde.reviews;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseSubject;
    private String courseTitle;
    private int courseNumber;
    private List<Review> reviews;


    public Course(String courseSubject, int courseNumber, String courseTitle){
        this.courseSubject = courseSubject;
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
        this.reviews = new ArrayList<>();
    }

    public String getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(String courseSubject) {
        this.courseSubject = courseSubject;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public List<Review> getReviews(){
        return reviews;
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    public double getAvgRating(){
        if (reviews.isEmpty()){
            return 0.0;
        }
        double sum = 0.0;
        for (Review review: reviews){
            sum += review.getRating();
        }
        return sum/reviews.size();
    }

    @Override
    public String toString() {
        return "Course{" +
                "subject='" + courseSubject + '\'' +
                ", number=" + courseNumber +
                ", title='" + courseTitle + '\'' +
                ", reviews=" + reviews +
                '}';
    }}