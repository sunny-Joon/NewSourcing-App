package com.paisalo.newinternalsourcingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.Modelclasses.EmiModel;
import com.paisalo.newinternalsourcingapp.R;

import java.util.List;

public class EMI_RecyclerviewAdapter extends RecyclerView.Adapter<EMI_RecyclerviewAdapter.ViewHolder> {

    private Context context;
    private List<EmiModel> emiModels;

    public EMI_RecyclerviewAdapter(Context context, List<EmiModel> emiModels) {
        this.context = context;
        this.emiModels = emiModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.emi_recyclerview_item, parent, false);
        return new EMI_RecyclerviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EmiModel emiModel = emiModels.get(position);
        holder.bind(emiModel);

    }

    @Override
    public int getItemCount() {
        return emiModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox amount;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
        }

        public void bind(EmiModel entry) {
            date.setText(entry.getDate());
            amount.setText(entry.getAmount());
        }
    }
}
