package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.paisalo.newinternalsourcingapp.R;

public class HouseVisitActivity1 extends AppCompatActivity {

    Button form;
    CheckBox confirmationBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_visit1);

        form = findViewById(R.id.fillForm);
        confirmationBox = findViewById(R.id.confirmationBox);

        form.setEnabled(false);
        form.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));

        confirmationBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                form.setEnabled(isChecked);
                int colorRes = isChecked ? R.color.white : R.color.grey;
                form.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorRes)));
            }
        });


        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseVisitActivity1.this,HouseVisitActivity2.class);
                startActivity(intent);
            }
        });

    }
}