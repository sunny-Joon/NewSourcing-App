package com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycDocsFlag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycDocsFlag {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private KycDocsFlagDataModel data;

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

    public KycDocsFlagDataModel getData() {
        return data;
    }

    public void setData(KycDocsFlagDataModel data) {
        this.data = data;
    }

}