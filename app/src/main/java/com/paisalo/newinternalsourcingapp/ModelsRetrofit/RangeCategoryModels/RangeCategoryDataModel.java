package com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RangeCategoryDataModel {

    @SerializedName("cat_key")
    @Expose
    private String catKey;
    @SerializedName("groupDescriptionEn")
    @Expose
    private String groupDescriptionEn;
    @SerializedName("groupDescriptionHi")
    @Expose
    private String groupDescriptionHi;
    @SerializedName("descriptionEn")
    @Expose
    private String descriptionEn;
    @SerializedName("descriptionHi")
    @Expose
    private String descriptionHi;
    @SerializedName("sortOrder")
    @Expose
    private Integer sortOrder;
    @SerializedName("code")
    @Expose
    private String code;

    public String getCatKey() {
        return catKey;
    }

    public void setCatKey(String catKey) {
        this.catKey = catKey;
    }

    public String getGroupDescriptionEn() {
        return groupDescriptionEn;
    }

    public void setGroupDescriptionEn(String groupDescriptionEn) {
        this.groupDescriptionEn = groupDescriptionEn;
    }

    public String getGroupDescriptionHi() {
        return groupDescriptionHi;
    }

    public void setGroupDescriptionHi(String groupDescriptionHi) {
        this.groupDescriptionHi = groupDescriptionHi;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionHi() {
        return descriptionHi;
    }

    public void setDescriptionHi(String descriptionHi) {
        this.descriptionHi = descriptionHi;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}