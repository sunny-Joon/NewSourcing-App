package com.paisalo.newinternalsourcingapp.ModelsRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IfscModel {
        @SerializedName("MICR")
        @Expose
        private String micr;
        @SerializedName("BRANCH")
        @Expose
        private String branch;
        @SerializedName("ADDRESS")
        @Expose
        private String address;
        @SerializedName("STATE")
        @Expose
        private String state;
        @SerializedName("CONTACT")
        @Expose
        private String contact;
        @SerializedName("UPI")
        @Expose
        private Boolean upi;
        @SerializedName("RTGS")
        @Expose
        private Boolean rtgs;
        @SerializedName("CITY")
        @Expose
        private String city;
        @SerializedName("CENTRE")
        @Expose
        private String centre;
        @SerializedName("DISTRICT")
        @Expose
        private String district;
        @SerializedName("NEFT")
        @Expose
        private Boolean neft;
        @SerializedName("IMPS")
        @Expose
        private Boolean imps;
        @SerializedName("SWIFT")
        @Expose
        private Object swift;
        @SerializedName("ISO3166")
        @Expose
        private String iso3166;
        @SerializedName("BANK")
        @Expose
        private String bank;
        @SerializedName("BANKCODE")
        @Expose
        private String bankcode;
        @SerializedName("IFSC")
        @Expose
        private String ifsc;

        public String getMicr() {
            return micr;
        }

        public void setMicr(String micr) {
            this.micr = micr;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public Boolean getUpi() {
            return upi;
        }

        public void setUpi(Boolean upi) {
            this.upi = upi;
        }

        public Boolean getRtgs() {
            return rtgs;
        }

        public void setRtgs(Boolean rtgs) {
            this.rtgs = rtgs;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCentre() {
            return centre;
        }

        public void setCentre(String centre) {
            this.centre = centre;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public Boolean getNeft() {
            return neft;
        }

        public void setNeft(Boolean neft) {
            this.neft = neft;
        }

        public Boolean getImps() {
            return imps;
        }

        public void setImps(Boolean imps) {
            this.imps = imps;
        }

        public Object getSwift() {
            return swift;
        }

        public void setSwift(Object swift) {
            this.swift = swift;
        }

        public String getIso3166() {
            return iso3166;
        }

        public void setIso3166(String iso3166) {
            this.iso3166 = iso3166;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBankcode() {
            return bankcode;
        }

        public void setBankcode(String bankcode) {
            this.bankcode = bankcode;
        }

        public String getIfsc() {
            return ifsc;
        }

        public void setIfsc(String ifsc) {
            this.ifsc = ifsc;
        }

}
