package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import android.app.DatePickerDialog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.paisalo.newinternalsourcingapp.R;

import java.util.Calendar;

public class KYCActivity extends AppCompatActivity {

    EditText editTextAadhar, editTextName, editTextAge, editTextDob, editTextGender, editTextGuardian,
            editTextRealationshipwithBorrower, editTextAddress1, editTextAddress2, editTextAddress3,
            editTextCity, editTextPincode, editTextStateName, editTextMobile, editTextPAN, editTextdrivingLicense,
            editTextvoterIdKyc,editTextmotherfirstname, editTextmothermiddlename, editTextmotherlastname, editTextfatherfirstname, editTextfathermiddlename,
            editTextfatherlastname, editTextmaritalstatus, editTextspousefirstname, editTextspousemiddlename, editTextspouselastname;

    String aadhaarId,name,age,dob,gender,guardian,relationshipWithBorrower,address1,address2,address3,city,pin,state,mobile,pan,drivingLicense,voterId,motherFirstName,motherMiddleName,motherLastName,fatherFirstName,fatherMiddleName,fatherLastName,maritalStatus,spouseFirstName,spouseMiddleName,spouseLastName;
    Button submitButton;
    ImageView aadhaarScanner, calendericon, Gendericon;

    private ProgressBar progressBar;
    private int maxProgress = 26;

