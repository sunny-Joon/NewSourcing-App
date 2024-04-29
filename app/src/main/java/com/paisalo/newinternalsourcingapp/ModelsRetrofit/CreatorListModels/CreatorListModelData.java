package com.paisalo.newinternalsourcingapp.ModelsRetrofit.CreatorListModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatorListModelData {

    @SerializedName("creator")
    @Expose
    private String creator;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

}