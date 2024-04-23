package com.wkp.po;

public class Answer {
    private String answer;
    private int personID;
    private int singleScore;
    public Answer(String answer, int personID) {
        this.answer = answer;
        this.personID = personID;
    }

    public Answer(String answer, int personID, int singleScore) {
        this.answer = answer;
        this.personID = personID;
        this.singleScore = singleScore;
    }

    public int getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(int singleScore) {
        this.singleScore = singleScore;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }
}
