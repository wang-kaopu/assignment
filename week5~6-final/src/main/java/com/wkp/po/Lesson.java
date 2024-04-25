package com.wkp.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lesson {
    private int courseID;
    private String chapterName;
    private String lessonName;
    private int lessonID;//四位数
    private String lessonContext;
    private List<User> studyRecord;

    public List<User> getStudyRecord() {
        return studyRecord;
    }

    public Lesson(String lessonName, List<User> studyRecord) {
        this.lessonName = lessonName;
        this.studyRecord = studyRecord;
    }

    public void setStudyRecord(List<User> studyRecord) {
        this.studyRecord = studyRecord;
    }

    public Lesson(int courseID, String lessonName, int lessonID, String chapterName) {
        this.courseID = courseID;
        this.lessonName = lessonName;
        this.lessonID = lessonID;
        this.chapterName=chapterName;
    }

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
                "courseID=" + courseID +
                ", chapterName='" + chapterName + '\'' +
                ", lessonName='" + lessonName + '\'' +
                ", lessonID=" + lessonID +
                ", lessonContext='" + lessonContext + '\'' +
                ", studyRecord=" + studyRecord +
                '}';
    }
}
