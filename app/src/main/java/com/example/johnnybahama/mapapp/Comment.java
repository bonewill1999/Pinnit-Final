package com.example.johnnybahama.mapapp;

public class Comment {
    private String user, comment, date, likes;

    public Comment(String user, String comment/*, String date, String likes*/) {
        this.user = user;
        this.comment = comment;
        //this.date = date;
        //this.likes = likes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /*
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
    */
}
