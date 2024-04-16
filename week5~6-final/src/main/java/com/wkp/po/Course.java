package com.wkp.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Course {
    private String method;
    private int courseID;
    private String courseName;
    private String courseDescription;
    private LocalDateTime courseStartTime;
    private LocalDateTime courseEndTime;
    private int studentNumberLimitation;
    private ArrayList<Lesson> lessons;
    //private Lesson[] lessons;
    private String teacherID;
    private String teacherName;

    @Override
    public String toString() {
        return "Course{" +
                "method='" + method + '\'' +
                ", courseID=" + courseID +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", courseStartTime=" + courseStartTime +
                ", courseEndTime=" + courseEndTime +
                ", studentNumberLimitation=" + studentNumberLimitation +
                ", lessons=" + lessons +
                ", teacherID='" + teacherID + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }

    public Course(String teacherID, String teacherName, String method, String courseName, String courseDescription, LocalDateTime courseStartTime, LocalDateTime courseEndTime, int studentNumberLimitation, ArrayList<Lesson> lessons) {
        this.method = method;
        this.teacherID=teacherID;
        this.teacherName=teacherName;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseStartTime=courseStartTime;
        this.courseEndTime = courseEndTime;
        this.studentNumberLimitation = studentNumberLimitation;
        this.lessons = lessons;
        this.courseID = new Random().nextInt(9000) + 1000;
    }
//    public Course(String teacherID,String teacherName,String method, String courseName, String courseDescription,LocalDateTime courseStartTime,LocalDateTime courseEndTime, int studentNumberLimitation, ArrayList<Lesson> lessons) {
//        this.method = method;
//        this.teacherID=teacherID;
//        this.teacherName=teacherName;
//        this.courseName = courseName;
//        this.courseDescription = courseDescription;
//        this.courseStartTime=courseStartTime;
//        this.courseEndTime = courseEndTime;
//        this.studentNumberLimitation = studentNumberLimitation;
//        //this.lessons = lessons;
//        this.courseID = new Random().nextInt(9000) + 1000;
//    }
    public int getCourseID() {
        return courseID;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public LocalDateTime getCourseStartTime() {
        return courseStartTime;
    }

    public void setCourseStartTime(LocalDateTime courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    public LocalDateTime getCourseEndTime() {
        return courseEndTime;
    }

    public void setCourseEndTime(LocalDateTime courseEndTime) {
        this.courseEndTime = courseEndTime;
    }

    public int getStudentNumberLimitation() {
        return studentNumberLimitation;
    }

    public void setStudentNumberLimitation(int studentNumberLimitation) {
        this.studentNumberLimitation = studentNumberLimitation;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }


//    public Lesson[] getLessons() {
//        return lessons;
//    }
//
//    public void setLessons(Lesson[] lessons) {
//        this.lessons = lessons;
//    }
}
