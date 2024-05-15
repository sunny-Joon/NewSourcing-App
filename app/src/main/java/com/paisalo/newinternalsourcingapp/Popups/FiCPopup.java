package com.paisalo.newinternalsourcingapp.Popups;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class FiCPopup extends DialogFragment {
    private String message1;
    private String message2;

    public FiCPopup(String message1, String message2) {
        this.message1 = message1;
        this.message2 = message2;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("KYC Saved Successfully")
                .setMessage(message1 + "\n" + message2)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });

        return builder.create();
    }
}