    private EditText[] editTexts;

    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kycactivity);

        progressBar = findViewById(R.id.simpleProgressBar);
        progressBar.setMax(maxProgress);
        aadhaarScanner = findViewById(R.id.aadhaarScanner);
        calendericon = findViewById(R.id.calendericonKyc);
        Gendericon = findViewById(R.id.Gendericon);
        submitButton = findViewById(R.id.submitButtonKyc);

        editTextAadhar = findViewById(R.id.editTextAadharKyc);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAgeKYC);
        editTextDob = findViewById(R.id.editTextDobKyc);
        editTextGender = findViewById(R.id.editTextGender);
        editTextGuardian = findViewById(R.id.editTextGuardianKyc);
        editTextRealationshipwithBorrower = findViewById(R.id.editTextRealationshipwithBorrower);
        editTextAddress1 = findViewById(R.id.editTextAddress1Kyc);
        editTextAddress2 = findViewById(R.id.editTextAddress2Kyc);
        editTextAddress3 = findViewById(R.id.editTextAddress3Kyc);
        editTextCity = findViewById(R.id.editTextCityKyc);
        editTextPincode = findViewById(R.id.editTextPincodeKyc);
        editTextStateName = findViewById(R.id.editTextStateName);
        editTextMobile = findViewById(R.id.editTextMobileKyc);
        editTextPAN = findViewById(R.id.editTextPANKyc);
        editTextdrivingLicense = findViewById(R.id.drivingLicenseKyc);
        editTextvoterIdKyc = findViewById(R.id.voterIdKyc);
        editTextmotherfirstname = findViewById(R.id.motherfirstnameKyc);
        editTextmothermiddlename = findViewById(R.id.mothermiddlenameKyc);
        editTextmotherlastname = findViewById(R.id.motherlastnameKyc);
        editTextfatherfirstname = findViewById(R.id.fatherfirstnameKyc);
        editTextfathermiddlename = findViewById(R.id.fathermiddlenameKyc);
        editTextfatherlastname = findViewById(R.id.fatherlastnameKyc);
        editTextmaritalstatus = findViewById(R.id.maritalstatusKyc);
        editTextspousefirstname = findViewById(R.id.spousefirstnameKyc);
        editTextspousemiddlename = findViewById(R.id.spousemiddlenameKyc);
        editTextspouselastname = findViewById(R.id.spouselastnameKyc);

        // diasable
        editTextAge.setEnabled(false);
        editTextDob.setEnabled(false);
        editTextGender.setEnabled(false);

        //edittext array for Progressbar
        editTexts = new EditText[]{
                editTextAadhar, editTextName, editTextAge, editTextGender, editTextGuardian,
                editTextRealationshipwithBorrower, editTextAddress1, editTextAddress2, editTextAddress3,
                editTextCity, editTextPincode, editTextStateName, editTextMobile, editTextPAN, editTextdrivingLicense,
                editTextmotherfirstname, editTextmothermiddlename, editTextmotherlastname, editTextfatherfirstname, editTextfathermiddlename,
                editTextfatherlastname, editTextmaritalstatus, editTextspousefirstname, editTextspousemiddlename, editTextspouselastname
        };
        for (final EditText editText : editTexts) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        if (isEmpty(editText)) {
                            progressBar.incrementProgressBy(-1);
                        } else {
                            progressBar.incrementProgressBy(1);
                        }
                    }
                }
            });
        }

        aadhaarScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KYCActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });

        calendar = Calendar.getInstance();

        calendericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Gendericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderMenu(v, editTextGender);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new GetEditTextData().execute();

            }
        });

    }//On Create Close

    private class GetEditTextData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            aadhaarId = editTextAadhar.getText().toString();
            name = editTextName.getText().toString();
            age = editTextAge.getText().toString();
            dob = editTextDob.getText().toString();
            gender = editTextGender.getText().toString();
            guardian = editTextGuardian.getText().toString();
            relationshipWithBorrower = editTextRealationshipwithBorrower.getText().toString();
            address1 = editTextAddress1.getText().toString();
            address2 = editTextAddress2.getText().toString();
            address3 = editTextAddress3.getText().toString();
            city = editTextCity.getText().toString();
            pin = editTextPincode.getText().toString();
            state = editTextStateName.getText().toString();
            mobile = editTextMobile.getText().toString();
            pan = editTextPAN.getText().toString();
            drivingLicense = editTextdrivingLicense.getText().toString();
            voterId = editTextvoterIdKyc.getText().toString();
            motherFirstName = editTextmotherfirstname.getText().toString();
            motherMiddleName = editTextmothermiddlename.getText().toString();
            motherLastName = editTextmotherlastname.getText().toString();
            fatherFirstName = editTextfatherfirstname.getText().toString();
            fatherMiddleName = editTextfathermiddlename.getText().toString();
            fatherLastName = editTextfatherlastname.getText().toString();
            maritalStatus = editTextmaritalstatus.getText().toString();
            spouseFirstName = editTextspousefirstname.getText().toString();
            spouseMiddleName = editTextspousemiddlename.getText().toString();
            spouseLastName = editTextspouselastname.getText().toString();

            Intent intent = new Intent(KYCActivity.this, KYCActivity2.class);

            intent.putExtra("aadhaarId", aadhaarId);
            intent.putExtra("name", name);
            intent.putExtra("age", age);
            intent.putExtra("dob", dob);
            intent.putExtra("gender", gender);
            intent.putExtra("guardian", guardian);
            intent.putExtra("relationshipWithBorrower", relationshipWithBorrower);
            intent.putExtra("address1", address1);
            intent.putExtra("address2", address2);
            intent.putExtra("address3", address3);
            intent.putExtra("city", city);
            intent.putExtra("pin", pin);
            intent.putExtra("state", state);
            intent.putExtra("mobile", mobile);
            intent.putExtra("voterId", voterId);
            intent.putExtra("pan", pan);
            intent.putExtra("drivingLicense", drivingLicense);
            intent.putExtra("motherFirstName", motherFirstName);
            intent.putExtra("motherMiddleName", motherMiddleName);
            intent.putExtra("motherLastName", motherLastName);
            intent.putExtra("fatherFirstName", fatherFirstName);
            intent.putExtra("fatherMiddleName", fatherMiddleName);
            intent.putExtra("fatherLastName", fatherLastName);
            intent.putExtra("maritalStatus", maritalStatus);
            intent.putExtra("spouseFirstName", spouseFirstName);
            intent.putExtra("spouseMiddleName", spouseMiddleName);
            intent.putExtra("spouseLastName", spouseLastName);
            startActivity(intent);
            return null;
        }
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(KYCActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                editTextDob.setText(selectedDate);
                calculateAge(selectedYear, selectedMonth, selectedDay);
                progressBar.incrementProgressBy(1);
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

        EditText ageEditText = findViewById(R.id.editTextAgeKYC);
        ageEditText.setText(String.valueOf(age));
    }

    private void showGenderMenu(View view, final EditText editTextGender) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_gender, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_male:
                        editTextGender.setText("Male");
                        progressBar.incrementProgressBy(1);
                        return true;
                    case R.id.menu_female:
                        editTextGender.setText("Female");
                        progressBar.incrementProgressBy(1);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    public boolean validate() {
        return true;
    }

}

