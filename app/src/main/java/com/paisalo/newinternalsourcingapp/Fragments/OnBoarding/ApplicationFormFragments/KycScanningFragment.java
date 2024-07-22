package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static android.app.Activity.RESULT_OK;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.Activities.CameraActivity;
import com.paisalo.newinternalsourcingapp.Adapters.KycRecyclerViewAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.OnItemClickListener;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelclassesRoom.KYCScanningModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CreatorListModels.CreatorListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiGuarantor;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycDocsFlag.KycDocsFlag;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycScanningModels.KycScanningDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycScanningModels.KycScanningModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KycScanningFragment extends Fragment implements OnItemClickListener {

    Button submitBtn;
    String fiCode, creator, name, grName="",rDocID;//oaadharNo,opanNo,ovoterIdNo,olicenseNo;
    int gurNo, position;

    File ImageFile;
    List<KYCScanningModel> kycScanningList;
    KYCScanningModel item;
    KycDocsFlag kycDocsFlag;
    private KycRecyclerViewAdapter kycRecyclerViewAdapter;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    AllDataAFDataModel allDataAFDataModel;

    public KycScanningFragment(AllDataAFDataModel allDataAFDataModel, String fiCode, String creator) {
        this.allDataAFDataModel = allDataAFDataModel;
        this.fiCode = fiCode;
        this.creator = creator;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kyc_scanning, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.kycScanningRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        if (allDataAFDataModel != null) {
            creator = (allDataAFDataModel.getCreator() != null) ? allDataAFDataModel.getCreator() : "N/A";
            fiCode = (allDataAFDataModel.getCode() != null) ? String.valueOf(allDataAFDataModel.getCode()) : "N/A";

            name = (allDataAFDataModel.getFname() != null) ? allDataAFDataModel.getFname() : "N/A";

            if (!allDataAFDataModel.getFiGuarantors().isEmpty()) {
                grName = (allDataAFDataModel.getFiGuarantors().get(0).getName() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getName() : "N/A";

            }

           /* oaadharNo = (allDataAFDataModel.getAadharID() != null) ? allDataAFDataModel.getAadharID() : "N/A";
            olicenseNo = (allDataAFDataModel.getDrivingLic() != null) ? allDataAFDataModel.getDrivingLic() : "N/A";
            ovoterIdNo = (allDataAFDataModel.getVoterID() != null) ? allDataAFDataModel.getVoterID() : "N/A";
            opanNo = (allDataAFDataModel.getPanNO() != null) ? allDataAFDataModel.getPanNO() : "N/A";
            gurNo = (allDataAFDataModel.getFiGuarantors() != null) ? allDataAFDataModel.getFiGuarantors().size() : 0;*/

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<KycDocsFlag> call = apiInterface.DocsFlag(GlobalClass.Token, GlobalClass.dbname, fiCode, creator);

            call.enqueue(new Callback<KycDocsFlag>() {
                @Override
                public void onResponse(Call<KycDocsFlag> call, Response<KycDocsFlag> response) {
                    if (response.isSuccessful()) {
                        kycDocsFlag = response.body();
                        Log.d("TAG", "onResponseodc: " + kycDocsFlag);
                        Log.d("TAG", "onResponseodc: " + kycDocsFlag.getData());
                        Log.d("TAG", "onResponseodc: " + kycDocsFlag.getData().getGrDocs());
                        Log.d("TAG", "onResponseodc: " + kycDocsFlag.getData().getGrDocs().get(0));
                        Log.d("TAG", "onResponseodc: " + kycDocsFlag.getData().getGrDocs().get(0).getDrivingExists());

                        kycScanningList = new ArrayList<>();
                        kycScanningList.add(new KYCScanningModel(name, "Borrower", "Aadhar", "Front", null, allDataAFDataModel.getAadharID(), "1","0",false));
                        Log.d("TAG", "DatainObjectB:" + "Adhaar front");
                        kycScanningList.add(new KYCScanningModel(name, "Borrower", "Aadhar", "Back", null, allDataAFDataModel.getAadharID(), "27","0",false));
                        Log.d("TAG", "DatainObjectB:" + "Adhaar back");

                        kycScanningList.add(new KYCScanningModel(name, "Borrower", "PassBook", "FirstPage", null, "", "2","0",false));
                        Log.d("TAG", "DatainObjectB:" + "passbook");

                        if (kycDocsFlag.getData().getVoterExists()) {
                            kycScanningList.add(new KYCScanningModel(name, "Borrower", "VoterId", "Front", null, allDataAFDataModel.getVoterID(), "3","0",false));
                            kycScanningList.add(new KYCScanningModel(name, "Borrower", "VoterId", "Back", null, allDataAFDataModel.getVoterID(), "26","0",false));
                            Log.d("TAG", "DatainObjectB:" + "VoterId");

                        }
                        if (kycDocsFlag.getData().getPanExists()) {
                            kycScanningList.add(new KYCScanningModel(name, "Borrower", "Pan", "Front", null, allDataAFDataModel.getPanNO(), "4","0",false));
                            Log.d("TAG", "DatainObjectB:" + "Pan");
                        }
                        if (kycDocsFlag.getData().getDrivingExists()) {
                            kycScanningList.add(new KYCScanningModel(name, "Borrower", "DL", "Front", null, allDataAFDataModel.getDrivingLic(), "15","0",false));
                            Log.d("TAG", "DatainObjectB:" + "Dl");

                        }
                        kycScanningList.add(new KYCScanningModel(grName, "Gurrantor" + 1, "GAadhaar", "Front", null, allDataAFDataModel.getFiGuarantors().get(0).getAadharID(),"7","1",false));
                        Log.d("TAG", "DatainObjectG:" + "Adhaarfront");


          /*  kycScanningList.add(new KYCScanningModel(grName, "Gurrantor" + 1, "GAadhaar", "Back", null, allDataAFDataModel.getFiGuarantors().get(0).getAadharID(),));
            Log.d("TAG", "DatainObjectG:" + "AdhaarBack");*/

                        if (kycDocsFlag.getData().getGrDocs().get(0).getVoterExists()) {
                            kycScanningList.add(new KYCScanningModel(grName, "Gurrantor", "GVoterId", "Front", null, allDataAFDataModel.getFiGuarantors().get(0).getVoterID(),"5","1",false));
                            Log.d("TAG", "DatainObjectG:" + "VoterId");
                        }
                        if (kycDocsFlag.getData().getGrDocs().get(0).getPanExists()) {
                            kycScanningList.add(new KYCScanningModel(grName, "Gurrantor", "GPan", "Front", null, allDataAFDataModel.getFiGuarantors().get(0).getPanNo(),"8","1",false));
                            Log.d("TAG", "DatainObjectG:" + "Pan");
                        }
                        if (kycDocsFlag.getData().getGrDocs().get(0).getDrivingExists()) {
                            kycScanningList.add(new KYCScanningModel(grName, "Gurrantor", "GDL", "Front", null, allDataAFDataModel.getFiGuarantors().get(0).getDrivingLic(),"16","1",false));
                            Log.d("TAG", "DatainObjectG:" + "Dl");
                        }

                        kycRecyclerViewAdapter = new KycRecyclerViewAdapter(requireContext(), kycScanningList,   KycScanningFragment.this);
                        recyclerView.setAdapter(kycRecyclerViewAdapter);

                    } else {
                        Log.d("TAG", "sunny: " + "main obj is null");
                    }

                }

                @Override
                public void onFailure(Call<KycDocsFlag> call, Throwable t) {
                    Log.d("TAG", "onFailure: " + t.getMessage());

                }
            });

        }
            submitBtn = view.findViewById(R.id.SubmitKycScanning);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        return view;
    }

    @Override
    public void onItemClick(KYCScanningModel item, int position) {
        Log.d("Sunny", "sunny" + item);
        this.item = item;
        this.position = position;
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("croppedImagePath")) {
                String croppedImagePath = data.getStringExtra("croppedImagePath");
                ImageFile = new File(croppedImagePath);
                int pos = kycScanningList.indexOf(item);

                SaveDocs(item, ImageFile, pos);

               // OSV(item, ImageFile, position);
            }
        }
    }

    private void SaveDocs(KYCScanningModel item, File imageFile, int pos) {

        RequestBody fileBody = RequestBody.create( MediaType.parse("image/*"),imageFile);
        MultipartBody.Part document = MultipartBody.Part.createFormData("Document", imageFile.getName(), fileBody);

// Prepare other form data
        RequestBody dbName = RequestBody.create(MediaType.parse("text/plain"), BuildConfig.dbname);
        RequestBody creator = RequestBody.create(MediaType.parse("text/plain"), GlobalClass.Creator);
        RequestBody fiTag = RequestBody.create(MediaType.parse("text/plain"), "RTAG");
        RequestBody ficode = RequestBody.create(MediaType.parse("text/plain"), fiCode);
        RequestBody checkListId = RequestBody.create(MediaType.parse("text/plain"), item.getChecklistId());
        RequestBody remarks = RequestBody.create(MediaType.parse("text/plain"), item.getRemarks());
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), GlobalClass.Id);
        RequestBody grNo = RequestBody.create(MediaType.parse("text/plain"), item.getGrNo());
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), imageFile.getName());
        RequestBody imageTag = RequestBody.create(MediaType.parse("text/plain"), item.getDocType());



        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call1 = apiInterface.saveDocKyc(GlobalClass.Token, BuildConfig.dbname, document, dbName, creator, fiTag,ficode, checkListId, remarks, userId, grNo, fileName, imageTag);
        Log.d("TAG", "saveDocKyc1: " + fiCode + " " + BuildConfig.dbname + " " + creator + " " + position + " " + GlobalClass.Id);

        call1.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "saveDocKyc2: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    kycScanningList.get(pos).setFile(ImageFile);
                    kycScanningList.get(pos).setUploaded(true);
                    kycRecyclerViewAdapter.notifyDataSetChanged();

                } else {
                    Log.d("TAG", "saveDocKyc3: " + response.code()+response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onResponse4: " + t.getMessage());
            }
        });
    }

    private void OSV(KYCScanningModel item, File imageFile, int position) {

        String imgType = item.getDocType() + item.getRemarks();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);

        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("Document", imageFile.getName(), requestFile);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<KycScanningModel> call = apiInterface.scanDoc(GlobalClass.Token, BuildConfig.dbname, imgType, imagePart);

        call.enqueue(new Callback<KycScanningModel>() {
            @Override
            public void onResponse(Call<KycScanningModel> call, Response<KycScanningModel> response) {

                if (response.isSuccessful()) {
                    Log.d("TAG", "onResponse: " + response.body().getMessage());
                    KycScanningModel kycScanningModel = response.body();
                    KycScanningDataModel kycScanningDataModel = kycScanningModel.getData();
                    if (kycScanningDataModel.getIsOSV()) {
                        if (imgType.equals(item.getDocType() + item.getRemarks())) {
                            rDocID = kycScanningDataModel.getAdharId().toString();
                            if (!Objects.equals(item.getDocId(), rDocID)) {
                                SubmitAlert(getActivity(), "Error", "Id No. Not Matched");
                            } else {
                                SubmitAlert(getActivity(), "Success", "Id No. Not Matched");
                            }
                        }
                        int pos = kycScanningList.indexOf(item);

                        kycScanningList.get(pos).setFile(ImageFile);
                        kycRecyclerViewAdapter.notifyDataSetChanged();
                        Log.d("TAG", "onResponse: " + position);

                    } else {
                        Toast.makeText(getActivity(), "Invalid Id  Check STamp", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<KycScanningModel> call, Throwable t) {
                Log.d("TAG", "onnnResponse: " + "failure");
            }
        });
    }
}

