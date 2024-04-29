package com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycSubmitModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycSubmitDataModel {

    @SerializedName("fiTag")
    @Expose
    private String fiTag;
    @SerializedName("fiCreator")
    @Expose
    private String fiCreator;
    @SerializedName("fiCode")
    @Expose
    private Integer fiCode;

    public String getFiTag() {
        return fiTag;
    }

    public void setFiTag(String fiTag) {
        this.fiTag = fiTag;
    }

    public String getFiCreator() {
        return fiCreator;
    }

    public void setFiCreator(String fiCreator) {
        this.fiCreator = fiCreator;
    }

    public Integer getFiCode() {
        return fiCode;
    }

    public void setFiCode(Integer fiCode) {
        this.fiCode = fiCode;
    }

}
