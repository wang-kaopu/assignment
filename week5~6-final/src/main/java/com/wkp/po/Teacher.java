package com.wkp.po;

public class Teacher extends User {
    private String email;
    private String teacherName;
    private String teacherQQ;
    private String teacherDescription;
//    public Teacher(Identity identity, String personID, String name) {
//        super(identity, personID, name);
//    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacherQQ() {
        return teacherQQ;
    }

    public void setTeacherQQ(String teacherQQ) {
        this.teacherQQ = teacherQQ;
    }

    public String getTeacherDescription() {
        return teacherDescription;
    }

    public void setTeacherDescription(String teacherDescription) {
        this.teacherDescription = teacherDescription;
    }

    public Teacher(Identity identity, String personID, String name, String email, String teacherQQ, String teacherDescription) {
        super(identity, personID, name);
        this.email = email;
        this.teacherQQ = teacherQQ;
        this.teacherDescription = teacherDescription;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "email='" + email + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherQQ='" + teacherQQ + '\'' +
                ", teacherDescription='" + teacherDescription + '\'' +
                '}';
    }
}
