package com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UploadedFiDocs implements Serializable {

    @SerializedName("fiCode")
    @Expose
    private Integer fiCode;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("checklistID")
    @Expose
    private Integer checklistID;
    @SerializedName("docRemark")
    @Expose
    private String docRemark;
    @SerializedName("grNo")
    @Expose
    private Integer grNo;

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

    public Integer getChecklistID() {
        return checklistID;
    }

    public void setChecklistID(Integer checklistID) {
        this.checklistID = checklistID;
    }

    public String getDocRemark() {
        return docRemark;
    }

    public void setDocRemark(String docRemark) {
        this.docRemark = docRemark;
    }

    public Integer getGrNo() {
        return grNo;
    }

    public void setGrNo(Integer grNo) {
        this.grNo = grNo;
    }

}