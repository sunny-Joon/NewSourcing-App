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

    EditText editTextAadhar,editTextName,editTextAge,editTextDob, editTextGender,editTextGuardian,
            editTextRealationshipwithBorrower,editTextAddress1,editTextAddress2,editTextAddress3,
            editTextCity,editTextPincode ,editTextStateName,editTextMobile,editTextPAN,drivingLicense,
            motherfirstname,mothermiddlename,motherlastname,fatherfirstname,fathermiddlename,
            fatherlastname,maritalstatus,spousefirstname,spousemiddlename, spouselastname;

    Button submitButton;
    ImageView imageView3,calendericon,Gendericon;

    private ProgressBar progressBar;
    private int maxProgress = 26;

    private EditText[] editTexts;


    private Calendar calendar;

    private AppDatabase appDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kycactivity);

//        Bitmap originalBitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.abc123,800,600);
//
//        // Apply blur effect
//        Bitmap blurredBitmap = BlurUtils.blurBitmap(this, originalBitmap, 2); // Adjust blur radius as needed


        progressBar = findViewById(R.id.simpleProgressBar);
        progressBar.setMax(maxProgress);
        imageView3 = findViewById(R.id.imageView3);
        calendericon = findViewById(R.id.calendericon);
        Gendericon = findViewById(R.id.Gendericon);




        submitButton = findViewById(R.id.button);
        editTextAadhar = findViewById(R.id.editTextAadhar);
        editTextName =  findViewById(R.id.editTextName);
        editTextAge=findViewById(R.id.editTextAge);
        editTextDob = findViewById(R.id.editTextDob);
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

        appDatabase = AppDatabase.getDatabase(this);



        // Populate the array with EditText objects
        editTexts = new EditText[]{
                editTextAadhar, editTextName, editTextAge, editTextGender, editTextGuardian,
                editTextRealationshipwithBorrower, editTextAddress1, editTextAddress2, editTextAddress3,
                editTextCity, editTextPincode, editTextStateName, editTextMobile, editTextPAN, drivingLicense,
                motherfirstname, mothermiddlename, motherlastname, fatherfirstname, fathermiddlename,
                fatherlastname, maritalstatus, spousefirstname, spousemiddlename, spouselastname
                // ... (add references to all other EditText fields here)
        };

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                insertDataToDatabase();
                Intent intent = new Intent(KYCActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });


// diasable
        editTextAge.setEnabled(false);
        editTextDob.setEnabled(false);
        editTextGender.setEnabled(false);
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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(KYCActivity.this, KYCActivity2.class);
                startActivity(intent);

                //                if (validate()) {
//                    showPopupWithBlur();
//                    // Perform action after successful validation
//                }
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

   /* private void submitData() {
        showToast("Data submitted successfully.");
    }*/




//    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }
//
//    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//        return inSampleSize;
//    }


    private void showPopupWithBlur() {


    }



    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }


