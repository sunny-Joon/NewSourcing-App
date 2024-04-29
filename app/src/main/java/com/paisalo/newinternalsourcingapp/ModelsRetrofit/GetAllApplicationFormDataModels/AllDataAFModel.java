package com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllDataAFModel implements Serializable {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private AllDataAFDataModel data;

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

    public AllDataAFDataModel getData() {
        return data;
    }

    public void setData(AllDataAFDataModel data) {
        this.data = data;
    }

}