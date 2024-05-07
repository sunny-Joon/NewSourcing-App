package com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetIndexModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kO_ID")
    @Expose
    private String kOID;
    @SerializedName("targetCommAmt")
    @Expose
    private Integer targetCommAmt;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private Integer year;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getkOID() {
        return kOID;
    }

    public void setkOID(String kOID) {
        this.kOID = kOID;
    }

    public Integer getTargetCommAmt() {
        return targetCommAmt;
    }

    public void setTargetCommAmt(Integer targetCommAmt) {
        this.targetCommAmt = targetCommAmt;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
