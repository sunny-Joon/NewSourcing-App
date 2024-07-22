package com.paisalo.newinternalsourcingapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.Visitreportmodel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.Utils.CustomProgress;
import com.paisalo.newinternalsourcingapp.Utils.Utils;
import com.paisalo.newinternalsourcingapp.location.GpsTracker;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertVisitReport extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1001;
    AppCompatSpinner acspVisitType;
    EditText etAmount,etSmcode;
    Button btnDataSave,btnUploadImage;
    ImageView clickedImage;
    private Uri uriPicture;
    File SaveImageFile;
    String AddressREGX="[a-zA-Z]{4}\\d{6}";

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    CustomProgress customProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_visit_report);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        etSmcode=findViewById(R.id.etSmcode);
        etAmount=findViewById(R.id.etAmount);
        acspVisitType=findViewById(R.id.acspVisitType);
        btnUploadImage=findViewById(R.id.btnUploadImage);
        customProgress=new CustomProgress();
        btnDataSave=findViewById(R.id.btnDatSave);
        clickedImage=findViewById(R.id.clickedImage);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertVisitReport.this, CameraActivity.class);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        btnDataSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etSmcode.getText()==null || etSmcode.getText().length()!=10){
                    etSmcode.setError("Please enter correct smcode");
                    Utils.alert(InsertVisitReport.this,"Please enter correct smcode");
                }else if(!etSmcode.getText().toString().matches("[a-zA-Z]{4}\\d{6}")){
                    Utils.alert(InsertVisitReport.this,"Please enter correct smcode");
                }else if(SaveImageFile==null){
                    Utils.alert(InsertVisitReport.this,"Click Image");
                }else if(acspVisitType.getSelectedItem().toString().equals("Recovery") ){
                    if (etAmount.getText()==null || etAmount.getText().length()<1 || Double.parseDouble(etAmount.getText().toString())<1){
                        etAmount.setError("Please enter correct Amount");
                        Utils.alert(InsertVisitReport.this,"Please enter correct amount");
                    }else{
                        submitDataToServer();
                    }
                }else {
                    submitDataToServer();
                }
            }
        });



    }

    private void submitDataToServer() {

        GpsTracker gpsTracker=new GpsTracker(InsertVisitReport.this);
        if(gpsTracker==null || gpsTracker.getLatitude()==0.0f){
            Toast.makeText(gpsTracker, "Please turn on the device location!!", Toast.LENGTH_SHORT).show();
        }else{
            customProgress.showProgress(InsertVisitReport.this,"Please wait...\nwe are sending data to server...",false);

            int amount=etAmount.getText().length()>1?Integer.valueOf(etAmount.getText().toString()):0;
            String latitude = String.valueOf(gpsTracker.getLatitude());
            String longitude = String.valueOf(gpsTracker.getLongitude());


            if (SaveImageFile == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
                return;
            }

            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), SaveImageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("Picture", SaveImageFile.getName(), requestBody);
            RequestBody visitTypeBody=RequestBody.create(MediaType.parse("multipart/form-data"),acspVisitType.getSelectedItem().toString());
            RequestBody etSmcodeBody=RequestBody.create(MediaType.parse("multipart/form-data"),etSmcode.getText().toString());
            RequestBody amountBody=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(amount));
            RequestBody latitudeBody=RequestBody.create(MediaType.parse("multipart/form-data"),latitude);
            RequestBody longitudeBody=RequestBody.create(MediaType.parse("multipart/form-data"),longitude);
            RequestBody useridBody=RequestBody.create(MediaType.parse("multipart/form-data"), GlobalClass.Id);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Visitreportmodel> call = apiInterface.getvisit(GlobalClass.Token,GlobalClass.dbname, visitTypeBody,etSmcodeBody, amountBody, latitudeBody,longitudeBody, useridBody,imagePart);
            call.enqueue(new Callback<Visitreportmodel>() {
                @Override
                public void onResponse(Call<Visitreportmodel> call, Response<Visitreportmodel> response) {
                    if (response.isSuccessful() && response.body().getStatusCode()==200) {
                        Visitreportmodel result = response.body();
                        Log.d("TAG", "onResponse1: " + result.getData());
                        showAlert("success", "Data saved successfully");
                    } else {
                        showAlert("Error", "Failed to save data. Please try again.");
                        Log.d("TAG", "onResponse2: " + response.code());
                    }
                    customProgress.hideProgress();
                }

                @Override
                public void onFailure(Call<Visitreportmodel> call, Throwable t) {
                    Log.d("TAG", "onFailure3: " + t.getMessage());
                    showAlert("Error", "Failed to save data. Something went wrong!!");
                    customProgress.hideProgress();
                }
            });
        }
    }
    private void showAlert(String success, String dataSavedSuccessfully) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertVisitReport.this);
        builder.setTitle("Success")
                .setMessage("Data saved successfully")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        etSmcode.setText("");
                        etAmount.setText("");
                        finish();
                    }
                })
                .show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("croppedImagePath")) {
                String croppedImagePath = data.getStringExtra("croppedImagePath");
                SaveImageFile = new File(croppedImagePath);
                setprofileImage(SaveImageFile);
            }
        }


      /*  if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {


            if (data != null) {
                uriPicture = data.getData();
                CropImage.activity(uriPicture)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(45, 52)
                        .start(  InsertVisitReport.this);
            } else {
                Log.e("ImageData","Null");
                Toast.makeText(this, "Image Data Null", Toast.LENGTH_SHORT).show();
            }

        }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            Exception error = null;

            int maxDimentions = 1000;
            Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, maxDimentions, error, false);
            tempCroppedImage = new File(imageUri.getPath());
            Toast.makeText(InsertVisitReport.this, "document pic is not null", Toast.LENGTH_SHORT).show();
            if (tempCroppedImage.length() > 100) {
                clickedImage.setImageURI(Uri.fromFile(tempCroppedImage));
                clickedImage.setVisibility(View.VISIBLE);
            }}*/

    }

    private void setprofileImage(File profileImageFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(profileImageFile.getAbsolutePath());
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        clickedImage.setImageDrawable(drawable);
    }
}