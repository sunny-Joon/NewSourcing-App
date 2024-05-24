package com.paisalo.newinternalsourcingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiGuarantor;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.RoomDatabase.ManagerListDataClass;

import java.util.ArrayList;

public class GurrantorListAdapter extends RecyclerView.Adapter<GurrantorListAdapter.MyViewHolder> {

    private Context context;
    ArrayList<FiGuarantor> gurrantorList;

    public GurrantorListAdapter(Context context, ArrayList<FiGuarantor> gurrantorList) {
        this.context = context;
        this.gurrantorList = gurrantorList;

    }
    @NonNull
    @Override
    public GurrantorListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gurrantors_list_item, parent, false);
        return new GurrantorListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GurrantorListAdapter.MyViewHolder holder, int position) {
        FiGuarantor gurrantor = gurrantorList.get(position);
        int pos =position+1;
        holder.GRTV.setText(String.valueOf(pos));
        holder.id.setText(gurrantor.getIdentityNo());
        holder.adhaaridTV.setText(gurrantor.getAadharID());
        holder.userNameTV.setText(gurrantor.getName());
        holder.addressTV.setText(gurrantor.getOffAdd1()+gurrantor.getOffAdd2()+gurrantor.getOffAdd3());
        holder.mobileTV.setText(gurrantor.getPerMob1());
        holder.fatherOrSpouseTV.setText(gurrantor.getGurName());
    }

    @Override
    public int getItemCount() {
        return gurrantorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView addressTV,GRTV,id,mobileTV,adhaaridTV,fatherOrSpouseTV,userNameTV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            addressTV = itemView.findViewById(R.id.addressTV);
            GRTV = itemView.findViewById(R.id.GRTV);
            id = itemView.findViewById(R.id.id);
            mobileTV = itemView.findViewById(R.id.mobileTV);
            adhaaridTV = itemView.findViewById(R.id.adhaaridTV);
            fatherOrSpouseTV = itemView.findViewById(R.id.fatherOrSpouseTV);
            userNameTV = itemView.findViewById(R.id.userNameTV);
        }
    }
}
