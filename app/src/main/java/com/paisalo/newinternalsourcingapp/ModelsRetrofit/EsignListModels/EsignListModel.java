package com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class EsignListModel {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private EsignListDataModel data;

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

    public EsignListDataModel getData() {
        return data;
    }

    public void setData(EsignListDataModel data) {
        this.data = data;
    }

}