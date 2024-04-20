package com.wkp.po;

import java.time.LocalDateTime;

public class Message {
    private String message;
    private int personID;
    private String personName;
    private LocalDateTime time;

    public Message(String message, int personID, String personName, LocalDateTime time) {
        this.message = message;
        this.personID = personID;
        this.personName = personName;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", personID=" + personID +
                ", personName='" + personName + '\'' +
                ", time=" + time +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
