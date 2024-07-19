package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static android.app.Activity.RESULT_OK;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;

import android.annotation.SuppressLint;
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
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelclassesRoom.KYCScanningModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiGuarantor;
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

public class KycScanningFragment extends Fragment implements KycRecyclerViewAdapter.OnItemClickListener {

    Button submitBtn;
    String name,creator,fiCode,oaadharNo,opanNo,ovoterIdNo,olicenseNo,rDocID;
    int gurNo,position;
    File ImageFile;
    List<KYCScanningModel> kycScanningList;
    KYCScanningModel item;

    private KycRecyclerViewAdapter kycRecyclerViewAdapter;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int REQUEST_IMAGE_CROP = 201;
    AllDataAFDataModel allDataAFDataModel;

    public KycScanningFragment(AllDataAFDataModel allDataAFDataModel, String fiCode, String creator) {
        this.allDataAFDataModel = allDataAFDataModel;
        this.fiCode = fiCode;
        this.creator = creator;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (allDataAFDataModel != null) {
            creator = (allDataAFDataModel.getCreator() != null) ? allDataAFDataModel.getCreator() : "N/A";
            name = (allDataAFDataModel.getFname() != null) ? allDataAFDataModel.getFname() : "N/A";
            oaadharNo = (allDataAFDataModel.getAadharID() != null) ? allDataAFDataModel.getAadharID() : "N/A";
            olicenseNo = (allDataAFDataModel.getDrivingLic() != null) ? allDataAFDataModel.getDrivingLic() : "N/A";
            ovoterIdNo = (allDataAFDataModel.getVoterID() != null) ? allDataAFDataModel.getVoterID() : "N/A";
            opanNo = (allDataAFDataModel.getPanNO() != null) ? allDataAFDataModel.getPanNO() : "N/A";
            fiCode = (allDataAFDataModel.getCode() != null) ? String.valueOf(allDataAFDataModel.getCode()) : "N/A";
            gurNo = (allDataAFDataModel.getFiGuarantors() != null) ? allDataAFDataModel.getFiGuarantors().size() : 0;

            Log.d("TAG", "DatainObject: "+ creator+" "+name+" "+oaadharNo+" "+olicenseNo+" "+ovoterIdNo+" "+opanNo+" "+fiCode+" "+gurNo);
            Log.d("TAG", "DatainObject: "+allDataAFDataModel.getFiGuarantors().size());

            kycScanningList = new ArrayList<>();
            kycScanningList.add(new KYCScanningModel(name, "Borrower", "Aadhar", "Borrower Aadhar", null, allDataAFDataModel.getAadharID()));
            Log.d("TAG", "DatainObjectB:" +"Adhaar front");
            kycScanningList.add(new KYCScanningModel(name, "Borrower", "Aadhar", "Aadhar ID Back", null, allDataAFDataModel.getAadharID()));
            Log.d("TAG", "DatainObjectB:" +"Adhaar back");

            kycScanningList.add(new KYCScanningModel(name, "Borrower", "PassBook", "Borrower Bank Passbook", null, ""));
            Log.d("TAG", "DatainObjectB:" +"passbook");

            if (!ovoterIdNo.isEmpty()) {
                kycScanningList.add(new KYCScanningModel(name, "Borrower", "VoterId", "Voter ID Borrower", null, allDataAFDataModel.getVoterID()));
                Log.d("TAG", "DatainObjectB:" +"VoterId");

            }

            if (!ovoterIdNo.isEmpty()) {
                kycScanningList.add(new KYCScanningModel(name, "Borrower", "VoterId", "Voter ID Back", null, allDataAFDataModel.getVoterID()));
                Log.d("TAG", "DatainObjectB:" +"VoterId");

            }
            if (!opanNo.isEmpty()) {
                kycScanningList.add(new KYCScanningModel(name, "Borrower", "Pan", "Pan Card Borrower", null, allDataAFDataModel.getPanNO()));
                Log.d("TAG", "DatainObjectB:" +"Pan");

            }
            if (!olicenseNo.isEmpty()) {
                kycScanningList.add(new KYCScanningModel(name, "Borrower", "DL", "Driving License Borrower", null, allDataAFDataModel.getDrivingLic()));
                Log.d("TAG", "DatainObjectB:" +"Dl");

            }


            for (FiGuarantor fiGuarantor : allDataAFDataModel.getFiGuarantors()) {

                kycScanningList.add(new KYCScanningModel(fiGuarantor.getName(), "Gurrantor" + fiGuarantor.getGrNo(), "GAadhaar", "Co-Borrower Aadhar", null, fiGuarantor.getAadharID()));
                Log.d("TAG", "DatainObjectG:" +"Adhaarfront");

                kycScanningList.add(new KYCScanningModel(fiGuarantor.getName(), "Gurrantor" + fiGuarantor.getGrNo(), "GAadhaar", "Co-Borrower Aadhar Back", null, fiGuarantor.getAadharID()));
                Log.d("TAG", "DatainObjectG:" +"AdhaarBack");

                if (!fiGuarantor.getVoterID().isEmpty()) {
                    kycScanningList.add(new KYCScanningModel(fiGuarantor.getName(), "Gurrantor" + fiGuarantor.getGrNo(), "GVoterId", "Voter ID Co-Borrower", null, fiGuarantor.getVoterID()));
                    Log.d("TAG", "DatainObjectG:" +"VoterId");
                }

                if (!fiGuarantor.getVoterID().isEmpty()) {
                    kycScanningList.add(new KYCScanningModel(fiGuarantor.getName(), "Gurrantor" + fiGuarantor.getGrNo(), "GVoterId", "Voter ID Co-Borrower Back", null, fiGuarantor.getVoterID()));
                    Log.d("TAG", "DatainObjectG:" +"VoterId");
                }


                if (!fiGuarantor.getPanNo().isEmpty()) {
                    kycScanningList.add(new KYCScanningModel(fiGuarantor.getName(), "Gurrantor" + fiGuarantor.getGrNo(), "GPan", "Pan Card Co-Borrower", null, fiGuarantor.getPanNo()));
                    Log.d("TAG", "DatainObjectG:" +"Pan");
                }
                if (!fiGuarantor.getDrivingLic().isEmpty()) {
                    kycScanningList.add(new KYCScanningModel(fiGuarantor.getName(), "Gurrantor" + fiGuarantor.getGrNo(), "GDL", "Driving License Co-Borrower", null, fiGuarantor.getDrivingLic()));
                    Log.d("TAG", "DatainObjectG:" +"Dl");
                }
            }
            kycRecyclerViewAdapter = new KycRecyclerViewAdapter(requireContext(), kycScanningList, this);

        } else {
            Log.d("TAG", "sunny: " + "main obj is null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kyc_scanning, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.kycScanningRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(kycRecyclerViewAdapter);

        submitBtn = view.findViewById(R.id.SubmitKycScanning);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestBody ficodeRB=RequestBody.create(MediaType.parse("multipart/form-data"),fiCode);
                RequestBody dbnameRB=RequestBody.create(MediaType.parse("multipart/form-data"),BuildConfig.dbname);
                RequestBody CreatorRB=RequestBody.create(MediaType.parse("multipart/form-data"),creator);
                RequestBody fiTagRB=RequestBody.create(MediaType.parse("multipart/form-data"),"RTAG");
                RequestBody CheckListIdRB=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(position));
                RequestBody RemarksRB=RequestBody.create(MediaType.parse("multipart/form-data"),"aadhafront");
                RequestBody userIDRB=RequestBody.create(MediaType.parse("multipart/form-data"),GlobalClass.Id);
                RequestBody GrNoRB=RequestBody.create(MediaType.parse("multipart/form-data"),"1");
                RequestBody fileNameRB=RequestBody.create(MediaType.parse("multipart/form-data"),"fileName");
                RequestBody imageTagRB=RequestBody.create(MediaType.parse("multipart/form-data"),"1");

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), ImageFile);

                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("Document", ImageFile.getName(), requestFile);

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<JsonObject> call1 = apiInterface.saveDocKyc(GlobalClass.Token, BuildConfig.dbname,imagePart,ficodeRB,dbnameRB,CreatorRB,fiTagRB,CheckListIdRB,RemarksRB,userIDRB,GrNoRB,fileNameRB,imageTagRB);
                Log.d("TAG", "saveDocKyc1: " + fiCode+" "+BuildConfig.dbname+" "+creator+" "+position+" "+GlobalClass.Id);

