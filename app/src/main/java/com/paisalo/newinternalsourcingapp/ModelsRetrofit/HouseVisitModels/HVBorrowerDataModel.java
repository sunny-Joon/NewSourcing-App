package com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HVBorrowerDataModel {

    @SerializedName("fiCode")
    @Expose
    private Integer fiCode;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("cityCode")
    @Expose
    private String cityCode;
    @SerializedName("groupCode")
    @Expose
    private String groupCode;
    @SerializedName("rent_of_House")
    @Expose
    private Integer rentOfHouse;
    @SerializedName("applicantName")
    @Expose
    private String applicantName;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("loanAmount")
    @Expose
    private Integer loanAmount;
    @SerializedName("loanDuration")
    @Expose
    private String loanDuration;
    @SerializedName("loanReason")
    @Expose
    private String loanReason;
    @SerializedName("applicantStatus")
    @Expose
    private String applicantStatus;
    @SerializedName("currentOccupation")
    @Expose
    private String currentOccupation;
    @SerializedName("occupationType")
    @Expose
    private String occupationType;
    @SerializedName("landOwnership")
    @Expose
    private String landOwnership;
    @SerializedName("residenceOwnership")
    @Expose
    private String residenceOwnership;
    @SerializedName("profilePic")
    @Expose
    private String profilePic;

    public Integer getFiCode() {
        return fiCode;
    }

    public void setFiCode(Integer fiCode) {
        this.fiCode = fiCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getRentOfHouse() {
        return rentOfHouse;
    }

    public void setRentOfHouse(Integer rentOfHouse) {
        this.rentOfHouse = rentOfHouse;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(String loanDuration) {
        this.loanDuration = loanDuration;
    }

    public String getLoanReason() {
        return loanReason;
    }

    public void setLoanReason(String loanReason) {
        this.loanReason = loanReason;
    }

    public String getApplicantStatus() {
        return applicantStatus;
    }

    public void setApplicantStatus(String applicantStatus) {
        this.applicantStatus = applicantStatus;
    }

    public String getCurrentOccupation() {
        return currentOccupation;
    }

    public void setCurrentOccupation(String currentOccupation) {
        this.currentOccupation = currentOccupation;
    }

    public String getOccupationType() {
        return occupationType;
    }

    public void setOccupationType(String occupationType) {
        this.occupationType = occupationType;
    }

    public String getLandOwnership() {
        return landOwnership;
    }

    public void setLandOwnership(String landOwnership) {
        this.landOwnership = landOwnership;
    }

    public String getResidenceOwnership() {
        return residenceOwnership;
    }

    public void setResidenceOwnership(String residenceOwnership) {
        this.residenceOwnership = residenceOwnership;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

}