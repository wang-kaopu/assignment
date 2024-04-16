package com.wkp.po;

public class User {
    private Identity identity;
    private String personID;
    private String name;
    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(Identity identity, String personID, String name) {
        this.identity = identity;
        this.personID = personID;
        this.name=name;
    }

    @Override
    public String toString() {
        return "User{" +
                "identity=" + identity +
                ", personID='" + personID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
