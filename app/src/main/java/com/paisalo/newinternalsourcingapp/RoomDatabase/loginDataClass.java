package com.paisalo.newinternalsourcingapp.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "loginDataBase")
public class loginDataClass {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String imeino;
    public String foCode;
    public String foName;
    public String creator;
    public String isActive;
    public String dataBase;
    public String tag;
    public String areaCd;
    public String areaName;
    public String creatorDesc;
    public String fiExecCode;
    public String fiExecName;
    public String foImeiimeino;
    public String actualYN;
    public String foImeiIsActive;
    public String newAppVersion;
    public String appDownPath;
    public String requestUrl;
    public String simno;
    public String tokenId;
    public String token;
    public String userName;
    public String deviceSrNo;
    public String password;
    public String validity;
    public String refreshToken;
    public String role;
    public String guidId;

    public loginDataClass() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImeino() {
        return imeino;
    }

    public void setImeino(String imeino) {
        this.imeino = imeino;
    }

    public String getFoCode() {
        return foCode;
    }

    public void setFoCode(String foCode) {
        this.foCode = foCode;
    }

    public String getFoName() {
        return foName;
    }

    public void setFoName(String foName) {
        this.foName = foName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCreatorDesc() {
        return creatorDesc;
    }

    public void setCreatorDesc(String creatorDesc) {
        this.creatorDesc = creatorDesc;
    }

    public String getFiExecCode() {
        return fiExecCode;
    }

    public void setFiExecCode(String fiExecCode) {
        this.fiExecCode = fiExecCode;
    }

    public String getFiExecName() {
        return fiExecName;
    }

    public void setFiExecName(String fiExecName) {
        this.fiExecName = fiExecName;
    }

    public String getFoImeiimeino() {
        return foImeiimeino;
    }

    public void setFoImeiimeino(String foImeiimeino) {
        this.foImeiimeino = foImeiimeino;
    }

    public String getActualYN() {
        return actualYN;
    }

    public void setActualYN(String actualYN) {
        this.actualYN = actualYN;
    }

    public String getFoImeiIsActive() {
        return foImeiIsActive;
    }

    public void setFoImeiIsActive(String foImeiIsActive) {
        this.foImeiIsActive = foImeiIsActive;
    }

    public String getNewAppVersion() {
        return newAppVersion;
    }

    public void setNewAppVersion(String newAppVersion) {
        this.newAppVersion = newAppVersion;
    }

    public String getAppDownPath() {
        return appDownPath;
    }

    public void setAppDownPath(String appDownPath) {
        this.appDownPath = appDownPath;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getSimno() {
        return simno;
    }

    public void setSimno(String simno) {
        this.simno = simno;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
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

    public String getDeviceSrNo() {
        return deviceSrNo;
    }

    public void setDeviceSrNo(String deviceSrNo) {
        this.deviceSrNo = deviceSrNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
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

    public loginDataClass(long id, String imeino, String foCode, String foName, String creator, String isActive, String dataBase, String tag, String areaCd, String areaName, String creatorDesc, String fiExecCode, String fiExecName, String foImeiimeino, String actualYN, String foImeiIsActive, String newAppVersion, String appDownPath, String requestUrl, String simno, String tokenId, String token, String userName, String deviceSrNo, String password, String validity, String refreshToken, String role, String guidId, String expiredTime) {
        this.id = id;
        this.imeino = imeino;
        this.foCode = foCode;
        this.foName = foName;
        this.creator = creator;
        this.isActive = isActive;
        this.dataBase = dataBase;
        this.tag = tag;
        this.areaCd = areaCd;
        this.areaName = areaName;
        this.creatorDesc = creatorDesc;
        this.fiExecCode = fiExecCode;
        this.fiExecName = fiExecName;
        this.foImeiimeino = foImeiimeino;
        this.actualYN = actualYN;
        this.foImeiIsActive = foImeiIsActive;
        this.newAppVersion = newAppVersion;
        this.appDownPath = appDownPath;
        this.requestUrl = requestUrl;
        this.simno = simno;
        this.tokenId = tokenId;
        this.token = token;
        this.userName = userName;
        this.deviceSrNo = deviceSrNo;
        this.password = password;
        this.validity = validity;
        this.refreshToken = refreshToken;
        this.role = role;
        this.guidId = guidId;
        this.expiredTime = expiredTime;
    }

    public String expiredTime;
}
