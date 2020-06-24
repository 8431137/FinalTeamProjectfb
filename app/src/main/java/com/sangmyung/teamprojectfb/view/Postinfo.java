package com.sangmyung.teamprojectfb.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Postinfo implements Serializable {

    private String title;
    private ArrayList<String> contents;
    private String  publisher;
    private Date createdAt;
    private String id;
    private String geocoder;
    private String deposit;

    public Postinfo(String title, ArrayList<String>  contents, String publisher, Date createdAt, String geocoder, String deposit, String id) {
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.geocoder = geocoder;
        this.id = id;
        this.deposit = deposit;
    }
    public Postinfo(String title, ArrayList<String>  contents, String publisher, Date createdAt, String geocoder, String deposit) {
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.geocoder= geocoder;
        this.deposit = deposit;
    }

    public Map<String, Object> getPostinfo(){ //파이어베이스 데이터베이스에 "id = null"값이 안뜨게 설정
        Map<String, Object> docData  = new HashMap<>();
        docData.put("title",title);
        docData.put("contents",contents);
        docData.put("publisher",publisher);
        docData.put("createdAt",createdAt);
        docData.put("geocoder",geocoder);
        docData.put("deposit",deposit);
        return docData;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String>  getContents() {
        return this.contents;
    }

    public void setContents(ArrayList<String>  contents) {
        this.contents = contents;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getGeocoder(){
        return this.geocoder;
    }

    public void setGeocoder(String address)
    {
        this.geocoder = geocoder;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getDeposit(){ return  this.deposit;}

    public  void setDeposit(String deposit){ this.deposit = deposit;}

}
