package com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Guarantor implements Serializable {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("guarantorNo")
    @Expose
    private Integer guarantorNo;
    @SerializedName("guarantorName")
    @Expose
    private String guarantorName;
    @SerializedName("guarantorFather")
    @Expose
    private String guarantorFather;
    @SerializedName("guarantorAadharNo")
    @Expose
    private String guarantorAadharNo;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("p_ph3")
    @Expose
    private String pPh3;
    @SerializedName("groupCode")
    @Expose
    private String groupCode;
    @SerializedName("eSignSucceed")
    @Expose
    private String eSignSucceed;
    @SerializedName("kycUuid")
    @Expose
    private String kycUuid;

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

    public Integer getGuarantorNo() {
        return guarantorNo;
    }

    public void setGuarantorNo(Integer guarantorNo) {
        this.guarantorNo = guarantorNo;
    }

    public String getGuarantorName() {
        return guarantorName;
    }

    public void setGuarantorName(String guarantorName) {
        this.guarantorName = guarantorName;
    }

    public String getGuarantorFather() {
        return guarantorFather;
    }

    public void setGuarantorFather(String guarantorFather) {
        this.guarantorFather = guarantorFather;
    }

    public String getGuarantorAadharNo() {
        return guarantorAadharNo;
    }

    public void setGuarantorAadharNo(String guarantorAadharNo) {
        this.guarantorAadharNo = guarantorAadharNo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getpPh3() {
        return pPh3;
    }

    public void setpPh3(String pPh3) {
        this.pPh3 = pPh3;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String geteSignSucceed() {
        return eSignSucceed;
    }

    public void seteSignSucceed(String eSignSucceed) {
        this.eSignSucceed = eSignSucceed;
    }

    public String getKycUuid() {
        return kycUuid;
    }

    public void setKycUuid(String kycUuid) {
        this.kycUuid = kycUuid;
    }

}