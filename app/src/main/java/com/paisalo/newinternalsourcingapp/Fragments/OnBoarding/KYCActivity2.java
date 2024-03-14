package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.paisalo.newinternalsourcingapp.R;

public class KYCActivity2 extends AppCompatActivity {

    EditText monthlyincome,expensemonthly,futureincome,agricultureincome,pensionincome,interestincome,otherincome,
              earningmemberincome,loanincome;

    Spinner earningmembertype,businessdetail,loanpurpose,occuption,loanduration,selectbank;
    KYCDataDao kycDataDao;
    AppDatabase appDatabase;

    String income;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kycactivity2);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "SEILESign2").allowMainThreadQueries().build();
        kycDataDao = appDatabase.kycDataDao();

        Button savedata = findViewById(R.id.savedata);

        monthlyincome = findViewById(R.id.monthlyincome);
        expensemonthly = findViewById(R.id.expensemonthly);
        futureincome = findViewById(R.id.futureincome);
         agricultureincome = findViewById(R.id.agricultureincome);
         pensionincome = findViewById(R.id.pensionincome);
         interestincome = findViewById(R.id.interestincome);
         otherincome = findViewById(R.id.otherincome);
         earningmemberincome = findViewById(R.id.earningmemberincome);
         loanincome = findViewById(R.id.loanincome);

         earningmembertype = findViewById(R.id.earningmembertype);
         businessdetail = findViewById(R.id.businessdetail);
         loanpurpose = findViewById(R.id.loanpurpose);
         occuption = findViewById(R.id.occuption);
         loanduration = findViewById(R.id.loanduration);
         selectbank = findViewById(R.id.selectbank);


        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                income = monthlyincome.getText().toString();
                insertDataToDatabase();
            }
        });


    }

        private void insertDataToDatabase() {

            income = monthlyincome.getText().toString();

            KYCData2 kycData2 = new KYCData2();

            kycData2.monthlyincome = monthlyincome.getText().toString();
            kycData2.expencemonthly = expensemonthly.getText().toString();
            kycData2.futureincomeannual = futureincome.getText().toString();
            kycData2.agricultureincomemonthky = futureincome.getText().toString();
            kycData2.pensionincome = pensionincome.getText().toString();
            kycData2.interestincome = interestincome.getText().toString();
            kycData2.otherincome = otherincome.getText().toString();
            // kycData2.earningmembertype = earningmembertype.getText().toString();
            kycData2.earningmemberincome = earningmemberincome.getText().toString();
            kycData2.loanamount = loanincome.getText().toString();
            //kycData2.businessdetail = businessdetail.getText().toString();
            // kycData2.loanpuose = loanpurpose.getText().toString();

            // kycData2.occuption = occuption.getText().toString();
            // kycData2.loandurationmonths = loanduration.getText().toString();

            //kycData2.selectbank = selectbank.getText().toString();


            kycDataDao.insert(kycData2);

            // Log for verification

            Log.d(TAG, "insertDataToDatabase: Data inserted into Room Database");


    }
}

