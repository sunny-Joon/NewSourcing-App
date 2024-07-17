package com.paisalo.newinternalsourcingapp.Modelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FiExtra implements Serializable {

    @SerializedName("motherName")
    @Expose
    private String motherName;
    @SerializedName("motherMiddleName")
    @Expose
    private String motherMiddleName;
    @SerializedName("motherLastName")
    @Expose
    private String motherLastName;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("fatherMiddleName")
    @Expose
    private String fatherMiddleName;
    @SerializedName("fatherLastName")
    @Expose
    private String fatherLastName;
    @SerializedName("spouseFirstName")
    @Expose
    private String spouseFirstName;
    @SerializedName("spouseMiddleName")
    @Expose
    private String spouseMiddleName;
    @SerializedName("spouseLastName")
    @Expose
    private String spouseLastName;
    @SerializedName("monthlyIncome")
    @Expose
    private Integer monthlyIncome;
    @SerializedName("futureIncome")
    @Expose
    private Integer futureIncome;
    @SerializedName("agriculture_Income")
    @Expose
    private Integer agricultureIncome;
    @SerializedName("pension_Income")
    @Expose
    private Integer pensionIncome;
    @SerializedName("interest_Income")
    @Expose
    private Integer interestIncome;
    @SerializedName("otherIncome")
    @Expose
    private Integer otherIncome;
    @SerializedName("earningMemberType")
    @Expose
    private String earningMemberType;
    @SerializedName("earningMemberIncome")
    @Expose
    private Integer earningMemberIncome;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("isBorrowerHandicap")
    @Expose
    private String isBorrowerHandicap;

    public String getIsBorrowerHandicap() {
        return isBorrowerHandicap;
    }

    public void setIsBorrowerHandicap(String isBorrowerHandicap) {
        this.isBorrowerHandicap = isBorrowerHandicap;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setFatherMiddleName(String fatherMiddleName) {
        this.fatherMiddleName = fatherMiddleName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getFatherMiddleName() {
        return fatherMiddleName;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherMiddleName() {
        return motherMiddleName;
    }

    public void setMotherMiddleName(String motherMiddleName) {
        this.motherMiddleName = motherMiddleName;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getSpouseFirstName() {
        return spouseFirstName;
    }

    public void setSpouseFirstName(String spouseFirstName) {
        this.spouseFirstName = spouseFirstName;
    }

    public String getSpouseMiddleName() {
        return spouseMiddleName;
    }

    public void setSpouseMiddleName(String spouseMiddleName) {
        this.spouseMiddleName = spouseMiddleName;
    }

    public String getSpouseLastName() {
        return spouseLastName;
    }

    public void setSpouseLastName(String spouseLastName) {
        this.spouseLastName = spouseLastName;
    }

    public Integer getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Integer monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Integer getFutureIncome() {
        return futureIncome;
    }

    public void setFutureIncome(Integer futureIncome) {
        this.futureIncome = futureIncome;
    }

    public Integer getAgricultureIncome() {
        return agricultureIncome;
    }

    public void setAgricultureIncome(Integer agricultureIncome) {
        this.agricultureIncome = agricultureIncome;
    }

    public Integer getPensionIncome() {
        return pensionIncome;
    }

    public void setPensionIncome(Integer pensionIncome) {
        this.pensionIncome = pensionIncome;
    }

    public Integer getInterestIncome() {
        return interestIncome;
    }

    public void setInterestIncome(Integer interestIncome) {
        this.interestIncome = interestIncome;
    }

    public Integer getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(Integer otherIncome) {
        this.otherIncome = otherIncome;
    }

    public String getEarningMemberType() {
        return earningMemberType;
    }

    public void setEarningMemberType(String earningMemberType) {
        this.earningMemberType = earningMemberType;
    }

    public Integer getEarningMemberIncome() {
        return earningMemberIncome;
    }

    public void setEarningMemberIncome(Integer earningMemberIncome) {
        this.earningMemberIncome = earningMemberIncome;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }


    @Override
    public String toString() {
        return "{" +
                "\"motherName\": \"" + motherName + "\"," +
                "\"motherMiddleName\": \"" + motherMiddleName + "\"," +
                "\"motherLastName\": \"" + motherLastName + "\"," +
                "\"fatherName\": \"" + fatherName + "\"," +
                "\"fatherMiddleName\": \"" + fatherMiddleName + "\"," +
                "\"fatherLastName\": \"" + fatherLastName + "\"," +
                "\"spouseFirstName\": " + (spouseFirstName != null ? "\"" + spouseFirstName + "\"" : null) + "," +
                "\"spouseMiddleName\": " + (spouseMiddleName != null ? "\"" + spouseMiddleName + "\"" : null) + "," +
                "\"spouseLastName\": " + (spouseLastName != null ? "\"" + spouseLastName + "\"" : null) + "," +
                "\"monthlyIncome\": " + monthlyIncome + "," +
                "\"futureIncome\": " + futureIncome + "," +
                "\"agricultureIncome\": " + agricultureIncome + "," +
                "\"pensionIncome\": " + pensionIncome + "," +
                "\"interestIncome\": " + interestIncome + "," +
                "\"otherIncome\": " + otherIncome + "," +

                "\"isBorrowerHandicap\": " + isBorrowerHandicap + "," +

                "\"earningMemberType\": " + (earningMemberType != null ? "\"" + earningMemberType + "\"" : null) + "," +
                "\"earningMemberIncome\": " + earningMemberIncome + "," +
                "\"occupation\": " + (occupation != null ? "\"" + occupation + "\"" : null) +


                "}";
    }


}