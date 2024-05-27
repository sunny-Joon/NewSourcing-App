package com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.AccountVerificationModels;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class AccountVerificationModel {
        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private AccVerificationDataModel data;

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public AccVerificationDataModel getData() {
            return data;
        }

        public void setData(AccVerificationDataModel data) {
            this.data = data;
        }
}
