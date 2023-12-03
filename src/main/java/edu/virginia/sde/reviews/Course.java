package edu.virginia.sde.reviews;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private String courseSubject;
    private String courseTitle;
    private int courseNumber;
    private List<Review> reviews;

    public String formatTitle(String title){
        title = title.toLowerCase();
        StringBuffer buffer = new StringBuffer();
        buffer.append(Character.toUpperCase(title.charAt(0)));
        for (int i = 1; i < title.length(); i++){
            if(title.charAt(title.length()-1) == ' '){
                break;
            }
            if (title.charAt(i) == ' '){
                buffer.append(title.charAt(i));
                buffer.append(Character.toUpperCase(title.charAt(i+1)));
                i++;
            }
            else{
            buffer.append(title.charAt(i));}
        }
        return buffer.toString();
    }


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
        this.courseSubject = courseSubject.toUpperCase();
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        // Compare relevant fields (courseSubject, courseNumber, courseTitle)
        return Objects.equals(courseSubject, course.courseSubject) &&
                courseNumber == course.courseNumber &&
                Objects.equals(courseTitle, course.courseTitle);
    }



    @Override
    public String toString() {
        return courseSubject.toUpperCase() +  ' ' +
                courseNumber + ' ' +  formatTitle(courseTitle) + ' ' +
                getAvgRating();
    }}