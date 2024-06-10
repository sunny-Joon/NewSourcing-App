package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static android.app.Activity.RESULT_OK;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.Activities.ManagerList;
import com.paisalo.newinternalsourcingapp.Adapters.GurrantorListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.ManagerListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.RangeCategoryAdapter;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity2;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ProfilePicModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.UpdateFiModels.KycUpdateModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
import com.paisalo.newinternalsourcingapp.Utils.CustomProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GuarantorsFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1001;
    private static final int REQUEST_IMAGE_CROP = 101;
    File profileImageFile, file;
    CustomProgressDialog customProgressDialog;
    private RecyclerView recyclerView;
    private GurrantorListAdapter adapter;

    List<String> gender_List = new ArrayList<>();
    List<String> state_List = new ArrayList<>();
    List<String> relationwithborr_List = new ArrayList<>();
    Button update, submitGurrantor, delete;
    ImageView calendericon, gurProfilePic;
    FloatingActionButton gurrantorFormButton;
    AllDataAFDataModel allDataAFDataModel;
    ArrayList<FiGuarantor> gurrantorList;

    List<FiGuarantor> list;
    EditText etTextAadhar, etTextName, etTextAge, etTextDob, etTextGuardian, etTextAddress1, etTextAddress2, etTextAddress3, etTextCity, etTextPincode, etTextMobile, etTextvoterid, etTextPAN, etdrivingLicense;
    Spinner spin_gender, spin_state, spin_relationwithborr;
    String creator, tag, fiCode, aadharID, name, age, dob, gender, gurName, perAdd1, perAdd2, perAdd3, perCity, p_Pin, p_StateID, perMob1, voterID, panno, drivingLic, relationwithborr;

    Bitmap bitmap;
    String timeStamp, currentPhotoPathBefWork;

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
        submitGurrantor = view.findViewById(R.id.submitGurrantor);
        recyclerView = view.findViewById(R.id.gurrantorsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        gurrantorList = new ArrayList<>();



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

                gurProfilePic = popupView.findViewById(R.id.gurprofilePic);
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

                if (allDataAFDataModel != null) {
                    list = allDataAFDataModel.getFiGuarantors();
                    if (!list.isEmpty() && list != null) {
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


                Log.d("TAG", "onCreateView222: " + "fiCode + tag + creator");
                if (allDataAFDataModel != null) {
                    fiCode = allDataAFDataModel.getCode().toString();
                    creator = allDataAFDataModel.getCreator().toString();
                    tag = allDataAFDataModel.getTag().toString();
                    Log.d("TAG", "onCreateView222: " + fiCode + tag + creator);
                    try {

                        if (allDataAFDataModel != null) {

                            Log.d("TAG", "onCreate:view1 " + list.get(0).getGender());
                            if (list.get(0).getGender() != null) {
                                int castePos3 = adapter1.getPosition(list.get(0).getGender());
                                spin_gender.setSelection(castePos3);
                            }

                            if (allDataAFDataModel.getpState() != null) {
                                int castePos3 = -1;
                                for (int i = 0; i < rangeCategoryAdapter.getCount(); i++) {
                                    if (rangeCategoryAdapter.getItem(i).code.equals(databaseClass.dao().getStateByCode("state", allDataAFDataModel.getpState()).code)) {
                                        castePos3 = i;
                                        break;
                                    }
                                }

                                Log.d("TAG", "onCreateView: " + castePos3);
                                spin_state.setSelection(castePos3);
                            }

                            Log.d("TAG", "onCreate:view1 " + list.get(0).getRelation());
                            if (list.get(0).getRelation() != null) {
                                int castePos3 = adapter3.getPosition(list.get(0).getRelation());
                                spin_relationwithborr.setSelection(castePos3);
                            }

                        }
                    } catch (Exception exception) {
                        Toast.makeText(getContext(), "fifamloans is null here", Toast.LENGTH_SHORT).show();
                    }
                }

                gurProfilePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
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
                        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        File image = File.createTempFile(
                                imageFileName,  /* prefix */
                                ".jpeg",         /* suffix */
                                storageDir      /* directory */
                        );
                        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);

                        FileOutputStream fos = new FileOutputStream(image);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                        fos.close();

                        bitmap.recycle();

                        currentPhotoPathBefWork = image.getAbsolutePath();
                        return image;
                    }
                });


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

                        if (profileImageFile == null) {
                            Toast.makeText(getActivity(), "Guarantor Picture not captured", Toast.LENGTH_SHORT).show();
                            allConditionsSatisfied = false;
                        }else {

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
                                p_StateID = ((RangeCategoryDataClass) spin_state.getSelectedItem()).getCode();
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
                                panno = etTextPAN.getText().toString();
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
                        }


                        if (allConditionsSatisfied) {

                            FiGuarantor guarantor = new FiGuarantor();
                            guarantor.setCreator(creator);
                            guarantor.setFiCode(Integer.parseInt(fiCode));
                            guarantor.setAadharID(aadharID);
                            guarantor.setName(name);
                            guarantor.setAge(Integer.parseInt(age));
                            guarantor.setDob(dob);
                            guarantor.setGender(gender);
                            guarantor.setGurName(gurName);
                            guarantor.setPerAdd1(perAdd1);
                            guarantor.setPerAdd2(perAdd2);
                            guarantor.setPerAdd3(perAdd3);
                            guarantor.setPerCity(perCity);
                            guarantor.setpPin(Integer.parseInt(p_Pin));
                            guarantor.setpStateID(p_StateID);
                            guarantor.setPerMob1(perMob1);
                            guarantor.setVoterID(voterID);
                            guarantor.setPanNo(panno);
                            guarantor.setDrivingLic(drivingLic);
                            guarantor.setRelation(relationwithborr);
                            guarantor.setGurImage(profileImageFile);
                            gurrantorList.add(guarantor);
                            adapter = new GurrantorListAdapter(getActivity(), gurrantorList);
                            recyclerView.setAdapter(adapter);
                            popupWindow.dismiss();
                        }
                    }
                });

                delete = popupView.findViewById(R.id.button2);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });



        submitGurrantor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gurrantorList.size();
                Log.d("TAG", "onClick: "+gurrantorList.size());
                for (FiGuarantor guarantor : gurrantorList) {
                    JsonObject jsonGuarantor = gurrantorJson(guarantor);
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<KycUpdateModel> call = apiInterface.updateGaurantors(GlobalClass.Token, GlobalClass.dbname, jsonGuarantor);
                    Log.d("TAG", "GurrantorLog: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + jsonGuarantor);

                    call.enqueue(new Callback<KycUpdateModel>() {
                        @Override
                        public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                            Log.d("TAG", "GurrantorLog: " + response.body());
                            if (response.isSuccessful()) {
                                Log.d("TAG", "GurrantorLog: " + response.body());
                                Log.d("TAG", "GurrantorLog: " + response.body().getMessage().toString());
                                SubmitAlert(getActivity(), "success", "Data set Successfully");
                                // profilepiapi
                             /*   RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                                Call<ProfilePicModel> call3 = apiInterface.updateprofilePic(GlobalClass.Token, GlobalClass.dbname, fiCode.trim(), GlobalClass.Creator, "CLAR", body);

                                call3.enqueue(new Callback<ProfilePicModel>() {
                                    @Override
                                    public void onResponse(Call<ProfilePicModel> call3, Response<ProfilePicModel> response3) {
                                        customProgressDialog.dismiss();
                                        if (response3.isSuccessful()) {
                                            ProfilePicModel profilePicModel = response3.body();

                                            Toast.makeText(getActivity(), "Profile picture updated successfully!", Toast.LENGTH_SHORT).show();
                                            SubmitAlert(getActivity(), "success", "Data set Successfully");

                                        } else {

                                            Toast.makeText(getActivity(), "Failed to update profile picture.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ProfilePicModel> call3, Throwable t) {
                                        customProgressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Network Issue", Toast.LENGTH_SHORT).show();
                                        // Optionally log the error
                                        Log.e("ProfilePicUpdate", "Error updating profile picture");
                                    }
                                });*/

                            } else {
                                Log.d("TAG", "GurrantorLog: " + response.code());
                                SubmitAlert(getActivity(), "unsuccessful", "Check Your Internet Connection");

                            }
                        }

                        @Override
                        public void onFailure(Call<KycUpdateModel> call, Throwable t) {
                            Log.d("TAG", "GurrantorLog: " + "failure");
                            SubmitAlert(getActivity(), "Network Error", "Check Your Internet Connection");

                        }
                    });
                }
            }
        });

        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: " + data + " // " + requestCode);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
           /* if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                Log.d("CheckXMLDATA3", "AadharData:->" + scanContent);
                if (scanFormat != null) {
                    try {
                        setAadharContent(scanContent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }*/
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            File imgFile = new File(currentPhotoPathBefWork);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                cropImage(myBitmap);
            }

        } else if (requestCode == REQUEST_IMAGE_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                bitmap = croppedBitmap;
                profileImageFile = bitmapToFile(bitmap);
              //  setprofileImage(profileImageFile);
                setprofileImage();
            }

        }
    }

    private void setprofileImage() {
      //  Bitmap bitmap = BitmapFactory.decodeFile(profileImageFile.getAbsolutePath());
        if(bitmap != null) {
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            gurProfilePic.setImageDrawable(drawable);
        }
    }

    public File bitmapToFile(Bitmap bitmap) {
        File directory = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Gur_profile_pictures");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "gur_profile_picture.png";
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

        private JsonObject gurrantorJson (FiGuarantor guarantor){
        JsonObject jsonGuarantor = new JsonObject();
        jsonGuarantor.addProperty("code", guarantor.getFiCode().toString());
        jsonGuarantor.addProperty("creator", guarantor.getCreator());
        jsonGuarantor.addProperty("fi_Code", guarantor.getFiCode().toString());
        jsonGuarantor.addProperty("aadharID", guarantor.getAadharID());
        jsonGuarantor.addProperty("name", guarantor.getName());
        jsonGuarantor.addProperty("age", guarantor.getAge());
        jsonGuarantor.addProperty("dob", guarantor.getDob());
        jsonGuarantor.addProperty("gender", guarantor.getGender());
        jsonGuarantor.addProperty("gurName", guarantor.getGurName());
        jsonGuarantor.addProperty("perAdd1", guarantor.getPerAdd1());
        jsonGuarantor.addProperty("perAdd2", guarantor.getPerAdd2());
        jsonGuarantor.addProperty("perAdd3", guarantor.getPerAdd3());
        jsonGuarantor.addProperty("perCity", guarantor.getPerCity());
        jsonGuarantor.addProperty("p_Pin", guarantor.getpPin());
        jsonGuarantor.addProperty("p_StateID", guarantor.getpStateID());
        jsonGuarantor.addProperty("perMob1", guarantor.getPerMob1());
        jsonGuarantor.addProperty("voterID", guarantor.getVoterID());
        jsonGuarantor.addProperty("panNo", guarantor.getPanNo());
        jsonGuarantor.addProperty("drivingLic", guarantor.getDrivingLic());
        jsonGuarantor.addProperty("relation", guarantor.getRelation());

        jsonGuarantor.addProperty("grNo", 0);
        jsonGuarantor.addProperty("gurInitials", "");
        jsonGuarantor.addProperty("corrAddr", 0);
        jsonGuarantor.addProperty("firmName", "");
        jsonGuarantor.addProperty("offAdd1", "");
        jsonGuarantor.addProperty("offAdd2", "");
        jsonGuarantor.addProperty("offAdd3", "");
        jsonGuarantor.addProperty("offCity", "");
        jsonGuarantor.addProperty("offPh1", "");
        jsonGuarantor.addProperty("offPh2", "");
        jsonGuarantor.addProperty("offPh3", "");
        jsonGuarantor.addProperty("offFax", "");
        jsonGuarantor.addProperty("offMob1", "");
        jsonGuarantor.addProperty("offMob2", "");
        jsonGuarantor.addProperty("resAdd1", "");
        jsonGuarantor.addProperty("resAdd2", "");
        jsonGuarantor.addProperty("resAdd3", "");
        jsonGuarantor.addProperty("resCity", "");
        jsonGuarantor.addProperty("resPh1", "");
        jsonGuarantor.addProperty("resPh2", "");
        jsonGuarantor.addProperty("resPh3", "");
        jsonGuarantor.addProperty("resFax", "");
        jsonGuarantor.addProperty("resMob1", "");
        jsonGuarantor.addProperty("resMob2", "");
        jsonGuarantor.addProperty("perFax", "");
        jsonGuarantor.addProperty("perMob2", "");
        jsonGuarantor.addProperty("perPh1", "");
        jsonGuarantor.addProperty("perPh2", "");
        jsonGuarantor.addProperty("perPh3", "");
        jsonGuarantor.addProperty("occupation", "");
        jsonGuarantor.addProperty("occupTypeDesig", "");
        jsonGuarantor.addProperty("location", "");
        jsonGuarantor.addProperty("bankAcNo", "");
        jsonGuarantor.addProperty("bankName", "");
        jsonGuarantor.addProperty("bankBranch", "");
        jsonGuarantor.addProperty("otherCase", "");
        jsonGuarantor.addProperty("remarks", "");
        jsonGuarantor.addProperty("recoveryAuth", "");
        jsonGuarantor.addProperty("recoveryExec", "");
        jsonGuarantor.addProperty("type", "");
        jsonGuarantor.addProperty("fDflag", "");
        jsonGuarantor.addProperty("incomeTax", "");
        jsonGuarantor.addProperty("minor", false);
        jsonGuarantor.addProperty("userID", "");
        jsonGuarantor.addProperty("auth_UserID", "");
        jsonGuarantor.addProperty("mod_Type", "");
        jsonGuarantor.addProperty("last_Mod_UserID", "");
        jsonGuarantor.addProperty("groupCode", "");
        jsonGuarantor.addProperty("cityCode", "");
        jsonGuarantor.addProperty("religion", "");
        jsonGuarantor.addProperty("landHolding", 0);
        jsonGuarantor.addProperty("exServiceMan", "");
        jsonGuarantor.addProperty("t_Pin", 0);
        jsonGuarantor.addProperty("o_Pin", 0);
        jsonGuarantor.addProperty("identityType", "");
        jsonGuarantor.addProperty("identity_No", "");
        jsonGuarantor.addProperty("eSignSucceed", "");
        jsonGuarantor.addProperty("kycuuid", "");
        jsonGuarantor.addProperty("concent", "");
        jsonGuarantor.addProperty("eSignUUID", "");
        jsonGuarantor.addProperty("initials", "");


        return jsonGuarantor;
    }


    }
