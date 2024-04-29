package com.paisalo.newinternalsourcingapp.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import com.paisalo.newinternalsourcingapp.R;


public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.circular_progressbar);
        setCancelable(false); // Prevent dismissal by tapping outside
    }
}
