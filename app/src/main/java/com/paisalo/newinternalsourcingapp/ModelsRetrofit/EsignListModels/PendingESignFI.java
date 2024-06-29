package com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PendingESignFI implements Serializable {

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
    @SerializedName("documentPath")
    @Expose
    private Object documentPath;
    @SerializedName("disbRequested")
    @Expose
    private Object disbRequested;
    @SerializedName("loan_Reason")
    @Expose
    private String loanReason;
    @SerializedName("expense")
    @Expose
    private String expense;
    @SerializedName("income")
    @Expose
    private String income;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("panNO")
    @Expose
    private String panNO;
    @SerializedName("p_Pin")
    @Expose
    private String pPin;
    @SerializedName("p_City")
    @Expose
    private String pCity;
    @SerializedName("loan_Duration")
    @Expose
    private String loanDuration;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("loan_Amt")
    @Expose
    private String loanAmt;
    @SerializedName("voterID")
    @Expose
    private String voterID;
    @SerializedName("drivinglic")
    @Expose
    private String drivinglic;
    @SerializedName("p_State")
    @Expose
    private String pState;

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

    public Object getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(Object documentPath) {
        this.documentPath = documentPath;
    }

    public Object getDisbRequested() {
        return disbRequested;
    }

    public void setDisbRequested(Object disbRequested) {
        this.disbRequested = disbRequested;
    }

    public String getLoanReason() {
        return loanReason;
    }

    public void setLoanReason(String loanReason) {
        this.loanReason = loanReason;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPanNO() {
        return panNO;
    }

    public void setPanNO(String panNO) {
        this.panNO = panNO;
    }

    public String getpPin() {
        return pPin;
    }

    public void setpPin(String pPin) {
        this.pPin = pPin;
    }

    public String getpCity() {
        return pCity;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public String getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(String loanDuration) {
        this.loanDuration = loanDuration;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(String loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getVoterID() {
        return voterID;
    }

    public void setVoterID(String voterID) {
        this.voterID = voterID;
    }

    public String getDrivinglic() {
        return drivinglic;
    }

    public void setDrivinglic(String drivinglic) {
        this.drivinglic = drivinglic;
    }

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

}