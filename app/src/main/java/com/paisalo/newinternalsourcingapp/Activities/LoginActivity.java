package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.BtnSaveKYCData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get values from EditText fields
                String username = binding.etLoginUsername.getText().toString();
                String password = binding.etLoginPassword.getText().toString();

                if (isValidUsername(username) && isValidPassword(password)) {
                    // If validations pass, start the new activity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {

                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 1;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 5;
    }
}
