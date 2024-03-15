package com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ManagerListModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ManagerListDataModel> data;

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

    public List<ManagerListDataModel> getData() {
        return data;
    }

    public void setData(List<ManagerListDataModel> data) {
        this.data = data;
    }

}
