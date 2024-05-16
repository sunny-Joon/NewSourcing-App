package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isNumber;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidAddr;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidFullName;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidName;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidPan;

import android.app.DatePickerDialog;
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
import android.widget.ImageView;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiGuarantor;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GuarantorsFragment extends Fragment {

    List<String> gender_List = new ArrayList<>();
    List<String> state_List = new ArrayList<>();
    List<String> relationwithborr_List = new ArrayList<>();
    Button update;
    ImageView calendericon;

    private ProgressBar progressBar;
    FloatingActionButton gurrantorFormButton;
    AllDataAFDataModel allDataAFDataModel;

    private Calendar calendar;
    EditText etTextAadhar, etTextName, etTextAge, etTextDob, etTextGuardian, etTextAddress1, etTextAddress2, etTextAddress3, etTextCity, etTextPincode, etTextMobile, etTextvoterid, etTextPAN, etdrivingLicense;
    Spinner spin_gender, spin_state, spin_relationwithborr;

    String code, creator,tag, fiCode, aadharID, name, age, dob, gender, gurName, perAdd1, perAdd2, perAdd3, perCity, p_Pin, p_StateID, perMob1, voterID, pano, drivingLic, relationwithborr;

    public GuarantorsFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel = allDataAFDataModel;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guarantors, container, false);

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

                calendericon = popupView.findViewById(R.id.imageView4);
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


                if (!list.isEmpty()) {
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


                List<RangeCategoryDataClass> gender_DataList = databaseClass.dao().getAllRCDataListby_catKey("gender");
                for (RangeCategoryDataClass data : gender_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    gender_List.add(descriptionEn);

                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, gender_List);
                spin_gender.setAdapter(adapter1);

                List<RangeCategoryDataClass> stateDataList = new ArrayList<>();
                RangeCategoryDataClass rangeCategoryDataClass = new RangeCategoryDataClass("--Select--", "--Select--", "--Select--", "--Select--", "--Select--", 0, "99");
                stateDataList.add(rangeCategoryDataClass);
                stateDataList.addAll(databaseClass.dao().getAllRCDataListby_catKey("state"));
                RangeCategoryAdapter rangeCategoryAdapter = new RangeCategoryAdapter(getActivity(), stateDataList);
                spin_state.setAdapter(rangeCategoryAdapter);


                List<RangeCategoryDataClass> relationwithborr_DataList = databaseClass.dao().getAllRCDataListby_catKey("relationship");
                for (RangeCategoryDataClass data : relationwithborr_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    relationwithborr_List.add(descriptionEn);

                }
                ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, relationwithborr_List);
                spin_relationwithborr.setAdapter(adapter3);



                if (allDataAFDataModel != null) {
                    fiCode = allDataAFDataModel.getCode().toString();
                    creator = allDataAFDataModel.getCreator().toString();
                    tag = allDataAFDataModel.getTag().toString();
                    Log.d("TAG", "onCreateView222: " + fiCode + tag + creator);
                    try {

                        if (allDataAFDataModel != null) {

                            Log.d("TAG", "onCreate:view1 "+list.get(0).getGender());
                            if (list.get(0).getGender() != null) {
                                int castePos3 = adapter1.getPosition(list.get(0).getGender());
                                spin_gender.setSelection(castePos3);
                            }

                            if (allDataAFDataModel.getpState() != null) {
                                int castePos3=-1;
                                for (int i=0;i<rangeCategoryAdapter.getCount();i++){
                                    if (rangeCategoryAdapter.getItem(i).code.equals(databaseClass.dao().getStateByCode("state",allDataAFDataModel.getpState()).code)){
                                        castePos3=i;
                                        break;
                                    }
                                }

                                Log.d("TAG", "onCreateView: "+castePos3);
                                spin_state.setSelection(castePos3);
                            }

                            Log.d("TAG", "onCreate:view1 "+list.get(0).getRelation());
                            if (list.get(0).getRelation() != null) {
                                int castePos3 = adapter1.getPosition(list.get(0).getRelation());
                                spin_relationwithborr.setSelection(castePos3);
                            }

                        }
                        } catch(Exception exception){
                            Toast.makeText(getContext(), "fifamloans is null here", Toast.LENGTH_SHORT).show();
                        }
                    }


                            calendericon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog();
                    }

                    private void showDatePickerDialog() {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                                etTextDob.setText(selectedDate);
                                calculateAge(selectedYear, selectedMonth, selectedDay);
                                //   progressBar.incrementProgressBy(1);
                            }
                        }, year, month, day);

                        datePickerDialog.show();
                    }

                    private void calculateAge(int selectedYear, int selectedMonth, int selectedDay) {
                        Calendar dobCalendar = Calendar.getInstance();
                        dobCalendar.set(selectedYear, selectedMonth, selectedDay);

                        Calendar currentCalendar = Calendar.getInstance();

                        int age = currentCalendar.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);

                        if (currentCalendar.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
                            age--;
                        }

                        etTextAge.setText(String.valueOf(age));
                    }
                });


                update = popupView.findViewById(R.id.updateGuarantor);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        boolean allConditionsSatisfied = true;

                        if (etTextAadhar.getText().toString().isEmpty()) {
                            etTextAadhar.setError("Invalid ID");
                            allConditionsSatisfied = false;
                        } else {
                            aadharID = etTextAadhar.getText().toString();
                        }

                        if (!isValidFullName(etTextName.getText().toString().isEmpty() ? "" : etTextName.getText().toString())) {
                            etTextName.setError("Invalid Name");
                            allConditionsSatisfied = false;
                        } else {
                            name = etTextName.getText().toString();
                        }

                        if (etTextAge.getText().toString().isEmpty()) {
                            etTextAge.setError("Invalid Age");
                            allConditionsSatisfied = false;
                        } else {
                            age = etTextAge.getText().toString();
                        }

                        if (etTextDob.getText().toString().isEmpty()) {
                            etTextDob.setError("Select Date");
                            allConditionsSatisfied = false;
                        } else {
                            dob = etTextDob.getText().toString();
                        }


                        if (spin_gender.getSelectedItem().toString().contains("-Select-")) {
                            ((TextView) spin_gender.getSelectedView()).setError("Please select a Gender");
                            allConditionsSatisfied = false;
                        } else {
                            gender = spin_gender.getSelectedItem().toString();
                        }

                        if (!isValidFullName(etTextGuardian.getText().toString().isEmpty() ? "" : etTextGuardian.getText().toString())) {
                            etTextGuardian.setError("Invalid GurName");
                            allConditionsSatisfied = false;
                        } else {
                            gurName = etTextGuardian.getText().toString();
                        }


                        if (!isValidAddr(etTextAddress1.getText().toString().isEmpty() ? "" : etTextAddress1.getText().toString())) {
                            etTextAddress1.setError("Invalid Address");
                            allConditionsSatisfied = false;
                        } else {
                            perAdd1 = etTextAddress1.getText().toString();
                        }

                        if (!isValidAddr(etTextAddress2.getText().toString().isEmpty() ? "" : etTextAddress2.getText().toString())) {
                            etTextAddress2.setError("Invalid Address");
                            allConditionsSatisfied = false;
                        } else {
                            perAdd2 = etTextAddress2.getText().toString();
                        }

                        if (!isValidAddr(etTextAddress3.getText().toString().isEmpty() ? "" : etTextAddress3.getText().toString())) {
                            etTextAddress3.setError("Invalid Address");
                            allConditionsSatisfied = false;
                        } else {
                            perAdd3 = etTextAddress3.getText().toString();
                        }

                        if (!isValidName(etTextCity.getText().toString().isEmpty() ? " " : etTextCity.getText().toString())) {
                            etTextCity.setError("Invalid City");
                            allConditionsSatisfied = false;
                        } else {
                            perCity = etTextCity.getText().toString();
                        }

                        if (!isNumber(etTextPincode.getText().toString())) {
                            etTextPincode.setError("Invalid PinCode");
                            allConditionsSatisfied = false;
                        } else {
                            p_Pin = etTextPincode.getText().toString();
                        }

                        if (spin_state.getSelectedItem().toString().contains("-Select-")) {
                            ((TextView) spin_state.getSelectedView()).setError("Please select a state");
                            allConditionsSatisfied = false;
                        } else {
                            p_StateID = ((RangeCategoryDataClass)spin_state.getSelectedItem()).getCode();
                        }

                        if (!isNumber(etTextMobile.getText().toString())) {
                            etTextMobile.setError("Invalid PinCode");
                            allConditionsSatisfied = false;
                        } else {
                            perMob1 = etTextMobile.getText().toString();
                        }

                        if (etTextvoterid.getText().toString().isEmpty()) {
                            etTextvoterid.setError("Empty Voter id");
                            allConditionsSatisfied = false;
                        } else {
                            voterID = etTextvoterid.getText().toString();
                        }

                        if (!isValidPan(etTextPAN.getText().toString())) {
                            etTextPAN.setError("Invalid Pan");
                            allConditionsSatisfied = false;
                        } else {
                            pano = etTextPAN.getText().toString();
                        }

                        if (etdrivingLicense.getText().toString().isEmpty()) {
                            etdrivingLicense.setError("Empty License");
                            allConditionsSatisfied = false;
                        } else {
                            drivingLic = etdrivingLicense.getText().toString();
                        }

                        if (spin_relationwithborr.getSelectedItem().toString().contains("-Select-")) {
                            ((TextView) spin_relationwithborr.getSelectedView()).setError("Please select a relationwithborr");
                            allConditionsSatisfied = false;
                        } else {
                            relationwithborr = spin_relationwithborr.getSelectedItem().toString();
                        }

                        if (allConditionsSatisfied) {

                            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                            Call<KycUpdateModel> call = apiInterface.updateGaurantors(GlobalClass.Token, GlobalClass.dbname, gurrantorJson());
                            Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + gurrantorJson());

                            call.enqueue(new Callback<KycUpdateModel>() {
                                @Override
                                public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                                    Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                    if (response.isSuccessful()) {
                                        Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                        Log.d("TAG", "onResponseAdhaarUpdatemsg: " + response.body().getMessage().toString());
                                        SubmitAlert(getActivity(), "success", "Data set Successfully");


                                        Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        Log.d("TAG", "onResponseAdhaarUpdate: " + response.code());
                                        SubmitAlert(getActivity(), "unsuccessful", "Check Your Internet Connection");

                                    }
                                }

                                @Override
                                public void onFailure(Call<KycUpdateModel> call, Throwable t) {
                                    Log.d("TAG", "onResponseAdhaarUpdate: " + "failure");
                                    SubmitAlert(getActivity(), "Network Error", "Check Your Internet Connection");

                                }
                            });
                        }
                    }
                });
            }
        });

        return view;
    }


    private JsonObject gurrantorJson() {
        JsonObject jsonGurrantor = new JsonObject();
        jsonGurrantor.addProperty("code", code);
        jsonGurrantor.addProperty("creator", creator);
        jsonGurrantor.addProperty("fi_Code", fiCode);
        jsonGurrantor.addProperty("aadharID", aadharID);
        jsonGurrantor.addProperty("name", name);
        jsonGurrantor.addProperty("age", age);
        jsonGurrantor.addProperty("dob", dob);
        jsonGurrantor.addProperty("gender", gender);
        jsonGurrantor.addProperty("gurName", gurName);
        jsonGurrantor.addProperty("perAdd1", perAdd1);
        jsonGurrantor.addProperty("perAdd2", perAdd2);
        jsonGurrantor.addProperty("perAdd3", perAdd3);
        jsonGurrantor.addProperty("perCity", perCity);
        jsonGurrantor.addProperty("p_Pin", p_Pin);
        jsonGurrantor.addProperty("p_StateID", p_StateID);
        jsonGurrantor.addProperty("perMob1", perMob1);
        jsonGurrantor.addProperty("voterID", voterID);
        jsonGurrantor.addProperty("pano", pano);
        jsonGurrantor.addProperty("drivingLic", drivingLic);
        jsonGurrantor.addProperty("relationwithborr", relationwithborr);

        return jsonGurrantor;
    }
}
