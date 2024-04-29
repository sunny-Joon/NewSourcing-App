package com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FiFamLoan implements Serializable {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("loneeName")
    @Expose
    private String loneeName;
    @SerializedName("lenderName")
    @Expose
    private String lenderName;
    @SerializedName("lenderType")
    @Expose
    private String lenderType;
    @SerializedName("isMFI")
    @Expose
    private String isMFI;
    @SerializedName("loanReason")
    @Expose
    private String loanReason;
    @SerializedName("loanAmount")
    @Expose
    private Integer loanAmount;
    @SerializedName("loanEMIAmount")
    @Expose
    private Integer loanEMIAmount;
    @SerializedName("loanBalanceAmount")
    @Expose
    private Integer loanBalanceAmount;
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

    public String getLoneeName() {
        return loneeName;
    }

    public void setLoneeName(String loneeName) {
        this.loneeName = loneeName;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public String getLenderType() {
        return lenderType;
    }

    public void setLenderType(String lenderType) {
        this.lenderType = lenderType;
    }

    public String getIsMFI() {
        return isMFI;
    }

    public void setIsMFI(String isMFI) {
        this.isMFI = isMFI;
    }

    public String getLoanReason() {
        return loanReason;
    }

    public void setLoanReason(String loanReason) {
        this.loanReason = loanReason;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanEMIAmount() {
        return loanEMIAmount;
    }

    public void setLoanEMIAmount(Integer loanEMIAmount) {
        this.loanEMIAmount = loanEMIAmount;
    }

    public Integer getLoanBalanceAmount() {
        return loanBalanceAmount;
    }

    public void setLoanBalanceAmount(Integer loanBalanceAmount) {
        this.loanBalanceAmount = loanBalanceAmount;
    }

    public Integer getAutoID() {
        return autoID;
    }

    public void setAutoID(Integer autoID) {
        this.autoID = autoID;
    }

}