package com.bibleproject.knowbibleverse;

public class Level {

    private String id;
    private String username;
    private String email;
    private String score;
    private String date;

    public Level (){

    }

    public Level(String id, String username, String email, String score, String date) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.score = score;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
