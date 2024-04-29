package com.paisalo.newinternalsourcingapp.ModelsRetrofit.SaveFiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveFiModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private SaveFiDataModel data;

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

    public SaveFiDataModel getData() {
        return data;
    }

    public void setData(SaveFiDataModel data) {
        this.data = data;
    }

}