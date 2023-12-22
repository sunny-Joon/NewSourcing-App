package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kyc_data_table")
public class KYCData {
    @PrimaryKey(autoGenerate = true)
    public int id;


    // ... add other fields
    public String Aadhar,Name,Age,Dob,Gender,Guardian,Relationshipwithborrower,Address1,Address2,Address3,
                  City,Pincode,StateName,MobileNo,PanNo,DLNo,MotherFirstName,MotherMiddleName,MotherLastName,
                  FatherFirstName,FatherMiddleName,FatherLastName,MaritalStatus,SpouseFirstName,SpouseMiddleName,
                  spouseLastName;


    // Constructor
    public KYCData(int id, String aadhar, String name, String age, String dob, String gender, String guardian, String relationshipwithborrower, String address1, String address2, String address3, String city, String pincode, String stateName, String mobileNo, String panNo, String DLNo, String motherFirstName, String motherMiddleName, String motherLastName, String fatherFirstName, String fatherMiddleName, String fatherLastName, String maritalStatus, String spouseFirstName, String spouseMiddleName, String spouseLastName) {
        this.id = id;
        Aadhar = aadhar;
        Name = name;
        Age = age;
        Dob = dob;
        Gender = gender;
        Guardian = guardian;
        Relationshipwithborrower = relationshipwithborrower;
        Address1 = address1;
        Address2 = address2;
        Address3 = address3;
        City = city;
        Pincode = pincode;
        StateName = stateName;
        MobileNo = mobileNo;
        PanNo = panNo;
        this.DLNo = DLNo;
        MotherFirstName = motherFirstName;
        MotherMiddleName = motherMiddleName;
        MotherLastName = motherLastName;
        FatherFirstName = fatherFirstName;
        FatherMiddleName = fatherMiddleName;
        FatherLastName = fatherLastName;
        MaritalStatus = maritalStatus;
        SpouseFirstName = spouseFirstName;
        SpouseMiddleName = spouseMiddleName;
        this.spouseLastName = spouseLastName;
    }



    // getters, and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAadhar() {
        return Aadhar;
    }

    public void setAadhar(String aadhar) {
        Aadhar = aadhar;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getGuardian() {
        return Guardian;
    }

    public void setGuardian(String guardian) {
        Guardian = guardian;
    }

    public String getRelationshipwithborrower() {
        return Relationshipwithborrower;
    }

    public void setRelationshipwithborrower(String relationshipwithborrower) {
        Relationshipwithborrower = relationshipwithborrower;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getAddress3() {
        return Address3;
    }

    public void setAddress3(String address3) {
        Address3 = address3;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getPanNo() {
        return PanNo;
    }

    public void setPanNo(String panNo) {
        PanNo = panNo;
    }

    public String getDLNo() {
        return DLNo;
    }

    public void setDLNo(String DLNo) {
        this.DLNo = DLNo;
    }

    public String getMotherFirstName() {
        return MotherFirstName;
    }

    public void setMotherFirstName(String motherFirstName) {
        MotherFirstName = motherFirstName;
    }

    public String getMotherMiddleName() {
        return MotherMiddleName;
    }

    public void setMotherMiddleName(String motherMiddleName) {
        MotherMiddleName = motherMiddleName;
    }

    public String getMotherLastName() {
        return MotherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        MotherLastName = motherLastName;
    }

    public String getFatherFirstName() {
        return FatherFirstName;
    }

    public void setFatherFirstName(String fatherFirstName) {
        FatherFirstName = fatherFirstName;
    }

    public String getFatherMiddleName() {
        return FatherMiddleName;
    }

    public void setFatherMiddleName(String fatherMiddleName) {
        FatherMiddleName = fatherMiddleName;
    }

    public String getFatherLastName() {
        return FatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        FatherLastName = fatherLastName;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        MaritalStatus = maritalStatus;
    }

    public String getSpouseFirstName() {
        return SpouseFirstName;
    }

    public void setSpouseFirstName(String spouseFirstName) {
        SpouseFirstName = spouseFirstName;
    }

    public String getSpouseMiddleName() {
        return SpouseMiddleName;
    }

    public void setSpouseMiddleName(String spouseMiddleName) {
        SpouseMiddleName = spouseMiddleName;
    }

    public String getSpouseLastName() {
        return spouseLastName;
    }

    public void setSpouseLastName(String spouseLastName) {
        this.spouseLastName = spouseLastName;
    }
}
