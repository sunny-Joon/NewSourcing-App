package com.paisalo.newinternalsourcingapp.Modelclasses;

import android.widget.TextView;

public class CustomerModel {
    private String customerName;
    private String guardian;
    private String customerMobile;
    private String totalPayment;
    private String smcode;
    private String customerAddress;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getSmcode() {
        return smcode;
    }

    public void setSmcode(String smcode) {
        this.smcode = smcode;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public CustomerModel(String customerName, String guardian, String customerMobile, String totalPayment, String smcode, String customerAddress) {
        this.customerName = customerName;
        this.guardian = guardian;
        this.customerMobile = customerMobile;
        this.totalPayment = totalPayment;
        this.smcode = smcode;
        this.customerAddress = customerAddress;
    }
}
