package com.paisalo.newinternalsourcingapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class BlurUtils {

    public static Bitmap blurBitmap(Context context, Bitmap bitmap, float radius) {
        // Create an empty bitmap with the same size as the input bitmap
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        // Create RenderScript context
        RenderScript rs = RenderScript.create(context);

        // Create an Allocation for the bitmap
        Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SCRIPT);

        // Create an Allocation for the output bitmap
        Allocation output = Allocation.createTyped(rs, input.getType());

        // Create a blur script
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);

        // Set the input for the script
        script.setInput(input);

        // Call the script to perform the blur
        script.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap);

        // Release resources
        input.destroy();
        output.destroy();
        script.destroy();
        rs.destroy();

        return blurredBitmap;
    }
}
