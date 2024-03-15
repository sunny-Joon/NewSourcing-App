package com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenDetailsModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("imeino")
    @Expose
    private String imeino;
    @SerializedName("deviceSrNo")
    @Expose
    private String deviceSrNo;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("validaty")
    @Expose
    private String validaty;

    public void setValidaty(String validaty) {
        this.validaty = validaty;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGuidId() {
        return guidId;
    }

    public void setGuidId(String guidId) {
        this.guidId = guidId;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("guidId")
    @Expose
    private String guidId;
    @SerializedName("expiredTime")
    @Expose
    private String expiredTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImeino() {
        return imeino;
    }

    public void setImeino(String imeino) {
        this.imeino = imeino;
    }

    public String getDeviceSrNo() {
        return deviceSrNo;
    }

    public void setDeviceSrNo(String deviceSrNo) {
        this.deviceSrNo = deviceSrNo;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getValidaty() {
        return validaty;
    }
}

