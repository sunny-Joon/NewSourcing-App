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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.Adapters.RangeCategoryAdapter;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
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
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;

import java.util.ArrayList;
import java.util.List;
public class GuarantorsFragment extends Fragment {

    List<String> gender_List = new ArrayList<>();
    List<String> state_List = new ArrayList<>();
    List<String> relationwithborr_List = new ArrayList<>();
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

        DatabaseClass databaseClass = DatabaseClass.getInstance(getContext());

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

                etTextAadhar = popupView.findViewById(R.id.editTextAadhar);
                etTextName = popupView.findViewById(R.id.editTextName);
                etTextAge = popupView.findViewById(R.id.editTextAge);
                etTextDob = popupView.findViewById(R.id.editTextDob);
                etTextGuardian = popupView.findViewById(R.id.editTextGuardian);
                etTextAddress1 = popupView.findViewById(R.id.editTextAddress1);
                etTextAddress2 = popupView.findViewById(R.id.editTextAddress2);
                etTextAddress3 = popupView.findViewById(R.id.editTextAddress3);
                etTextCity = popupView.findViewById(R.id.editTextCity);
                etTextPincode = popupView.findViewById(R.id.editTextPincode);
                etTextMobile = popupView.findViewById(R.id.editTextMobile);
                etTextvoterid = popupView.findViewById(R.id.editTextvoterid);
                etTextPAN = popupView.findViewById(R.id.editTextPAN);
                etdrivingLicense = popupView.findViewById(R.id.drivingLicense);

                spin_gender = popupView.findViewById(R.id.spinnerOptions1);
                spin_state = popupView.findViewById(R.id.spinnerOptions2);
                spin_relationwithborr = popupView.findViewById(R.id.spinnerOptions);

                String selectOption = "--Select--";
                gender_List.add(selectOption);
                state_List.add(selectOption);
                relationwithborr_List.add(selectOption);


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

                DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {

                        List<RangeCategoryDataClass> gender_DataList = databaseClass.dao().getAllRCDataListby_catKey("gender");
                        for (RangeCategoryDataClass data : gender_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            gender_List.add(descriptionEn);
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, gender_List);
                            spin_gender.setAdapter(adapter1);
                        }

                        List<RangeCategoryDataClass> stateDataList = new ArrayList<>();
                        RangeCategoryDataClass rangeCategoryDataClass = new RangeCategoryDataClass("--Select--","--Select--","--Select--","--Select--","--Select--",0,"99");
                        stateDataList.add(rangeCategoryDataClass);
                        stateDataList.addAll(databaseClass.dao().getAllRCDataListby_catKey("state"));
                        RangeCategoryAdapter rangeCategoryAdapter = new RangeCategoryAdapter(getActivity(),stateDataList );
                        spin_state.setAdapter(rangeCategoryAdapter);


                        List<RangeCategoryDataClass> relationwithborr_DataList = databaseClass.dao().getAllRCDataListby_catKey("relationship");
                        for (RangeCategoryDataClass data : relationwithborr_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            relationwithborr_List.add(descriptionEn);
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, relationwithborr_List);
                            spin_relationwithborr.setAdapter(adapter3);
                        }
                    }
                });

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

