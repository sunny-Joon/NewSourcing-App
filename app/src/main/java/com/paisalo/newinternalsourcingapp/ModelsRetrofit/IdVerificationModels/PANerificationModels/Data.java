package com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.PANerificationModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("data")
    @Expose
    private Data__1 data;
    @SerializedName("errorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public Data__1 getData() {
        return data;
    }

    public void setData(Data__1 data) {
        this.data = data;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
