package com.paisalo.newinternalsourcingapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.paisalo.newinternalsourcingapp.R;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    int resourecId;
    List<String> rangeCategoryList = null;

    Boolean showDetail = false;

    public CustomSpinnerAdapter(Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourecId = resource;
        this.rangeCategoryList = objects;
    }

    public CustomSpinnerAdapter(Context context, @NonNull List<String> objects, Boolean withSubDetail) {
        super(context, R.layout.spinner_card_orange, objects);
        this.context = context;
        this.resourecId = R.layout.spinner_card_orange;
        this.rangeCategoryList = objects;
        this.showDetail = withSubDetail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(R.layout.spinner_card_orange, parent, false);
        } else {
            v = convertView;
        }

        ((TextView) v.findViewById(R.id.text_cname)).setText(rangeCategoryList.get(position));
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {

                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_card_orange, parent, false);

        } else {
            v = convertView;
        }

            ((TextView) v.findViewById(R.id.text_cname)).setText(rangeCategoryList.get(position));


        return v;
    }
}
