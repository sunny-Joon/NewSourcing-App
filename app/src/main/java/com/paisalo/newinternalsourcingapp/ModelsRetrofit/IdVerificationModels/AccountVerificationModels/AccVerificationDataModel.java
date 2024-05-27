package com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.AccountVerificationModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class AccVerificationDataModel {
        @SerializedName("data")
        @Expose
        private FirstData data;
        @SerializedName("errorMessage")
        @Expose
        private Object errorMessage;
        @SerializedName("success")
        @Expose
        private Boolean success;

        public FirstData getData() {
            return data;
        }

        public void setData(FirstData data) {
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