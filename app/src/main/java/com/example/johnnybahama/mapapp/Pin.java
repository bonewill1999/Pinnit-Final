package com.example.johnnybahama.mapapp;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class Pin {

    private Date date;
    private String originalPoster;
    private Double lat;
    private Double lng;
    private String postType;
    private String title;
    private int likes;
    public String body;
    private String ID;
    //private int votes;



    private ArrayList<Comment> comments = new ArrayList<Comment>();


    public Pin(Date date, String body, String title, Double lat, Double lng, String postType, String originalPoster, int likes, String ID){
        this.date = date;
        this.lat = lat;
        this.lng = lng;
        this.postType = postType;
        this.originalPoster = originalPoster;
        this.title = body;
        this.body = title;
        this.likes = likes;
        this.ID = ID;
    }

    public Pin(Date date, String body, String title, Double lat, Double lng, String postType, String originalPoster, int likes){
        this.date = date;
        this.lat = lat;
        this.lng = lng;
        this.postType = postType;
        this.originalPoster = originalPoster;
        this.title = body;
        this.body = title;
        this.likes = likes;
        this.ID = "";
    }


    //public void upVote(){
      //  votes ++;
    //}
    //public void downVote(){
      //  votes --;
    //}

    public void editBody(String body) {
        this.body = body ;
    }

    public void editTitle(String title) {
        this.title = title ;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public void setLocation(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public void setOrigionalPoster(String origionalPoster) {
        this.originalPoster = originalPoster;
    }


    public Double getLat(){ return lat;}
    public Double getLng(){ return lng;}

    public void deleteComment(Comment Comment){
        comments.remove(Comment);
    }

    public void addComment(Comment Comment){
        comments.add(Comment);
    }

    public Date getDate() {
        return this.date;
    }


    //public int getVotes() {
      //  return votes;
    //}

    public String getTitle() {
        return this.title;
    }

    public int getLikes(){
        return this.likes;
    }

    public void upVote(){
        this.likes +=1;
    }

    public void downVote(){
        this.likes -=1;
    }

    public String getPostType() {
        return this.postType;
    }

    public String getOriginalPoster(){
        return this.originalPoster;
    }

    public String getBody(){
        return this.body;
    }

    public String getID(){
        return this.ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }


    public ArrayList<Comment> getComments(){
        return comments;

    }


    public int generateWidth(dateClass currentDate, String sortPref) {

        int ratio = 1;

        if (sortPref == "likes") {

            if (this.likes < 0) {
                return (100 + this.likes);
            } else {
                return (100) + (this.likes * ratio);
            }


        }
        if (sortPref == "date") {

//            dateClass postDate = new dateClass(this.date);
//            int daysSince = postDate.getOldness(currentDate);
//
//            if (daysSince == 1)return 230;
//            if (daysSince > 1 && daysSince <= 3)return 200;
//            if (daysSince > 3 && daysSince <= 6)return 170;
//            if (daysSince > 6 && daysSince <= 10)return 130;
//            if (daysSince > 10 && daysSince <= 16)return 100;
//            if (daysSince > 16 && daysSince <= 26)return 50;
            return 50;


        }

        return 200;

      }

    public boolean isInRegion(LatLng middleScreen, int radius){
//        if(thisproperty == false)return false

        float distanceFloats[] = new float[] {1};
        Location.distanceBetween(middleScreen.latitude,middleScreen.latitude, lat, lng, distanceFloats);

        if(distanceFloats[0] < radius) {
            return true;
        }
        return false;

    }


    }



