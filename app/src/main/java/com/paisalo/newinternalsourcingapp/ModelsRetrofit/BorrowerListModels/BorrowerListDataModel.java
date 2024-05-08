package com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BorrowerListDataModel {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("sanctionedAmt")
    @Expose
    private Double sanctionedAmt;
    @SerializedName("remarks")
    @Expose
    private Object remarks;
    @SerializedName("dt_Fin")
    @Expose
    private String dtFin;
    @SerializedName("dt_Start")
    @Expose
    private String dtStart;
    @SerializedName("schCode")
    @Expose
    private Object schCode;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("mname")
    @Expose
    private String mname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("f_fname")
    @Expose
    private String fFname;
    @SerializedName("f_mname")
    @Expose
    private String fMname;
    @SerializedName("f_lname")
    @Expose
    private String fLname;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("period")
    @Expose
    private Object period;
    @SerializedName("rate")
    @Expose
    private double rate;
    @SerializedName("aadharid")
    @Expose
    private String aadharid;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("p_ph3")
    @Expose
    private String pPh3;
    @SerializedName("groupCode")
    @Expose
    private String groupCode;
    @SerializedName("cityCode")
    @Expose
    private String cityCode;
    @SerializedName("borrLoanAppSignStatus")
    @Expose
    private Object borrLoanAppSignStatus;
    @SerializedName("approved")
    @Expose
    private Object approved;
    @SerializedName("sel")
    @Expose
    private Object sel;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Double getSanctionedAmt() {
        return sanctionedAmt;
    }

    public void setSanctionedAmt(Double sanctionedAmt) {
        this.sanctionedAmt = sanctionedAmt;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public String getDtFin() {
        return dtFin;
    }

    public void setDtFin(String dtFin) {
        this.dtFin = dtFin;
    }

    public String getDtStart() {
        return dtStart;
    }

    public void setDtStart(String dtStart) {
        this.dtStart = dtStart;
    }

    public Object getSchCode() {
        return schCode;
    }

    public void setSchCode(Object schCode) {
        this.schCode = schCode;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
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

    public String getfMname() {
        return fMname;
    }

    public void setfMname(String fMname) {
        this.fMname = fMname;
    }

    public String getfLname() {
        return fLname;
    }

    public void setfLname(String fLname) {
        this.fLname = fLname;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getPeriod() {
        return period;
    }

    public void setPeriod(Object period) {
        this.period = period;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getAadharid() {
        return aadharid;
    }

    public void setAadharid(String aadharid) {
        this.aadharid = aadharid;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Object getBorrLoanAppSignStatus() {
        return borrLoanAppSignStatus;
    }

    public void setBorrLoanAppSignStatus(Object borrLoanAppSignStatus) {
        this.borrLoanAppSignStatus = borrLoanAppSignStatus;
    }

    public Object getApproved() {
        return approved;
    }

    public void setApproved(Object approved) {
        this.approved = approved;
    }

    public Object getSel() {
        return sel;
    }

    public void setSel(Object sel) {
        this.sel = sel;
    }

}