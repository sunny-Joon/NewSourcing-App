package com.paisalo.newinternalsourcingapp.ModelsRetrofit.Collection;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class CustomerListModel {
        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<CustomerListDataModel> data;

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
        public List<CustomerListDataModel> getData() {
            return data;
        }
        public void setData(List<CustomerListDataModel> data) {
            this.data = data;
        }
}
