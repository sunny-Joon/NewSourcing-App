package com.paisalo.newinternalsourcingapp.Retrofit;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Entities.CkycNoMODEL;
import com.paisalo.newinternalsourcingapp.Modelclasses.AdharExitsModel;
import com.paisalo.newinternalsourcingapp.Modelclasses.AppAttendance;
import com.paisalo.newinternalsourcingapp.Modelclasses.EmiCollectionModels.CollectionReportModel;
import com.paisalo.newinternalsourcingapp.Modelclasses.PosInstRcv;
import com.paisalo.newinternalsourcingapp.Modelclasses.PosInstRcvNew;
import com.paisalo.newinternalsourcingapp.Modelclasses.QRCollStatus;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.AccountDetails_Model;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BranchStatusResponce;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BreResponseModels.BREResponse;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.Collection.CustomerListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CollectionTokenModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CreatorListModels.CreatorListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.DownloadEsignXml;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.EsignListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HVBorrowerModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HVGetModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HouseVisitSaveModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.AccountVerificationModels.AccountVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.PANerificationModels.PanVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.DLVerificationModels.DLVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.VoterIdVerificationModels.VoterIdVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ImeiMappingModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycDocsFlag.KycDocsFlag;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycScanningModels.KycScanningModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LiveToken;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.OCRScanModels.AdharDataResponse;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ProfilePicModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.QrUrlData;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ReferralCodeModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.SaveFiModels.SaveFiModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.SaveVerifiedInfo;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.CityModelList;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.DistrictListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LeaderBoardModels.LeaderboardModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.LoginModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.SubDistrictModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetCountModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetIndexModels.TargetResponseModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetSetModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TopAdImageModels.ImageDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.VillageListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.UpdateFiModels.KycUpdateModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.VersionCheck;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ViewStatusmodel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.Visitreportmodel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //page
    //link

    @GET("UserMobile/BranchFunction")
    Call<BranchStatusResponce> getBranchStatus(@Query("BranchCode") String BranchCode, @Query("Creator") String Creator);

    @GET("LiveTrack/GetAppLink")
    Call<VersionCheck> VersionCheck(@Header("dbname") String dbName, @Query("version") String version, @Query("AppName") String AppName, @Query("action") int action);
    //response


    @POST("Account/GetToken")
    Call<LoginModel> LoginApi(@Header("devid") String devid, @Header("dbname") String dbname,@Body JsonObject object);

    @GET("Master/GetRangeCategories")
    Call<RangeCategoryModel> RangeCategory(@Header("Authorization") String token, @Header("dbname") String dbName);

    @GET("POSDB/GetMappedFO")
    Call<ManagerListModel> ManagerListApi(@Header("Authorization") String token, @Header("dbname") String dbName, @Query("IMEINO") String imeiNo, @Query("UserID") String userId);


    @POST("POSFI/SaveFi")
    Call<SaveFiModel> getSaveFi(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

//-----------------------------------------------------------------------------------------------------------------------------------------
    @GET("api/LiveTrack/GetCSOMothlyTargetByUserId")
    public Call<LeaderboardModel> getLeaderboardData(@Header("Authorization") String token, @Header("dbname") String dbname);

    @POST("LiveTrack/CreateLiveTrack")
    public Call<JsonObject> livetrack(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @GET("LiveTrack/GetCSOMothlyTarget")
    public Call<TargetResponseModel> getTarget(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("KO_ID") String KO_ID, @Query("Month") String Month, @Query("Year") String Year);

    @GET("Notification/GetBannerPostingMobile")

    Call<ImageDataModel> getTopImage(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("AppType") String AppType,@Query("MessageType")String MessageType);


    @GET("Notification/GetBannerViewStatus")
    Call<Integer> getBannerExit(@Query("Viewer_Id") String Viewer_Id);

    @POST("Notification/InsertBannerViewStatus")
    Call<ViewStatusmodel> getBannerView(@Query("Viewer_Id") String Viewer_Id, @Query("Message_Id") String Message_Id);

//    @GET("Notification/GetBannerPostingMobile")
//    Call<ImageDataModel> getTopImage(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("AppType") String AppType,@Query("MessageType") String MessageType);

    //----

    @GET("LiveTrack/MonthlyTargetCsoIdCount")
     Call<TargetCountModel> getTargetCount(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("CsoId") String CsoId);

    @POST("LiveTrack/InsertMonthTargetCSO")
     Call<TargetSetModel> setTarget(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);
//----
    @GET("DDLHelper/GetSBICityMaster")
    Call<CityModelList> getCityList(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("ststecode") String stcode);

    @GET("DDLHelper/GetSBIDistrictMaster")
    Call<DistrictListModel> getDistictList(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("ststecode") String subDistrict);

    @GET("DDLHelper/GetSBISubDistrictMaster")
    Call<SubDistrictModel> getSubDistrictList(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("subDistrict") String subDistrict);

    @GET("DDLHelper/GetSBIVillageMaster")
    Call<VillageListModel> getVillageList(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("stcode") String stcode, @Query("discode") String discode, @Query("subdiscode") String subdiscode);


    @POST("OCR/GetIdentityVerfication")
    Call<PanVerificationModel> getpanVerified(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("OCR/GetIdentityVerfication")
    Call<DLVerificationModel> getDLVerified(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("OCR/GetIdentityVerfication")
    Call<VoterIdVerificationModel> getvoterIDVerified(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("OCR/GetIdentityVerfication")
    Call<AccountVerificationModel> getAccVerified(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @GET("LiveTrack/CheckLoanByAadhar")
    Call<JsonObject> CheckLoanByAadhar(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("Aadharno") String Aadharno);

    @POST("Miscellaneous/CreateFiVerfiedInfo")
    Call<SaveVerifiedInfo> SaveVerifiedInfo(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("IMEIMapping/InsertDevicedata")
    Call<ImeiMappingModel> getImeiMappingReq(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @GET("DDLHelper/GetCreator")
    Call<CreatorListModel> getCreatorList(@Header("Authorization") String token, @Header("dbname") String dbname);

    @Multipart
    @POST("DDLHelper/ProfilePicUpload")
    Call<ProfilePicModel> updateprofilePic(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("fi") String fi, @Query("cr") String cr, @Query("tag") String tag, @Query("GrNo") String GrNo, @Part MultipartBody.Part file);



    @Multipart
    @POST("OCR/DocVerifyforSpaceOCR")
    Call<AdharDataResponse> getAdharDataByOCR(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("imgType") String imgData, @Part MultipartBody.Part file);
// done
    @GET("POSFI/getFiToEdit")
    Call<AllDataAFModel> getAllAFData(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("FiCode") String FiCode, @Query("Creator") String Creator);

    @POST("POSFI/UpdateFIAddress")
    Call<KycUpdateModel> updateAddress(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("POSFI/UpdateFIPersonalDetails")
    Call<KycUpdateModel> updatePersonalInfo(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("POSFI/UpdateFIFamLoans")
    Call<KycUpdateModel> updateFamLoans(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("POSFI/UpdateFIFamMemIncome")
    Call<KycUpdateModel> updateFamMemIncome(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("POSFI/UpdateFIFinance")
    Call<KycUpdateModel> updateFinance(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("POSFI/UpdateFIGaurantors")
    Call<KycUpdateModel> updateGaurantors(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("Miscellaneous/CreateUpdateHomeVisit")
    Call<HouseVisitSaveModel> SaveHouseVisit(@Header("Authorization") String token, @Header("dbname") String dbname, @Body RequestBody file);

    @GET("Miscellaneous/GetHomeVisitBorrowerData")
    Call<HVBorrowerModel> HouseVisitBorrowerData(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("ficode") String fi, @Query("creator") String cr);

    @GET("Miscellaneous/GetHomeVisitData")
    Call<HVGetModel> GetHouseVisitData(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("ficode") String fi, @Query("creator") String cr);

    @GET("POSFI/getFiListEditing")
    Call<BorrowerListModel> PendingApplicationForms(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("IMEINO") String IMEINO, @Query("FOCode") String FOCode, @Query("AreaCd") String AreaCd, @Query("Creator") String Creator);

    @GET("POSDB/GetDataForESignCheck")
    Call<EsignListModel> PendingFEsign(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("IMEINO") String IMEINO, @Query("FOCode") String FOCode, @Query("CityCode") String CityCode, @Query("Creator") String Creator);

    @GET("POSDB/GetDataPendingForESign")
    Call<EsignListModel> PendingSEsign(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("IMEINO") String IMEINO, @Query("FOCode") String FOCode, @Query("AreaCd") String AreaCd, @Query("Creator") String Creator);

    @GET("InstCollection/getDueInstallments")
    Call<BorrowerListModel> PendingCollection(@Header("Authorization") String token, @Header("dbname") String dbname, @retrofit2.http.Header("imeino") String imeino, @retrofit2.http.Header("userid") String userid, @Query("gdate") String gdate, @Query("CityCode") String CityCode);

    @POST("api/DocESignLoanApplication/signdoc")
    Call<DownloadEsignXml>  getXMLforESign(@Header("Authorization") String token,
                                          @Header("Content-Encoding") String Encoding,
                                          @Header("devid") String devid,
                                          @Header("dbname") String dbname,
                                          @Header("imeino") String imeino,
                                          @Header("access") String access,
                                          @Header("content-type") String content,
                                          @Header("Accept") String Accept,
                                          @Body JsonObject jsonObject);

    @POST("api/docsESignPvn/signdoc")
    Call<DownloadEsignXml>  getXMLforSecondESign(@Header("Authorization") String token,
                                          @Header("Content-Encoding") String Encoding,
                                          @Header("devid") String devid,
                                          @Header("dbname") String dbname,
                                          @Header("imeino") String imeino,
                                          @Header("access") String access,
                                          @Header("content-type") String content,
                                          @Header("Accept") String Accept,
                                          @Body JsonObject jsonObject);

    @Multipart
    @POST("UploadDocs/SaveFiDocsJson")
    Call<JsonObject> saveDocKyc(@Header("Authorization") String token,
                                @Header("dbname") String dbname,
                                @Part MultipartBody.Part Document,
                                @Part("DbName") RequestBody Dbname,
                                @Part("Creator") RequestBody creator,
                                @Part("FiTag") RequestBody FiTag,
                                @Part("FiCode") RequestBody FiCode,
                                @Part("CheckListId") RequestBody CheckListId,
                                @Part("Remarks") RequestBody Remarks,
                                @Part("UserId") RequestBody UserId,
                                @Part("GrNo") RequestBody GrNo,
                                @Part("fileName") RequestBody fileName,
                                @Part("imageTag") RequestBody imageTag);

    @Multipart
    @POST("OCR/DocVerifyforOSVSpaceOCR")
    Call<KycScanningModel> scanDoc(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("imgType") String imgType, @Part MultipartBody.Part file);

    @GET("Miscellaneous/HomeVisitExistance")
    Call<TargetSetModel> checkHVForm(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("FiCode") String FiCode, @Query("Creator") String Creator);

    @POST("UserMobile/SearchCkycNo")
    Call<CkycNoMODEL> getCkycNo(@Header("Authorization") String token, @Header("dbname") String dbName, @Query("FiCode") String FiCode, @Query("Creator") String Creator);

    @GET("{fullUrl}")
    Call<JsonObject> razorpayIfsc(@Path(value = "fullUrl", encoded = true) String fullUrl);


    //------------------------------------------------------------------------------------------------------------------------
    @GET("LiveTrack/CollectionStatus")
    Call<CollectionReportModel> getCollectionReprt(@Header("Authorization") String token, @Header("dbname") String dbName, @Query("Smcode") String Smcode);

    @GET("InstCollection/GetQrPaymentsBySmcode")
    Call<AccountDetails_Model> getQrPaymentsBySmcode(
            @retrofit2.http.Header("Authorization") String token,
            @retrofit2.http.Header("dbname") String dbName,
            @Query("SmCode") String smCode,
            @Query("userid") String userId,
            @Query("type") String type
    );
    @Multipart
    @POST("LiveTrack/InsertVisitReports")
    Call<Visitreportmodel> getvisit(
            @retrofit2.http.Header("Authorization") String token,
            @retrofit2.http.Header("dbname") String dbName,
            @Part("VisitType") RequestBody VisitType,
            @Part("SmCode") RequestBody SmCode,
            @Part("Amount") RequestBody Amount,
            @Part("Lat") RequestBody Lat,
            @Part("Long") RequestBody Long,
            @Part("userId") RequestBody userId,
            @Part MultipartBody.Part file

    );
    @Multipart
    @POST("InstCollection/QrPaymentSettlement")
    Call<JsonObject> saveReciptOnpayment(
            @retrofit2.http.Header("Authorization") String token,
            @retrofit2.http.Header("dbname") String dbName,
            @Part MultipartBody.Part FileName,
            @Part("SmCode") RequestBody SmCode);

    @GET("LiveTrack/GetCSOReferralCode")
    Call<ReferralCodeModel> getReferralCode(@Header("Authorization") String token, @Header("dbname") String dbName, @Query("username") String username);

    @POST("IMEIMapping/RcPromiseToPay")//Working
    Call<JsonObject> insertRcPromiseToPay(@Header("Authorization") String token, @Header("dbname") String dbName, @Body JsonObject jsonObject);

    @GET("InstCollection/CheckQrCode")//working
    Call<QrUrlData> getCheckQrCode(@Header("Authorization") String token, @Header("dbname") String dbName, @Query("smcode") String SmCode);

    @POST("IMEIMapping/InsertRcDistribution")
    Call<JsonObject> insertRcDistribution( @retrofit2.http.Header("Authorization") String token,
                                           @retrofit2.http.Header("dbname") String dbName,
                                           @Body JsonObject jsonObject);
    @POST("InstCollection/SaveReceipt")//working
    Call<JsonObject> insertRcDistributionNew(@Body PosInstRcvNew jsonObject,
                                             @Header("dbname") String dbname,
                                             @Header("userid") String userid);
    @POST("POSDATA/instcollection/savereceipt")
    Call<Void> saveDeposit(@Header("Authorization") String token,
                           @Header("devid") String devid,
                           @Header("dbname") String dbname,
                           @Header("imeino") String imeino,
                           @Header("access") String access,
                           @Header("content-type") String content,
                           @Header("Accept") String Accept,
                           @Body PosInstRcv instRcv);

    @POST("InstCollection/UpdateQrRcCollection")//working
    Call<JsonObject> insertQRPayment(@Body QRCollStatus jsonObject,
                                     @retrofit2.http.Header("Authorization") String token,
                                     @retrofit2.http.Header("dbname") String dbName,
                                     @retrofit2.http.Header("userid") String userid);

    @GET("InstCollection/getDueInstallments")//working
    Call<CustomerListModel> dueInstallments(@retrofit2.http.Header("Authorization") String token,
                                                  @retrofit2.http.Header("imeino") String Imei,
                                                  @retrofit2.http.Header("dbname") String dbName,
                                                  @retrofit2.http.Header("userid") String userid,
                                                  @Query("gdate") String gdate,
                                                  @Query("CityCode") String CityCode);

    @GET("InstCollection/getFMSettlementData")
    Call<List<PosInstRcv>> getFMSettlementData(@retrofit2.http.Header("Authorization") String token,
                                         @retrofit2.http.Header("Imei") String Imei,
                                         @retrofit2.http.Header("dbname") String dbName,
                                         @Query("FoCode") String FoCode,
                                         @Query("Creator") String Creator);

    @GET("InstCollection/getFMSettlementData")
    Call<Void> updateUUID(@retrofit2.http.Header("Authorization") String token,
                                @retrofit2.http.Header("dbname") String dbName,
                                @Body JsonObject jsonObject);



    @GET("POSFI/getFiUploadedDocs")
    Call<KycDocsFlag> DocsFlag(@Header("Authorization") String token,
                               @Header("dbname") String dbName,
                               @Query("ficode") String ficode,
                               @Query("creator") String creator);


    @POST("DocESignLoanApplication/DownloadUnSignedDoc")
    Call<ResponseBody> DownloadDocFirstEsign(@Header("Authorization") String token,
                                             @Body JsonObject jsonObject,
                                             @Header("Content-Encoding") String ContentEncoding,
                                             @Header("devid") String devid,
                                             @Header("dbname") String dbName,
                                             @Header("IMEINO") String IMEINO);
    @POST("DocsESignPvn/downloadunsigneddoc")
    Call<ResponseBody> DownloadDocSecondEsign(@Header("Authorization") String token,
                                       @Body JsonObject jsonObject,
                                       @Header("Content-Encoding") String ContentEncoding,
                                       @Header("devid") String devid,
                                       @Header("dbname") String dbName,
                                       @Header("IMEINO") String IMEINO);


    @FormUrlEncoded
    @POST("token")
    Call<LiveToken> liveToken(@Header("Authorization") String token,
                              @Header("devid") String devid,
                              @Header("dbname") String dbname,
                              @Header("imeino") String imeino,
                              @Header("access") String access,
                              @Header("content-type") String content,
                              @Header("Accept") String Accept,
                              @Field("grant_type") String grant_type,
                              @Field("username") String username,
                              @Field("password") String password);
    @FormUrlEncoded
    @POST("token")
    Call<CollectionTokenModel> LiveTokenCollection(@Header("devid") String devid,
                                                   @Header("dbname") String dbname,
                                                   @Header("imeino") String imeino,
                                                   @Header("access") String access,
                                                   @Header("content-type") String content,
                                                   @Header("Accept") String Accept,
                                                   @Header("srcappver") String srcappver,
                                                   @Field("grant_type") String grant_type,
                                                   @Field("username") String username,
                                                   @Field("password") String password);


//
    @FormUrlEncoded
    @POST("{subUrl}") // CallBack APi
    Call<ResponseBody> postEntityEsign(@Field("msg") String msg, @Field("obj") String obj,@Path("subUrl")String subUrl);
//

    @POST("api/docsESignPvn/AcceptESign") // Replace with your actual endpoint
    Call<ResponseBody> postEntityESignSubmit(@Header("Authorization") String token,
                                             @Header("Content-Encoding") String ContentEncoding,
                                             @Header("devid") String devid,
                                             @Header("dbname") String dbName,
                                             @Header("IMEINO") String IMEINO,
                                            @Body JsonObject body);

    @POST("LiveTrack/UpdateFiStatus")
    Call<JsonObject> restrictBorrower(@Query("ficode") String ficode,@Query("creator") String creator,@Query("Approved") String Approved);

    @POST("LiveTrack/SourcingStatus")
    Call<JsonObject> updateStatus(@Query("ficode") String Ficode, @Query("creator") String Creator);

    @POST("Crif/InitilizeCrif")
    Call<JsonObject> generateCrifForVehicle(@Body JsonObject jsonObject);

    @POST("Crif/GetBREDetails")
    Call<BREResponse> getBREStatus(@Query("creator") String creator, @Query("ficode") String ficode);

    @POST("BreEligibility/SaveBreEligibility")
    Call<Void> saveBreEligibility(@Body RequestBody body);


    @POST("UserMobile/SearchCkycNoByAadhar")
    Call<CkycNoMODEL> checkCkycData(@Query("AadharId") String AadharId, @Query("Panno") String Panno, @Query("VoterId") String VoterId, @Query("DOB") String DOB, @Query("Gender") String Gender, @Query("Name") String Name);


    @POST("UserMobile/UpdateSelectFi")
    Call<JsonObject> updateAdharWithCodeCreator(@Query("Aadharid") String Aadharid,@Query("FiCode") String FiCode, @Query("Creator") String Creator);

    @POST("LiveTrack/InsertMobileAppAttendance")
    Call<JsonObject> updateAttendance(@Body JsonObject object);

    @GET("LiveTrack/GetMobileAppAttendance")
    Call<AppAttendance> getAttendance(@Query("UserName") String UserName);

    @POST("PDL.SMS.API/api/Sms/SendSms?MessageType=otp")
    Call<JsonObject> getOtp(@Body JsonObject jsonObject);

    @GET("PDL.SMS.API/api/Sms/SendOtp")
    Call<JsonObject> verifyOTP(@Query("MobileNo") String MobileNo,@Query("Otp") String Otp);

    @GET("UserMobile/ValidateFiExists")
    Call<AdharExitsModel> getadharexit(@Query("aadharid") String aadharid);

    @POST("LiveTrack/FiForUdhan")
    Call<JsonObject> saveSchemeForVH(@Body JsonObject jsonObject);
}
