package edu.virginia.sde.reviews;

public class Course {
    private String courseSubject;
    private String courseTitle;
    private int courseNumber;


    public Course(String courseSubject, int courseNumber, String courseTitle){
        this.courseSubject = courseSubject;
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
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

    @Override
    public String toString() {
        return "Course{" +
                "subject='" + courseSubject + '\'' +
                ", number=" + courseNumber +
                ", title='" + courseTitle + '\'' +
                '}';
    }



}
