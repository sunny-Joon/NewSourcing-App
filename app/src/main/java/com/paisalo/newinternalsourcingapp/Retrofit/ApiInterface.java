package com.paisalo.newinternalsourcingapp.Retrofit;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Entities.CkycNoMODEL;
import com.paisalo.newinternalsourcingapp.Modelclasses.EmiCollectionModels.CollectionReportModel;
import com.paisalo.newinternalsourcingapp.Modelclasses.PosInstRcv;
import com.paisalo.newinternalsourcingapp.Modelclasses.PosInstRcvNew;
import com.paisalo.newinternalsourcingapp.Modelclasses.QRCollStatus;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.AccountDetails_Model;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BreResponseModels.BREResponse;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.Collection.CustomerListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CreatorListModels.CreatorListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.DownloadEsignXml;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HVBorrowerModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HVGetModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HouseVisitSaveModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.AccountVerificationModels.AccountVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.PANerificationModels.PanVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.DLVerificationModels.DLVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.VoterIdVerificationModels.VoterIdVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ImeiMappingModel;
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


    @POST("Account/GetToken")
    Call<LoginModel> LoginApi(@retrofit2.http.Header("devid") String devid, @retrofit2.http.Header("dbname") String dbname, @retrofit2.http.Header("imeino") String imeino, @Body JsonObject object);

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
    Call<ImageDataModel> getTopImage(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("AppType") String AppType);
//----
    @GET("api/LiveTrack/MonthlyTargetCsoIdCount")
     Call<TargetCountModel> getTargetCount(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("CsoId") String CsoId);

    @POST("api/LiveTrack/InsertMonthTargetCSO")
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
    Call<BorrowerListModel> PendingFEsign(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("IMEINO") String IMEINO, @Query("FOCode") String FOCode, @Query("CityCode") String CityCode, @Query("Creator") String Creator);

    @GET("POSDB/GetDataPendingForESign")
    Call<BorrowerListModel> PendingSEsign(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("IMEINO") String IMEINO, @Query("FOCode") String FOCode, @Query("AreaCd") String AreaCd, @Query("Creator") String Creator);

    @GET("InstCollection/getDueInstallments")
    Call<BorrowerListModel> PendingCollection(@Header("Authorization") String token, @Header("dbname") String dbname, @retrofit2.http.Header("imeino") String imeino, @retrofit2.http.Header("userid") String userid, @Query("gdate") String gdate, @Query("CityCode") String CityCode);

    @POST("api/DocESignLoanApplication/signdoc")
    Call<DownloadEsignXml> getXMLforESign(@Header("Authorization") String token,
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
    Call<JsonObject> saveDocKyc(@Header("Authorization") String token, @Header("dbname") String dbname,
                                @Part MultipartBody.Part Document,
                                @Part("ficode") RequestBody ficode,
                                @Part("DbName") RequestBody DbName,
                                @Part("Creator") RequestBody Creator,
                                @Part("FiTag") RequestBody FiTag,
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
                                             @retrofit2.http.Header("Authorization") String token,
                                             @retrofit2.http.Header("dbname") String dbName,
                                             @retrofit2.http.Header("userid") String userid);

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

    @GET("InstCollection/SaveReceipt")
    Call<Void> saveDeposit(@retrofit2.http.Header("Authorization") String token,
                                @retrofit2.http.Header("dbname") String dbName,
                                @Body JsonObject jsonObject);

    @POST("DocESignLoanApplication/DownloadUnSignedDoc")
    Call<ResponseBody> DownloadDocFirstEsign(@Header("Authorization") String token,
                                       @Header("dbname") String dbName,
                                       @Body JsonObject jsonObject);

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
    @POST("{subUrl}") // CallBack APi
    Call<ResponseBody> postEntityEsign(@Field("msg") String msg, @Field("obj") String obj,@Path("subUrl")String subUrl);


    @POST("api/docsESignPvn/AcceptESign") // Replace with your actual endpoint
    Call<ResponseBody> postEntityESignSubmit(@Body RequestBody body);

    @POST("LiveTrack/UpdateFiStatus")
    Call<JsonObject> restrictBorrower(@Query("ficode") String ficode,@Query("creator") String creator,@Query("Approved") String Approved);

    @POST("LiveTrack/SourcingStatus")
    Call<JsonObject> updateStatus(@Query("ficode") String Ficode, @Query("creator") String Creator);

    @POST("Crif/InitilizeCrif")
    Call<JsonObject> generateCrifForVehicle(@Body JsonObject jsonObject);
    @POST("PDL.Mobile.API/api/Crif/GetBREDetails")
    Call<BREResponse> getBREStatus(@Query("creator") String creator, @Query("ficode") String ficode);

    @POST("BreEligibility/SaveBreEligibility")
    Call<Void> saveBreEligibility(@Body RequestBody body);

}
