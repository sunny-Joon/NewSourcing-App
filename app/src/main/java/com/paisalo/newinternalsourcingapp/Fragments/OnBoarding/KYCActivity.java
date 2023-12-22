package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.paisalo.newinternalsourcingapp.R;

import java.util.Calendar;

public class KYCActivity extends AppCompatActivity {

    EditText editTextAadhar,editTextName,editTextAge,editTextDob,editTextGender,editTextGuardian,
            editTextRealationshipwithBorrower,editTextAddress1,editTextAddress2,editTextAddress3,
            editTextCity,editTextPincode ,editTextStateName,editTextMobile,editTextPAN,drivingLicense,
            motherfirstname,mothermiddlename,motherlastname,fatherfirstname,fathermiddlename,
            fatherlastname,maritalstatus,spousefirstname,spousemiddlename, spouselastname;
    Button submitButton;

    private ProgressBar progressBar;
    private int maxProgress = 26;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kycactivity);

        progressBar = findViewById(R.id.simpleProgressBar);
        progressBar.setMax(maxProgress);

        submitButton = findViewById(R.id.button);
        editTextAadhar = findViewById(R.id.editTextAadhar);
        editTextName =  findViewById(R.id.editTextName);
        editTextAge=findViewById(R.id.editTextAge);
        editTextDob=findViewById(R.id.editTextDob);
        editTextGender= findViewById(R.id.editTextGender);
        editTextGuardian= findViewById(R.id.editTextGuardian);
        editTextRealationshipwithBorrower= findViewById(R.id.editTextRealationshipwithBorrower);
        editTextAddress1=findViewById(R.id.editTextAddress1);
        editTextAddress2= findViewById(R.id.editTextAddress2);
        editTextAddress3= findViewById(R.id.editTextAddress3);
        editTextCity=findViewById(R.id.editTextCity);
        editTextPincode= findViewById(R.id.editTextPincode);
        editTextStateName= findViewById(R.id.editTextStateName);
        editTextMobile= findViewById(R.id.editTextMobile);
        editTextPAN= findViewById(R.id.editTextPAN);
        drivingLicense= findViewById(R.id.drivingLicense);
        motherfirstname =   findViewById(R.id.motherfirstname);
        mothermiddlename= findViewById(R.id.mothermiddlename);
        motherlastname= findViewById(R.id.motherlastname);
        fatherfirstname =  findViewById(R.id.fatherfirstname);
        fathermiddlename =findViewById(R.id.fathermiddlename);
        fatherlastname =  findViewById(R.id.fatherlastname);
        maritalstatus =  findViewById(R.id.maritalstatus);
        spousefirstname =  findViewById(R.id.spousefirstname);
        spousemiddlename =  findViewById(R.id.spousemiddlename);
        spouselastname = findViewById(R.id.spouselastname);


        calendar = Calendar.getInstance();

        editTextDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        editTextGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderMenu(v, editTextGender);
            }
        });

        submitButton = findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){
                    showPopupWithBlur();
                }

            }
        });

    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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

    private void calculateAge(int selectedYear, int selectedMonth, int selectedDay) {
        Calendar dobCalendar = Calendar.getInstance();
        dobCalendar.set(selectedYear, selectedMonth, selectedDay);

        Calendar currentCalendar = Calendar.getInstance();

        int age = currentCalendar.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);

        if (currentCalendar.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        EditText ageEditText = findViewById(R.id.editTextAge);
        ageEditText.setText(String.valueOf(age));
    }

    private void showPopupWithBlur() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.DECOR_CAPTION_SHADE_LIGHT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    public boolean validate() {
        int flagAadhar = 0;
        int flagName = 0;
        int flagAge=0;

        if (editTextAadhar.getText().toString().isEmpty()) {
            Toast.makeText(this, "Aadhar is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagAadhar = 1;
        }

        if (editTextName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagName = 1;
        }

        if (editTextAge.getText().toString().isEmpty()) {
            Toast.makeText(this, "Age is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagName = 1;
        }

        // Add similar checks for other fields

        return flagAadhar == 1 && flagName == 1 && flagAge ==1;
    }

}