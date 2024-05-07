package com.paisalo.newinternalsourcingapp.ModelsRetrofit.TopAdImageModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageDataModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ImageModel data;

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

    public ImageModel getData() {
        return data;
    }

    public void setData(ImageModel data) {
        this.data = data;
    }

}