//    private void insertDataToDatabase() {
//        // Create a KYCData object and set values from EditText fields
//        KYCData kycData = new KYCData();
//        kycData.Aadhar = editTextAadhar.getText().toString();
//        kycData.Name = editTextName.getText().toString();
//        // ... set other fields
//
//        // Insert the KYCData object into the Room Database
//        new InsertDataTask().execute(kycData);
//    }
//
//    // AsyncTask to perform database insertion in the background
//    private static class InsertDataTask extends AsyncTask<KYCData, Void, Void> {
//        @Override
//        protected Void doInBackground(KYCData... kycData) {
//            AppDatabase.getDatabase().kycDataDao()
//            return null;
//        }
//    }


    public boolean validate() {

        int flagaadhar = 0;
        int flagName = 0;
        int flagAge=0;
        int flagDob=0;
        int  flagGender=0;
        int flagGuardian=0;
        int flagRealationshipwithBorrower=0;
        int flagAddress1=0;
        int flagAddress2=0;
        int flagAddress3=0;
        int flagCity=0;
        int flagPinCode=0;
        int  flagStatename=0;
        int  flagMobile=0;
        int  flagPan=0;
        int flagdrivingLicense=0;
        int flagmotherfirstname=0;
        int  flagmothermiddlename=0;
        int  flagmotherlastname=0;
        int  flagfatherfirstname=0;
        int  flagfathermiddlename=0;
        int flagfatherlastname=0;
        int flagmaritalstatus=0;
        int flagspousefirstname=0;
        int flagspousemiddlename=0;
        int flagspouselastname=0;


        if (editTextAadhar.getText().toString().isEmpty()) {
            Toast.makeText(this, "Aadhar is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagaadhar = 1;
        }

        if (editTextName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagName = 1;
        }

        if (editTextAge.getText().toString().isEmpty()) {
            Toast.makeText(this, "Age is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagAge= 1;
        }

        if (editTextDob.getText().toString().isEmpty()) {
            Toast.makeText(this, "Dob is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagDob = 1;
        }

        if (editTextGender.getText().toString().isEmpty()) {
            Toast.makeText(this, "Gender is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagGender = 1;
        }

        if (editTextGuardian.getText().toString().isEmpty()) {
            Toast.makeText(this, "Guardian is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagGuardian = 1;
        }

        if (editTextRealationshipwithBorrower.getText().toString().isEmpty()) {
            Toast.makeText(this, "RealationshipwithBorrower is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagRealationshipwithBorrower = 1;
        }

        if (editTextAddress1.getText().toString().isEmpty()) {
            Toast.makeText(this, "Address1 is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagAddress1 = 1;
        }

        if (editTextAddress2.getText().toString().isEmpty()) {
            Toast.makeText(this, "Address2 is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagAddress2 = 1;
        }

        if (editTextAddress3.getText().toString().isEmpty()) {
            Toast.makeText(this, "Address3 is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagAddress3= 1;
        }

        if (editTextCity.getText().toString().isEmpty()) {
            Toast.makeText(this, "City is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagCity= 1;
        }

        if (editTextPincode.getText().toString().isEmpty()) {
            Toast.makeText(this, "PINCode is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagPinCode = 1;
        }

        if (editTextStateName.getText().toString().isEmpty()) {
            Toast.makeText(this, "StateName is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagStatename = 1;
        }

        if (editTextMobile.getText().toString().isEmpty()) {
            Toast.makeText(this, "Mobile is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagMobile = 1;
        }

        if (editTextPAN.getText().toString().isEmpty()) {
            Toast.makeText(this, "Pan is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagPan = 1;
        }

        if (drivingLicense.getText().toString().isEmpty()) {
            Toast.makeText(this, "drivingLicense is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagdrivingLicense = 1;
        }

        if (motherfirstname.getText().toString().isEmpty()) {
            Toast.makeText(this, "motherfirstname is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagmotherfirstname = 1;
        }

        if (mothermiddlename.getText().toString().isEmpty()) {
            Toast.makeText(this, "mothermiddlename is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagmothermiddlename = 1;
        }

        if (motherlastname.getText().toString().isEmpty()) {
            Toast.makeText(this, "motherlastname is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagmotherlastname = 1;
        }

        if (fatherfirstname.getText().toString().isEmpty()) {
            Toast.makeText(this, "fatherfirstname is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagfatherfirstname = 1;
        }

        if (fathermiddlename.getText().toString().isEmpty()) {
            Toast.makeText(this, "fathermiddlename is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagfathermiddlename = 1;
        }

        if (fatherlastname.getText().toString().isEmpty()) {
            Toast.makeText(this, "fatherlastname is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagfatherlastname = 1;
        }

        if (maritalstatus.getText().toString().isEmpty()) {
            Toast.makeText(this, "maritalstatus is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagmaritalstatus = 1;
        }

        if (spousefirstname.getText().toString().isEmpty()) {
            Toast.makeText(this, "spousefirstname is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagspousefirstname = 1;
        }

        if (spousemiddlename.getText().toString().isEmpty()) {
            Toast.makeText(this, "spousemiddlename is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagspousemiddlename = 1;
        }

        if (spouselastname.getText().toString().isEmpty()) {
            Toast.makeText(this, "spouselastname is empty", Toast.LENGTH_SHORT).show();
        } else {
            flagspouselastname = 1;
        }

        // Add similar checks for other fields

        return flagaadhar == 1 && flagName == 1 && flagAge == 1 && flagDob == 1 && flagGender == 1 &&
                flagGuardian == 1 && flagRealationshipwithBorrower == 1 && flagAddress1 == 1 &&
                flagAddress2 == 1 && flagAddress3 == 1 && flagCity == 1 && flagPinCode == 1 &&
                flagStatename == 1 && flagMobile == 1 && flagPan == 1 && flagdrivingLicense == 1 &&
                flagmotherfirstname == 1 && flagmothermiddlename == 1 && flagmotherlastname == 1 &&
                flagfatherfirstname == 1 && flagfathermiddlename == 1 && flagfatherlastname == 1 &&
                flagmaritalstatus == 1 && flagspousefirstname == 1 && flagspousemiddlename == 1 &&
                flagspouselastname == 1;
    }


}
