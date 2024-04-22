package com.wkp.po;

public class Answer {
    private String answer;
    private int personID;
    public Answer(String answer, int personID) {
        this.answer = answer;
        this.personID = personID;
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
