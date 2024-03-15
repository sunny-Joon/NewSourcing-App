package com.paisalo.newinternalsourcingapp.ModelclassesRoom;

public class KYCScanningModel {

    private String name;
    private String type;
    private String docType;
    private String remarks;
 //   private String aadhar;

   /* public KYCScanningModel(String name, String type, String docType, String remarks, String aadhar) {*/
   public KYCScanningModel(String name, String type, String docType, String remarks) {
        this.name = name;
        this.type = type;
        this.docType = docType;
        this.remarks = remarks;
   //     this.aadhar = aadhar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

   /* public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }*/
}
