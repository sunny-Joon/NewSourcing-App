package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static android.app.Activity.RESULT_OK;
import static com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity.REQUEST_ADHAARBACK_CAPTURE;
import static com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity.REQUEST_ADHAARFRONT_CAPTURE;
import static com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity.formatDate;
import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isNumber;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidAddr;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidFullName;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidName;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidPan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
import com.paisalo.newinternalsourcingapp.Activities.CameraActivity;
import com.paisalo.newinternalsourcingapp.Activities.ManagerList;
import com.paisalo.newinternalsourcingapp.Adapters.CustomSpinnerAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.GurrantorListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.ManagerListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.RangeCategoryAdapter;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.Entities.ScanAadhaar.AadharData;
import com.paisalo.newinternalsourcingapp.Entities.ScanAadhaar.AadharUtils;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity2;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.OCRScanModels.AdharDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.OCRScanModels.AdharDataResponse;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ProfilePicModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.UpdateFiModels.KycUpdateModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

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

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiGuarantor;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
import com.paisalo.newinternalsourcingapp.Utils.CustomProgressDialog;
import com.paisalo.newinternalsourcingapp.Utils.Utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

public class GuarantorsFragment extends Fragment {
    protected static final int VTC_INDEX = 15;

    protected static final byte SEPARATOR_BYTE = (byte) 255;
    protected ArrayList<String> decodedData;
    protected String signature, email, mobile;
    String isAadharVerified;
    protected int imageStartIndex, emailMobilePresent, imageEndIndex;

    boolean aadharNumberentry = false;
    File  profileImageFile, adhaarFrontFile, adhaarBackFile;
    private static final int REQUEST_IMAGE_CAPTURE = 1001;
    private static final int REQUEST_IMAGE_CROP = 101;
    CustomProgressDialog customProgressDialog;
    private RecyclerView recyclerView;
    private GurrantorListAdapter adapter;
    List<RangeCategoryDataClass> stateDataList;
    Button qrScan, adhaarBack, adhaarFront;
    AlertDialog alertDialog;
    List<String> gender_List = new ArrayList<>();
    List<String> state_List = new ArrayList<>();
    List<String> relationwithborr_List = new ArrayList<>();
    Button update, submitGurrantor, delete;
    ImageView calendericon, gurProfilePic,aadhaarScanner;
    FloatingActionButton gurrantorFormButton;
    AllDataAFDataModel allDataAFDataModel;
    ArrayList<FiGuarantor> gurrantorList;

    List<FiGuarantor> list;
    EditText etTextAadhar, etTextName, etTextAge, etTextDob, etTextGuardian, etTextAddress1, etTextAddress2, etTextAddress3, etTextCity, etTextPincode, etTextMobile, etTextvoterid, etTextPAN, etdrivingLicense;
    Spinner spin_gender, spin_state, spin_relationwithborr;
    String creator, tag, fiCode, aadharID, name, age, dob, gender, gurName, perAdd1, perAdd2, perAdd3, perCity, p_Pin, p_StateID, perMob1, voterID, panno, drivingLic, relationwithborr;
    int GrNo;
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

        Log.d("TAG", "allDataAFDataModel: "+" list.size()");

        if (allDataAFDataModel != null) {
            Log.d("TAG", "allDataAFDataModel: "+" list.size()");

            list =allDataAFDataModel.getFiGuarantors();
            Log.d("TAG", "allDataAFDataModel: "+list.size());

            allDataAFDataModel.getFiGuarantors();
            adapter = new GurrantorListAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);

                  /*  Log.d("TAG", "allDataAFDataModel: "+ list.size());

                    if (!list.isEmpty() && list != null) {
                        Log.d("TAG", "allDataAFDataModel: "+list.get(0).getGrNo());
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
                    }*/
        }
        gurrantorFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GrNo = gurrantorList.size()+1;
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.guarantorspopup, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                aadhaarScanner = popupView.findViewById(R.id.aadhaarScanner);
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



                List<RangeCategoryDataClass> gender_DataList = databaseClass.dao().getAllRCDataListby_catKey("gender");
                for (RangeCategoryDataClass data : gender_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    gender_List.add(descriptionEn);
                }
                CustomSpinnerAdapter adapter1 = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, gender_List);
                spin_gender.setAdapter(adapter1);

                stateDataList = new ArrayList<>();
                RangeCategoryDataClass rangeCategoryDataClass = new RangeCategoryDataClass("--Select--", "--Select--", "--Select--", "--Select--", "--Select--", 0, "99");
                stateDataList.add(rangeCategoryDataClass);
                stateDataList.addAll(databaseClass.dao().getAllRCDataListby_catKey("state"));
                RangeCategoryAdapter rangeCategoryAdapter = new RangeCategoryAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,stateDataList);
                spin_state.setAdapter(rangeCategoryAdapter);


                List<RangeCategoryDataClass> relationwithborr_DataList = databaseClass.dao().getAllRCDataListby_catKey("relationship");
                for (RangeCategoryDataClass data : relationwithborr_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    relationwithborr_List.add(descriptionEn);

                }
                CustomSpinnerAdapter adapter3 = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, relationwithborr_List);
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


                aadhaarScanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openAlertDialog();
                    }

                    public void openAlertDialog() {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.adhaar_scanner_popup, null);
                        alertDialogBuilder.setView(dialogView);

                        adhaarFront = dialogView.findViewById(R.id.adhaarFront);
                        adhaarBack = dialogView.findViewById(R.id.adhaarBack);
                        qrScan = dialogView.findViewById(R.id.scanQrCode);


                        qrScan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss(); // Dismiss the AlertDialog
                                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                                scanIntegrator.setOrientationLocked(false);
                                scanIntegrator.initiateScan(Collections.singleton("QR_CODE"));
                            }
                        });

                        adhaarFront.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getActivity(), CameraActivity.class);
                                startActivityForResult(intent, REQUEST_ADHAARFRONT_CAPTURE);


                            }
                        });

                        adhaarBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), CameraActivity.class);
                                startActivityForResult(intent, REQUEST_ADHAARBACK_CAPTURE);

                            }
                        });


                        alertDialog = alertDialogBuilder.create(); // Create AlertDialog
                        alertDialog.show(); // Show AlertDialog
                    }

                });



                gurProfilePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), CameraActivity.class);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                        }*/
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
                             //   allConditionsSatisfied = false;
                            } else {
                                perAdd2 = etTextAddress2.getText().toString();
                            }

