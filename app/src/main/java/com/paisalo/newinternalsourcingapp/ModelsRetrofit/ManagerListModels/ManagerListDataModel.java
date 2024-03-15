package com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ManagerListDataModel {

    @SerializedName("imeino")
    @Expose
    private Long imeino;
    @SerializedName("foCode")
    @Expose
    private String foCode;
    @SerializedName("foName")
    @Expose
    private String foName;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("isActive")
    @Expose
    private String isActive;
    @SerializedName("dataBase")
    @Expose
    private String dataBase;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("areaCd")
    @Expose
    private String areaCd;
    @SerializedName("areaName")
    @Expose
    private String areaName;
    @SerializedName("creatorDesc")
    @Expose
    private String creatorDesc;
    @SerializedName("fiExecCode")
    @Expose
    private String fiExecCode;
    @SerializedName("fiExecName")
    @Expose
    private String fiExecName;

    public Long getImeino() {
        return imeino;
    }

    public void setImeino(Long imeino) {
        this.imeino = imeino;
    }

    public String getFoCode() {
        return foCode;
    }

    public void setFoCode(String foCode) {
        this.foCode = foCode;
    }

    public String getFoName() {
        return foName;
    }

    public void setFoName(String foName) {
        this.foName = foName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCreatorDesc() {
        return creatorDesc;
    }

    public void setCreatorDesc(String creatorDesc) {
        this.creatorDesc = creatorDesc;
    }

    public String getFiExecCode() {
        return fiExecCode;
    }

    public void setFiExecCode(String fiExecCode) {
        this.fiExecCode = fiExecCode;
    }

    public String getFiExecName() {
        return fiExecName;
    }

    public void setFiExecName(String fiExecName) {
        this.fiExecName = fiExecName;
    }

}