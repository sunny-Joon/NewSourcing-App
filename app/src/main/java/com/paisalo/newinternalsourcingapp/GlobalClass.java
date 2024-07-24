package com.paisalo.newinternalsourcingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.camera.core.ImageCapture;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.paisalo.newinternalsourcingapp.Adapters.AdapterCollectionFragmentPager;
import com.paisalo.newinternalsourcingapp.Fragments.AbsCollectionFragment;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
import com.paisalo.newinternalsourcingapp.Modelclasses.DueData;
import com.paisalo.newinternalsourcingapp.Modelclasses.PosInstRcv;
import com.paisalo.newinternalsourcingapp.Modelclasses.SmCode_DateModel;
import com.paisalo.newinternalsourcingapp.Utils.Utils;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalClass extends Application {

    static KYCActivity kycActivity;

    public static String Id = "";
    public static String Creator = "";
    public static String Address = "";
    public static String AreaCode = "";
    public static String UserName = "";
    public static String Token = "";
    public static String LiveToken = "";
    public static String Tag = "";
    public static String Imei = "";
    public static String DATABASE_NAME = "";
    public static Double Longitude;
    public static Double Latitude;

    public static String DevId = "";
    public static String dbname = "yfMerfC6mRvfr0AOoHmOJ8Et9Q9MPwNEKzFdLsfEs1A=";
    public static final String MANAGER_TAG = "MANAGERD";
    public static final int EKYC_REQUEST_CODE = 403;

    public static final String ESIGNER_TAG = "ESIGNER";
    public static final String ESIGN_TYPE_TAG = "ESIGN_TYPE";
    public static final String ESIGN_BORROWER = "ESIGN_BORROWER";
    public static final String ESIGN_GUARANTOR = "ESIGN_GUARANTOR";


    private static final String[] INPUT_DATE_FORMATS = {
            "dd-MM-yyyy",
            "yyyy-MM-dd",
            "dd/MM/yyyy",
            "yyyy/MM/dd",

    };
    static String currentFileName;
    private static ImageCapture imageCapture;
    private static Uri capturedImageUri;

    private static ArrayList<DueData> dueDataList = new ArrayList<>();
    private static ArrayList<PosInstRcv> settlementDataList = new ArrayList<>();
    private static AdapterCollectionFragmentPager fragmentPagerAdapter;
    private static AbsCollectionFragment fragSettlement = null;
    private static ArrayList<AbsCollectionFragment> absFragments;
    private static androidx.appcompat.app.AlertDialog alertDialog;

    public static boolean isValidUsername(String username) {
        return username.length() >= 1;
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 5;
    }

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
        builder.setCancelable(true);
        builder.show();
    }

    public static void showLottieAlertDialog(int order, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View alertLayout = inflater.inflate(R.layout.custom_alert, null);

        LottieAnimationView animationView = alertLayout.findViewById(R.id.animation_view);

        switch (order) {
            case 1:
                animationView.setAnimation(R.raw.unlock); // Set the animation resource programmatically
                break;
            case 2:
                animationView.setAnimation(R.raw.ai); // Set the animation resource programmatically
                break;
            case 3:
                animationView.setAnimation(R.raw.location); // Set the animation resource programmatically
                break;
            case 4:
                animationView.setAnimation(R.raw.locationtip); // Set the animation resource programmatically
                break;
            case 5:
                animationView.setAnimation(R.raw.map); // Set the animation resource programmatically
                break;
            case 6:
                animationView.setAnimation(R.raw.networkerror); // Set the animation resource programmatically
                break;
            case 7:
                animationView.setAnimation(R.raw.qrscanner); // Set the animation resource programmatically
                break;
            case 8:
                animationView.setAnimation(R.raw.rendering); // Set the animation resource programmatically
                break;
            case 9:
                animationView.setAnimation(R.raw.error); // Set the animation resource programmatically
                break;
        }

        androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.CustomAlertDialog);
        alert.setView(alertLayout);

         alertDialog = alert.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        alertDialog.show();
    }

    public static void dismissLottieAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
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


    static int[][] p = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            {1, 5, 7, 6, 2, 8, 3, 0, 9, 4},
            {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
            {8, 9, 1, 6, 0, 4, 3, 5, 2, 7},
            {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
            {4, 2, 8, 6, 5, 7, 3, 9, 0, 1},
            {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
            {7, 0, 4, 6, 9, 1, 3, 2, 5, 8}};


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

    public static <E> List<E> convertToObjectArray(String jsonString, Type listType) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        List<E> eList = gson.fromJson(jsonString, listType);
        return eList;
    }

    public static List<SmCode_DateModel> getList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("payment_case", Context.MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        String json = prefs.getString("payment_list", null);
        Type type = new TypeToken<List<SmCode_DateModel>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void removeItem(Context context, String propertyValueToRemove) {
        List<SmCode_DateModel> list = getList(context);

        for (SmCode_DateModel obj : list) {
            if (obj.getTranDate().equals(propertyValueToRemove)) {
                list.remove(obj);
                break;
            }
        }
        saveList(context, list);
    }

    public static void saveList(Context context, List<SmCode_DateModel> list) {
        SharedPreferences prefs = context.getSharedPreferences("payment_case", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(list);
        editor.putString("payment_list", json);
        editor.apply();
    }

    public static String formatDateString(String inputDateString) {
        for (String format : INPUT_DATE_FORMATS) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat(format, Locale.getDefault());
                Date date = inputFormat.parse(inputDateString);
                if (date != null) {
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    return outputFormat.format(date);
                }
            } catch (ParseException e) {

            }
        }
        return null;
    }

    public static String formatDateString2(String inputDateString, String style) {

        for (String format : INPUT_DATE_FORMATS) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat(format, Locale.getDefault());
                Date date = inputFormat.parse(inputDateString);
                if (date != null) {
                    SimpleDateFormat outputFormat = new SimpleDateFormat(style, Locale.getDefault());
                    return outputFormat.format(date);
                }
            } catch (ParseException e) {

            }
        }
        return null;
    }

    public static int calculateAge(String birthDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date birthDate = sdf.parse(birthDateString);
            if (birthDate == null) return -1;

            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(birthDate);

            Calendar todayCalendar = Calendar.getInstance();

            int age = todayCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

            if (todayCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void showToast(Context context, int toastType, String msg) {
        switch (toastType) {
            case 1:
                FancyToast.makeText(context, msg, FancyToast.LENGTH_SHORT, FancyToast.DEFAULT, true).show();
                break;
            case 2:
                FancyToast.makeText(context, msg, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                break;
            case 3:
                FancyToast.makeText(context, msg, FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                break;
            case 4:
                FancyToast.makeText(context, msg, FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                break;
            case 5:
                FancyToast.makeText(context, msg, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                break;
            case 6:
                FancyToast.makeText(context, msg, FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, true).show();
                break;
            case 7:
                FancyToast.makeText(context, msg, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                break;
            case 8:
                FancyToast.makeText(context, msg, FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, R.drawable.rotate, false).show();
                break;
            default:
                FancyToast.makeText(context, msg, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                break;
        }
    }


    private static final float BLUR_RADIUS = 25.0f;

    public static Dialog showBlurredPopup(Context context, View view, ImageView imageView, int popupLayoutId) {

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap screenshot = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);


        Bitmap blurredScreenshot = blurBitmap(context, screenshot, BLUR_RADIUS);


        imageView.setImageBitmap(blurredScreenshot);


        View popupView = LayoutInflater.from(context).inflate(popupLayoutId, null);


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(popupView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dialog.show();

        return dialog;
    }

    public static void dismissPopup(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private static Bitmap blurBitmap(Context context, Bitmap bitmap, float radius) {

        RenderScript rs = RenderScript.create(context);

        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        Allocation output = Allocation.createTyped(rs, input.getType());

        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        blurScript.setInput(input);

        blurScript.setRadius(radius);
        blurScript.forEach(output);

        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        output.copyTo(blurredBitmap);

        rs.destroy();

        return blurredBitmap;


    }

}
