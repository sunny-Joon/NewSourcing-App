package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static android.app.Activity.RESULT_OK;

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
import com.paisalo.newinternalsourcingapp.Adapters.KycRecyclerViewAdapter;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelclassesRoom.KYCScanningModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KycScanningFragment extends Fragment implements KycRecyclerViewAdapter.OnItemClickListener {

    Button submitBtn;
    String name,guarantorName,guarantorAadhar,guarantorPan,guarantorVoterID,guarantorDL,guarantorName2,guarantorAadhar2,guarantorPan2,guarantorVoterID2,
            guarantorDL2,creator,timeStamp, currentPhotoPathBefWork,oaadharNo,opanNo,ovoterIdNo,olicenseNo,raadharNo,rpanNo,rvoterIdNo,rlicenseNo;
    int gurNo,ficode;
    File ImageFile, file;
    Bitmap myBitmap;
    List<KYCScanningModel> kycScanningList;
    int position;

    private KycRecyclerViewAdapter kycRecyclerViewAdapter;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int REQUEST_IMAGE_CROP = 201;


    AllDataAFDataModel allDataAFDataModel;

    public KycScanningFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel = allDataAFDataModel;
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
            ficode = (allDataAFDataModel.getCode() != 0) ? allDataAFDataModel.getCode() : 0;
            gurNo = (allDataAFDataModel.getFiGuarantors() != null) ? allDataAFDataModel.getFiGuarantors().size() : 0;

            if (gurNo ==1) {
                guarantorName = (allDataAFDataModel.getFiGuarantors().get(0).getName() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getName() : "N/A";
                guarantorAadhar = (allDataAFDataModel.getFiGuarantors().get(0).getAadharID() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getAadharID() : "N/A";
                guarantorPan = (allDataAFDataModel.getFiGuarantors().get(0).getPanNo() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getPanNo() : "N/A";
                guarantorVoterID = (allDataAFDataModel.getFiGuarantors().get(0).getVoterID() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getVoterID() : "N/A";
                guarantorDL = (allDataAFDataModel.getFiGuarantors().get(0).getDrivingLic() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getDrivingLic() : "N/A";
            } else if(gurNo ==2){
                guarantorName = (allDataAFDataModel.getFiGuarantors().get(0).getName() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getName() : "N/A";
                guarantorAadhar = (allDataAFDataModel.getFiGuarantors().get(0).getAadharID() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getAadharID() : "N/A";
                guarantorPan = (allDataAFDataModel.getFiGuarantors().get(0).getPanNo() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getPanNo() : "N/A";
                guarantorVoterID = (allDataAFDataModel.getFiGuarantors().get(0).getVoterID() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getVoterID() : "N/A";
                guarantorDL = (allDataAFDataModel.getFiGuarantors().get(0).getDrivingLic() != null) ? allDataAFDataModel.getFiGuarantors().get(0).getDrivingLic() : "N/A";
                guarantorName2 = (allDataAFDataModel.getFiGuarantors().get(1).getName() != null) ? allDataAFDataModel.getFiGuarantors().get(1).getName() : "N/A";
                guarantorAadhar2 = (allDataAFDataModel.getFiGuarantors().get(1).getAadharID() != null) ? allDataAFDataModel.getFiGuarantors().get(1).getAadharID() : "N/A";
                guarantorPan2 = (allDataAFDataModel.getFiGuarantors().get(1).getPanNo() != null) ? allDataAFDataModel.getFiGuarantors().get(1).getPanNo() : "N/A";
                guarantorVoterID2 = (allDataAFDataModel.getFiGuarantors().get(1).getVoterID() != null) ? allDataAFDataModel.getFiGuarantors().get(1).getVoterID() : "N/A";
                guarantorDL2 = (allDataAFDataModel.getFiGuarantors().get(1).getDrivingLic() != null) ? allDataAFDataModel.getFiGuarantors().get(1).getDrivingLic() : "N/A";

            }

    }else{
            Log.d("TAG", "sunny: " + "main obj is null");

        }
        kycScanningList = new ArrayList<>();
        kycScanningList.add(new KYCScanningModel(name, "Borrower", "Aadhaar", "Front", null));
        kycScanningList.add(new KYCScanningModel(name, "Borrower", "Aadhaar", "Back", null));
        kycScanningList.add(new KYCScanningModel(name, "Borrower", "PassBook", "First Page", null));

        if(ovoterIdNo!=null) {
            kycScanningList.add(new KYCScanningModel(name, "Borrower", "Voter Id", "Front", null));
        }
        if(opanNo!=null){
            kycScanningList.add(new KYCScanningModel(name, "Borrower", "Pan", "Front", null));
        }
        if(olicenseNo != null){
            kycScanningList.add(new KYCScanningModel(name, "Borrower", "DL", "Front", null));
        }



        if(gurNo ==1){
            kycScanningList.add(new KYCScanningModel(name, "Gurrantor1", "GAadhaar", "Front", null));
            kycScanningList.add(new KYCScanningModel(name, "Gurrantor1", "GAadhaar", "Back", null));
            if (guarantorVoterID != null) {
                kycScanningList.add(new KYCScanningModel(name, "Gurrantor1", "GVoter Id", "Front", null));
            }
            if (guarantorPan != null) {
                kycScanningList.add(new KYCScanningModel(name, "Gurrantor1", "GPan", "Front", null));
            }
            if (guarantorDL != null) {
                kycScanningList.add(new KYCScanningModel(name, "Gurrantor1", "GDL", "Front", null));
            }
        }else if(gurNo ==2) {
            kycScanningList.add(new KYCScanningModel(name, "Gurrantor1", "GAadhaar", "Front", null));
            kycScanningList.add(new KYCScanningModel(name, "Gurrantor1", "GAadhaar", "Back", null));
            if (guarantorVoterID != null) {
                kycScanningList.add(new KYCScanningModel(name, "Gurrantor1", "GVoter Id", "Front", null));
            }
            if (guarantorPan != null) {
                kycScanningList.add(new KYCScanningModel(name, "Gurrantor1", "GPan", "Front", null));
            }
            if (guarantorDL != null) {
                kycScanningList.add(new KYCScanningModel(name, "Gurrantor1", "GDL", "Front", null));
            }
            kycScanningList.add(new KYCScanningModel(name, "Gurrantor2", "GAadhaar", "Front", null));
            kycScanningList.add(new KYCScanningModel(name, "Gurrantor2", "GAadhaar", "Back", null));
            if(guarantorVoterID2 != null) {
            kycScanningList.add(new KYCScanningModel(name, "Gurrantor2", "GVoter Id", "Front", null));
            }
            if(guarantorPan2 != null) {
                kycScanningList.add(new KYCScanningModel(name, "Gurrantor2", "GPan", "Front", null));
            }
            if(guarantorDL2 != null) {
                kycScanningList.add(new KYCScanningModel(name, "Gurrantor2", "GDL", "Front", null));
            }
        }

        kycRecyclerViewAdapter = new KycRecyclerViewAdapter(requireContext(), kycScanningList, this);
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
                /*Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                startActivity(intent);
                getActivity().finish();*/

                RequestBody ficodeRB=RequestBody.create(MediaType.parse("multipart/form-data"),"ficode");
                RequestBody dbnameRB=RequestBody.create(MediaType.parse("multipart/form-data"),BuildConfig.dbname);
                RequestBody CreatorRB=RequestBody.create(MediaType.parse("multipart/form-data"),GlobalClass.Creator);
                RequestBody fiTagRB=RequestBody.create(MediaType.parse("multipart/form-data"),"RTAG");
                RequestBody CheckListIdRB=RequestBody.create(MediaType.parse("multipart/form-data"),"1");
                RequestBody RemarksRB=RequestBody.create(MediaType.parse("multipart/form-data"),"aadhafront");
                RequestBody userIDRB=RequestBody.create(MediaType.parse("multipart/form-data"),GlobalClass.Id);
                RequestBody GrNoRB=RequestBody.create(MediaType.parse("multipart/form-data"),"1");
                RequestBody fileNameRB=RequestBody.create(MediaType.parse("multipart/form-data"),"fileName");
                RequestBody imageTagRB=RequestBody.create(MediaType.parse("multipart/form-data"),"1");


                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("Document", file.getName(), requestFile);

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<JsonObject> call1 = apiInterface.saveDocKyc(GlobalClass.Token, BuildConfig.dbname,imagePart,ficodeRB,dbnameRB,CreatorRB,fiTagRB,CheckListIdRB,RemarksRB,userIDRB,GrNoRB,fileNameRB,imageTagRB);
                call1.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            Log.d("TAG", "onResponse: " + response.body());
                        }else{
                            Log.d("TAG", "onResponse: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("TAG", "onResponse: " + "failure");
                    }
                });



            }
        });
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Log.d("Sunny", "sunny" + position);
        this.position =position;
        openCamera();
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startCameraIntent();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraIntent();
            } else {

            }
        }
    }

    private void startCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.paisalo.newinternalsourcingapp.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpeg",         /* suffix */
                storageDir      /* directory */
        );
        // Create an empty bitmap with the desired size (adjust as needed)
        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);

        // Create a file output stream
        FileOutputStream fos = new FileOutputStream(image);

        // Compress the bitmap into JPEG format and write it to the file
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

        // Close the file output stream
        fos.close();

        // Optionally, you can recycle the bitmap to free up memory
        bitmap.recycle();

        currentPhotoPathBefWork = image.getAbsolutePath();

        return image;
    }

    private void cropImage(Bitmap bitmap) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(getImageUri(bitmap), "image/*");
        cropIntent.putExtra("crop", "true");
        /*cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);*/
        cropIntent.putExtra("scale", true); // Add this line to prevent rotation
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    private Uri getImageUri(Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "JPEG_" + timeStamp + "_", null);
        if (path != null) {
            return Uri.parse(path);
        } else {

            return null;
        }
    }

    public File bitmapToFile(Bitmap bitmap) {
        File directory = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "profile_pictures");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "profile_picture.png";
        File file = new File(directory, fileName);
        this.file = file;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new File(currentPhotoPathBefWork);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                cropImage(myBitmap);
            }
        } else if (requestCode == REQUEST_IMAGE_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                myBitmap = croppedBitmap;
                ImageFile = bitmapToFile(myBitmap);
                Log.d("TAG", "onnnResponse: " + position);
                OSV(position,ImageFile);
            }
        }
    }

    private void OSV(int position, File imageFile) {

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);

                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("Document", imageFile.getName(), requestFile);

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<KycScanningModel> call=apiInterface.scanDoc(GlobalClass.Token, BuildConfig.dbname,"aadharfront",imagePart);

                call.enqueue(new Callback<KycScanningModel>() {
                    @Override
                    public void onResponse(Call<KycScanningModel> call, Response<KycScanningModel> response) {

                        if (response.isSuccessful()) {
                            KycScanningModel kycScanningModel = response.body();
                            KycScanningDataModel kycScanningDataModel = kycScanningModel.getData();
                            if (kycScanningDataModel.getIsOSV() == true) {

                                kycScanningList.get(position).setFile(ImageFile);
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

