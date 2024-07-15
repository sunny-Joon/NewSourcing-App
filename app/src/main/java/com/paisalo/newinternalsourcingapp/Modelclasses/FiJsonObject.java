package com.paisalo.newinternalsourcingapp.Modelclasses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FiJsonObject implements Serializable {

    @SerializedName("groupCode")
    @Expose
    private String groupCode;
    @SerializedName("AadharID")
    @Expose
    private String aadharID;
    @SerializedName("Age")
    @Expose
    private String age;
    @SerializedName("Fname")
    @Expose
    private String fname;
    @SerializedName("Mname")
    @Expose
    private String mname;
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
    private String VoterId;
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
    @SerializedName("IsNameVerify")
    @Expose
    private String IsNameVerify;
    public String getIsNameVerify() {
        return IsNameVerify;
    }

    public void setIsNameVerify(String isNameVerify) {
        IsNameVerify = isNameVerify;
    }

    public String getgroupCode() {
        return groupCode;
    }

    public void setgroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
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
        return VoterId;
    }

    public void setVoterId(String voterId) {
        this.VoterId = voterId;
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
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getpAdd1() {
        return pAdd1;
    }

    public void setpAdd1(String pAdd1) {
        this.pAdd1 = pAdd1;
    }

    public String getpAdd2() {
        return pAdd2;
    }

    public void setpAdd2(String pAdd2) {
        this.pAdd2 = pAdd2;
    }

    public String getpAdd3() {
        return pAdd3;
    }

    public void setpAdd3(String pAdd3) {
        this.pAdd3 = pAdd3;
    }

    public String getpCity() {
        return pCity;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public Integer getpPin() {
        return pPin;
    }

    public void setpPin(Integer pPin) {
        this.pPin = pPin;
    }

    public String getpPh3() {
        return pPh3;
    }

    public void setpPh3(String pPh3) {
        this.pPh3 = pPh3;
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

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

    public String gettPh3() {
        return tPh3;
    }

    public void settPh3(String tPh3) {
        this.tPh3 = tPh3;
    }

    public int getfAmilyMember() {
        return fAmilyMember;
    }

    public void setfAmilyMember(int fAmilyMember) {
        this.fAmilyMember = fAmilyMember;
    }

    public Integer gettPin() {
        return tPin;
    }

    public void settPin(Integer tPin) {
        this.tPin = tPin;
    }

    @Override
    public String toString() {
        return "{" +
                "\"groupCode\": \"" + groupCode + "\"," +
                "\"AadharID\": \"" + aadharID + "\"," +
                "\"Age\": \"" + age + "\"," +
                "\"Fname\": \"" + fname + "\"," +
                "\"Mname\": " + (mname != null ? "\"" + mname + "\"" : null) + "," +
                "\"Lname\": " + (lname != null ? "\"" + lname + "\"" : null) + "," +
                "\"DOB\": \"" + dob + "\"," +
                "\"P_Add1\": \"" + pAdd1 + "\"," +
                "\"P_Add2\": \"" + pAdd2 + "\"," +
                "\"P_Add3\": \"" + pAdd3 + "\"," +
                "\"P_City\": \"" + pCity + "\"," +
                "\"P_Pin\": " + pPin + "," +
                "\"P_Ph3\": \"" + pPh3 + "\"," +
                "\"PanNO\": \"" + panNO + "\"," +
                "\"DrivingLic\": \"" + drivingLic + "\"," +
                "\"voterId\": " + (VoterId != null ? "\"" + VoterId + "\"" : null) + "," +
                "\"F_Fname\": \"" + fFname + "\"," +
                "\"F_Mname\": " + (fMname != null ? "\"" + fMname + "\"" : null) + "," +
                "\"F_Lname\": " + (fLname != null ? "\"" + fLname + "\"" : null) + "," +
                "\"isMarried\": \"" + isMarried + "\"," +
                "\"Gender\": \"" + gender + "\"," +
                "\"P_State\": \"" + pState + "\"," +
                "\"guardianRelatnWithBorrower\": \"" + guardianRelatnWithBorrower + "\"," +
                "\"Loan_Amt\": " + loanAmt + "," +
                "\"Loan_Duration\": " + loanDuration + "," +
                "\"Business_Detail\": " + (businessDetail != null ? "\"" + businessDetail + "\"" : null) + "," +
                "\"T_Ph3\": " + (tPh3 != null ? "\"" + tPh3 + "\"" : null) + "," +
                "\"Loan_Reason\": " + (loanReason != null ? "\"" + loanReason + "\"" : null) + "," +
                "\"Area_Of_House\": " + areaOfHouse + "," +
                "\"BankName\": " + (bankName != null ? "\"" + bankName + "\"" : null) + "," +
                "\"Cast\": " + (cast != null ? "\"" + cast + "\"" : null) + "," +
                "\"CityCode\": " + (cityCode != null ? "\"" + cityCode + "\"" : null) + "," +
                "\"Code\": " + code + "," +
                "\"Creator\": " + (creator != null ? "\"" + creator + "\"" : null) + "," +
                "\"FAmily_member\": " + fAmilyMember + "," +
                "\"Loan_EMi\": " + loanEMi + "," +
                "\"Latitude\": " + latitude + "," +
                "\"Longitude\": " + longitude + "," +
                "\"T_Pin\": " + tPin + "," +
                "\"Tag\": " + (tag != null ? "\"" + tag + "\"" : null) + "," +
                "\"UserID\": " + (userID != null ? "\"" + userID + "\"" : null) + "," +
                "\"Expense\": " + expense + "," +
                "\"fiExtra\": " + (fiExtra != null ? fiExtra.toString() : null) +
                "}";
    }



}