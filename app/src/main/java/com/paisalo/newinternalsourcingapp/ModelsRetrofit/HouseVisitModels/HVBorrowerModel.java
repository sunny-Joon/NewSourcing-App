package com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HVBorrowerModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private HVBorrowerDataModel data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HVBorrowerDataModel getData() {
        return data;
    }

    public void setData(HVBorrowerDataModel data) {
        this.data = data;
    }

}
