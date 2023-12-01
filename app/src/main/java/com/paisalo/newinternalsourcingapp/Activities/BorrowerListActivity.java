package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.paisalo.newinternalsourcingapp.Adapters.BorrowerListAdapter;
import com.paisalo.newinternalsourcingapp.R;

public class BorrowerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BorrowerListAdapter borrowerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowerlist);

        Intent intent = getIntent();
        String id = intent.getStringExtra("keyName");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.esignRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        borrowerListAdapter = new BorrowerListAdapter(this, id, "Rahul", "Rakul", "021", "7777777777", "AGRA","Shimla");
        recyclerView.setAdapter(borrowerListAdapter);
    }
}
