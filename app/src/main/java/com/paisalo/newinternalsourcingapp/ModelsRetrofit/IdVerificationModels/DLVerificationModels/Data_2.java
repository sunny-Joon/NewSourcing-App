package com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.DLVerificationModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data_2 {

    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("license_number")
    @Expose
    private String licenseNumber;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("permanent_address")
    @Expose
    private String permanentAddress;
    @SerializedName("permanent_zip")
    @Expose
    private String permanentZip;
    @SerializedName("temporary_address")
    @Expose
    private String temporaryAddress;
    @SerializedName("temporary_zip")
    @Expose
    private String temporaryZip;
    @SerializedName("citizenship")
    @Expose
    private String citizenship;
    @SerializedName("ola_name")
    @Expose
    private String olaName;
    @SerializedName("ola_code")
    @Expose
    private String olaCode;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("father_or_husband_name")
    @Expose
    private String fatherOrHusbandName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("doe")
    @Expose
    private String doe;
    @SerializedName("transport_doe")
    @Expose
    private String transportDoe;
    @SerializedName("doi")
    @Expose
    private String doi;
    @SerializedName("transport_doi")
    @Expose
    private String transportDoi;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("has_image")
    @Expose
    private Boolean hasImage;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("vehicle_classes")
    @Expose
    private List<String> vehicleClasses;
    @SerializedName("less_info")
    @Expose
    private Boolean lessInfo;
    @SerializedName("additional_check")
    @Expose
    private List<Object> additionalCheck;
    @SerializedName("initial_doi")
    @Expose
    private String initialDoi;
    @SerializedName("current_status")
    @Expose
    private Object currentStatus;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPermanentZip() {
        return permanentZip;
    }

    public void setPermanentZip(String permanentZip) {
        this.permanentZip = permanentZip;
    }

    public String getTemporaryAddress() {
        return temporaryAddress;
    }

    public void setTemporaryAddress(String temporaryAddress) {
        this.temporaryAddress = temporaryAddress;
    }

    public String getTemporaryZip() {
        return temporaryZip;
    }

    public void setTemporaryZip(String temporaryZip) {
        this.temporaryZip = temporaryZip;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getOlaName() {
        return olaName;
    }

    public void setOlaName(String olaName) {
        this.olaName = olaName;
    }

    public String getOlaCode() {
        return olaCode;
    }

    public void setOlaCode(String olaCode) {
        this.olaCode = olaCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherOrHusbandName() {
        return fatherOrHusbandName;
    }

    public void setFatherOrHusbandName(String fatherOrHusbandName) {
        this.fatherOrHusbandName = fatherOrHusbandName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoe() {
        return doe;
    }

    public void setDoe(String doe) {
        this.doe = doe;
    }

    public String getTransportDoe() {
        return transportDoe;
    }

    public void setTransportDoe(String transportDoe) {
        this.transportDoe = transportDoe;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTransportDoi() {
        return transportDoi;
    }

    public void setTransportDoi(String transportDoi) {
        this.transportDoi = transportDoi;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public List<String> getVehicleClasses() {
        return vehicleClasses;
    }

    public void setVehicleClasses(List<String> vehicleClasses) {
        this.vehicleClasses = vehicleClasses;
    }

    public Boolean getLessInfo() {
        return lessInfo;
    }

    public void setLessInfo(Boolean lessInfo) {
        this.lessInfo = lessInfo;
    }

    public List<Object> getAdditionalCheck() {
        return additionalCheck;
    }

    public void setAdditionalCheck(List<Object> additionalCheck) {
        this.additionalCheck = additionalCheck;
    }

    public String getInitialDoi() {
        return initialDoi;
    }

    public void setInitialDoi(String initialDoi) {
        this.initialDoi = initialDoi;
    }

    public Object getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Object currentStatus) {
        this.currentStatus = currentStatus;
    }

}
