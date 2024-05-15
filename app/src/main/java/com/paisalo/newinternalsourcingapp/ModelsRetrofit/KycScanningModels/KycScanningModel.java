package com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycScanningModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycScanningModel {
        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private KycScanningDataModel data;

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

        public KycScanningDataModel getData() {
            return data;
        }

        public void setData(KycScanningDataModel data) {
            this.data = data;
        }

}
