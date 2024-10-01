package com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllDataAFDataModel implements Serializable {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("fI_NO")
    @Expose
    private Integer fINO;
    @SerializedName("delCode")
    @Expose
    private String delCode;
    @SerializedName("a_Code")
    @Expose
    private String aCode;
    @SerializedName("case_City")
    @Expose
    private String caseCity;
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("approved")
    @Expose
    private String approved;
    @SerializedName("saved")
    @Expose
    private String saved;
    @SerializedName("loan_Amt")
    @Expose
    private Integer loanAmt;
    @SerializedName("loan_Duration")
    @Expose
    private Integer loanDuration;
    @SerializedName("loan_Reason")
    @Expose
    private String loanReason;
    @SerializedName("ini")
    @Expose
    private String ini;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("mname")
    @Expose
    private String mname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("f_Ini")
    @Expose
    private String fIni;
    @SerializedName("f_Fname")
    @Expose
    private String fFname;
    @SerializedName("f_Mname")
    @Expose
    private String fMname;
    @SerializedName("f_Lname")
    @Expose
    private String fLname;
    @SerializedName("business_Detail")
    @Expose
    private String businessDetail;
    @SerializedName("income")
    @Expose
    private Integer income;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("cast")
    @Expose
    private String cast;
    @SerializedName("p_Add1")
    @Expose
    private String pAdd1;
    @SerializedName("p_Add2")
    @Expose
    private String pAdd2;
    @SerializedName("p_Add3")
    @Expose
    private String pAdd3;
    @SerializedName("p_City")
    @Expose
    private String pCity;
    @SerializedName("p_Ph1")
    @Expose
    private String pPh1;
    @SerializedName("p_Ph2")
    @Expose
    private String pPh2;
    @SerializedName("p_Ph3")
    @Expose
    private String pPh3;
    @SerializedName("p_Pin")
    @Expose
    private Integer pPin;
    @SerializedName("p_State")
    @Expose
    private String pState;
    @SerializedName("t_Add1")
    @Expose
    private String tAdd1;
    @SerializedName("t_Add2")
    @Expose
    private String tAdd2;
    @SerializedName("t_Add3")
    @Expose
    private String tAdd3;
    @SerializedName("t_City")
    @Expose
    private String tCity;
    @SerializedName("t_Ph1")
    @Expose
    private String tPh1;
    @SerializedName("t_Ph2")
    @Expose
    private String tPh2;
    @SerializedName("t_Ph3")
    @Expose
    private String tPh3;
    @SerializedName("t_Pin")
    @Expose
    private Integer tPin;
    @SerializedName("t_State")
    @Expose
    private String tState;
    @SerializedName("o_Add1")
    @Expose
    private String oAdd1;
    @SerializedName("o_Add2")
    @Expose
    private String oAdd2;
    @SerializedName("o_Add3")
    @Expose
    private String oAdd3;
    @SerializedName("o_City")
    @Expose
    private String oCity;
    @SerializedName("o_Ph1")
    @Expose
    private String oPh1;
    @SerializedName("o_Ph2")
    @Expose
    private String oPh2;
    @SerializedName("o_Ph3")
    @Expose
    private String oPh3;
    @SerializedName("o_Pin")
    @Expose
    private Integer oPin;
    @SerializedName("o_State")
    @Expose
    private String oState;
    @SerializedName("identity")
    @Expose
    private String identity;
    @SerializedName("panNO")
    @Expose
    private String panNO;
    @SerializedName("identity_No")
    @Expose
    private String identityNo;
    @SerializedName("bank_Ac_No")
    @Expose
    private String bankAcNo;
    @SerializedName("bank_Address")
    @Expose
    private String bankAddress;
    @SerializedName("res_type")
    @Expose
    private String resType;
    @SerializedName("house_Owner")
    @Expose
    private String houseOwner;
    @SerializedName("rent_of_House")
    @Expose
    private Integer rentOfHouse;
    @SerializedName("property_Det")
    @Expose
    private String propertyDet;
    @SerializedName("fAmily_member")
    @Expose
    private String fAmilyMember;
    @SerializedName("enc_Property")
    @Expose
    private String encProperty;
    @SerializedName("main_Title_Deed")
    @Expose
    private String mainTitleDeed;
    @SerializedName("lit_Det")
    @Expose
    private String litDet;
    @SerializedName("sec_Details")
    @Expose
    private String secDetails;
    @SerializedName("fI_REPORT")
    @Expose
    private String fIREPORT;
    @SerializedName("flat_Typ")
    @Expose
    private String flatTyp;
    @SerializedName("near_House")
    @Expose
    private String nearHouse;
    @SerializedName("house_Loan")
    @Expose
    private String houseLoan;
    @SerializedName("loan_EMi")
    @Expose
    private Integer loanEMi;
    @SerializedName("house_Identity")
    @Expose
    private String houseIdentity;
    @SerializedName("live_IN_City")
    @Expose
    private String liveINCity;
    @SerializedName("live_In_Present_Place")
    @Expose
    private String liveInPresentPlace;
    @SerializedName("area_Of_House")
    @Expose
    private Integer areaOfHouse;
    @SerializedName("house_Locality")
    @Expose
    private String houseLocality;
    @SerializedName("house_Interior")
    @Expose
    private String houseInterior;
    @SerializedName("two_Wh_From")
    @Expose
    private String twoWhFrom;
    @SerializedName("two_Wh_Modal")
    @Expose
    private String twoWhModal;
    @SerializedName("two_Wh_Make")
    @Expose
    private String twoWhMake;
    @SerializedName("two_Wh_Regdno")
    @Expose
    private String twoWhRegdno;
    @SerializedName("four_Wh_From")
    @Expose
    private String fourWhFrom;
    @SerializedName("four_Wh_Modal")
    @Expose
    private String fourWhModal;
    @SerializedName("four_Wh_Make")
    @Expose
    private String fourWhMake;
    @SerializedName("four_Wh_Regdno")
    @Expose
    private String fourWhRegdno;
    @SerializedName("vehicle_USe_By")
    @Expose
    private String vehicleUSeBy;
    @SerializedName("oth_Prop_Det")
    @Expose
    private String othPropDet;
    @SerializedName("person_Contact_place")
    @Expose
    private String personContactPlace;
    @SerializedName("oth_Contact_Place")
    @Expose
    private String othContactPlace;
    @SerializedName("verified_phone")
    @Expose
    private String verifiedPhone;
    @SerializedName("sel")
    @Expose
    private String sel;
    @SerializedName("casE_NO")
    @Expose
    private String casENO;
    @SerializedName("groupCode")
    @Expose
    private String groupCode;
    @SerializedName("cityCode")
    @Expose
    private String cityCode;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("landHolding")
    @Expose
    private String landHolding;
    @SerializedName("exServiceMan")
    @Expose
    private String exServiceMan;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("aadharID")
    @Expose
    private String aadharID;
    @SerializedName("voterID")
    @Expose
    private String voterID;
    @SerializedName("drivingLic")
    @Expose
    private String drivingLic;
    @SerializedName("isCustvisited")
    @Expose
    private String isCustvisited;
    @SerializedName("smCode")
    @Expose
    private String smCode;
    @SerializedName("oldCaseCode")
    @Expose
    private String oldCaseCode;
    @SerializedName("isAadharVerified")
    @Expose
    private String isAadharVerified;
    @SerializedName("isMarried")
    @Expose
    private String isMarried;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("bankAcType")
    @Expose
    private String bankAcType;
    @SerializedName("bankAcOpenDt")
    @Expose
    private String bankAcOpenDt;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("geoDateTime")
    @Expose
    private Object geoDateTime;
    @SerializedName("relationWBorrower")
    @Expose
    private String relationWBorrower;
    @SerializedName("kycuuid")
    @Expose
    private String kycuuid;
    @SerializedName("eKycTkn")
    @Expose
    private String eKycTkn;
    @SerializedName("m_Ini")
    @Expose
    private String mIni;
    @SerializedName("m_Fname")
    @Expose
    private String mFname;
    @SerializedName("m_Mname")
    @Expose
    private String mMname;
    @SerializedName("m_Lname")
    @Expose
    private String mLname;
    @SerializedName("s_Ini")
    @Expose
    private String sIni;
    @SerializedName("s_Fname")
    @Expose
    private String sFname;
    @SerializedName("s_Mname")
    @Expose
    private String sMname;
    @SerializedName("s_Lname")
    @Expose
    private String sLname;
    @SerializedName("monthlyIncome")
    @Expose
    private Integer monthlyIncome;
    @SerializedName("futureIncome")
    @Expose
    private Integer futureIncome;
    @SerializedName("agriculture")
    @Expose
    private String agriculture;
    @SerializedName("pension")
    @Expose
    private Integer pension;
    @SerializedName("interest")
    @Expose
    private Integer interest;
    @SerializedName("otherIncome")
    @Expose
    private Integer otherIncome;
    @SerializedName("earningMemberType")
    @Expose
    private String earningMemberType;
    @SerializedName("earningMemberIncome")
    @Expose
    private String earningMemberIncome;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("fiExtra")
    @Expose
    private FiExtra fiExtra;
    @SerializedName("fiFamLoans")
    @Expose
    private List<FiFamLoan> fiFamLoans;
    @SerializedName("fiGuarantors")
    @Expose
    private List<FiGuarantor> fiGuarantors;
    @SerializedName("fiFamMems")
    @Expose
    private List<FiFamMem> fiFamMems;
    @SerializedName("fiFamExpenses")
    @Expose
    private FiFamExpenses fiFamExpenses;
    @SerializedName("fiExtraBankBo")
    @Expose
    private FiExtraBankBo fiExtraBankBo;
    @SerializedName("uploadedFiDocsList")
    @Expose
    private List<UploadedFiDocs> uploadedFiDocsList;
    @SerializedName("dmlOperation")
    @Expose
    private String dmlOperation;
    @SerializedName("ipAddr")
    @Expose
    private String ipAddr;
    @SerializedName("expense")
    @Expose
    private Integer expense;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("creation_Date")
    @Expose
    private String creationDate;
    @SerializedName("auth_UserID")
    @Expose
    private String authUserID;
    @SerializedName("auth_Date")
    @Expose
    private Object authDate;
    @SerializedName("mod_Type")
    @Expose
    private Object modType;
    @SerializedName("last_Mod_UserID")
    @Expose
    private Object lastModUserID;
    @SerializedName("last_Mod_Date")
    @Expose
    private Object lastModDate;

        public String getProfilePic() {

            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        @SerializedName("profilePic")
        @Expose
        private String profilePic;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getfINO() {
        return fINO;
    }

    public void setfINO(Integer fINO) {
        this.fINO = fINO;
    }

    public String getDelCode() {
        return delCode;
    }

    public void setDelCode(String delCode) {
        this.delCode = delCode;
    }

    public String getaCode() {
        return aCode;
    }

    public void setaCode(String aCode) {
        this.aCode = aCode;
    }

    public String getCaseCity() {
        return caseCity;
    }

    public void setCaseCity(String caseCity) {
        this.caseCity = caseCity;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }

    public Integer getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(Integer loanAmt) {
        this.loanAmt = loanAmt;
    }

    public Integer getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(Integer loanDuration) {
        this.loanDuration = loanDuration;
    }

    public String getLoanReason() {
        return loanReason;
    }

    public void setLoanReason(String loanReason) {
        this.loanReason = loanReason;
    }

    public String getIni() {
        return ini;
    }

    public void setIni(String ini) {
        this.ini = ini;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getfIni() {
        return fIni;
    }

    public void setfIni(String fIni) {
        this.fIni = fIni;
    }

    public String getfFname() {
        return fFname;
    }

    public void setfFname(String fFname) {
        this.fFname = fFname;
    }

    public String getfMname() {
        return fMname;
    }

    public void setfMname(String fMname) {
        this.fMname = fMname;
    }

    public String getfLname() {
        return fLname;
    }

    public void setfLname(String fLname) {
        this.fLname = fLname;
    }

    public String getBusinessDetail() {
        return businessDetail;
    }

    public void setBusinessDetail(String businessDetail) {
        this.businessDetail = businessDetail;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getpAdd1() {
        return pAdd1;
    }

    public void setpAdd1(String pAdd1) {
        this.pAdd1 = pAdd1;
    }

    public String getpAdd2() {
        return pAdd2;
    }

    public void setpAdd2(String pAdd2) {
        this.pAdd2 = pAdd2;
    }

    public String getpAdd3() {
        return pAdd3;
    }

    public void setpAdd3(String pAdd3) {
        this.pAdd3 = pAdd3;
    }

    public String getpCity() {
        return pCity;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public String getpPh1() {
        return pPh1;
    }

    public void setpPh1(String pPh1) {
        this.pPh1 = pPh1;
    }

    public String getpPh2() {
        return pPh2;
    }

    public void setpPh2(String pPh2) {
        this.pPh2 = pPh2;
    }

    public String getpPh3() {
        return pPh3;
    }

    public void setpPh3(String pPh3) {
        this.pPh3 = pPh3;
    }

    public Integer getpPin() {
        return pPin;
    }

    public void setpPin(Integer pPin) {
        this.pPin = pPin;
    }

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

    public String gettAdd1() {
        return tAdd1;
    }

    public void settAdd1(String tAdd1) {
        this.tAdd1 = tAdd1;
    }

    public String gettAdd2() {
        return tAdd2;
    }

    public void settAdd2(String tAdd2) {
        this.tAdd2 = tAdd2;
    }

    public String gettAdd3() {
        return tAdd3;
    }

    public void settAdd3(String tAdd3) {
        this.tAdd3 = tAdd3;
    }

    public String gettCity() {
        return tCity;
    }

    public void settCity(String tCity) {
        this.tCity = tCity;
    }

    public String gettPh1() {
        return tPh1;
    }

    public void settPh1(String tPh1) {
        this.tPh1 = tPh1;
    }

    public String gettPh2() {
        return tPh2;
    }

    public void settPh2(String tPh2) {
        this.tPh2 = tPh2;
    }

    public String gettPh3() {
        return tPh3;
    }

    public void settPh3(String tPh3) {
        this.tPh3 = tPh3;
    }

    public Integer gettPin() {
        return tPin;
    }

    public void settPin(Integer tPin) {
        this.tPin = tPin;
    }

    public String gettState() {
        return tState;
    }

    public void settState(String tState) {
        this.tState = tState;
    }

    public String getoAdd1() {
        return oAdd1;
    }

    public void setoAdd1(String oAdd1) {
        this.oAdd1 = oAdd1;
    }

    public String getoAdd2() {
        return oAdd2;
    }

    public void setoAdd2(String oAdd2) {
        this.oAdd2 = oAdd2;
    }

    public String getoAdd3() {
        return oAdd3;
    }

    public void setoAdd3(String oAdd3) {
        this.oAdd3 = oAdd3;
    }

    public String getoCity() {
        return oCity;
    }

    public void setoCity(String oCity) {
        this.oCity = oCity;
    }

    public String getoPh1() {
        return oPh1;
    }

    public void setoPh1(String oPh1) {
        this.oPh1 = oPh1;
    }

    public String getoPh2() {
        return oPh2;
    }

    public void setoPh2(String oPh2) {
        this.oPh2 = oPh2;
    }

    public String getoPh3() {
        return oPh3;
    }

    public void setoPh3(String oPh3) {
        this.oPh3 = oPh3;
    }

    public Integer getoPin() {
        return oPin;
    }

    public void setoPin(Integer oPin) {
        this.oPin = oPin;
    }

    public String getoState() {
        return oState;
    }

    public void setoState(String oState) {
        this.oState = oState;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPanNO() {
        return panNO;
    }

    public void setPanNO(String panNO) {
        this.panNO = panNO;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getBankAcNo() {
        return bankAcNo;
    }

    public void setBankAcNo(String bankAcNo) {
        this.bankAcNo = bankAcNo;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(String houseOwner) {
        this.houseOwner = houseOwner;
    }

    public Integer getRentOfHouse() {
        return rentOfHouse;
    }

    public void setRentOfHouse(Integer rentOfHouse) {
        this.rentOfHouse = rentOfHouse;
    }

    public String getPropertyDet() {
        return propertyDet;
    }

    public void setPropertyDet(String propertyDet) {
        this.propertyDet = propertyDet;
    }

    public String getfAmilyMember() {
        return fAmilyMember;
    }

    public void setfAmilyMember(String fAmilyMember) {
        this.fAmilyMember = fAmilyMember;
    }

    public String getIFSCCode() {
        return encProperty;
    }

    public void setIFSCCode(String encProperty) {
        this.encProperty = encProperty;
    }

    public String getMainTitleDeed() {
        return mainTitleDeed;
    }

    public void setMainTitleDeed(String mainTitleDeed) {
        this.mainTitleDeed = mainTitleDeed;
    }

    public String getLitDet() {
        return litDet;
    }

    public void setLitDet(String litDet) {
        this.litDet = litDet;
    }

    public String getSecDetails() {
        return secDetails;
    }

    public void setSecDetails(String secDetails) {
        this.secDetails = secDetails;
    }

    public String getfIREPORT() {
        return fIREPORT;
    }

    public void setfIREPORT(String fIREPORT) {
        this.fIREPORT = fIREPORT;
    }

    public String getFlatTyp() {
        return flatTyp;
    }

    public void setFlatTyp(String flatTyp) {
        this.flatTyp = flatTyp;
    }

    public String getNearHouse() {
        return nearHouse;
    }

    public void setNearHouse(String nearHouse) {
        this.nearHouse = nearHouse;
    }

    public String getHouseLoan() {
        return houseLoan;
    }

    public void setHouseLoan(String houseLoan) {
        this.houseLoan = houseLoan;
    }

    public Integer getLoanEMi() {
        return loanEMi;
    }

    public void setLoanEMi(Integer loanEMi) {
        this.loanEMi = loanEMi;
    }

    public String getHouseIdentity() {
        return houseIdentity;
    }

    public void setHouseIdentity(String houseIdentity) {
        this.houseIdentity = houseIdentity;
    }

    public String getLiveINCity() {
        return liveINCity;
    }

    public void setLiveINCity(String liveINCity) {
        this.liveINCity = liveINCity;
    }

    public String getLiveInPresentPlace() {
        return liveInPresentPlace;
    }

    public void setLiveInPresentPlace(String liveInPresentPlace) {
        this.liveInPresentPlace = liveInPresentPlace;
    }

    public Integer getAreaOfHouse() {
        return areaOfHouse;
    }

    public void setAreaOfHouse(Integer areaOfHouse) {
        this.areaOfHouse = areaOfHouse;
    }

    public String getHouseLocality() {
        return houseLocality;
    }

    public void setHouseLocality(String houseLocality) {
        this.houseLocality = houseLocality;
    }

    public String getHouseInterior() {
        return houseInterior;
    }

    public void setHouseInterior(String houseInterior) {
        this.houseInterior = houseInterior;
    }

    public String getTwoWhFrom() {
        return twoWhFrom;
    }

    public void setTwoWhFrom(String twoWhFrom) {
        this.twoWhFrom = twoWhFrom;
    }

    public String getTwoWhModal() {
        return twoWhModal;
    }

    public void setTwoWhModal(String twoWhModal) {
        this.twoWhModal = twoWhModal;
    }

    public String getTwoWhMake() {
        return twoWhMake;
    }

    public void setTwoWhMake(String twoWhMake) {
        this.twoWhMake = twoWhMake;
    }

    public String getTwoWhRegdno() {
        return twoWhRegdno;
    }

    public void setTwoWhRegdno(String twoWhRegdno) {
        this.twoWhRegdno = twoWhRegdno;
    }

    public String getFourWhFrom() {
        return fourWhFrom;
    }

    public void setFourWhFrom(String fourWhFrom) {
        this.fourWhFrom = fourWhFrom;
    }

    public String getFourWhModal() {
        return fourWhModal;
    }

    public void setFourWhModal(String fourWhModal) {
        this.fourWhModal = fourWhModal;
    }

    public String getFourWhMake() {
        return fourWhMake;
    }

    public void setFourWhMake(String fourWhMake) {
        this.fourWhMake = fourWhMake;
    }

    public String getFourWhRegdno() {
        return fourWhRegdno;
    }

    public void setFourWhRegdno(String fourWhRegdno) {
        this.fourWhRegdno = fourWhRegdno;
    }

    public String getVehicleUSeBy() {
        return vehicleUSeBy;
    }

    public void setVehicleUSeBy(String vehicleUSeBy) {
        this.vehicleUSeBy = vehicleUSeBy;
    }

    public String getOthPropDet() {
        return othPropDet;
    }

    public void setOthPropDet(String othPropDet) {
        this.othPropDet = othPropDet;
    }

    public String getPersonContactPlace() {
        return personContactPlace;
    }

    public void setPersonContactPlace(String personContactPlace) {
        this.personContactPlace = personContactPlace;
    }

    public String getOthContactPlace() {
        return othContactPlace;
    }

    public void setOthContactPlace(String othContactPlace) {
        this.othContactPlace = othContactPlace;
    }

    public String getVerifiedPhone() {
        return verifiedPhone;
    }

    public void setVerifiedPhone(String verifiedPhone) {
        this.verifiedPhone = verifiedPhone;
    }

    public String getSel() {
        return sel;
    }

    public void setSel(String sel) {
        this.sel = sel;
    }

    public String getCasENO() {
        return casENO;
    }

    public void setCasENO(String casENO) {
        this.casENO = casENO;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getLandHolding() {
        return landHolding;
    }

    public void setLandHolding(String landHolding) {
        this.landHolding = landHolding;
    }

    public String getExServiceMan() {
        return exServiceMan;
    }

    public void setExServiceMan(String exServiceMan) {
        this.exServiceMan = exServiceMan;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAadharID() {
        return aadharID;
    }

    public void setAadharID(String aadharID) {
        this.aadharID = aadharID;
    }

    public String getVoterID() {
        return voterID;
    }

    public void setVoterID(String voterID) {
        this.voterID = voterID;
    }

    public String getDrivingLic() {
        return drivingLic;
    }

    public void setDrivingLic(String drivingLic) {
        this.drivingLic = drivingLic;
    }

    public String getIsCustvisited() {
        return isCustvisited;
    }

    public void setIsCustvisited(String isCustvisited) {
        this.isCustvisited = isCustvisited;
    }

    public String getSmCode() {
        return smCode;
    }

    public void setSmCode(String smCode) {
        this.smCode = smCode;
    }

    public String getOldCaseCode() {
        return oldCaseCode;
    }

    public void setOldCaseCode(String oldCaseCode) {
        this.oldCaseCode = oldCaseCode;
    }

    public String getIsAadharVerified() {
        return isAadharVerified;
    }

    public void setIsAadharVerified(String isAadharVerified) {
        this.isAadharVerified = isAadharVerified;
    }

    public String getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(String isMarried) {
        this.isMarried = isMarried;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAcType() {
        return bankAcType;
    }

    public void setBankAcType(String bankAcType) {
        this.bankAcType = bankAcType;
    }

    public String getBankAcOpenDt() {
        return bankAcOpenDt;
    }

    public void setBankAcOpenDt(String bankAcOpenDt) {
        this.bankAcOpenDt = bankAcOpenDt;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getGeoDateTime() {
        return geoDateTime;
    }

    public void setGeoDateTime(Object geoDateTime) {
        this.geoDateTime = geoDateTime;
    }

    public String getRelationWBorrower() {
        return relationWBorrower;
    }

    public void setRelationWBorrower(String relationWBorrower) {
        this.relationWBorrower = relationWBorrower;
    }

    public String getKycuuid() {
        return kycuuid;
    }

    public void setKycuuid(String kycuuid) {
        this.kycuuid = kycuuid;
    }

    public String geteKycTkn() {
        return eKycTkn;
    }

    public void seteKycTkn(String eKycTkn) {
        this.eKycTkn = eKycTkn;
    }

    public String getmIni() {
        return mIni;
    }

    public void setmIni(String mIni) {
        this.mIni = mIni;
    }

    public String getmFname() {
        return mFname;
    }

    public void setmFname(String mFname) {
        this.mFname = mFname;
    }

    public String getmMname() {
        return mMname;
    }

    public void setmMname(String mMname) {
        this.mMname = mMname;
    }

    public String getmLname() {
        return mLname;
    }

    public void setmLname(String mLname) {
        this.mLname = mLname;
    }

    public String getsIni() {
        return sIni;
    }

    public void setsIni(String sIni) {
        this.sIni = sIni;
    }

    public String getsFname() {
        return sFname;
    }

    public void setsFname(String sFname) {
        this.sFname = sFname;
    }

    public String getsMname() {
        return sMname;
    }

    public void setsMname(String sMname) {
        this.sMname = sMname;
    }

    public String getsLname() {
        return sLname;
    }

    public void setsLname(String sLname) {
        this.sLname = sLname;
    }

    public Integer getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Integer monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Integer getFutureIncome() {
        return futureIncome;
    }

    public void setFutureIncome(Integer futureIncome) {
        this.futureIncome = futureIncome;
    }

    public String getAgriculture() {
        return agriculture;
    }

    public void setAgriculture(String agriculture) {
        this.agriculture = agriculture;
    }

    public Integer getPension() {
        return pension;
    }

    public void setPension(Integer pension) {
        this.pension = pension;
    }

    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    public Integer getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(Integer otherIncome) {
        this.otherIncome = otherIncome;
    }

    public String getEarningMemberType() {
        return earningMemberType;
    }

    public void setEarningMemberType(String earningMemberType) {
        this.earningMemberType = earningMemberType;
    }

    public String getEarningMemberIncome() {
        return earningMemberIncome;
    }

    public void setEarningMemberIncome(String earningMemberIncome) {
        this.earningMemberIncome = earningMemberIncome;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public FiExtra getFiExtra() {
        return fiExtra;
    }

    public void setFiExtra(FiExtra fiExtra) {
        this.fiExtra = fiExtra;
    }

    public List<FiFamLoan> getFiFamLoans() {
        return fiFamLoans;
    }

    public void setFiFamLoans(List<FiFamLoan> fiFamLoans) {
        this.fiFamLoans = fiFamLoans;
    }

    public List<FiGuarantor> getFiGuarantors() {
        return fiGuarantors;
    }

    public void setFiGuarantors(List<FiGuarantor> fiGuarantors) {
        this.fiGuarantors = fiGuarantors;
    }

    public List<FiFamMem> getFiFamMems() {
        return fiFamMems;
    }

    public void setFiFamMems(List<FiFamMem> fiFamMems) {
        this.fiFamMems = fiFamMems;
    }

    public FiFamExpenses getFiFamExpenses() {
        return fiFamExpenses;
    }

    public void setFiFamExpenses(FiFamExpenses fiFamExpenses) {
        this.fiFamExpenses = fiFamExpenses;
    }

    public FiExtraBankBo getFiExtraBankBo() {
        return fiExtraBankBo;
    }

    public void setFiExtraBankBo(FiExtraBankBo fiExtraBankBo) {
        this.fiExtraBankBo = fiExtraBankBo;
    }

    public List<UploadedFiDocs> getUploadedFiDocsList() {
        return uploadedFiDocsList;
    }

    public void setUploadedFiDocsList(List<UploadedFiDocs> uploadedFiDocsList) {
        this.uploadedFiDocsList = uploadedFiDocsList;
    }

    public String getDmlOperation() {
        return dmlOperation;
    }

    public void setDmlOperation(String dmlOperation) {
        this.dmlOperation = dmlOperation;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthUserID() {
        return authUserID;
    }

    public void setAuthUserID(String authUserID) {
        this.authUserID = authUserID;
    }

    public Object getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Object authDate) {
        this.authDate = authDate;
    }

    public Object getModType() {
        return modType;
    }

    public void setModType(Object modType) {
        this.modType = modType;
    }

    public Object getLastModUserID() {
        return lastModUserID;
    }

    public void setLastModUserID(Object lastModUserID) {
        this.lastModUserID = lastModUserID;
    }

    public Object getLastModDate() {
        return lastModDate;
    }

    public void setLastModDate(Object lastModDate) {
        this.lastModDate = lastModDate;
    }

}