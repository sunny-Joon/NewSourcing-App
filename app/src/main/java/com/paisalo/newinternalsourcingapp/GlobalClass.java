package com.paisalo.newinternalsourcingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;

public class GlobalClass extends Application {

    public static String Id ="";
    public static String Creator ="";
    public static String Address ="";
    public static String UserName ="";
    public static String Token ="";
    public static String Imei ="";
    public static String DevId ="";
    public static String dbname ="yfMerfC6mRvfr0AOoHmOJ8Et9Q9MPwNEKzFdLsfEs1A=";

    public static void SubmitAlert(final Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Finish the activity
                activity.finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


}
