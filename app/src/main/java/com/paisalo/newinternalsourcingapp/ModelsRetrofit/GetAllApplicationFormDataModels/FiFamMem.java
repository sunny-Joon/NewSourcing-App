package com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FiFamMem implements Serializable {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("memName")
    @Expose
    private String memName;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("relationWBorrower")
    @Expose
    private String relationWBorrower;
    @SerializedName("health")
    @Expose
    private String health;
    @SerializedName("educatioin")
    @Expose
    private String educatioin;
    @SerializedName("schoolType")
    @Expose
    private String schoolType;
    @SerializedName("business")
    @Expose
    private String business;
    @SerializedName("businessType")
    @Expose
    private String businessType;
    @SerializedName("income")
    @Expose
    private Integer income;
    @SerializedName("incomeType")
    @Expose
    private String incomeType;
    @SerializedName("autoID")
    @Expose
    private Integer autoID;

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

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelationWBorrower() {
        return relationWBorrower;
    }

    public void setRelationWBorrower(String relationWBorrower) {
        this.relationWBorrower = relationWBorrower;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getEducatioin() {
        return educatioin;
    }

    public void setEducatioin(String educatioin) {
        this.educatioin = educatioin;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public Integer getAutoID() {
        return autoID;
    }

    public void setAutoID(Integer autoID) {
        this.autoID = autoID;
    }

}