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
    public static String AreaCode = "";
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
                activity.finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public static boolean isValidFullName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z. ]+(?:[\\s'-][a-zA-Z]+)*$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(name).matches();
    }

    public static boolean isValidName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z.]+(?:[\\s'-][a-zA-Z]+)*$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(name).matches();
    }
    public static boolean isValidMName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return true;
        }

        String regex = "^[a-zA-Z.]+(?:[\\s'-][a-zA-Z ]+)*$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(name).matches();
    }

    public static boolean isValidAddr(String input) {
        if (input.isEmpty()) {
            return false;
        }
        String allowedCharactersRegex = "[a-zA-Z0-9 ,:./\\-()]+";

        return (input.matches(allowedCharactersRegex) && !(input.startsWith(".") || input.startsWith(":") || input.startsWith("/") || input.startsWith("-") || input.startsWith(",")));
    }

    public static boolean isValidSAddr(String input) {
        if (input == null || input.trim().isEmpty()) {
            return true;
        }
        String allowedCharactersRegex = "[a-zA-Z0-9 ,:./\\-()]+";
        return (input.matches(allowedCharactersRegex) && !(input.startsWith(".") || input.startsWith(":") || input.startsWith("/") || input.startsWith("-") || input.startsWith(",")));
    }


    public static boolean isNumber(String input) {

        if (input.isEmpty()) {
            return false;
        }
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input).matches();
    }

    public static boolean isValidPan(String pan) {
        if (pan.isEmpty()) {
            return false;
        }

        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(pan).matches();
    }

}
