package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

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

        imageView = findViewById(R.id.imagePreview); // Initialize your ImageView

        // Retrieve the image URI from the Intent
        String imageUriString = getIntent().getStringExtra("imageUri");

        // Convert the URI string back to a URI
        Uri imageUri = Uri.parse(imageUriString);

        // Set the image URI to the ImageView to display it
        imageView.setImageURI(imageUri);
    }
}