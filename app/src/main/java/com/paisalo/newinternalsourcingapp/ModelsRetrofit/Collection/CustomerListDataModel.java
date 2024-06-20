package com.paisalo.newinternalsourcingapp.ModelsRetrofit.Collection;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.paisalo.newinternalsourcingapp.Modelclasses.Installment;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CustomerListDataModel {

    private List<Installment> installmentDataList;

    @SerializedName("db")
    @Expose
    private String db;

    @SerializedName("dbName")
    @Expose
    private String dbName;

    @SerializedName("creator")
    @Expose
    private String creator;

    @SerializedName("foCode")
    @Expose
    private String foCode;

    @SerializedName("partyCd")
    @Expose
    private String partyCd;

    @SerializedName("custName")
    @Expose
    private String custName;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("fhName")
    @Expose
    private String fhName;

    @SerializedName("cityCode")
    @Expose
    private String cityCode;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("aadhar")
    @Expose
    private String aadhar;

    @SerializedName("caseCode")
    @Expose
    private String caseCode;

    @SerializedName("firstInstDate")
    @Expose
    private String firstInstDate;

    @SerializedName("noOfInsts")
    @Expose
    private Integer noOfInsts;

    @SerializedName("totalDueAmt")
    @Expose
    private int totalDueAmt;

    @SerializedName("totalDueCnt")
    @Expose
    private Integer totalDueCnt;

    @SerializedName("totalRecdAmt")
    @Expose
    private int totalRecdAmt;

    @SerializedName("totalRecdCnt")
    @Expose
    private Integer totalRecdCnt;

    @SerializedName("instsAmtDue")
    @Expose
    private int instsAmtDue;

    @SerializedName("nofInstDue")
    @Expose
    private Integer nofInstDue;

    @SerializedName("instData")
    @Expose
    private String instData;

    @SerializedName("toBeDueAmt")
    @Expose
    private int toBeDueAmt;

    @SerializedName("toBeDueDate")
    @Expose
    private String toBeDueDate;

    @SerializedName("futureDue")
    @Expose
    private int futureDue;

    @SerializedName("instDueAsOn")
    @Expose
    private String instDueAsOn;

    @SerializedName("isNachReg")
    @Expose
    private String isNachReg;

    @SerializedName("dataAsOn")
    @Expose
    private String dataAsOn;

    @SerializedName("interestAmt")
    @Expose
    private int interestAmt;

    @SerializedName("schmCode")
    @Expose
    private String schmCode;

    private boolean isEnabled = true;

    // Method to parse instData to List<Installment>
    public List<Installment> getInstallmentDataList() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Installment>>() {}.getType();
        return gson.fromJson(instData, listType);
    }

    public int getInstallmentSum(boolean selectedOnly) {
        List<Installment> installments = getInstallmentDataList();
        int sum = 0;
        for (Installment installment : installments) {
            sum += installment.getAmountAsInt();
        }
        return sum;
    }

    // Method to print installment data
    public void printInstallmentData() {
        List<Installment> installmentDataList = getInstallmentDataList();
        for (Installment data : installmentDataList) {
            System.out.println(data);
        }
    }

    public static Comparator<CustomerListDataModel> DueDataNachName = new Comparator<CustomerListDataModel>() {
        public int compare(CustomerListDataModel dueData1, CustomerListDataModel dueData2) {
            int compareNach = dueData1.isNachReg.compareTo(dueData2.isNachReg);
            int compareName = dueData1.custName.compareTo(dueData2.custName);
            return ((compareNach == 0) ? compareName : compareNach);
        }
    };

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getFoCode() {
        return foCode;
    }

    public void setFoCode(String foCode) {
        this.foCode = foCode;
    }

    public String getPartyCd() {
        return partyCd;
    }

    public void setPartyCd(String partyCd) {
        this.partyCd = partyCd;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFhName() {
        return fhName;
    }

    public void setFhName(String fhName) {
        this.fhName = fhName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getFirstInstDate() {
        return firstInstDate;
    }

    public void setFirstInstDate(String firstInstDate) {
        this.firstInstDate = firstInstDate;
    }

    public Integer getNoOfInsts() {
        return noOfInsts;
    }

    public void setNoOfInsts(Integer noOfInsts) {
        this.noOfInsts = noOfInsts;
    }

    public int getTotalDueAmt() {
        return totalDueAmt;
    }

    public void setTotalDueAmt(int totalDueAmt) {
        this.totalDueAmt = totalDueAmt;
    }

    public Integer getTotalDueCnt() {
        return totalDueCnt;
    }

    public void setTotalDueCnt(Integer totalDueCnt) {
        this.totalDueCnt = totalDueCnt;
    }

    public int getTotalRecdAmt() {
        return totalRecdAmt;
    }

    public void setTotalRecdAmt(int totalRecdAmt) {
        this.totalRecdAmt = totalRecdAmt;
    }

    public Integer getTotalRecdCnt() {
        return totalRecdCnt;
    }

    public void setTotalRecdCnt(Integer totalRecdCnt) {
        this.totalRecdCnt = totalRecdCnt;
    }

    public int getInstsAmtDue() {
        return instsAmtDue;
    }

    public void setInstsAmtDue(int instsAmtDue) {
        this.instsAmtDue = instsAmtDue;
    }

    public Integer getNofInstDue() {
        return nofInstDue;
    }

    public void setNofInstDue(Integer nofInstDue) {
        this.nofInstDue = nofInstDue;
    }

    public int getToBeDueAmt() {
        return toBeDueAmt;
    }

    public void setToBeDueAmt(int toBeDueAmt) {
        this.toBeDueAmt = toBeDueAmt;
    }

    public String  getToBeDueDate() {
        return toBeDueDate;
    }

    public void setToBeDueDate(String  toBeDueDate) {
        this.toBeDueDate = toBeDueDate;
    }

    public int getFutureDue() {
        return futureDue;
    }

    public void setFutureDue(int futureDue) {
        this.futureDue = futureDue;
    }

    public String getInstDueAsOn() {
        return instDueAsOn;
    }

    public void setInstDueAsOn(String instDueAsOn) {
        this.instDueAsOn = instDueAsOn;
    }

    public String getIsNachReg() {
        return isNachReg;
    }

    public void setIsNachReg(String isNachReg) {
        this.isNachReg = isNachReg;
    }

    public String getDataAsOn() {
        return dataAsOn;
    }

    public void setDataAsOn(String dataAsOn) {
        this.dataAsOn = dataAsOn;
    }

    public int getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(int interestAmt) {
        this.interestAmt = interestAmt;
    }

    public String getSchmCode() {
        return schmCode;
    }

    public void setSchmCode(String schmCode) {
        this.schmCode = schmCode;
    }

    public int getMaxDueAmount() {
        return  (instsAmtDue + toBeDueAmt + futureDue + interestAmt);
    }

    public List<Installment> getInstData() {
        List<Installment> installments = new ArrayList<>();
        boolean isFirst = true;
        // Get the parsed installment data from the global field
        List<Installment> installmentDataList = getInstallmentDataList();
        int sunBal = getSundryBalance();
        for (Installment inst : installmentDataList) {
            Installment cloneInst = new Installment(inst);
            if (isFirst) {
                cloneInst.setAmount(cloneInst.getAmount() - sunBal);
                isFirst = false;
            }
            installments.add(cloneInst);
        }
        if (toBeDueAmt > 0) {
            Installment installment = new Installment();
            installment.setAmount(toBeDueAmt);
          //  installment.setDueDate((new SimpleDateFormat("dd-MM-yyyy")).format(toBeDueDate));
            installment.setDueDate(toBeDueDate);
            installment.setSelected(false);
            installments.add(installment);
        }
        return installments;
    }


    public int getSundryBalance() {
        return getInstallmentSum(false) - getInstsAmtDue();
    }

    public void clearSection(List<Installment> installments) {
        int tot = 0;
        for (Installment installment : installments) {
            installment.setSelected(false);
        }
    }
}
