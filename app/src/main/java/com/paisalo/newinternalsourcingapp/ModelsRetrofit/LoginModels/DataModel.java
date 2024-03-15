package com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {

    @SerializedName("folist")
    @Expose
    private List<FoModel> folist;
    @SerializedName("foImei")
    @Expose
    private FoImeiModel foImeiModel;
    @SerializedName("tokenDetails")
    @Expose
    private TokenDetailsModel tokenDetailsModel;

    public List<FoModel> getFolist() {
        return folist;
    }

    public void setFolist(List<FoModel> folist) {
        this.folist = folist;
    }

    public FoImeiModel getFoImei() {
        return foImeiModel;
    }

    public void setFoImei(FoImeiModel foImeiModel) {
        this.foImeiModel = foImeiModel;
    }

    public TokenDetailsModel getTokenDetails() {
        return tokenDetailsModel;
    }

    public void setTokenDetails(TokenDetailsModel tokenDetailsModel) {
        this.tokenDetailsModel = tokenDetailsModel;
    }

}
