package com.paisalo.newinternalsourcingapp.Retrofit;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CreatorListModels.CreatorListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HVBorrowerModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HVGetModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HouseVisitSaveModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.PANerificationModels.PanVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.DLVerificationModels.DLVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.VoterIdVerificationModels.VoterIdVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ImeiMappingModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycScanningModels.KycScanningModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.OCRScanModels.AdharDataResponse;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ProfilePicModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.SaveFiModels.SaveFiModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.SaveVerifiedInfo;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.CityModelList;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.DistrictListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycSubmitModels.KycSubmitModel;
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

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("Account/GetToken")
    Call<LoginModel> LoginApi(@Header("devid") String devid, @Header("dbname") String dbname, @Header("imeino") String imeino, @Body JsonObject object);

    @GET("Master/GetRangeCategories")
    Call<RangeCategoryModel> RangeCategory(@Header("Authorization") String token, @Header("dbname") String dbName);

    @GET("POSDB/GetMappedFO")
    Call<ManagerListModel> ManagerListApi(@Header("Authorization") String token, @Header("dbname") String dbName, @Query("IMEINO") String imeiNo, @Query("UserID") String userId);

    @POST("/POSFI/SaveFi")
    Call<KycSubmitModel> SubmitKycApi(@Header("Authorization") String token, @Header("dbname") String dbName, @Body JsonObject object);

    @POST("POSFI/SaveFi")
    Call<SaveFiModel> getSaveFi(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);


    @GET("api/LiveTrack/GetCSOMothlyTargetByUserId")
    public Call<LeaderboardModel> getLeaderboardData(@Header("Authorization") String token, @Header("dbname") String dbname);

    @POST("LiveTrack/CreateLiveTrack")
    public Call<JsonObject> livetrack(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @GET("LiveTrack/GetCSOMothlyTarget")
    public Call<TargetResponseModel> getTarget(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("KO_ID") String KO_ID, @Query("Month") String Month, @Query("Year") String Year);

    @GET("Notification/GetBannerPostingMobile")
    Call<ImageDataModel> getTopImage(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("AppType") String AppType);

    @GET("api/LiveTrack/MonthlyTargetCsoIdCount")
    public Call<TargetCountModel> getTargetCount(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("CsoId") String CsoId);

    @POST("api/LiveTrack/InsertMonthTargetCSO")
    public Call<TargetSetModel> setTarget(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

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
    Call<ProfilePicModel> updateprofilePic(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("fi") String fi, @Query("cr") String cr, @Query("tag") String tag, @Part MultipartBody.Part FileName);


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

    @POST("Miscellaneous/CreateHomeVisit")
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
    Call<BorrowerListModel> PendingCollection(@Header("Authorization") String token, @Header("dbname") String dbname, @Query("IMEINO") String IMEINO, @Query("FOCode") String FOCode, @Query("AreaCd") String AreaCd, @Query("Creator") String Creator);

    @GET("DocSignIn/GetXMLDoc")
    Call<ResponseBody> getXMLforESign();

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
}
