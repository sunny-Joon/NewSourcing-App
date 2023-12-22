package com.paisalo.newinternalsourcingapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.paisalo.newinternalsourcingapp.Modelclasses.KYCScanningModel;
import com.paisalo.newinternalsourcingapp.R;

import java.util.List;

public class KycRecyclerViewAdapter extends RecyclerView.Adapter<KycRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<KYCScanningModel> kycScanning;
    private OnItemClickListener onItemClickListener; // Add this interface

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public KycRecyclerViewAdapter(Context context, List<KYCScanningModel> kycScanning, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.kycScanning = kycScanning;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kycrecyclerviewlistitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        KYCScanningModel entry = kycScanning.get(position);
        holder.bind(entry);

        holder.imgViewKycItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return kycScanning.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvKycItemLayoutName, tvKycItemLayoutType, tvKycItemLayoutDocType, tvKycItemLayoutRemarks, tvKycItemLayoutAadhar;
        ImageView imgViewKycItemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgViewKycItemLayout = itemView.findViewById(R.id.imgViewKycItemLayout);
            tvKycItemLayoutName = itemView.findViewById(R.id.tvKycItemLayoutName);
            tvKycItemLayoutType = itemView.findViewById(R.id.tvKycItemLayoutType);
            tvKycItemLayoutDocType = itemView.findViewById(R.id.tvKycItemLayoutDocType);
            tvKycItemLayoutRemarks = itemView.findViewById(R.id.tvKycItemLayoutRemarks);
        }

        public void bind(KYCScanningModel entry) {
            tvKycItemLayoutName.setText(entry.getName());
            tvKycItemLayoutType.setText(entry.getType());
            tvKycItemLayoutDocType.setText(entry.getDocType());
            tvKycItemLayoutRemarks.setText(entry.getRemarks());
        }
    }
}

