package com.paisalo.newinternalsourcingapp.ModelsRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchStatusResponce {
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private BranchStatusDataModel data;

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

    public BranchStatusDataModel getData() {
        return data;
    }

    public void setData(BranchStatusDataModel data) {
        this.data = data;
    }
}
