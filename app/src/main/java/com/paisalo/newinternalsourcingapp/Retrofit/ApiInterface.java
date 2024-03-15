package com.paisalo.newinternalsourcingapp.Retrofit;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.LoginModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("Account/GetToken")
    Call<LoginModel> LoginApi(@Header("devid") String devid, @Header("dbname") String dbname, @Header("imeino") String imeino, @Body JsonObject object);
  @GET("Master/GetRangeCategories")
    Call<RangeCategoryModel> RangeCategory(@Header ("Authorization") String token,@Header("dbname") String dbName);
 @GET("POSDB/GetMappedFO")
 Call<ManagerListModel> ManagerListApi(@Header("Authorization") String token, @Header("dbname") String dbName,@Query("IMEINO") String imeiNo, @Query("UserID") String userId);

}
