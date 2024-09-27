package com.paisalo.newinternalsourcingapp.ModelsRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchStatusDataModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("disbursement")
    @Expose
    private Integer disbursement;
    @SerializedName("crifScore")
    @Expose
    private Integer crifScore;
    @SerializedName("functions")
    @Expose
    private Integer functions;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDisbursement() {
        return disbursement;
    }

    public void setDisbursement(Integer disbursement) {
        this.disbursement = disbursement;
    }

    public Integer getCrifScore() {
        return crifScore;
    }

    public void setCrifScore(Integer crifScore) {
        this.crifScore = crifScore;
    }

    public Integer getFunctions() {
        return functions;
    }

    public void setFunctions(Integer functions) {
        this.functions = functions;
    }

}
