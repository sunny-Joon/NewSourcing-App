package com.paisalo.newinternalsourcingapp.Modelclasses.EmiCollectionModels;

public class EmiModel {
    private String date;
    private String amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public EmiModel(String date, String amount) {
        this.date = date;
        this.amount = amount;
    }
}
