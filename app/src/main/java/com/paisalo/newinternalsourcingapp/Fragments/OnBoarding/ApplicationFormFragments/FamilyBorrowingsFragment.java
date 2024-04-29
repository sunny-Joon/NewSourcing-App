package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.R;


public class FamilyBorrowingsFragment extends Fragment {

    Button  addBtn,dltButton,canButton;
    EditText LenderName,etLoanamount,etEmiamount,etbalanceamount;

    Spinner lenderTypespin,spinnerLoanUsed,spinnerReasonforloan,spinnerisMFI;
    FloatingActionButton addBorrower;

    AllDataAFDataModel allDataAFDataModel;
    public FamilyBorrowingsFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel = allDataAFDataModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_borrowings,container,false);

        addBorrower = view.findViewById(R.id.addBorrower);
        dltButton = view.findViewById(R.id.button2);
        canButton = view.findViewById(R.id.button3);

        addBorrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.familyborrowingspopup, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                LenderName = popupView.findViewById(R.id.LenderName);
                addBtn = popupView.findViewById(R.id.addBorrowings);
                etLoanamount = popupView.findViewById(R.id.editLoanamount);
                etEmiamount = popupView.findViewById(R.id.editTextEmiamount);
                etbalanceamount = popupView.findViewById(R.id.balanceamount);


                lenderTypespin = popupView.findViewById(R.id.lenderType);
                spinnerLoanUsed = popupView.findViewById(R.id.spinnerOptions2);
                spinnerReasonforloan = popupView.findViewById(R.id.spinnerOptions2);
                spinnerisMFI = popupView.findViewById(R.id.isMFI);

                canButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("borrowingsCheckBox", true);
                        editor.apply();

                        Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
        });

        return view;    }
}