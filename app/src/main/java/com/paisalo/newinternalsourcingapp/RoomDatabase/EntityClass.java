package com.paisalo.newinternalsourcingapp.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "KYC_Database")
public class EntityClass {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String FiCode;
    // First Page
    public String aadhaarId;
    public String name;
    public String  age;
    public String dob;
    public String gender;
    public String guardian;
    public String relationshipWithBorrower;
    public String address1;
    public String address2;
    public String address3;
    public String city;
    public String pin;
    public String state;
    public String mobile;
    public String voterId;
    public String pan;
    public String drivingLicense;
    public String motherFirstName;
    public String motherMiddleName;
    public String motherLastName;
    public String fatherFirstName;
    public String fatherMiddleName;
    public String fatherLastName;
    public String maritalStatus;
    public String spouseFirstName;
    public String spouseMiddleName;
    public String spouseLastName;

    //  Page 2
    public String monthlyIncome;
    public String expense;
    public String futureIncome;
    public String agricultureIncome;
    public String pensionIncome;
    public String interestIncome;
    public String otherIncome;
    public String earningMemberType;
    public String earningMemberIncome;
    public String loanAmount;
    public String businessDetails;
    public String loanPurpose;
    public String occupation;
    public String loanDuration;
    public String selectedBank;

    public EntityClass() {
    }

    public EntityClass(long id, String fiCode, String aadhaarId, String name, String age, String dob, String gender, String guardian, String relationshipWithBorrower, String address1, String address2, String address3, String city, String pin, String state, String mobile, String voterId, String pan, String drivingLicense, String motherFirstName, String motherMiddleName, String motherLastName, String fatherFirstName, String fatherMiddleName, String fatherLastName, String maritalStatus, String spouseFirstName, String spouseMiddleName, String spouseLastName, String monthlyIncome, String expense, String futureIncome, String agricultureIncome, String pensionIncome, String interestIncome, String otherIncome, String earningMemberType, String earningMemberIncome, String loanAmount, String businessDetails, String loanPurpose, String occupation, String loanDuration, String selectedBank) {
        this.id = id;
        FiCode = fiCode;
        this.aadhaarId = aadhaarId;
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.gender = gender;
        this.guardian = guardian;
        this.relationshipWithBorrower = relationshipWithBorrower;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.pin = pin;
        this.state = state;
        this.mobile = mobile;
        this.voterId = voterId;
        this.pan = pan;
        this.drivingLicense = drivingLicense;
        this.motherFirstName = motherFirstName;
        this.motherMiddleName = motherMiddleName;
        this.motherLastName = motherLastName;
        this.fatherFirstName = fatherFirstName;
        this.fatherMiddleName = fatherMiddleName;
        this.fatherLastName = fatherLastName;
        this.maritalStatus = maritalStatus;
        this.spouseFirstName = spouseFirstName;
        this.spouseMiddleName = spouseMiddleName;
        this.spouseLastName = spouseLastName;
        this.monthlyIncome = monthlyIncome;
        this.expense = expense;
        this.futureIncome = futureIncome;
        this.agricultureIncome = agricultureIncome;
        this.pensionIncome = pensionIncome;
        this.interestIncome = interestIncome;
        this.otherIncome = otherIncome;
        this.earningMemberType = earningMemberType;
        this.earningMemberIncome = earningMemberIncome;
        this.loanAmount = loanAmount;
        this.businessDetails = businessDetails;
        this.loanPurpose = loanPurpose;
        this.occupation = occupation;
        this.loanDuration = loanDuration;
        this.selectedBank = selectedBank;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFiCode() {
        return FiCode;
    }

    public void setFiCode(String fiCode) {
        FiCode = fiCode;
    }

    public String getAadhaarId() {
        return aadhaarId;
    }

    public void setAadhaarId(String aadhaarId) {
        this.aadhaarId = aadhaarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getRelationshipWithBorrower() {
        return relationshipWithBorrower;
    }

    public void setRelationshipWithBorrower(String relationshipWithBorrower) {
        this.relationshipWithBorrower = relationshipWithBorrower;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getMotherFirstName() {
        return motherFirstName;
    }

    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
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

    public String getFatherFirstName() {
        return fatherFirstName;
    }

    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }

    public String getFatherMiddleName() {
        return fatherMiddleName;
    }

    public void setFatherMiddleName(String fatherMiddleName) {
        this.fatherMiddleName = fatherMiddleName;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getFutureIncome() {
        return futureIncome;
    }

    public void setFutureIncome(String futureIncome) {
        this.futureIncome = futureIncome;
    }

    public String getAgricultureIncome() {
        return agricultureIncome;
    }

    public void setAgricultureIncome(String agricultureIncome) {
        this.agricultureIncome = agricultureIncome;
    }

    public String getPensionIncome() {
        return pensionIncome;
    }

    public void setPensionIncome(String pensionIncome) {
        this.pensionIncome = pensionIncome;
    }

    public String getInterestIncome() {
        return interestIncome;
    }

    public void setInterestIncome(String interestIncome) {
        this.interestIncome = interestIncome;
    }

    public String getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(String otherIncome) {
        this.otherIncome = otherIncome;
    }

    public String getEarningMemberType() {
        return earningMemberType;
    }

    public void setEarningMemberType(String earningMemberType) {
        this.earningMemberType = earningMemberType;
    }

    public String getEarningMemberIncome() {
        return earningMemberIncome;
    }

    public void setEarningMemberIncome(String earningMemberIncome) {
        this.earningMemberIncome = earningMemberIncome;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(String businessDetails) {
        this.businessDetails = businessDetails;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(String loanDuration) {
        this.loanDuration = loanDuration;
    }

    public String getSelectedBank() {
        return selectedBank;
    }

    public void setSelectedBank(String selectedBank) {
        this.selectedBank = selectedBank;
    }
}

