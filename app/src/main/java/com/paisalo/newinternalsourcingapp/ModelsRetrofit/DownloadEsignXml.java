package com.paisalo.newinternalsourcingapp.ModelsRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class DownloadEsignXml {

        @SerializedName("ErrorCode")
        @Expose
        private String errorCode;
        @SerializedName("ErrorMessage")
        @Expose
        private String errorMessage;
        @SerializedName("ESignXml")
        @Expose
        private String eSignXml;
        @SerializedName("Transaction")
        @Expose
        private String transaction;
        @SerializedName("TimeStamp")
        @Expose
        private String timeStamp;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getESignXml() {
            return eSignXml;
        }

        public void setESignXml(String eSignXml) {
            this.eSignXml = eSignXml;
        }

        public String getTransaction() {
            return transaction;
        }

        public void setTransaction(String transaction) {
            this.transaction = transaction;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }
}
