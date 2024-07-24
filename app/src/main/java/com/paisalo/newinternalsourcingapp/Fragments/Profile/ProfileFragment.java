package com.paisalo.newinternalsourcingapp.Fragments.Profile;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.CollectionReportActivity;
import com.paisalo.newinternalsourcingapp.Activities.QrPayments;
import com.paisalo.newinternalsourcingapp.Activities.ReferAndEanActivity;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.Modelclasses.AppAttendance;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.Utils.Utils;
import com.paisalo.newinternalsourcingapp.databinding.FragmentProfileBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    TextView username, creator, id, address;
    CardView cvCollectionReport, cvQrPayments;
    Button buttonprofile;

    AppCompatButton punchin, punchout;
    ConstraintLayout referAndEarn;
    FragmentProfileBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        punchin = root.findViewById(R.id.punchin);
        punchout = root.findViewById(R.id.punchout);

        buttonprofile = root.findViewById(R.id.buttonprofile);
        referAndEarn = root.findViewById(R.id.referAndEarn);
        username = root.findViewById(R.id.username);
        creator = root.findViewById(R.id.creator);
        id = root.findViewById(R.id.id);
        address = root.findViewById(R.id.address);
        cvQrPayments = root.findViewById(R.id.cvQrPayments);
        cvCollectionReport = root.findViewById(R.id.cvCollectionReport);


        username.setText(GlobalClass.UserName);
        creator.setText(GlobalClass.Creator);
        id.setText(GlobalClass.Id);
        address.setText(GlobalClass.Address);

        punchin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.alert(getContext(), "PUNCH IN SUCCESSFULLY !!");
                UpdateDailyAtt("PUNCH IN");
            }
        });

        punchout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDailyAtt("PUNCH OUT");
            }
        });

        cvQrPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentQrPayments = new Intent(getActivity(), QrPayments.class);
                intentQrPayments.putExtra("UserID", GlobalClass.Id);
                startActivity(intentQrPayments);
            }
        });

        cvCollectionReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcollectionreport = new Intent(getActivity(), CollectionReportActivity.class);
                //intentcollectionreport.putExtra("UserID",IglPreferences.getPrefString(this, SEILIGL.USER_ID, ""));
                startActivity(intentcollectionreport);
            }
        });

        buttonprofile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReferAndEanActivity.class);
                startActivity(intent);
                // fragmentManager.beginTransaction().replace(R.id.frameLayout, new ReferAndEarnFragment(fragmentManager))
                //   .commit();
            }
        });

        getDailyAtt();
        return root;
    }


    private void UpdateDailyAtt(String punchStatus) {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("userName", GlobalClass.Id);

        if (punchStatus.equals("PUNCH IN")) {
            jsonobject.addProperty("inTime", "2024-05-10T11:47:52.845Z");  // Use actual date and time
            jsonobject.addProperty("inLatlon", GlobalClass.Latitude + "," + GlobalClass.Longitude);
            jsonobject.addProperty("inLocation", Utils.getAddress(GlobalClass.Latitude, GlobalClass.Longitude, getContext()));
            jsonobject.addProperty("status", "1");  // Set status to 1 for punch in
        } else {
            jsonobject.addProperty("outTime", "2024-05-10T11:47:52.845Z");  // Use actual date and time
            jsonobject.addProperty("outLatlon", GlobalClass.Latitude + "," + GlobalClass.Longitude);
            jsonobject.addProperty("outLocation", Utils.getAddress(GlobalClass.Latitude, GlobalClass.Longitude, getContext()));
            jsonobject.addProperty("status", "0");  // Set status to 0 for punch out
        }

        jsonobject.addProperty("appType", "S");
        ApiInterface apiInterface = ApiClient.getClient4().create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.updateAttendance(jsonobject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: " + response.body());
                if (response.body() != null) {
                    Utils.alert(getContext(), response.body().get("message").getAsString());
                    getDailyAtt();
                } else {
                    Log.d("TAG", "Response body is null");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void getDailyAtt() {
        ApiInterface apiInterface = ApiClient.getClient4().create(ApiInterface.class);
        Call<AppAttendance> call = apiInterface.getAttendance(GlobalClass.Id);

        call.enqueue(new Callback<AppAttendance>() {
            @Override
            public void onResponse(Call<AppAttendance> call, Response<AppAttendance> response) {
                if (response.body() != null && response.body().getStatusCode() == 200) {
                    if (response.body().getData().getStatus() == 1 && response.body().getData().getOutTime() == null) {
                        punchin.setVisibility(View.GONE);
                        punchout.setVisibility(View.VISIBLE);

                    } else {
                        punchin.setVisibility(View.GONE);
                        punchout.setVisibility(View.GONE);

                    }
                    Log.d("TAG", "Attendance status: " + response.body().getData().getStatus());
                } else {
                    punchin.setVisibility(View.VISIBLE);
                    punchout.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<AppAttendance> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                punchin.setVisibility(View.VISIBLE);
                punchout.setVisibility(View.GONE);

            }
        });
    }


}