package com.paisalo.newinternalsourcingapp.Modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FiJsonObject implements Serializable {

    @SerializedName("AadharID")
    @Expose
    private String aadharID;
    @SerializedName("Age")
    @Expose
    private String age;
    @SerializedName("Fname")
    @Expose
    private String fname;
    @SerializedName("Lname")
    @Expose
    private String lname;
    @SerializedName("DOB")
    @Expose
    private String dob;
    @SerializedName("P_Add1")
    @Expose
    private String pAdd1;
    @SerializedName("P_Add2")
    @Expose
    private String pAdd2;
    @SerializedName("P_Add3")
    @Expose
    private String pAdd3;
    @SerializedName("P_City")
    @Expose
    private String pCity;
    @SerializedName("P_Pin")
    @Expose
    private Integer pPin;
    @SerializedName("P_Ph3")
    @Expose
    private String pPh3;
    @SerializedName("PanNO")
    @Expose
    private String panNO;
    @SerializedName("DrivingLic")
    @Expose
    private String drivingLic;
    @SerializedName("voterId")
    @Expose
    private String voterId;
    @SerializedName("F_Fname")
    @Expose
    private String fFname;
    @SerializedName("F_Mname")
    @Expose
    private String fMname;
    @SerializedName("F_Lname")
    @Expose
    private String fLname;
    @SerializedName("isMarried")
    @Expose
    private String isMarried;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("P_State")
    @Expose
    private String pState;
    @SerializedName("guardianRelatnWithBorrower")
    @Expose
    private String guardianRelatnWithBorrower;
    @SerializedName("Loan_Amt")
    @Expose
    private Integer loanAmt;
    @SerializedName("Loan_Duration")
    @Expose
    private int loanDuration;
    @SerializedName("Business_Detail")
    @Expose
    private String businessDetail;
    @SerializedName("T_Ph3")
    @Expose
    private String tPh3;
    @SerializedName("Loan_Reason")
    @Expose
    private String loanReason;
    @SerializedName("Area_Of_House")
    @Expose
    private Integer areaOfHouse;
    @SerializedName("BankName")
    @Expose
    private String bankName;
    @SerializedName("Cast")
    @Expose
    private String cast;
    @SerializedName("CityCode")
    @Expose
    private String cityCode;
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Creator")
    @Expose
    private String creator;
    @SerializedName("FAmily_member")
    @Expose
    private int fAmilyMember;
    @SerializedName("Loan_EMi")
    @Expose
    private Integer loanEMi;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("T_Pin")
    @Expose
    private Integer tPin;
    @SerializedName("Tag")
    @Expose
    private String tag;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("Expense")
    @Expose
    private Integer expense;
    @SerializedName("fiExtra")
    @Expose
    private FiExtra fiExtra;

    public String getAadharID() {
        return aadharID;
    }

    public void setAadharID(String aadharID) {
        this.aadharID = aadharID;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPAdd1() {
        return pAdd1;
    }

    public void setPAdd1(String pAdd1) {
        this.pAdd1 = pAdd1;
    }

    public String getPAdd2() {
        return pAdd2;
    }

    public void setPAdd2(String pAdd2) {
        this.pAdd2 = pAdd2;
    }

    public String getPAdd3() {
        return pAdd3;
    }

    public void setPAdd3(String pAdd3) {
        this.pAdd3 = pAdd3;
    }

    public String getPCity() {
        return pCity;
    }

    public void setPCity(String pCity) {
        this.pCity = pCity;
    }

    public Integer getPPin() {
        return pPin;
    }

    public void setPPin(Integer pPin) {
        this.pPin = pPin;
    }

    public String getPPh3() {
        return pPh3;
    }

    public void setPPh3(String pPh3) {
        this.pPh3 = pPh3;
    }

    public String getPanNO() {
        return panNO;
    }

    public void setPanNO(String panNO) {
        this.panNO = panNO;
    }

    public String getDrivingLic() {
        return drivingLic;
    }

    public void setDrivingLic(String drivingLic) {
        this.drivingLic = drivingLic;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getFFname() {
        return fFname;
    }

    public void setFFname(String fFname) {
        this.fFname = fFname;
    }

    public String getFMname() {
        return fMname;
    }

    public void setFMname(String fMname) {
        this.fMname = fMname;
    }

    public String getFLname() {
        return fLname;
    }

    public void setFLname(String fLname) {
        this.fLname = fLname;
    }

    public String getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(String isMarried) {
        this.isMarried = isMarried;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPState() {
        return pState;
    }

    public void setPState(String pState) {
        this.pState = pState;
    }

    public String getGuardianRelatnWithBorrower() {
        return guardianRelatnWithBorrower;
    }

    public void setGuardianRelatnWithBorrower(String guardianRelatnWithBorrower) {
        this.guardianRelatnWithBorrower = guardianRelatnWithBorrower;
    }

    public Integer getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(Integer loanAmt) {
        this.loanAmt = loanAmt;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }

    public String getBusinessDetail() {
        return businessDetail;
    }

    public void setBusinessDetail(String businessDetail) {
        this.businessDetail = businessDetail;
    }

    public String getTPh3() {
        return tPh3;
    }

    public void setTPh3(String tPh3) {
        this.tPh3 = tPh3;
    }

    public String getLoanReason() {
        return loanReason;
    }

    public void setLoanReason(String loanReason) {
        this.loanReason = loanReason;
    }

    public Integer getAreaOfHouse() {
        return areaOfHouse;
    }

    public void setAreaOfHouse(Integer areaOfHouse) {
        this.areaOfHouse = areaOfHouse;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

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

    public int getFAmilyMember() {
        return fAmilyMember;
    }

    public void setFAmilyMember(int fAmilyMember) {
        this.fAmilyMember = fAmilyMember;
    }

    public Integer getLoanEMi() {
        return loanEMi;
    }

    public void setLoanEMi(Integer loanEMi) {
        this.loanEMi = loanEMi;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getTPin() {
        return tPin;
    }

    public void setTPin(Integer tPin) {
        this.tPin = tPin;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }

    public FiExtra getFiExtra() {
        return fiExtra;
    }

    public void setFiExtra(FiExtra fiExtra) {
        this.fiExtra = fiExtra;
    }

}