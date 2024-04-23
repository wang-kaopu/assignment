package com.wkp.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Problem {
    private String context;
    private String answer;
    private int lessonID;
    private int courseID;
    private int type;
    private String correctAnswer;
    private ArrayList<Answer> answerList;
    private int singleScore;

    public Problem(String context, String answer, int lessonID, int courseID, int type, String correctAnswer, int singleScore) {
        this.context = context;
        this.answer = answer;
        this.lessonID = lessonID;
        this.courseID = courseID;
        this.type = type;
        this.correctAnswer = correctAnswer;
        this.singleScore = singleScore;
    }

    public int getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(int singleScore) {
        this.singleScore = singleScore;
    }

    public Problem(String context, String answer, int lessonID, int courseID, int type, String correctAnswer, ArrayList<Answer> answerList) {
        this.context = context;
        this.answer = answer;
        this.lessonID = lessonID;
        this.courseID = courseID;
        this.type = type;
        this.correctAnswer = correctAnswer;
        this.answerList = answerList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(ArrayList<Answer> answerList) {
        this.answerList = answerList;
    }

    public Problem(String context, String answer, int lessonID, int courseID, int type, String correctAnswer) {
        this.context = context;
        this.answer = answer;
        this.lessonID = lessonID;
        this.courseID = courseID;
        this.type = type;
        this.correctAnswer = correctAnswer;
    }

    public Problem(String context, String answer, int lessonID, int courseID) {
        this.context = context;
        this.answer = answer;
        this.lessonID = lessonID;
        this.courseID = courseID;
    }

    public Problem(String context, String answer, int lessonID, int courseID, String correctAnswer) {
        this.context = context;
        this.answer = answer;
        this.lessonID = lessonID;
        this.courseID = courseID;
        this.correctAnswer = correctAnswer;
    }

    public Problem(String context, int lessonID, int courseID, int type) {
        this.context = context;
        this.lessonID = lessonID;
        this.courseID = courseID;
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Problem problem)) return false;
        return lessonID == problem.lessonID && courseID == problem.courseID && Objects.equals(context, problem.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context, lessonID, courseID);
    }

    @Override
    public String toString() {
        return "Problem{" +
                "context='" + context + '\'' +
                ", answer='" + answer + '\'' +
                ", lessonID=" + lessonID +
                ", courseID=" + courseID +
                ", type=" + type +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", answerList='" + answerList + '\'' +
                '}';
    }
}
