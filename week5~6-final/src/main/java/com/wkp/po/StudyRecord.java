package com.wkp.po;

public class StudyRecord {
    private String studyRecord;
    private int lessonID;

    public StudyRecord(String studyRecord, int lessonID) {
        this.studyRecord = studyRecord;
        this.lessonID = lessonID;
    }

    public String getStudyRecord() {
        return studyRecord;
    }

    public void setStudyRecord(String studyRecord) {
        this.studyRecord = studyRecord;
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }
}
