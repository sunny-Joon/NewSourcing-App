package com.paisalo.newinternalsourcingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.regex.Matcher;
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


    static int[][] d = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            {1, 2, 3, 4, 0, 6, 7, 8, 9, 5},
            {2, 3, 4, 0, 1, 7, 8, 9, 5, 6},
            {3, 4, 0, 1, 2, 8, 9, 5, 6, 7},
            {4, 0, 1, 2, 3, 9, 5, 6, 7, 8},
            {5, 9, 8, 7, 6, 0, 4, 3, 2, 1},
            {6, 5, 9, 8, 7, 1, 0, 4, 3, 2},
            {7, 6, 5, 9, 8, 2, 1, 0, 4, 3},
            {8, 7, 6, 5, 9, 3, 2, 1, 0, 4},
            {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}};

    // The permutation table
    static int[][] p = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            {1, 5, 7, 6, 2, 8, 3, 0, 9, 4},
            {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
            {8, 9, 1, 6, 0, 4, 3, 5, 2, 7},
            {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
            {4, 2, 8, 6, 5, 7, 3, 9, 0, 1},
            {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
            {7, 0, 4, 6, 9, 1, 3, 2, 5, 8}};

    // The inverse table
    int[] inv = {0, 4, 3, 2, 1, 5, 6, 7, 8, 9};

    public static boolean validateVerhoeff(String num) {

        int c = 0;
        if (num.toUpperCase().contains("X")) return false;
        int[] myArray = stringToReversedIntArray(num);

        for (int i = 0; i < myArray.length; i++) {
            c = d[c][p[(i % 8)][myArray[i]]];
        }

        return (c == 0);
    }

    public static int[] stringToReversedIntArray(String num) {

        int[] myArray = new int[num.length()];
        for (int i = 0; i < num.length(); i++) {
            myArray[i] = Integer.parseInt(num.substring(i, i + 1));
        }

        myArray = reverse(myArray);

        return myArray;

    }

    public static int[] reverse(int[] myArray) {
        int[] reversed = new int[myArray.length];

        for (int i = 0; i < myArray.length; i++) {
            reversed[i] = myArray[myArray.length - (i + 1)];
        }

        return reversed;
    }

    public static boolean validatePan(String strPAN) {
        Pattern pattern = Pattern.compile("^[A-Z]{3}P[A-Z]{1}[0-9]{4}[A-Z]{1}$");
        boolean retVal = false;
        if (strPAN != null & strPAN.length() == 10) {
            Matcher matcher = pattern.matcher(strPAN);
            retVal = matcher.matches();
        }
        return retVal;
    }

    public static boolean validateIFSC(String strIFSC) {
        Pattern pattern = Pattern.compile("^[A-Z]{4}0[A-Z,0-9]{6}$");
        boolean retVal = false;
        if (strIFSC != null & strIFSC.length() == 11) {
            Matcher matcher = pattern.matcher(strIFSC);
            retVal = matcher.matches();
        }
        return retVal;
    }

    public static boolean validateCaseCode(String caseCode) {
        Pattern pattern = Pattern.compile("^[A-Z]{4}[0-9]{6}$");
        boolean retVal = false;
        if (caseCode != null & caseCode.length() == 10) {
            Matcher matcher = pattern.matcher(caseCode);
            retVal = matcher.matches();
        }
        return retVal;
    }

    public static String getPrefString(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, value);
    }


}
