package com.paisalo.newinternalsourcingapp.Retrofit;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CreatorListModels.CreatorListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.PANerificationModels.PanVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.DLVerificationModels.DLVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.VoterIdVerificationModels.VoterIdVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ImeiMappingModel;
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

import org.json.JSONObject;

import okhttp3.MultipartBody;
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
    Call<RangeCategoryModel> RangeCategory(@Header ("Authorization") String token,@Header("dbname") String dbName);
 @GET("POSDB/GetMappedFO")
 Call<ManagerListModel> ManagerListApi(@Header("Authorization") String token, @Header("dbname") String dbName,@Query("IMEINO") String imeiNo, @Query("UserID") String userId    );

 @POST("/POSFI/SaveFi")
 Call<KycSubmitModel> SubmitKycApi(@Header("Authorization") String token, @Header("dbname") String dbName,@Body JsonObject object);

    @GET("api/LiveTrack/GetCSOMothlyTargetByUserId")
    public Call<LeaderboardModel> getLeaderboardData(@Header("Authorization") String token,@Header("dbname") String dbname);

    @POST("LiveTrack/CreateLiveTrack")
    public Call<JsonObject> livetrack(@Header("Authorization") String token,@Header("dbname") String dbname,@Body JsonObject object);

    @GET("LiveTrack/GetCSOMothlyTarget")
    public Call<TargetResponseModel> getTarget(@Header("Authorization") String token,@Header("dbname") String dbname,@Query("KO_ID") String KO_ID, @Query("Month") String Month, @Query("Year") String Year);

    @GET("Notification/GetBannerPostingMobile")
        Call<ImageDataModel> getTopImage(@Header("Authorization") String token,@Header("dbname") String dbname,@Query("AppType") String AppType);

    @GET("api/LiveTrack/MonthlyTargetCsoIdCount")
    public Call<TargetCountModel> getTargetCount(@Header("Authorization") String token,@Header("dbname") String dbname,@Query("CsoId") String CsoId);
    @POST("api/LiveTrack/InsertMonthTargetCSO")
    public Call<TargetSetModel> setTarget(@Header("Authorization") String token,@Header("dbname") String dbname,@Body JsonObject object);
    @GET("DDLHelper/GetSBICityMaster")
    Call<CityModelList> getCityList(@Header("Authorization") String token,@Header("dbname") String dbname,@Query("ststecode") String stcode);

    @GET("DDLHelper/GetSBIDistrictMaster")
    Call<DistrictListModel> getDistictList(@Header("Authorization") String token,@Header("dbname") String dbname,@Query("ststecode") String subDistrict);

    @GET("DDLHelper/GetSBISubDistrictMaster")
    Call<SubDistrictModel> getSubDistrictList(@Header("Authorization") String token,@Header("dbname") String dbname,@Query("subDistrict") String subDistrict);

    @GET("DDLHelper/GetSBIVillageMaster")
    Call<VillageListModel> getVillageList(@Header("Authorization") String token,@Header("dbname") String dbname,@Query("stcode") String stcode, @Query("discode") String discode, @Query("subdiscode") String subdiscode);

    @POST("POSFI/SaveFi")
    Call<SaveFiModel> getSaveFi(@Header("Authorization") String token,@Header("dbname") String dbname,@Body JsonObject object);

    @POST("OCR/GetIdentityVerfication")
    Call<PanVerificationModel> getpanVerified(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("OCR/GetIdentityVerfication")
    Call<DLVerificationModel> getDLVerified(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @POST("OCR/GetIdentityVerfication")
    Call<VoterIdVerificationModel> getvoterIDVerified(@Header("Authorization") String token, @Header("dbname") String dbname, @Body JsonObject object);

    @GET("LiveTrack/CheckLoanByAadhar")
    Call<JsonObject> CheckLoanByAadhar(@Header("Authorization") String token, @Header("dbname") String dbname,@Query("Aadharno") String Aadharno);

    @POST("Miscellaneous/CreateFiVerfiedInfo")
    Call<SaveVerifiedInfo> SaveVerifiedInfo(@Header("Authorization") String token, @Header("dbname") String dbname,@Body JsonObject object);

    @POST("IMEIMapping/InsertDevicedata")
    Call<ImeiMappingModel> getImeiMappingReq(@Header("Authorization") String token, @Header("dbname") String dbname,@Body JsonObject object);

    @GET("DDLHelper/GetCreator")
    Call<CreatorListModel> getCreatorList(@Header("Authorization") String token, @Header("dbname") String dbname);
    @Multipart
    @POST("DDLHelper/ProfilePicUpload")
    Call<ProfilePicModel> updateprofilePic(@Header("Authorization") String token,@Header("dbname") String dbname, @Query("fi") String fi, @Query("cr") String cr, @Query("tag") String tag, @Part MultipartBody.Part FileName);


   @Multipart
   @POST("OCR/DocVerifyforSpaceOCR")
   Call<AdharDataResponse> getAdharDataByOCR(@Header("Authorization") String token, @Header("dbname") String dbname,@Query("imgType") String imgData, @Part MultipartBody.Part file);

    @GET("POSFI/getFiToEdit")
    Call<AllDataAFModel> getAllAFData(@Header("Authorization") String token, @Header("dbname") String dbname,@Query("FiCode") String FiCode, @Query("Creator") String Creator);

}