                call1.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            Log.d("TAG", "saveDocKyc2: " + response.body());
                        }else{
                            Log.d("TAG", "saveDocKyc3: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("TAG", "onResponse4: " + "failure");
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onItemClick(KYCScanningModel  item,int position) {
        Log.d("Sunny", "sunny" + item);
        this.item =item;
        this.position= position;
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
                kycScanningList.get(pos).setFile(ImageFile);
                kycRecyclerViewAdapter.notifyDataSetChanged();

                OSV(item,ImageFile,position);
            }
        }
    }

    private void OSV(KYCScanningModel item, File imageFile,int position) {

        String imgType =item.getDocType() + item.getRemarks();
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);

                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("Document", imageFile.getName(), requestFile);

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<KycScanningModel> call=apiInterface.scanDoc(GlobalClass.Token, BuildConfig.dbname,imgType,imagePart);

                call.enqueue(new Callback<KycScanningModel>() {
                    @Override
                    public void onResponse(Call<KycScanningModel> call, Response<KycScanningModel> response) {

                        if (response.isSuccessful()) {
                            Log.d("TAG", "onResponse: " + response.body().getMessage());
                            KycScanningModel kycScanningModel = response.body();
                            KycScanningDataModel kycScanningDataModel = kycScanningModel.getData();
                            if (kycScanningDataModel.getIsOSV()) {
                                if(imgType.equals(item.getDocType() + item.getRemarks())){
                                    rDocID = kycScanningDataModel.getAdharId().toString();
                                    if(!Objects.equals(item.getDocId(), rDocID)){
                                        SubmitAlert(getActivity(), "Error", "Id No. Not Matched");
                                    }else{
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

