package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Userdetail {

    @PrimaryKey(autoGenerate = true)
    int id;

    String userid;
    String password;

    public Userdetail(String userid, String password) {
        this.userid = userid;
        this.password = password;


    }

    public Userdetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
