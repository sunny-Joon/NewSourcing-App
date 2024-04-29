package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.UpdateFiModels.KycUpdateModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.EditText;
import android.widget.Spinner;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiGuarantor;
import java.util.List;
public class GuarantorsFragment extends Fragment {

    Button update;
    FloatingActionButton gurrantorFormButton;
    AllDataAFDataModel allDataAFDataModel;
    EditText etTextAadhar,etTextName,etTextAge,etTextDob,etTextGuardian,etTextAddress1,etTextAddress2, etTextAddress3,etTextCity,etTextPincode,etTextMobile,etTextvoterid,etTextPAN,etdrivingLicense;
    Spinner spin_gender,spin_state,spin_relationwithborr;

    public GuarantorsFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel = allDataAFDataModel;}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guarantors,container,false);

        gurrantorFormButton = view.findViewById(R.id.guarantorFormButton);
        List<FiGuarantor> list = allDataAFDataModel.getFiGuarantors();
        gurrantorFormButton = view.findViewById(R.id.guarantorFormButton);

        etTextAadhar = view.findViewById(R.id.editTextAadhar);
        etTextName = view.findViewById(R.id.editTextName);
        etTextAge = view.findViewById(R.id.editTextAge);
        etTextDob = view.findViewById(R.id.editTextDob);
        etTextGuardian = view.findViewById(R.id.editTextGuardian);
        etTextAddress1 = view.findViewById(R.id.editTextAddress1);
        etTextAddress2 = view.findViewById(R.id.editTextAddress2);
        etTextAddress3 = view.findViewById(R.id.editTextAddress3);
        etTextCity = view.findViewById(R.id.editTextCity);
        etTextPincode = view.findViewById(R.id.editTextPincode);
        etTextMobile = view.findViewById(R.id.editTextMobile);
        etTextvoterid = view.findViewById(R.id.editTextvoterid);
        etTextPAN = view.findViewById(R.id.editTextPAN);
        etdrivingLicense = view.findViewById(R.id.drivingLicense);

        spin_gender = view.findViewById(R.id.spinnerOptions1);
        spin_state = view.findViewById(R.id.spinnerOptions2);
        spin_relationwithborr = view.findViewById(R.id.spinnerOptions);

        if(!list.isEmpty()) {
            etTextAadhar.setText(list.get(0).getAadharID());
            etTextName.setText(list.get(0).getName());
            etTextAge.setText(list.get(0).getAge());
            etTextDob.setText(list.get(0).getDob());
            etTextGuardian.setText(list.get(0).getGurName());
            etTextAddress1.setText(list.get(0).getPerAdd1());
            etTextAddress2.setText(list.get(0).getPerAdd2());
            etTextAddress3.setText(list.get(0).getPerAdd3());
            etTextCity.setText(list.get(0).getPerCity());
            etTextPincode.setText(list.get(0).getpPin());
            etTextMobile.setText(list.get(0).getPerMob1());
            etTextvoterid.setText(list.get(0).getVoterID());
            etTextPAN.setText(list.get(0).getPanNo());
            etdrivingLicense.setText(list.get(0).getDrivingLic());

        }
        gurrantorFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.guarantorspopup, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                update = popupView.findViewById(R.id.updateGuarantor);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<KycUpdateModel> call= apiInterface.updateFamLoans(GlobalClass.Token,GlobalClass.dbname, gurrantorJson());
                        Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token+" "+GlobalClass.dbname+" "+ gurrantorJson());

                        call.enqueue(new Callback<KycUpdateModel>() {
                            @Override
                            public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                                Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                if(response.isSuccessful()){
                                    Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                    Log.d("TAG", "onResponseAdhaarUpdatemsg: " + response.body().getMessage().toString());
                                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("guarantorCheckBox", true);
                                    editor.apply();

                                    Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }else{
                                    Log.d("TAG", "onResponseAdhaarUpdate: " + response.code());

                                }
                            }

                            @Override
                            public void onFailure(Call<KycUpdateModel> call, Throwable t) {
                                Log.d("TAG", "onResponseAdhaarUpdate: " + "failure");

                            }
                        });
                    }
                });
            }
        });

        return view;
    }

    private JsonObject gurrantorJson() {
        JsonObject jsonGurrantor = new JsonObject();
        jsonGurrantor.addProperty("fiCode", allDataAFDataModel.getCode().toString());
        jsonGurrantor.addProperty("creator", allDataAFDataModel.getCreator().toString());
        jsonGurrantor.addProperty("tag", allDataAFDataModel.getTag().toString());
        jsonGurrantor.addProperty("tag", allDataAFDataModel.getTag().toString());
        jsonGurrantor.addProperty("tag", allDataAFDataModel.getTag().toString());
        jsonGurrantor.addProperty("tag", allDataAFDataModel.getTag().toString());
        jsonGurrantor.addProperty("tag", allDataAFDataModel.getTag().toString());
        jsonGurrantor.addProperty("tag", allDataAFDataModel.getTag().toString());


        return jsonGurrantor;
    }
    }

