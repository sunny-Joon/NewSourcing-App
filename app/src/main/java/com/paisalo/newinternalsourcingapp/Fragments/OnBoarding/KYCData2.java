package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kyc_data_table2")
public class KYCData2 {
    @PrimaryKey(autoGenerate = true)
    int id;

    public String monthlyincome,expencemonthly,futureincomeannual,agricultureincomemonthky,pensionincome,interestincome,
                  otherincome,earningmembertype,earningmemberincome,loanamount,businessdetail,loanpuose,occuption,loandurationmonths,
                  selectbank;


    public KYCData2() {
    }

    public KYCData2(int id, String monthlyincome, String expencemonthly, String futureincomeannual, String agricultureincomemonthky, String pensionincome, String interestincome, String otherincome, String earningmembertype, String earningmemberincome, String loanamount, String businessdetail, String loanpuose, String occuption, String loandurationmonths, String selectbank) {
        this.id = id;
        this.monthlyincome = monthlyincome;
        this.expencemonthly = expencemonthly;
        this.futureincomeannual = futureincomeannual;
        this.agricultureincomemonthky = agricultureincomemonthky;
        this.pensionincome = pensionincome;
        this.interestincome = interestincome;
        this.otherincome = otherincome;
        this.earningmembertype = earningmembertype;
        this.earningmemberincome = earningmemberincome;
        this.loanamount = loanamount;
        this.businessdetail = businessdetail;
        this.loanpuose = loanpuose;
        this.occuption = occuption;
        this.loandurationmonths = loandurationmonths;
        this.selectbank = selectbank;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonthlyincome() {
        return monthlyincome;
    }

    public void setMonthlyincome(String monthlyincome) {
        this.monthlyincome = monthlyincome;
    }

    public String getExpencemonthly() {
        return expencemonthly;
    }

    public void setExpencemonthly(String expencemonthly) {
        this.expencemonthly = expencemonthly;
    }

    public String getFutureincomeannual() {
        return futureincomeannual;
    }

    public void setFutureincomeannual(String futureincomeannual) {
        this.futureincomeannual = futureincomeannual;
    }

    public String getAgricultureincomemonthky() {
        return agricultureincomemonthky;
    }

    public void setAgricultureincomemonthky(String agricultureincomemonthky) {
        this.agricultureincomemonthky = agricultureincomemonthky;
    }

    public String getPensionincome() {
        return pensionincome;
    }

    public void setPensionincome(String pensionincome) {
        this.pensionincome = pensionincome;
    }

    public String getInterestincome() {
        return interestincome;
    }

    public void setInterestincome(String interestincome) {
        this.interestincome = interestincome;
    }

    public String getOtherincome() {
        return otherincome;
    }

    public void setOtherincome(String otherincome) {
        this.otherincome = otherincome;
    }

    public String getEarningmembertype() {
        return earningmembertype;
    }

    public void setEarningmembertype(String earningmembertype) {
        this.earningmembertype = earningmembertype;
    }

    public String getEarningmemberincome() {
        return earningmemberincome;
    }

    public void setEarningmemberincome(String earningmemberincome) {
        this.earningmemberincome = earningmemberincome;
    }

    public String getLoanamount() {
        return loanamount;
    }

    public void setLoanamount(String loanamount) {
        this.loanamount = loanamount;
    }

    public String getBusinessdetail() {
        return businessdetail;
    }

    public void setBusinessdetail(String businessdetail) {
        this.businessdetail = businessdetail;
    }

    public String getLoanpuose() {
        return loanpuose;
    }

    public void setLoanpuose(String loanpuose) {
        this.loanpuose = loanpuose;
    }

    public String getOccuption() {
        return occuption;
    }

    public void setOccuption(String occuption) {
        this.occuption = occuption;
    }

    public String getLoandurationmonths() {
        return loandurationmonths;
    }

    public void setLoandurationmonths(String loandurationmonths) {
        this.loandurationmonths = loandurationmonths;
    }

    public String getSelectbank() {
        return selectbank;
    }

    public void setSelectbank(String selectbank) {
        this.selectbank = selectbank;
    }
}


