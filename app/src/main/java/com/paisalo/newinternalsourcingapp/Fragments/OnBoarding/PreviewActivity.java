package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.paisalo.newinternalsourcingapp.R;

public class PreviewActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        imageView = findViewById(R.id.imagePreview);

        String imageUriString = getIntent().getStringExtra("imageUri");

        Uri imageUri = Uri.parse(imageUriString);

        imageView.setImageURI(imageUri);
    }
}