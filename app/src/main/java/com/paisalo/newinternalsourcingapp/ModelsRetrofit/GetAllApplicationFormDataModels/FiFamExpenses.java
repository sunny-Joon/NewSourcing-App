package com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FiFamExpenses implements Serializable {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("rent")
    @Expose
    private Integer rent;
    @SerializedName("fooding")
    @Expose
    private Integer fooding;
    @SerializedName("education")
    @Expose
    private Integer education;
    @SerializedName("health")
    @Expose
    private Integer health;
    @SerializedName("travelling")
    @Expose
    private Integer travelling;
    @SerializedName("entertainment")
    @Expose
    private Integer entertainment;
    @SerializedName("others")
    @Expose
    private Integer others;
    @SerializedName("homeType")
    @Expose
    private String homeType;
    @SerializedName("homeRoofType")
    @Expose
    private String homeRoofType;
    @SerializedName("toiletType")
    @Expose
    private String toiletType;
    @SerializedName("livingWSpouse")
    @Expose
    private String livingWSpouse;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Integer getFooding() {
        return fooding;
    }

    public void setFooding(Integer fooding) {
        this.fooding = fooding;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getTravelling() {
        return travelling;
    }

    public void setTravelling(Integer travelling) {
        this.travelling = travelling;
    }

    public Integer getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(Integer entertainment) {
        this.entertainment = entertainment;
    }

    public Integer getOthers() {
        return others;
    }

    public void setOthers(Integer others) {
        this.others = others;
    }

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }

    public String getHomeRoofType() {
        return homeRoofType;
    }

    public void setHomeRoofType(String homeRoofType) {
        this.homeRoofType = homeRoofType;
    }

    public String getToiletType() {
        return toiletType;
    }

    public void setToiletType(String toiletType) {
        this.toiletType = toiletType;
    }

    public String getLivingWSpouse() {
        return livingWSpouse;
    }

    public void setLivingWSpouse(String livingWSpouse) {
        this.livingWSpouse = livingWSpouse;
    }

}