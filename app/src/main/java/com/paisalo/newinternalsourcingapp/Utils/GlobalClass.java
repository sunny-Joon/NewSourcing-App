package com.paisalo.newinternalsourcingapp.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class GlobalClass {

    private static final float BLUR_RADIUS = 25.0f;

    public static Dialog showBlurredPopup(Context context, View view, ImageView imageView, int popupLayoutId) {
        // Take screenshot of the provided view
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap screenshot = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        // Blur the screenshot using BlurUtils
        Bitmap blurredScreenshot = blurBitmap(context, screenshot, BLUR_RADIUS);

        // Set the blurred image in the provided ImageView
        imageView.setImageBitmap(blurredScreenshot);

        // Show the blurred image in a custom dialog
        View popupView = LayoutInflater.from(context).inflate(popupLayoutId, null);

        // Create the custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(popupView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Show the popup
        dialog.show();

        return dialog; // Return the created dialog
    }

    public static void dismissPopup(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private static Bitmap blurBitmap(Context context, Bitmap bitmap, float radius) {
        // Create renderscript
        RenderScript rs = RenderScript.create(context);

        // Create an allocation from the bitmap and an allocation for the blurred output
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        Allocation output = Allocation.createTyped(rs, input.getType());

        // Create a blur script and set the input
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        blurScript.setInput(input);

        // Set the blur radius and apply the blur
        blurScript.setRadius(radius);
        blurScript.forEach(output);

        // Copy the blurred output into a new bitmap
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        output.copyTo(blurredBitmap);

        // Release resources
        rs.destroy();

        return blurredBitmap;
    }
}
