package com.paisalo.newinternalsourcingapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelclassesRoom.KYCScanningModel;

import com.paisalo.newinternalsourcingapp.ModelsRetrofit.KycDocsFlag.KycDocsFlagDataModel;
import com.paisalo.newinternalsourcingapp.R;

import java.io.File;
import java.util.List;

public class KycRecyclerViewAdapter extends RecyclerView.Adapter<KycRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<KYCScanningModel> kycScanning;
    private OnItemClickListener onItemClickListener;


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

        holder.bind(position);



    }
//not changing
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return kycScanning.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvKycItemLayoutName, tvKycItemLayoutType, tvKycItemLayoutDocType, tvKycItemLayoutRemarks, tvKycItemLayoutAadhar;
        ImageView imgViewKycItemLayout;
        LinearLayout mainLinearLayout;
        CardView docsCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            docsCardView = itemView.findViewById(R.id.docsCardView);
            mainLinearLayout = itemView.findViewById(R.id.mainLinearLayout);
            imgViewKycItemLayout = itemView.findViewById(R.id.imgViewKycItemLayout);
            tvKycItemLayoutName = itemView.findViewById(R.id.tvKycItemLayoutName);
            tvKycItemLayoutType = itemView.findViewById(R.id.tvKycItemLayoutType);
            tvKycItemLayoutDocType = itemView.findViewById(R.id.tvKycItemLayoutDocType);
            tvKycItemLayoutRemarks = itemView.findViewById(R.id.tvKycItemLayoutRemarks);
        }

        public void bind(int position) {
            String URL ="https://pdlmobilelending.paisalo.in:5320";

            KYCScanningModel entry = kycScanning.get(position);
            tvKycItemLayoutName.setText(entry.getName());
            tvKycItemLayoutType.setText(entry.getType());
            tvKycItemLayoutDocType.setText(entry.getDocType());
            tvKycItemLayoutRemarks.setText(entry.getRemarks());

            imgViewKycItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(entry, position);
                }
            });

            if (entry.getFile()!=null){
                docsCardView.setBackgroundColor(context.getResources().getColor(R.color.green));
            }else{
                docsCardView.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
            if(entry.isUploaded()){
                mainLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.green));
                Log.d("TAG", "bind: "+URL+(entry.getImagePath().replaceAll("\\\\", "/")));
                Glide.with(context)
                        .load(URL+(entry.getImagePath().replaceAll("\\\\", "/")))
                        .into(imgViewKycItemLayout);


            }else{
                mainLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.red));
            }


        }
    }
}



