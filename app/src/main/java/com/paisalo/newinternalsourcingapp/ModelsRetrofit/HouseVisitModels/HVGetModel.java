package com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HVGetModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private HVGetDataModel data;

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

    public HVGetDataModel getData() {
        return data;
    }

    public void setData(HVGetDataModel data) {
        this.data = data;
    }

}
