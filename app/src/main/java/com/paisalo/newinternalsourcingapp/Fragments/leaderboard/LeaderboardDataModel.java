package com.paisalo.newinternalsourcingapp.Fragments.leaderboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderboardDataModel {
    @SerializedName("kO_ID")
    @Expose
    private String kOID;
    @SerializedName("targetCommAmt")
    @Expose
    private Double targetCommAmt;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("position")
    @Expose
    private Integer position;





    public String getkOID() {
        return kOID;
    }

    public void setkOID(String kOID) {
        this.kOID = kOID;
    }

    public Double getTargetCommAmt() {
        return targetCommAmt;
    }

    public void setTargetCommAmt(Double targetCommAmt) {
        this.targetCommAmt = targetCommAmt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
