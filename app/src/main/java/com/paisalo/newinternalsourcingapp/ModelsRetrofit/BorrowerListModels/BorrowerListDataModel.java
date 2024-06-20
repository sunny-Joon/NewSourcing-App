package com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cz.msebera.android.httpclient.entity.SerializableEntity;

public class BorrowerListDataModel implements Serializable {

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("mname")
    @Expose
    private Object mname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("f_fname")
    @Expose
    private String fFname;
    @SerializedName("f_mname")
    @Expose
    private Object fMname;
    @SerializedName("f_lname")
    @Expose
    private String fLname;
    @SerializedName("aadharid")
    @Expose
    private String aadharid;
    @SerializedName("p_ph3")
    @Expose
    private String pPh3;
    @SerializedName("sanctionedAmt")
    @Expose
    private int sanctionedAmt;
    @SerializedName("dtFin")
    @Expose
    private String dtFin;
    @SerializedName("period")
    @Expose
    private int period;
    @SerializedName("intRate")
    @Expose
    private Double intRate;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("foCode")
    @Expose
    private String foCode;
    @SerializedName("eSignSucceed")
    @Expose
    private String eSignSucceed;
    @SerializedName("kycUuid")
    @Expose
    private String kycUuid;
    @SerializedName("cityCode")
    @Expose
    private String cityCode;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Object getMname() {
        return mname;
    }

    public void setMname(Object mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getfFname() {
        return fFname;
    }

    public void setfFname(String fFname) {
        this.fFname = fFname;
    }

    public Object getfMname() {
        return fMname;
    }

    public void setfMname(Object fMname) {
        this.fMname = fMname;
    }

    public String getfLname() {
        return fLname;
    }

    public void setfLname(String fLname) {
        this.fLname = fLname;
    }

    public String getAadharid() {
        return aadharid;
    }

    public void setAadharid(String aadharid) {
        this.aadharid = aadharid;
    }

    public String getpPh3() {
        return pPh3;
    }

    public void setpPh3(String pPh3) {
        this.pPh3 = pPh3;
    }

    public int getSanctionedAmt() {
        return sanctionedAmt;
    }

    public void setSanctionedAmt(int sanctionedAmt) {
        this.sanctionedAmt = sanctionedAmt;
    }

    public String getDtFin() {
        return dtFin;
    }

    public void setDtFin(String dtFin) {
        this.dtFin = dtFin;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Double getIntRate() {
        return intRate;
    }

    public void setIntRate(Double intRate) {
        this.intRate = intRate;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getFoCode() {
        return foCode;
    }

    public void setFoCode(String foCode) {
        this.foCode = foCode;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

}