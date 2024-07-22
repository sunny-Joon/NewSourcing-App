package com.paisalo.newinternalsourcingapp.ModelclassesRoom;

import java.io.File;

public class KYCScanningModel {

    private String name;
    private String type;
    private String docType;
    private String remarks;
    private String checklistId;
    private String GrNo;

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    private boolean isUploaded;
    private File file;

    private String docId;

    public KYCScanningModel(String name, String type, String docType, String remarks, File file, String docId,String checklistId,String GrNo,boolean isUploaded) {
        this.name = name;
        this.type = type;
        this.docType = docType;
        this.remarks = remarks;
        this.file = file;
        this.docId = docId;
        this.checklistId = checklistId;
        this.GrNo = GrNo;
        this.isUploaded = isUploaded;
    }

    public String getGrNo() {
        return GrNo;
    }

    public void setGrNo(String grNo) {
        GrNo = grNo;
    }

    public File getFile() {
        return file;
    }

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }

    public void setFile(File file) {
        this.file = file;
    }

 //   private String aadhar;

   /* public KYCScanningModel(String name, String type, String docType, String remarks, String aadhar) {*/

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

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