//                            if (!isValidAddr(etTextAddress3.getText().toString().isEmpty() ? "" : etTextAddress3.getText().toString())) {
//                                etTextAddress3.setError("Invalid Address");
//                                allConditionsSatisfied = false;
//                            } else {
                                perAdd3 = etTextAddress3.getText().toString();
                          //  }

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

//                            if (etTextvoterid.getText().toString().isEmpty()) {
//                                etTextvoterid.setError("Empty Voter id");
//                                allConditionsSatisfied = false;
//                            } else {
                                voterID = etTextvoterid.getText().toString();
                          //  }

                            if (!isValidPan(etTextPAN.getText().toString())) {
                                etTextPAN.setError("Invalid Pan");
                                allConditionsSatisfied = false;
                            } else {
                                panno = etTextPAN.getText().toString();
                            }

//                            if (etdrivingLicense.getText().toString().isEmpty()) {
//                                etdrivingLicense.setError("Empty License");
//                                allConditionsSatisfied = false;
//                            } else {
                                drivingLic = etdrivingLicense.getText().toString();
                          //  }

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
                            guarantor.setGrNo(GrNo);
                            guarantor.setDrivingLic(drivingLic);
                            guarantor.setRelation(relationwithborr);
                            guarantor.setGurImage(profileImageFile);
                            Log.d("TAG", "onBindViewHolder: "+perAdd1+","+perAdd2+","+perAdd3);

                            gurrantorList.add(guarantor);

                            Log.d("TAG", "onBindViewHolder: "+gurrantorList.size()+","+gurrantorList.indexOf(guarantor));
                            Log.d("TAG", "onBindViewHolder: "+gurrantorList.get(0).getPerAdd1());
                            Log.d("TAG", "onBindViewHolder: "+gurrantorList.get(0).getPerAdd1());
                            Log.d("TAG", "onBindViewHolder: "+gurrantorList.get(gurrantorList.indexOf(guarantor)).getPerAdd1());

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
                              //  SubmitAlert(getActivity(), "success", "Data set Successfully");
                                /*gurrantorList.add(guarantor);
                                adapter.notifyDataSetChanged();*/
                                // profilepiapi
                                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), profileImageFile);
                                MultipartBody.Part body = MultipartBody.Part.createFormData("file", profileImageFile.getName(), requestFile);

                                Call<ProfilePicModel> call3 = apiInterface.updateprofilePic(GlobalClass.Token, GlobalClass.dbname, fiCode.trim(), GlobalClass.Creator, "CLAR","1", body);

                                call3.enqueue(new Callback<ProfilePicModel>() {
                                    @Override
                                    public void onResponse(Call<ProfilePicModel> call3, Response<ProfilePicModel> response3) {
                                        if (response3.isSuccessful()) {
                                            ProfilePicModel profilePicModel = response3.body();
                                            Toast.makeText(getActivity(), "Profile picture updated successfully!", Toast.LENGTH_SHORT).show();
                                            SubmitAlert(getActivity(), "success", "Data set Successfully");
                                            getActivity().finish();
                                        } else {
                                            Toast.makeText(getActivity(), "Failed to update profile picture.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ProfilePicModel> call3, Throwable t) {
                                        Toast.makeText(getActivity(), "Network Issue", Toast.LENGTH_SHORT).show();
                                        // Optionally log the error
                                        Log.e("ProfilePicUpdate", "Error updating profile picture");
                                    }
                                });

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

    private void setDataOfAdhar(File croppedImage, String imageData) {
        ProgressDialog progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("Data Fetching from " + imageData.toUpperCase() + " Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        RequestBody image_befWorkBody = RequestBody.create(MediaType.parse("image/*"), croppedImage);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file", croppedImage.getName(), image_befWorkBody);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), croppedImage);
        builder.addFormDataPart("file", croppedImage.getName(), surveyBody);
        RequestBody requestBody = builder.build();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AdharDataResponse> call = apiInterface.getAdharDataByOCR(GlobalClass.Token, BuildConfig.dbname, imageData, imagePart);

        call.enqueue(new Callback<AdharDataResponse>() {
            @Override
            public void onResponse(Call<AdharDataResponse> call, Response<AdharDataResponse> response) {
                Log.d("TAG", "onResponsews: " + response.body());
                AdharDataResponse adharDataResponse = response.body();
                if (response.isSuccessful()) {
                    progressBar.dismiss();

                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }

                    if (adharDataResponse != null) {
                        AdharDataModel adharDataModel = adharDataResponse.getData();
                        if (adharDataModel != null) {
                            if (imageData.equals("aadharfront")) {
                                if (response.code() == 200) {
                                    String adharId = (String) adharDataModel.getAdharId();
                                    etTextAadhar.setText(adharId);
                                    String name = (String) adharDataModel.getName();
                                    etTextName.setText(name);

                                    String dob = (String) adharDataModel.getDob();
                                    Log.d("TAG", "onResponse:dob " + dob);

                                    try {
                                        dob=formatDate(dob, "dd/MM/yyyy", "dd-MMM-YYYY");
                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }


                                    etTextDob.setText(dob);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                    if(adharDataModel.getDob()!= null){
                                        try {
                                            Date dateOfBirth = sdf.parse((String) adharDataModel.getDob());
                                            Calendar dobCalendar = Calendar.getInstance();
                                            dobCalendar.setTime(dateOfBirth);
                                            Calendar todayCalendar = Calendar.getInstance();
                                            int age = todayCalendar.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);
                                            if (todayCalendar.get(Calendar.MONTH) < dobCalendar.get(Calendar.MONTH) ||
                                                    (todayCalendar.get(Calendar.MONTH) == dobCalendar.get(Calendar.MONTH) &&
                                                            todayCalendar.get(Calendar.DAY_OF_MONTH) < dobCalendar.get(Calendar.DAY_OF_MONTH))) {
                                                age--;
                                            }
                                            etTextAge.setText(String.valueOf(age));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    String gender = (String) adharDataModel.getGender();

                                    if(gender != null) {
                                        if (gender.equalsIgnoreCase("Male")) {
                                            spin_gender.setSelection(2);
                                        } else if (gender.equalsIgnoreCase("Female")) {
                                            spin_gender.setSelection(1);
                                        } else {
                                            spin_gender.setSelection(3);
                                        }
                                    }
                                    // borrower.isAadharVerified = "O";

                                } else {
                                    Utils.alert(getContext(), "Please capture aadhaar front image!!");
                                }


                                progressBar.dismiss();

                            } else if (imageData.equals("aadharback")) {
                                if (response.code() == 200) {
                                    String address1 = adharDataModel.getAddress1();
                                    Log.d("TAG", "onResponse:(address1) " + address1);
                                    if (address1 != null) {
                                        String[] addressParts = address1.split("[,-]");
                                        if (addressParts.length >= 1) {
                                            StringBuilder address1Builder = new StringBuilder();
                                            StringBuilder address2Builder = new StringBuilder();
                                            StringBuilder address3Builder = new StringBuilder();

                                            for (int i = 0; i < addressParts.length; i++) {
                                                String trimmedPart = addressParts[i].trim();
                                                if (i < addressParts.length - 3) {
                                                    if (i < 3) {
                                                        address1Builder.append(trimmedPart).append(" ");
                                                    } else if (i >= 3 && i <= 5) {
                                                        address2Builder.append(trimmedPart).append(" ");
                                                    } else if (i > 5 && i < addressParts.length - 3) {
                                                        address3Builder.append(trimmedPart).append(" ");
                                                    }
                                                }
                                            }
                                            etTextAddress1.setText(address1Builder.toString().trim());
                                            etTextAddress2.setText(address2Builder.toString().trim());
                                            etTextAddress3.setText(address3Builder.toString().trim());
                                        }
                                    }

                                    String guardian = adharDataModel.getGuardianName();
                                    etTextGuardian.setText(guardian);

                                    String CityName = adharDataModel.getCityName();
                                    etTextCity.setText(CityName);

                                    String Pincode = adharDataModel.getPincode();
                                    etTextPincode.setText(Pincode);

                                    String StateName = adharDataModel.getStateName();
                                    int statePosition=0;
                                    for (int statePos=0;statePos<stateDataList.size();statePos++){
                                        if (stateDataList.get(statePos).descriptionEn.equals(StateName)){
                                            statePosition=statePos;
                                        }

                                    }
                                    spin_state.setSelection(statePosition);

                                    String Relation = adharDataModel.getRelation();
                                 /*   for (int i = 0; i < acspRelationship.getAdapter().getCount(); i++) {
                                        RangeCategoryDataModel rangeCategory = (RangeCategoryDataModel) acspRelationship.getAdapter().getItem(i);
                                        if (rangeCategory.getDescriptionEn().equals(Relation)) {
                                            acspRelationship.setSelection(i);
                                            break;
                                        }
                                    }*/
                                    if(Relation.equalsIgnoreCase("Husband")){
                                        spin_relationwithborr.setSelection(3);
                                    } else if (Relation.equalsIgnoreCase("Father")) {
                                        spin_relationwithborr.setSelection(2);
                                    }else {
                                        spin_relationwithborr.setSelection(1);
                                    }
                                    Log.d("TAG", "onResponse(relation): " + Relation);
                                    String Relation1 =  adharDataModel.getRelation();
                                  /*  if (Relation1 != null) {
                                        if (Relation1.equals("Father")) {
                                            String guardian1 = adharDataModel.getGuardianName();

                                            etTextGuardian.setText(guardian1);

                                            if (guardian1 != null) {
                                                String[] nameParts = guardian1.split(" ");
                                                if (nameParts.length >= 1) {
                                                    etTextFatherFname.setText(nameParts[0]);
                                                }
                                                if (nameParts.length >= 2) {
                                                    if (nameParts.length == 2) {
                                                        editTextFatherFname.setText(nameParts[0]);
                                                        editTextfatherlastname.setText(nameParts[1]);
                                                    } else {
                                                        StringBuilder middleNameBuilder = new StringBuilder();
                                                        for (int i = 1; i < nameParts.length - 1; i++) {
                                                            middleNameBuilder.append(nameParts[i]).append(" ");
                                                        }
                                                        editTextfathermiddlename.setText(middleNameBuilder.toString().trim());
                                                    }
                                                }
                                                if (nameParts.length >= 3) {
                                                    editTextfatherlastname.setText(nameParts[nameParts.length - 1]);
                                                }
                                            }
                                        } else if (Relation1.equals("Husband")) {
                                            String guardian2 = adharDataModel.getGuardianName();
                                            if (guardian2 != null) {
                                                String[] nameParts1 = guardian2.split(" ");
                                                if (nameParts1.length >= 1) {
                                                    editTextspousefirstname.setText(nameParts1[0]);
                                                }
                                                if (nameParts1.length >= 2) {
                                                    if (nameParts1.length == 2) {
                                                        editTextspousefirstname.setText(nameParts1[0]);
                                                        editTextspouselastname.setText(nameParts1[1]);
                                                    } else {
                                                        StringBuilder middleNameBuilder = new StringBuilder();
                                                        for (int i = 1; i < nameParts1.length - 1; i++) {
                                                            middleNameBuilder.append(nameParts1[i]).append(" ");
                                                        }
                                                        editTextspousemiddlename.setText(middleNameBuilder.toString().trim());
                                                    }
                                                }
                                                if (nameParts1.length >= 3) {
                                                    editTextspouselastname.setText(nameParts1[nameParts1.length - 1]);
                                                }
                                            }
                                        }
                                        progressBar.dismiss();
                                    }*/
                                    //        borrower.isAadharVerified = "O";

                                } else {
                                    Utils.alert(getContext(), "Please capture aadhaar back image!!");

                                }

                                progressBar.dismiss();

                            } else if (imageData.equals("pan")) {
                                if (response.code() == 200) {
                                    String panno = adharDataModel.getPanNo().toString();
                                    Log.d("TAG", "onResponse: panno => " + panno);
                                    etTextPAN.setText(panno);
                                    //    isgetPanwithOCR = true;
                                    //  borrower.isAadharVerified = "O";
                                    //  panaadharDOBMatched = true;

                                } else {
                                    Toast.makeText(getContext(), "Please capture PAN Card on behalf sample", Toast.LENGTH_SHORT).show();
                                    Utils.alert(getContext(), "Please capture PAN image again!!");
                                }
                            }
                            progressBar.dismiss();
                        } else {
                            progressBar.dismiss();
                            if (imageData.equals("aadharfront")) {
                                Utils.alert(getContext(), "Failed to fetch Aadhaar front image data. Please make sure the image is clear and try again.");
                                Toast.makeText(getContext(), "Failed to fetch Aadhaar front image data. Please make sure the image is clear and try again.", Toast.LENGTH_SHORT).show();
                            } else if (imageData.equals("aadharback")) {
                                Utils.alert(getContext(), "Failed to fetch Aadhaar back image data. Please make sure the image is clear and try again.");
                                Toast.makeText(getContext(), "Failed to fetch Aadhaar back image data. Please make sure the image is clear and try again.", Toast.LENGTH_SHORT).show();
                            } else if (imageData.equals("pan")) {
                                Utils.alert(getContext(), "Failed to fetch pan image data. Please make sure the image is clear and try again.");
                                Toast.makeText(getContext(), "Failed to fetch pan image data. Please make sure the image is clear and try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

            }

            public void onFailure(Call<AdharDataResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                progressBar.dismiss();
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: " + data + " // " + requestCode);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
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
            }
        }
        /*else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            File imgFile = new File(currentPhotoPathBefWork);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                cropImage(myBitmap);
            }

        }*/

        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("croppedImagePath")) {
                String croppedImagePath = data.getStringExtra("croppedImagePath");
                profileImageFile = new File(croppedImagePath);
                setprofileImage(profileImageFile);
            }
        }

       /* else if (requestCode == REQUEST_IMAGE_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                bitmap = croppedBitmap;
                profileImageFile = bitmapToFile(bitmap);
                setprofileImage(profileImageFile);
            }
        }*/
        else if (requestCode == REQUEST_ADHAARFRONT_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("croppedImagePath")) {
                String croppedImagePath = data.getStringExtra("croppedImagePath");
                adhaarFrontFile = new File(croppedImagePath);
                setDataOfAdhar(adhaarFrontFile, "aadharfront");
            }
        } else if (requestCode == REQUEST_ADHAARBACK_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("croppedImagePath")) {
                String croppedImagePath = data.getStringExtra("croppedImagePath");
                adhaarBackFile = new File(croppedImagePath);
                setDataOfAdhar(adhaarBackFile, "aadharback");
            }
        }
        /*else if (requestCode == REQUEST_PAN_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("croppedImagePath")) {
                String croppedImagePath = data.getStringExtra("croppedImagePath");
                panFile = new File(croppedImagePath);
                setDataOfAdhar(panFile, "pan");
            }
        }*/


        /*else if (requestCode == REQUEST_ADHAARFRONT_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cropImage(imageBitmap);
        } else if (requestCode == REQUEST_ADHAARFRONT_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                bitmap = croppedBitmap;
                adhaarFrontFile = bitmapToFile(bitmap);
                setDataOfAdhar(adhaarFrontFile, "aadharfront");
            }
        } *//*else if (requestCode == REQUEST_ADHAARBACK_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cropImage(imageBitmap);
        } else if (requestCode == REQUEST_ADHAARBACK_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                bitmap = croppedBitmap;
                adhaarBackFile = bitmapToFile(bitmap);
                setDataOfAdhar(adhaarBackFile, "aadharback");
            }
        } */
       /* else if (requestCode == REQUEST_PAN_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cropImage(imageBitmap);
        }
        else if (requestCode == REQUEST_PAN_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                bitmap = croppedBitmap;
                panFile = bitmapToFile(bitmap);
                setDataOfAdhar(panFile, "pan");
            }
        }*/
    }

    private void setAadharContent(String aadharDataString) throws Exception {

        if (aadharDataString.toUpperCase().contains("XML")) {

            Log.d("XML printing", "AadharData:->" + aadharDataString);
            //AadharData aadharData = AadharUtils.getAadhar(aadharDataString);
            AadharData aadharData = AadharUtils.getAadhar(AadharUtils.ParseAadhar(aadharDataString));

            Log.d("TAG", "setAadharContent: " + aadharData.isAadharVerified);
            if (aadharData.AadharId != null) {
            }

            if (aadharData.Address2 == null) {
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            } else if (aadharData.Address2.trim().equals("")) {
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }
            if (aadharData.Address1 == null) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            } else if (aadharData.Address1.trim().equals("")) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }

        } else {

            final BigInteger bigIntScanData = new BigInteger(aadharDataString, 10);
            Log.e("testbigin======", "AadharData:->" + bigIntScanData);
            // 2. Convert BigInt to Byte Array
            final byte byteScanData[] = bigIntScanData.toByteArray();

            // 3. Decompress Byte Array
            final byte[] decompByteScanData = decompressData(byteScanData);

            // 4. Split the byte array using delimiter
            List<byte[]> parts = separateData(decompByteScanData);
            // Throw error if there are no parts
            Log.e("Parts======11======> ", "part data =====> " + parts.toString());
            decodeData(parts);
            decodeSignature(decompByteScanData);
            decodeMobileEmail(decompByteScanData);
            aadharNumberentry = true;
        }
    }
    protected void decodeMobileEmail(byte[] decompressedData) {
        int mobileStartIndex = 0, mobileEndIndex = 0, emailStartIndex = 0, emailEndIndex = 0;
        switch (emailMobilePresent) {
            case 3:
                mobileStartIndex = decompressedData.length - 289; // length -1 -256 -32
                mobileEndIndex = decompressedData.length - 257; // length -1 -256
                emailStartIndex = decompressedData.length - 322;// length -1 -256 -32 -1 -32
                emailEndIndex = decompressedData.length - 290;// length -1 -256 -32 -1

                mobile = bytesToHex(Arrays.copyOfRange(decompressedData, mobileStartIndex, mobileEndIndex + 1));
                email = bytesToHex(Arrays.copyOfRange(decompressedData, emailStartIndex, emailEndIndex + 1));
                imageEndIndex = decompressedData.length - 323;
                break;

            case 2:
                // only mobile
                email = "";
                mobileStartIndex = decompressedData.length - 289; // length -1 -256 -32
                mobileEndIndex = decompressedData.length - 257; // length -1 -256

                mobile = bytesToHex(Arrays.copyOfRange(decompressedData, mobileStartIndex, mobileEndIndex + 1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 290;

                break;

            case 1:
                // only email
                mobile = "";
                emailStartIndex = decompressedData.length - 289; // length -1 -256 -32
                emailEndIndex = decompressedData.length - 257; // length -1 -256

                email = bytesToHex(Arrays.copyOfRange(decompressedData, emailStartIndex, emailEndIndex + 1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 290;
                break;

            default:
                // no mobile or email
                mobile = "";
                email = "";
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 257;
        }

        Log.e("email mobile======> ", "Data=====>" + email + "   " + mobile);

    }
    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    protected void decodeSignature(byte[] decompressedData) {
        // extract 256 bytes from the end of the byte array
        int startIndex = decompressedData.length - 257,
                noOfBytes = 256;
        try {
            signature = new String(decompressedData, startIndex, noOfBytes, "ISO-8859-1");
            Log.e("signature======>", "signature===> " + signature);
        } catch (UnsupportedEncodingException e) {
            Log.e("Exception", "Decoding Signature of QRcode, ISO-8859-1 not supported: " + e.toString());
            // throw new QrCodeException("Decoding Signature of QRcode, ISO-8859-1 not supported",e);
        }

    }
    protected void decodeData(List<byte[]> encodedData) throws ParseException {
        Iterator<byte[]> i = encodedData.iterator();
        decodedData = new ArrayList<String>();
        while (i.hasNext()) {
            decodedData.add(new String(i.next(), StandardCharsets.ISO_8859_1));
        }
        Log.e("Parts======2======> ", "part data =====> " + decodedData.toString());

        Log.d("part data =====> ", "Parts======1======> " + decodedData.get(1));
        Log.d("part data =====> ", "Parts======2======> " + decodedData.get(2));
        Log.d("part data =====> ", "Parts======3======> " + decodedData.get(3));
        Log.d("part data =====> ", "Parts======4======> " + decodedData.get(4));
        Log.d("part data =====> ", "Parts======5======> " + decodedData.get(5));
        Log.d("part data =====> ", "Parts======6======> " + decodedData.get(6));
        Log.d("part data =====> ", "Parts======7======> " + decodedData.get(7));
        Log.d("part data =====> ", "Parts======8======> " + decodedData.get(8));
        Log.d("part data =====> ", "Parts======9=====> " + decodedData.get(9));
        Log.d("part data =====> ", "Parts======10=====> " + decodedData.get(10));
        Log.d("part data =====> ", "Parts======11=====> " + decodedData.get(11));
        Log.d("part data =====> ", "Parts======12=====> " + decodedData.get(12));
        Log.d("part data =====> ", "Parts======13=====> " + decodedData.get(13));
        Log.d("part data =====> ", "Parts======14=====> " + decodedData.get(14));
        Log.d("part data =====> ", "Parts======15=====> " + decodedData.get(15));

        int inc = 0;
        Log.d("TAG", "decodeData: " + decodedData.get(0).startsWith("V") + "/////" + decodedData.get(0));
        if (decodedData.get(0).startsWith("V")) {
            inc = 0;
        } else {
            inc = 1;
        }

        String dob = decodedData.get(4 - inc);
        etTextDob.setText(dob);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Log.d("TAG", "decodeData:dob " + dob);

        try {
            Date dateOfBirth = formatter.parse(dob);
            Log.d("TAG", "Parsed date of birth: " + dateOfBirth);
            Calendar dobCalendar = Calendar.getInstance();
            dobCalendar.setTime(dateOfBirth);
            Calendar todayCalendar = Calendar.getInstance();
            int age = todayCalendar.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);
            if (todayCalendar.get(Calendar.MONTH) < dobCalendar.get(Calendar.MONTH) ||
                    (todayCalendar.get(Calendar.MONTH) == dobCalendar.get(Calendar.MONTH) &&
                            todayCalendar.get(Calendar.DAY_OF_MONTH) < dobCalendar.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }
            Log.d("TAG", "Calculated age: " + age);
            etTextAge.setText(String.valueOf(age));
            isAadharVerified = "Q";
        } catch (ParseException e) {
            Log.e("TAG", "Error parsing date: " + e.getMessage(), e);
        }




/*
        editTextAadhar.setText(decodedData.get(2 - inc));
*/

        if (decodedData.get(3 - inc).equals("") || decodedData.get(3 - inc).equals(null)) {
            etTextName.setEnabled(true);
        } else {
            etTextName.setText(decodedData.get(3 - inc));
            Log.d("TAG", "Parts======name=====>  " + etTextName.getText().toString());
        }
        if (decodedData.get(4 - inc).equals("") || decodedData.get(4 - inc).equals(null)) {
            etTextDob.setEnabled(true);
            Log.d("TAG", "Parts======dobnull=====>  " + etTextDob.getText().toString());
        } else {
            etTextDob.setText(decodedData.get(4 - inc));
            Log.d("TAG", "Parts======dob=====>  " + etTextDob.getText().toString());
        }

        if (decodedData.get(5 - inc).equals("") || decodedData.get(5 - inc).equals(null)) {

            Log.d("TAG", "Parts======rps=====>  " + "null");
        } else {

            Log.d("TAG", "PParts======rps=====>  " + decodedData.get(5 - inc));
        }

        int positionOfGender = 0;
        for (int genPos = 0; genPos < gender_List.size(); genPos++) {
            if (gender_List.get(genPos).toUpperCase().startsWith(decodedData.get(5 - inc))) {
                positionOfGender = genPos;
            }
        }

        spin_gender.setSelection(positionOfGender);

        StringBuilder joinedStringBuilder = new StringBuilder();
        Log.d("TAG", "Value at index " + decodedData.get(8 - inc));
        Log.d("TAG", "Value at index " + decodedData.get(9 - inc));
        Log.d("TAG", "Value at index " + decodedData.get(10 - inc));
        Log.d("TAG", "Value at index " + decodedData.get(12 - inc));
        Log.d("TAG", "Value at index " + decodedData.get(14 - inc));
        Log.d("TAG", "Value at index " + decodedData.get(15 - inc));

        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(8 - inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(9 - inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(10 - inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(12 - inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(14 - inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(15 - inc));

        String joinedString = joinedStringBuilder.toString();
        Log.d("TAG", "Value at indexsss: " + joinedString);

        boolean changesMade;
        do {
            String previousString = joinedString;
            joinedString = joinedString.replaceAll(",+", ",");
            changesMade = !previousString.equals(joinedString);
        } while (changesMade);

        if (!joinedString.contains(",")) {
            etTextAddress1.setText(joinedString);
        } else {
            String[] separatedStrings = joinedString.split(",");
            int totalStrings = separatedStrings.length;
            if (totalStrings < 3) {
                etTextAddress1.setText(concatenateStrings(separatedStrings, 0, 1));
                etTextAddress2.setText(concatenateStrings(separatedStrings, 1, 2));
            } else {
                int stringsInA = totalStrings / 3;
                int stringsInB = (totalStrings - stringsInA) / 2;
                int stringsInC = totalStrings - stringsInA - stringsInB;

                etTextAddress1.setText(concatenateStrings(separatedStrings, 0, stringsInA));
                etTextAddress2.setText(concatenateStrings(separatedStrings, stringsInA, stringsInA + stringsInB));
                etTextAddress3.setText(concatenateStrings(separatedStrings, stringsInA + stringsInB, totalStrings));
            }
        }

        if (decodedData.get(7 - inc).equals("") || decodedData.get(7 - inc).equals(null)) {
            etTextCity.setEnabled(true);
        } else {
            etTextCity.setText(decodedData.get(7 - inc));
        }

        if (decodedData.get(11 - inc).equals("") || decodedData.get(11 - inc).equals(null)) {
        } else {
            etTextPincode.setText(decodedData.get(11 - inc));
        }

        if (decodedData.get(13 - inc).equals("") || decodedData.get(13 - inc).equals(null)) {

            Log.d("TAG", "Parts======rps_state=====>  " + "null");
        } else {

            Log.d("TAG", "PParts======rps-state=====>  " + decodedData.get(13 - inc));
        }

        String state = decodedData.get(13 - inc);
        Log.d("TAG", "decodeData:state "+state);
        int statePosition=0;
        for (int statePos=0;statePos<stateDataList.size();statePos++){
            if (stateDataList.get(statePos).descriptionEn.equals(state)){
                statePosition=statePos;
            }

        }
        spin_state.setSelection(statePosition);


        String relation = decodedData.get(6 - inc);
        Log.d("TAG", "decodeData:acspRelationship "+relation);

        if (relation.startsWith("S/O:") ||relation.startsWith("S/O ") || relation.startsWith("D/O:")) {
            Utils.setSpinnerPosition1(spin_relationwithborr, "Father", false);
            spin_relationwithborr.setEnabled(false);

        } else if (relation.startsWith("W/O:")) {
            Utils.setSpinnerPosition1(spin_relationwithborr, "Husband", false);
            spin_relationwithborr.setEnabled(false);

        }



        if (decodedData.get(6 - inc).equals("") || decodedData.get(6 - inc).equals(null)) {

        } else {
            if (decodedData.get(6 - inc).startsWith("S/O:") || decodedData.get(6 - inc).startsWith("D/O:") || decodedData.get(6 - inc).startsWith("W/O:")) {
                etTextGuardian.setText(decodedData.get(6 - inc).split(":")[1].replace("S/O:", "").replace("D/O:", "").replace("W/O:", "").replace("S/O", "").replace("D/O", "").replace("\"", "").replace("W/O", "").trim());
                if (decodedData.get(6 - inc).toUpperCase().startsWith("W/O:")) {
                    Utils.setSpinnerPosition(spin_relationwithborr, "Married", false);
                    String[] spouseName = decodedData.get(6 - inc).split(" ");
                   /* switch (spouseName.length) {
                        case 2:
                            editTextspousefirstname.setText(spouseName[1]);
                            break;
                        case 3:
                            editTextspousefirstname.setText(spouseName[1]);
                            editTextspouselastname.setText(spouseName[2]);
                            break;
                        case 4:
                            editTextspousefirstname.setText(spouseName[1]);
                            editTextspousemiddlename.setText(spouseName[2]);
                            editTextspouselastname.setText(spouseName[3]);
                            break;
                        default:
                            editTextmotherfirstname.setText(decodedData.get(6 - inc));
                            break;
                    }*/

                }
            } else if (decodedData.get(6 - inc).startsWith("S/O,") || decodedData.get(6 - inc).startsWith("D/O,") || decodedData.get(6 - inc).startsWith("W/O,")) {
                etTextGuardian.setText(decodedData.get(6 - inc).split(",")[1].trim());
                Log.d("TAG", "Parts======guardian=====>  " + etTextGuardian.getText().toString());

            } else {
                etTextGuardian.setText(decodedData.get(6 - inc).replace("S/O:", "").replace("D/O:", "").replace("W/O:", "").replace("S/O", "").replace("D/O", "").replace("W/O", "").replace("\"", ""));

            }
            if (decodedData.get(6 - inc).startsWith("S/O:") || decodedData.get(6 - inc).startsWith("D/O:") || decodedData.get(6 - inc).startsWith("W/O:")) {
                etTextGuardian.setText(decodedData.get(6 - inc).split(":")[1].replace("S/O:", "").replace("D/O:", "").replace("W/O:", "").replace("S/O", "").replace("D/O", "").replace("\"", "").replace("W/O", "").trim());
               /* if (decodedData.get(6 - inc).toUpperCase().startsWith("W/O:")) {
                    Utils.setSpinnerPosition(isMarriedSpinner, "Married", false);
                    String[] spouseName = decodedData.get(6 - inc).split(" ");
                    switch (spouseName.length) {
                        case 2:
                            editTextspousefirstname.setText(spouseName[1]);
                            break;
                        case 3:
                            editTextspousefirstname.setText(spouseName[1]);
                            editTextspouselastname.setText(spouseName[2]);
                            break;
                        case 4:
                            editTextspousefirstname.setText(spouseName[1]);
                            editTextspousemiddlename.setText(spouseName[2]);
                            editTextspouselastname.setText(spouseName[3]);
                            break;
                        default:
                            editTextspouselastname.setText(decodedData.get(6 - inc));
                            break;
                    }

                }*/
            } else if (decodedData.get(6 - inc).startsWith("S/O,") || decodedData.get(6 - inc).startsWith("D/O,") || decodedData.get(6 - inc).startsWith("W/O,")) {
                etTextGuardian.setText(decodedData.get(6 - inc).split(",")[1].trim());
            } else {
                etTextGuardian.setText(decodedData.get(6 - inc).replace("S/O:", "").replace("D/O:", "").replace("W/O:", "").replace("S/O", "").replace("D/O", "").replace("W/O", "").replace("\"", ""));

            }
            if (decodedData.get(6 - inc).startsWith("S/O") || decodedData.get(6 - inc).startsWith("D/O")) {
                Utils.setSpinnerPosition(spin_relationwithborr, "Father", false);
                spin_relationwithborr.setEnabled(false);
               /* String[] fatherNames = decodedData.get(6 - inc).contains(":") ? decodedData.get(6 - inc).split(": ") : decodedData.get(6 - inc).split("/O");
                String[] newFatherName = fatherNames[1].split(" ");
                if (newFatherName.length > 2) {
                    String fatherFirstName = "";
                    for (int a = 1; a < newFatherName.length - 1; a++) {
                        fatherFirstName = fatherFirstName + " " + newFatherName[a];
                    }
                    editTextFatherFname.setText(fatherFirstName.trim());
                    Log.d("TAG", "Parts======fatherName=====>  " + editTextFatherFname.getText().toString());

                    editTextFatherFname.setEnabled(false);
                    editTextfatherlastname.setText(newFatherName[newFatherName.length - 1].trim());
                    editTextfatherlastname.setEnabled(false);

                } else {
                    if (decodedData.get(6 - inc).contains(":")) {

                        editTextFatherFname.setText(decodedData.get(6 - inc).split(":")[1].trim());
                        editTextFatherFname.setEnabled(false);
                    } else {
                        editTextFatherFname.setText(decodedData.get(6 - inc).split("/O")[1].trim());
                        editTextFatherFname.setEnabled(false);
                    }

                }*/
            } else if (decodedData.get(6 - inc).startsWith("W/O")) {
                Utils.setSpinnerPosition(spin_relationwithborr, "Husband", false);
                spin_relationwithborr.setEnabled(false);
              /*  String[] spouseNames = decodedData.get(6 - inc).contains(":") ? decodedData.get(6 - inc).split(":") : decodedData.get(6 - inc).split("/O");
                String[] newSpouseName = spouseNames[1].split(" ");
                if (newSpouseName.length > 2) {
                    String spouseFirstName = "";
                    for (int a = 1; a < newSpouseName.length - 1; a++) {
                        spouseFirstName = spouseFirstName + " " + newSpouseName[a];
                    }
                    editTextspousefirstname.setText(spouseFirstName.trim());
                    editTextspousefirstname.setEnabled(false);
                    editTextfatherlastname.setText(newSpouseName[newSpouseName.length - 1].trim());
                    editTextspouselastname.setEnabled(false);

                } else {

                    if (decodedData.get(6 - inc).contains(":")) {
                        editTextspousefirstname.setText(decodedData.get(6 - inc).split(":")[1].trim());
                        editTextspousefirstname.setEnabled(false);
                    } else {
                        editTextfatherlastname.setText(decodedData.get(6 - inc).split("/O")[1].trim());
                        editTextfatherlastname.setEnabled(false);
                    }

                }*/

            }
        }

    }
    private String concatenateStrings(String[] strings, int startIndex, int endIndex) {
        StringBuilder result = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                result.append(",");
            }
            result.append(strings[i]);
        }
        return result.toString();
    }
    private void appendIfNotNullOrEmpty(StringBuilder stringBuilder, String str) {
        if (str != null && !str.isEmpty()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(str);
        }
    }
    protected byte[] decompressData(byte[] byteScanData) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(byteScanData.length);
        ByteArrayInputStream bin = new ByteArrayInputStream(byteScanData);
        GZIPInputStream gis = null;

        try {
            gis = new GZIPInputStream(bin);
        } catch (IOException e) {
            Log.e("Exception", "Decompressing QRcode, Opening byte stream failed: " + e.toString());
            // throw new QrCodeException("Error in opening Gzip byte stream while decompressing QRcode",e);
        }

        int size = 0;
        byte[] buf = new byte[1024];
        while (size >= 0) {
            try {
                size = gis.read(buf, 0, buf.length);
                if (size > 0) {
                    bos.write(buf, 0, size);
                }
            } catch (IOException e) {
                Log.e("Exception", "Decompressing QRcode, writing byte stream failed: " + e.toString());
                // throw new QrCodeException("Error in writing byte stream while decompressing QRcode",e);
            }
        }

        try {
            gis.close();
            bin.close();
        } catch (IOException e) {
            Log.e("Exception", "Decompressing QRcode, closing byte stream failed: " + e.toString());
            // throw new QrCodeException("Error in closing byte stream while decompressing QRcode",e);
        }

        return bos.toByteArray();
    }
    protected List<byte[]> separateData(byte[] source) {
        List<byte[]> separatedParts = new LinkedList<>();
        int begin = 0;

        for (int i = 0; i < source.length; i++) {
            if (source[i] == SEPARATOR_BYTE) {
                if (i != 0 && i != (source.length - 1)) {
                    separatedParts.add(Arrays.copyOfRange(source, begin, i));
                }
                begin = i + 1;
                if (separatedParts.size() == (VTC_INDEX + 1)) {
                    imageStartIndex = begin;
                    break;
                }
            }
        }
        return separatedParts;
    }
    private void setprofileImage(File profileImageFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(profileImageFile.getAbsolutePath());
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        gurProfilePic.setImageDrawable(drawable);
    }
  /*  public File bitmapToFile(Bitmap bitmap) {
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
        */
  /*cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);*/
  /*
        cropIntent.putExtra("scale", true); // Add this line to prevent rotation
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }
*/
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
        jsonGuarantor.addProperty("dob",guarantor.getDob());
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
        jsonGuarantor.addProperty("grNo", guarantor.getGrNo());
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
