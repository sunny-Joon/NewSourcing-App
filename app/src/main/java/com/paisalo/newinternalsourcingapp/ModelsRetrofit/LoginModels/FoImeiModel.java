package com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoImeiModel {

    @SerializedName("imeino")
    @Expose
    private Long imeino;
    @SerializedName("actualYN")
    @Expose
    private String actualYN;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("newAppVerison")
    @Expose
    private Integer newAppVerison;
    @SerializedName("appDownPath")
    @Expose
    private String appDownPath;
    @SerializedName("requestUrl")
    @Expose
    private String requestUrl;
    @SerializedName("simno")
    @Expose
    private String simno;

    public Long getImeino() {
        return imeino;
    }

    public void setImeino(Long imeino) {
        this.imeino = imeino;
    }

    public String getActualYN() {
        return actualYN;
    }

    public void setActualYN(String actualYN) {
        this.actualYN = actualYN;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getNewAppVerison() {
        return newAppVerison;
    }

    public void setNewAppVerison(Integer newAppVerison) {
        this.newAppVerison = newAppVerison;
    }

    public String getAppDownPath() {
        return appDownPath;
    }

    public void setAppDownPath(String appDownPath) {
        this.appDownPath = appDownPath;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getSimno() {
        return simno;
    }

    public void setSimno(String simno) {
        this.simno = simno;
    }

}