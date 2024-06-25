package com.paisalo.newinternalsourcingapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiGuarantor;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.RoomDatabase.ManagerListDataClass;

import java.util.ArrayList;
import java.util.List;

public class GurrantorListAdapter extends RecyclerView.Adapter<GurrantorListAdapter.MyViewHolder> {

    private Context context;
    List<FiGuarantor> gurrantorList;

    public GurrantorListAdapter(Context context, List<FiGuarantor> gurrantorList) {
        this.context = context;
        this.gurrantorList = gurrantorList;
    }
    @NonNull
    @Override
    public GurrantorListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gurrantors_list_item, parent, false);
        return new GurrantorListAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GurrantorListAdapter.MyViewHolder holder, int position) {
        FiGuarantor gurrantor = gurrantorList.get(position);
        int pos =position+1;
        holder.GRTV.setText(String.valueOf(pos));
        holder.id.setText(gurrantor.getIdentityNo());
        holder.adhaaridTV.setText(gurrantor.getAadharID());
        holder.userNameTV.setText(gurrantor.getName());
        Log.d("TAG", "onBindViewHolder: "+gurrantor.getPerAdd1()+","+gurrantor.getPerAdd2()+","+gurrantor.getPerAdd3());
        holder.addressTV.setText(gurrantor.getPerAdd1()+","+gurrantor.getPerAdd2()+","+gurrantor.getPerAdd3());
        holder.mobileTV.setText(gurrantor.getPerMob1());
        holder.fatherOrSpouseTV.setText(gurrantor.getGurName());
       // holder.profilePicGur.setImageBitmap(BitmapFactory.decodeFile(gurrantor.getGurImage().getAbsolutePath()));
    }

    @Override
    public int getItemCount() {
        return gurrantorList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView addressTV,GRTV,id,mobileTV,adhaaridTV,fatherOrSpouseTV,userNameTV;
        ImageView profilePicGur;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            addressTV = itemView.findViewById(R.id.addressTV);
            profilePicGur = itemView.findViewById(R.id.profilePicGur);
            GRTV = itemView.findViewById(R.id.GRTV);
            id = itemView.findViewById(R.id.id);
            mobileTV = itemView.findViewById(R.id.mobileTV);
            adhaaridTV = itemView.findViewById(R.id.adhaaridTV);
            fatherOrSpouseTV = itemView.findViewById(R.id.fatherOrSpouseTV);
            userNameTV = itemView.findViewById(R.id.userNameTV);
        }
    }
}
