package com.sangmyung.teamprojectfb.activity;

public class MemberInfo {
    private String univ;
    private String name;
    private String phoneNumber;
    private String birthDay;
    private String address;


    public MemberInfo(String univ,String name, String phoneNumber, String birthDay, String address){
        this.univ=univ;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.address = address;
    }

    public MemberInfo(String univ){
        this.univ=univ;
    }

    public String getUniv(){return this.univ;}
    public void setUniv(String univ){
        this.univ = univ;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getBirthDay(){
        return this.birthDay;
    }
    public void setBirthDay(String birthDay){
        this.birthDay = birthDay;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }
}