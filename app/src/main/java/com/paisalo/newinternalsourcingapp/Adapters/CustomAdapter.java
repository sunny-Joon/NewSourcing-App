package com.paisalo.newinternalsourcingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paisalo.newinternalsourcingapp.Activities.FirstEsignActivity;
import com.paisalo.newinternalsourcingapp.R;

public class CustomAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private String[] mValues;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull String[] values) {
        super(context, resource, values);
        mContext = context;
        mValues = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_esigner, parent, false);
            viewHolder = new ViewHolder();
           // viewHolder.textView = convertView.findViewById(R.id.textView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

      //  viewHolder.textView.setText(mValues[position]);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FirstEsignActivity.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
      //  TextView textView;
    }
}
