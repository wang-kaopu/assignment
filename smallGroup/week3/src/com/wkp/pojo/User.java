package com.wkp.pojo;

public class User {
    String username;
    String password;
    int gender;

    public User(String username, String password, int gender) {
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\t\t\"username\": \"" +
                username +
                "\",\n" +
                "\t\t\t\"password\": \"" +
                password +
                "\"}";
    }


}
