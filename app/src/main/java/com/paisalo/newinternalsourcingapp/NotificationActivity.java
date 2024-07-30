package com.paisalo.newinternalsourcingapp;
import android.app.Activity;
import android.os.Bundle;

public class NotificationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make the activity transparent
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Finish the activity immediately after creation
        finish();
    }
}
