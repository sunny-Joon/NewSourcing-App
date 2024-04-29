package com.paisalo.newinternalsourcingapp.ModelsRetrofit.OCRScanModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdharDataModel {


    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("pan_No")
    @Expose
    private Object panNo;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("adharId")
    @Expose
    private Object adharId;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("voterfatherName")
    @Expose
    private Object voterfatherName;
    @SerializedName("voterHusbandName")
    @Expose
    private Object voterHusbandName;
    @SerializedName("guardianName")
    @Expose
    private String guardianName;
    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("voterId")
    @Expose
    private Object voterId;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("error_reason")
    @Expose
    private Object errorReason;
    @SerializedName("error_code")
    @Expose
    private Object errorCode;
    @SerializedName("error_message")
    @Expose
    private Object errorMessage;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("reasonPhase")
    @Expose
    private Object reasonPhase;
    @SerializedName("isSuccessStatusCode")
    @Expose
    private Boolean isSuccessStatusCode;
    @SerializedName("responseContent")
    @Expose
    private Object responseContent;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Object getPanNo() {
        return panNo;
    }

    public void setPanNo(Object panNo) {
        this.panNo = panNo;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getAdharId() {
        return adharId;
    }

    public void setAdharId(Object adharId) {
        this.adharId = adharId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Object getVoterfatherName() {
        return voterfatherName;
    }

    public void setVoterfatherName(Object voterfatherName) {
        this.voterfatherName = voterfatherName;
    }

    public Object getVoterHusbandName() {
        return voterHusbandName;
    }

    public void setVoterHusbandName(Object voterHusbandName) {
        this.voterHusbandName = voterHusbandName;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Object getVoterId() {
        return voterId;
    }

    public void setVoterId(Object voterId) {
        this.voterId = voterId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(Object errorReason) {
        this.errorReason = errorReason;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Object getReasonPhase() {
        return reasonPhase;
    }

    public void setReasonPhase(Object reasonPhase) {
        this.reasonPhase = reasonPhase;
    }

    public Boolean getIsSuccessStatusCode() {
        return isSuccessStatusCode;
    }

    public void setIsSuccessStatusCode(Boolean isSuccessStatusCode) {
        this.isSuccessStatusCode = isSuccessStatusCode;
    }

    public Object getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(Object responseContent) {
        this.responseContent = responseContent;
    }

}