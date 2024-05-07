package com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BorrowerListModel {

    @SerializedName("statusCode")
    @Expose
    private int statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<BorrowerListDataModel> data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BorrowerListDataModel> getData() {
        return data;
    }

    public void setData(List<BorrowerListDataModel> data) {
        this.data = data;
    }

}