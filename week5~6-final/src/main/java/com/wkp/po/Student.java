package com.wkp.po;

import java.util.Objects;

public class Student extends User{
    private String studentName;
    private String personID;
    private String email;
    private String studentDescription;
    private int studentGrade;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String getPersonID() {
        return personID;
    }

    @Override
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentDescription() {
        return studentDescription;
    }

    public void setStudentDescription(String studentDescription) {
        this.studentDescription = studentDescription;
    }

    public int getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(int studentGrade) {
        this.studentGrade = studentGrade;
    }

    public Student(Identity identity, String personID, String name) {
        super(identity, personID, name);
    }

    public Student(String studentName, String personID, String email, String studentDescription, int studentGrade) {
        this.studentName = studentName;
        this.personID = personID;
        this.email = email;
        this.studentDescription = studentDescription;
        this.studentGrade = studentGrade;
    }
}
