package com.wkp.po;

public class User {
    private Identity identity;
    private String personID;

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

    public User(Identity identity, String personID) {
        this.identity = identity;
        this.personID = personID;
    }
}
