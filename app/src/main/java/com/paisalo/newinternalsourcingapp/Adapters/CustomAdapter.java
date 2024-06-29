package com.paisalo.newinternalsourcingapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paisalo.newinternalsourcingapp.Activities.FirstEsignActivity;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.Guarantor;
import com.paisalo.newinternalsourcingapp.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Guarantor> {

        private Context context;
        private int resource;
        private ArrayList<Guarantor> guarantors;

        public CustomAdapter(Context context, int resource, ArrayList<Guarantor> guarantors) {
            super(context, resource, guarantors);
            this.context = context;
            this.resource = resource;
            this.guarantors = guarantors;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(resource, parent, false);
            }

            Guarantor guarantor = guarantors.get(position);

            TextView layoutCustGurFather = convertView.findViewById(R.id.layoutCustGurFather);
            TextView layoutCustGurSignerAs = convertView.findViewById(R.id.layoutCustGurSignerAs);
            TextView layoutCustGurMobile = convertView.findViewById(R.id.layoutCustGurMobile);
            TextView itemLayoutCreator = convertView.findViewById(R.id.itemLayoutCreator);
            TextView layoutCustGurAddress = convertView.findViewById(R.id.layoutCustGurAddress);
            TextView layoutCustGurName = convertView.findViewById(R.id.layoutCustGurName);

            layoutCustGurFather.setText(guarantor.getGuarantorFather());
            layoutCustGurSignerAs.setText("GUARANTOR "+guarantor.getGuarantorNo());
            layoutCustGurMobile.setText(String.valueOf(guarantor.getpPh3()));
            itemLayoutCreator.setText(String.valueOf(guarantor.getCreator()));
            layoutCustGurAddress.setText(String.valueOf(guarantor.getAddr()));
            layoutCustGurName.setText(String.valueOf(guarantor.getGuarantorName()));

            return convertView;
        }
    }