package com.paisalo.newinternalsourcingapp.ModelsRetrofit.LeaderBoardModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaderboardModel {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<LeaderboardDataModel> data;

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

    public List<LeaderboardDataModel> getData() {
        return data;
    }

    public void setData(List<LeaderboardDataModel> data) {
        this.data = data;
    }
}
