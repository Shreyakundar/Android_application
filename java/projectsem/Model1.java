package com.example.projectsem;

public class Model1 {


    String name,mble,gender,city,state,uid;
    public Model1()
    {

    }

    public Model1(String name, String mble, String gender, String city, String state,String uid) {
        this.name = name;
        this.mble = mble;
        this.gender = gender;
        this.city = city;
        this.state = state;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMble() {
        return mble;
    }

    public void setMble(String mble) {
        this.mble = mble;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}