package com.paisalo.newinternalsourcingapp.ModelsRetrofit.UpdateFiModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycUpdateModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private KycUpdateDataModel data;

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

    public KycUpdateDataModel getData() {
        return data;
    }

    public void setData(KycUpdateDataModel data) {
        this.data = data;
    }

}