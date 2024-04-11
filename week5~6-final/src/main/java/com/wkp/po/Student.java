package com.wkp.po;

import java.util.Objects;

public class Student {
    private String personID;

    public Student(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
