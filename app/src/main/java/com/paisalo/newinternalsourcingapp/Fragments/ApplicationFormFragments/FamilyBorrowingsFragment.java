package com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paisalo.newinternalsourcingapp.R;


public class FamilyBorrowingsFragment extends Fragment {

    FloatingActionButton FMIncomeButton;

    public FamilyBorrowingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_borrowings,container,false);

        FMIncomeButton = view.findViewById(R.id.FMIncome);

        FMIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.familyborrowingspopup, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            }
        });

        return view;    }
}