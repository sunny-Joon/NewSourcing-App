package com.paisalo.newinternalsourcingapp.Utils;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class TiltTransformer implements ViewPager.PageTransformer {
    private static final float MAX_ROTATION_ANGLE = 20; // Maximum rotation angle in degrees

    @Override
    public void transformPage(View view, float position) {
        if (position < -1 || position > 1) {
            view.setAlpha(1);
            view.setRotation(0);
        } else {
            view.setAlpha(1);

            // Calculate and set the rotation angle based on the swipe position
            float rotation = position * MAX_ROTATION_ANGLE;
            view.setRotation(rotation);
        }
    }
}
