package com.paisalo.newinternalsourcingapp.ModelsRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectionTokenModel {
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;
    @SerializedName("LoginData")
    @Expose
    private String loginData;
    @SerializedName(".issued")
    @Expose
    private String issued;
    @SerializedName(".expires")
    @Expose
    private String expires;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getLoginData() {
        return loginData;
    }

    public void setLoginData(String loginData) {
        this.loginData = loginData;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}

