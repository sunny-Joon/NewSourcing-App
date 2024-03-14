package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.paisalo.newinternalsourcingapp.Adapters.CustomAdapter;
import com.paisalo.newinternalsourcingapp.R;

public class DownloadDocumentActivity extends AppCompatActivity {

    ListView lvLoanDetails;
    String[] loanDetailsArray = {"Loan 1", "Loan 2"};
    Button btnLoanDetailsDownloadDoc;


    boolean isListVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_document);

        btnLoanDetailsDownloadDoc = findViewById(R.id.btnLoanDetailsDownloadDoc);
        lvLoanDetails = findViewById(R.id.lvLoanDetails);

//        lvLoanDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DownloadDocumentActivity.this, ActivityEsignWithDownloadPL.class);
//                 startActivity(intent);
//            }
//        });

      //  lvLoanDetails.setLayoutManager(new LinearLayoutManager(this));

        CustomAdapter adapter = new CustomAdapter(this, R.layout.layout_esigner, loanDetailsArray);
        lvLoanDetails.setAdapter(adapter);
    }


}
