package com.paisalo.newinternalsourcingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

import java.util.regex.Pattern;

public class GlobalClass extends Application {

    public static String Id = "";
    public static String Creator = "";
    public static String Address = "";
    public static String UserName = "";
    public static String Token = "";
    public static String Imei = "";
    public static String DevId = "";
    public static String dbname = "yfMerfC6mRvfr0AOoHmOJ8Et9Q9MPwNEKzFdLsfEs1A=";


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

    public static boolean isValidName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        // Regex pattern to match alphabets with optional spaces and hyphens
        String regex = "^[a-zA-Z.]+(?:[\\s'-][a-zA-Z]+)*$";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Match the input string against the pattern
        return pattern.matcher(name).matches();
    }

    public static boolean isValidAddr(String addr) {
        if (addr.isEmpty()) {
            return false;
        }
        // Regex pattern to match alphabets with optional spaces and hyphens
        String regex = "^[a-zA-Z.]+(?:[\\s'-][a-zA-Z]+)*$";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Match the input string against the pattern
        return pattern.matcher(addr).matches();
    }

    public static boolean isNumber(String input) {

        if (input.isEmpty()) {
            return false;
        }
        // Regex pattern to match numeric digits only
        String regex = "^[0-9]+$";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Match the input string against the pattern
        return pattern.matcher(input).matches();
    }

    public static boolean isValidPan(String pan) {
        // Check if the input string is not empty
        if (pan.isEmpty()) {
            return false;
        }

        // Regex pattern for PAN validation
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Match the input string against the pattern
        return pattern.matcher(pan).matches();
    }



}
