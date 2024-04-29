package com.paisalo.newinternalsourcingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;

import java.util.List;

public class RangeCategoryAdapter extends ArrayAdapter<RangeCategoryDataClass> {

    public RangeCategoryAdapter(Context context, List<RangeCategoryDataClass> yearItems) {
        super(context, 0, yearItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        RangeCategoryDataClass yearItem = getItem(position);
        if (yearItem != null) {
            textView.setText(yearItem.getDescriptionEn());
        }
        return convertView;
    }
}
