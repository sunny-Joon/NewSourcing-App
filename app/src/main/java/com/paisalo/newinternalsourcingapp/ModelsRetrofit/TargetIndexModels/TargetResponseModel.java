package com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetIndexModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TargetResponseModel {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ResponseModel> responseModels;

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

    public List<ResponseModel> getResponseModels() {
        return responseModels;
    }

    public void setData(List<ResponseModel> responseModels) {
        this.responseModels = responseModels;
    }

}

