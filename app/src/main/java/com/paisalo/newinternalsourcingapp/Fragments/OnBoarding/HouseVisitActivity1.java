package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HVBorrowerDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HVBorrowerModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseVisitActivity1 extends AppCompatActivity {

    TextView applicantNameTV, maritalStatusTV, loanAmountTV, purposeTV, durationTV, applicantStatusTV, currentOccupationTV, occupationTypeTV, landOwnershipTV, residenceOwnershipTV;
    String fiCode, creator, applicantName, maritalStatus, loanAmount, purpose, duration, applicantStatus, currentOccupation, occupationType, landOwnership, residenceOwnership, groupCode, cityCode, rentOfHouse;
    Button form;
    CheckBox confirmationBox;
    ImageView applicantImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_visit1);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        if (intent != null) {
            fiCode = intent.getStringExtra("fiCode");
            creator = intent.getStringExtra("creator");

            Log.d("TAG", "Received fiCode: " + fiCode);
            Log.d("TAG", "Received creator: " + creator);
        }


        applicantImage = findViewById(R.id.applicantImage);
        applicantNameTV = findViewById(R.id.applicantName);
        maritalStatusTV = findViewById(R.id.maritalStatus);
        loanAmountTV = findViewById(R.id.loanAmount);
        purposeTV = findViewById(R.id.purpose);
        durationTV = findViewById(R.id.duration);
        applicantStatusTV = findViewById(R.id.applicantStatus);
        currentOccupationTV = findViewById(R.id.currentOccupation);
        occupationTypeTV = findViewById(R.id.occupationType);
        landOwnershipTV = findViewById(R.id.landOwnership);
        residenceOwnershipTV = findViewById(R.id.residenceOwnership);
        form = findViewById(R.id.fillForm);
        confirmationBox = findViewById(R.id.confirmationBox);








        form.setEnabled(false);
        form.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<HVBorrowerModel> call = apiInterface.HouseVisitBorrowerData(GlobalClass.Token, GlobalClass.dbname, fiCode, creator);
        Log.d("TAG", "HouseVisitBorrowerData: " + fiCode + creator);

        call.enqueue(new Callback<HVBorrowerModel>() {
            @Override
            public void onResponse(Call<HVBorrowerModel> call, Response<HVBorrowerModel> response) {
                Log.d("TAG", "HouseVisitBorrowerData: " + response.body());
                if (response.isSuccessful()) {
                    Log.d("TAG", "HouseVisitBorrowerData: " + response.body());
                    if (response.body() != null) {
                        HVBorrowerModel hvBorrowerModel = response.body();
                        HVBorrowerDataModel hvBorrowerDataModel = hvBorrowerModel.getData();
                        if (hvBorrowerDataModel != null) {
                            applicantNameTV.setText(hvBorrowerDataModel.getApplicantName().toUpperCase().toString());
                            maritalStatusTV.setText(hvBorrowerDataModel.getMaritalStatus().toUpperCase().toString());
                            loanAmountTV.setText(hvBorrowerDataModel.getLoanAmount().toString());
                            purposeTV.setText(hvBorrowerDataModel.getLoanReason().toUpperCase().toString());
                            durationTV.setText(hvBorrowerDataModel.getLoanDuration().toUpperCase().toString());
                            currentOccupationTV.setText(hvBorrowerDataModel.getCurrentOccupation().toUpperCase().toString());
                            occupationTypeTV.setText(hvBorrowerDataModel.getOccupationType().toUpperCase().toString());
                            landOwnershipTV.setText(hvBorrowerDataModel.getLandOwnership().toUpperCase().toString());
                            residenceOwnershipTV.setText(hvBorrowerDataModel.getResidenceOwnership().toUpperCase().toString());
                            fiCode = hvBorrowerDataModel.getFiCode().toString();
                            creator = hvBorrowerDataModel.getCreator().toString();
                            cityCode = hvBorrowerDataModel.getCityCode();
                            groupCode = hvBorrowerDataModel.getGroupCode();
                            rentOfHouse = hvBorrowerDataModel.getRentOfHouse().toString();


                            String profilePicPath = hvBorrowerDataModel.getProfilePic();
                            Log.d("TAG", "onCreate:121 " +profilePicPath );

                            if (profilePicPath != null && !profilePicPath.isEmpty()) {
                                if (profilePicPath.startsWith("http")) {
                                    Glide.with(HouseVisitActivity1.this)
                                            .load(profilePicPath)
                                            .error(R.drawable.error)
                                            .into(applicantImage);
                                } else {
                                    File imgFile = new File(profilePicPath);
                                    if (imgFile.exists()) {
                                        Glide.with(HouseVisitActivity1.this)
                                                .load(imgFile)
                                                .error(R.drawable.error)
                                                .into(applicantImage);
                                    } else {
                                        applicantImage.setImageDrawable(ContextCompat.getDrawable(HouseVisitActivity1.this, R.drawable.error));
                                    }
                                }
                            } else {
                                applicantImage.setImageDrawable(ContextCompat.getDrawable(HouseVisitActivity1.this, R.drawable.error));
                            }

                        }
                    }

                } else {
                    Log.d("TAG", "HouseVisitBorrowerData: " + response.code());
                    Log.d("TAG", "HouseVisitBorrowerData: " + response.message());

                }
            }

            @Override
            public void onFailure(Call<HVBorrowerModel> call, Throwable t) {
                Log.d("TAG", "HouseVisitBorrowerData: " + "failure");

            }
        });
        confirmationBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                form.setEnabled(isChecked);
                int colorRes = isChecked ? R.color.white : R.color.grey;
                form.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorRes)));
            }
        });


        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                applicantName = applicantNameTV.getText().toString();
                maritalStatus = maritalStatusTV.getText().toString();
                loanAmount = loanAmountTV.getText().toString();
                purpose = purposeTV.getText().toString();
                duration = durationTV.getText().toString();
                applicantStatus = applicantStatusTV.getText().toString();
                currentOccupation = currentOccupationTV.getText().toString();
                occupationType = occupationTypeTV.getText().toString();
                landOwnership = landOwnershipTV.getText().toString();
                residenceOwnership = residenceOwnershipTV.getText().toString();

                Intent intent = new Intent(HouseVisitActivity1.this, HouseVisitActivity2.class);
                intent.putExtra("FiCode", fiCode);
                intent.putExtra("Creator", creator);
                intent.putExtra("Rent_of_House", "450");
                intent.putExtra("GroupCode", groupCode);
                intent.putExtra("CityCode", cityCode);
                intent.putExtra("Latitude", "15.2154");
                intent.putExtra("Longitude", "155.561");
                startActivity(intent);
                finish();
            }
        });

    }


}