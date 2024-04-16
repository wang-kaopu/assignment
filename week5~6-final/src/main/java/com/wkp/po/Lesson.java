package com.wkp.po;

import java.util.Random;

public class Lesson {
    private int courseID;
    private String chapterName;
    private String lessonName;
    private int lessonID;//四位数
    private String lessonContext;

    public String getLessonContext() {
        return lessonContext;
    }

    public void setLessonContext(String lessonContext) {
        this.lessonContext = lessonContext;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "courseID='" + courseID + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", lessonName='" + lessonName + '\'' +
                ", lessonID=" + lessonID +
                ", lessonContext='" + lessonContext + '\'' +
                '}';
    }
}
