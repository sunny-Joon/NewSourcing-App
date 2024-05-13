package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.paisalo.newinternalsourcingapp.Adapters.CustomAdapter;
import com.paisalo.newinternalsourcingapp.R;

public class SecondEsignActivity extends AppCompatActivity {


    ListView lvLoanDetails;
    String[] loanDetailsArray = {"Loan 1", "Loan 2"};
    Button btnLoanDetailsDownloadDoc;


    boolean isListVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_esign);

        btnLoanDetailsDownloadDoc = findViewById(R.id.btnLoanDetailsDownloadDoc);
        lvLoanDetails = findViewById(R.id.lvLoanDetails);

//        lvLoanDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SecondEsignActivity.this, FirstEsignActivity.class);
//                 startActivity(intent);
//            }
//        });

      //  lvLoanDetails.setLayoutManager(new LinearLayoutManager(this));

        CustomAdapter adapter = new CustomAdapter(this, R.layout.layout_esigner, loanDetailsArray);
        lvLoanDetails.setAdapter(adapter);
    }


}
