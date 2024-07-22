package com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycDocsFlag;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycDocsFlagDataModel {

    @SerializedName("fiCode")
    @Expose
    private String fiCode;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("docname")
    @Expose
    private Object docname;
    @SerializedName("addharExists")
    @Expose
    private Boolean addharExists;
    @SerializedName("aadharPath")
    @Expose
    private Object aadharPath;
    @SerializedName("aadharBPath")
    @Expose
    private Object aadharBPath;
    @SerializedName("voterExists")
    @Expose
    private Boolean voterExists;
    @SerializedName("voterPath")
    @Expose
    private Object voterPath;
    @SerializedName("voterBPath")
    @Expose
    private Object voterBPath;
    @SerializedName("panExists")
    @Expose
    private Boolean panExists;
    @SerializedName("panPath")
    @Expose
    private Object panPath;
    @SerializedName("drivingExists")
    @Expose
    private Boolean drivingExists;
    @SerializedName("drivingPath")
    @Expose
    private Object drivingPath;
    @SerializedName("passBookExists")
    @Expose
    private Boolean passBookExists;
    @SerializedName("passBookPath")
    @Expose
    private String passBookPath;
    @SerializedName("passBookBPath")
    @Expose
    private Object passBookBPath;
    @SerializedName("grDocs")
    @Expose
    private List<GrDoc> grDocs;

    public String getFiCode() {
        return fiCode;
    }

    public void setFiCode(String fiCode) {
        this.fiCode = fiCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Object getDocname() {
        return docname;
    }

    public void setDocname(Object docname) {
        this.docname = docname;
    }

    public Boolean getAddharExists() {
        return addharExists;
    }

    public void setAddharExists(Boolean addharExists) {
        this.addharExists = addharExists;
    }

    public Object getAadharPath() {
        return aadharPath;
    }

    public void setAadharPath(Object aadharPath) {
        this.aadharPath = aadharPath;
    }

    public Object getAadharBPath() {
        return aadharBPath;
    }

    public void setAadharBPath(Object aadharBPath) {
        this.aadharBPath = aadharBPath;
    }

    public Boolean getVoterExists() {
        return voterExists;
    }

    public void setVoterExists(Boolean voterExists) {
        this.voterExists = voterExists;
    }

    public Object getVoterPath() {
        return voterPath;
    }

    public void setVoterPath(Object voterPath) {
        this.voterPath = voterPath;
    }

    public Object getVoterBPath() {
        return voterBPath;
    }

    public void setVoterBPath(Object voterBPath) {
        this.voterBPath = voterBPath;
    }

    public Boolean getPanExists() {
        return panExists;
    }

    public void setPanExists(Boolean panExists) {
        this.panExists = panExists;
    }

    public Object getPanPath() {
        return panPath;
    }

    public void setPanPath(Object panPath) {
        this.panPath = panPath;
    }

    public Boolean getDrivingExists() {
        return drivingExists;
    }

    public void setDrivingExists(Boolean drivingExists) {
        this.drivingExists = drivingExists;
    }

    public Object getDrivingPath() {
        return drivingPath;
    }

    public void setDrivingPath(Object drivingPath) {
        this.drivingPath = drivingPath;
    }

    public Boolean getPassBookExists() {
        return passBookExists;
    }

    public void setPassBookExists(Boolean passBookExists) {
        this.passBookExists = passBookExists;
    }

    public String getPassBookPath() {
        return passBookPath;
    }

    public void setPassBookPath(String passBookPath) {
        this.passBookPath = passBookPath;
    }

    public Object getPassBookBPath() {
        return passBookBPath;
    }

    public void setPassBookBPath(Object passBookBPath) {
        this.passBookBPath = passBookBPath;
    }

    public List<GrDoc> getGrDocs() {
        return grDocs;
    }

    public void setGrDocs(List<GrDoc> grDocs) {
        this.grDocs = grDocs;
    }

}