package com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycDocsFlag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrDoc {

    @SerializedName("addharExists")
    @Expose
    private Boolean addharExists;
    @SerializedName("aadharPath")
    @Expose
    private String aadharPath;
    @SerializedName("aadharBPath")
    @Expose
    private Object aadharBPath;
    @SerializedName("voterExists")
    @Expose
    private Boolean voterExists;
    @SerializedName("voterPath")
    @Expose
    private Object voterPath;
    @SerializedName("voterBPath")
    @Expose
    private Object voterBPath;
    @SerializedName("panExists")
    @Expose
    private Boolean panExists;
    @SerializedName("panPath")
    @Expose
    private Object panPath;
    @SerializedName("drivingExists")
    @Expose
    private Boolean drivingExists;
    @SerializedName("drivingPath")
    @Expose
    private Object drivingPath;
    @SerializedName("gid")
    @Expose
    private String gid;

    public Boolean getAddharExists() {
        return addharExists;
    }

    public void setAddharExists(Boolean addharExists) {
        this.addharExists = addharExists;
    }

    public String getAadharPath() {
        return aadharPath;
    }

    public void setAadharPath(String aadharPath) {
        this.aadharPath = aadharPath;
    }

    public Object getAadharBPath() {
        return aadharBPath;
    }

    public void setAadharBPath(Object aadharBPath) {
        this.aadharBPath = aadharBPath;
    }

    public Boolean getVoterExists() {
        return voterExists;
    }

    public void setVoterExists(Boolean voterExists) {
        this.voterExists = voterExists;
    }

    public Object getVoterPath() {
        return voterPath;
    }

    public void setVoterPath(Object voterPath) {
        this.voterPath = voterPath;
    }

    public Object getVoterBPath() {
        return voterBPath;
    }

    public void setVoterBPath(Object voterBPath) {
        this.voterBPath = voterBPath;
    }

    public Boolean getPanExists() {
        return panExists;
    }

    public void setPanExists(Boolean panExists) {
        this.panExists = panExists;
    }

    public Object getPanPath() {
        return panPath;
    }

    public void setPanPath(Object panPath) {
        this.panPath = panPath;
    }

    public Boolean getDrivingExists() {
        return drivingExists;
    }

    public void setDrivingExists(Boolean drivingExists) {
        this.drivingExists = drivingExists;
    }

    public Object getDrivingPath() {
        return drivingPath;
    }

    public void setDrivingPath(Object drivingPath) {
        this.drivingPath = drivingPath;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

}