package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.DataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.FoImeiModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.FoModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.LoginModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.TokenDetailsModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryModel;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DaoClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.loginDataClass;
import com.paisalo.newinternalsourcingapp.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    DaoClass daoClass ;
    ActivityLoginBinding binding;
    String username, password,token;
    DatabaseClass database;
    String devid = "2239713985787245", dbname = "yfMerfC6mRvfr0AOoHmOJ8Et9Q9MPwNEKzFdLsfEs1A=", imei_no = "868368051227918";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = DatabaseClass.getInstance(LoginActivity.this);
        daoClass=database.dao();
        binding.BtnSaveKYCData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = binding.etLoginUsername.getText().toString();
                password = binding.etLoginPassword.getText().toString();

                if (isValidUsername(username) && isValidPassword(password)) {
                    LoginAPi(devid, dbname, imei_no);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void LoginAPi(String devid, String dbname, String imeino) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginModel> call = apiInterface.LoginApi(devid, dbname, imeino, getJsonOfUserIdPassword());
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                if (response.isSuccessful()) {

                    LoginModel responseData = response.body();
                    if (responseData.getMessage().contains("Successfully")) {

                        DataModel dataModel = responseData.getData();
                        List<FoModel> foModel = dataModel.getFolist();
                        FoImeiModel foImeiModel = dataModel.getFoImei();
                        TokenDetailsModel tokenDetailsModel = dataModel.getTokenDetails();
                        token = tokenDetailsModel.getToken().toString();
                        imei_no = foImeiModel.getImeino().toString();

                    //    RangeCategoriesApi(tokenDetailsModel.getToken().toString());
                        ManagerListApi(username,imeino,token);

                       /* if (foModel != null && foImeiModel != null && tokenDetailsModel != null) {
                            saveDataToDatabase(foModel,foImeiModel,tokenDetailsModel);
                      }*/

                    } else {
                        Toast.makeText(LoginActivity.this, "Id Password Not Matched", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    LoginAPi(devid, dbname, imeino);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void ManagerListApi(String username, String imeino, String token) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ManagerListModel> call = apiInterface.ManagerListApi(token,dbname,imeino,username);
        call.enqueue(new Callback<ManagerListModel>() {
            @Override
            public void onResponse(Call<ManagerListModel> call, Response<ManagerListModel> response) {
                if(response.isSuccessful()){
                    Log.d("TAG", "onResponsemanager: "+ response.body());
                    ManagerListModel managerListModel = response.body();
                    List<ManagerListDataModel> managerListDataModel=  managerListModel.getData();
                    Log.d("TAG", "onResponsemanager: "+managerListDataModel);
                }else{
                    Log.d("TAG", "onResponsemanager: "+ response.body());

                }
            }

            @Override
            public void onFailure(Call<ManagerListModel> call, Throwable t) {
                Log.d("TAG", "onResponsemanager: "+ "Manager List Failure");

            }
        });
    }

    private void RangeCategoriesApi(String token) {
        Log.d("TAG", "RangeCartegory: "+"APi Started");
        token = "Bearer "+token;

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RangeCategoryModel> call = apiInterface.RangeCategory(token,dbname);
        Log.d("TAG", "RangeCartegory: "+token);
        Log.d("TAG", "RangeCartegory: "+dbname);

        call.enqueue(new Callback<RangeCategoryModel>() {
            @Override
            public void onResponse(Call<RangeCategoryModel> call, Response<RangeCategoryModel> response) {
                //       Log.d("TAG", "RangeCartegory: "+response.body());
                if (response.isSuccessful()) {
                    //         Log.d("TAG", "RangeCategory: " + response.body().getMessage().toString());
                    RangeCategoryModel rangeCategoryModel = response.body();
                    assert rangeCategoryModel != null;
                    List<RangeCategoryDataModel> rangeCategoryDataModelList = rangeCategoryModel.getData();
                    //     Log.d("TAG", "RangeCategory: " + rangeCategoryDataModelList.toString());

                    List<RangeCategoryDataClass> rangeCategoryDataClassList = new ArrayList<>();
                    for (RangeCategoryDataModel dataModel : rangeCategoryDataModelList) {
                        RangeCategoryDataClass dataClass = new RangeCategoryDataClass(
                                dataModel.getCatKey(),
                                dataModel.getGroupDescriptionEn(),
                                dataModel.getGroupDescriptionHi(),
                                dataModel.getDescriptionEn(),
                                dataModel.getDescriptionHi(),
                                dataModel.getSortOrder(),
                                dataModel.getCode()
                        );
                        rangeCategoryDataClassList.add(dataClass);
                    }

                    DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            daoClass.insertRCData( rangeCategoryDataClassList);
                        }
                    });
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }else{
                    Log.d("TAG", "RangeCartegory: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<RangeCategoryModel> call, Throwable t) {
                Log.d("TAG", "RangeCartegory: "+"failure");
            }
        });
    }

    private void saveDataToDatabase(List<FoModel> foModel, FoImeiModel foImeiModel, TokenDetailsModel tokenDetailsModel) {
        DatabaseClass database = DatabaseClass.getInstance(LoginActivity.this);
        loginDataClass entity = new loginDataClass();

    /*    for (FoModel fomodel : foModel) {

            entity.setImeino(fomodel.getImeino().toString());
            entity.setFoCode(fomodel.getFoCode());
            entity.setFoName(fomodel.getFoName());
            entity.setCreator(fomodel.getCreator());
            entity.setIsActive(fomodel.getIsActive());
            entity.setDataBase(fomodel.getDataBase());
            entity.setTag(fomodel.getTag());
            entity.setAreaCd(fomodel.getAreaCd());
            entity.setAreaName(fomodel.getAreaName());
            entity.setCreatorDesc(fomodel.getCreatorDesc());
            entity.setFiExecCode(fomodel.getFiExecCode());
            entity.setFiExecName(fomodel.getFiExecName());

//            daoClass.insert(entity);
        }*/
        entity.setFoImeiimeino(foImeiModel.getImeino().toString());
        entity.setActualYN(foImeiModel.getActualYN().toString());
        entity.setFoImeiIsActive(foImeiModel.getIsActive().toString());
        entity.setNewAppVersion(foImeiModel.getNewAppVerison().toString());
        entity.setAppDownPath(foImeiModel.getAppDownPath().toString());
        entity.setRequestUrl(foImeiModel.getRequestUrl().toString());
        entity.setSimno(foImeiModel.getSimno().toString());
        entity.setTokenId(tokenDetailsModel.getId().toString());
        entity.setToken(tokenDetailsModel.getToken().toString());
        entity.setUserName(tokenDetailsModel.getUserName().toString());
        entity.setDeviceSrNo(tokenDetailsModel.getDeviceSrNo().toString());
        entity.setPassword(tokenDetailsModel.getPassword().toString());
        entity.setValidity(tokenDetailsModel.getValidaty().toString());
        entity.setRefreshToken(tokenDetailsModel.getRefreshToken().toString());
        entity.setRole(tokenDetailsModel.getRole().toString());
        entity.setGuidId(tokenDetailsModel.getGuidId().toString());
        entity.setExpiredTime(tokenDetailsModel.getExpiredTime().toString());

        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                daoClass.insertLoginData(entity);
            }
        });


    }

    private JsonObject getJsonOfUserIdPassword() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        return jsonObject;
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 1;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 5;
    }
}
