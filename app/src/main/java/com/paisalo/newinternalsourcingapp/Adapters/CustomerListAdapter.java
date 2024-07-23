package com.paisalo.newinternalsourcingapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.ModelclassesRoom.CustomerModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.EsignListDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.Guarantor;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.PendingESignFI;
import com.paisalo.newinternalsourcingapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> {
    private EsignListDataModel dataModel;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PendingESignFI adapterItem,ArrayList<Guarantor> guarantorArrayList);
    }

    public CustomerListAdapter(Context context, EsignListDataModel dataModel, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataModel = dataModel;
        this.listener = onItemClickListener;

    }

    @NonNull
    @Override
    public CustomerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customerlist_item, parent, false);
        return new CustomerListAdapter.MyViewHolder(view);
    }


   /* @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.MyViewHolder holder, int position) {
        PendingESignFI data = dataModel.getPendingESignFI().get(position);

        holder.customerNameTextView.setText(data.getFname()+" "+data.getMname() +" "+data.getLname());
        holder.guardianTV.setText(data.getfFname()+" "+data.getfMname()+" "+data.getfLname());
        holder.customerMobileTV.setText(data.getpPh3());
        holder.totalPaymentTV.setText(data.getLoanAmt());
        holder.ficodeTV.setText(String.valueOf(data.getCode()));
        holder.customerAddressTV.setText(data.getAddr());

    }*/

    @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.MyViewHolder holder, int position) {
        PendingESignFI data = dataModel.getPendingESignFI().get(position);

        StringBuilder customerNameBuilder = new StringBuilder();
        if (data.getFname() != null && !data.getFname().isEmpty()) {
            customerNameBuilder.append(data.getFname());
        }
        if (data.getMname() != null && !data.getMname().isEmpty()) {
            if (customerNameBuilder.length() > 0) {
                customerNameBuilder.append(" ");
            }
            customerNameBuilder.append(data.getMname());
        }
        if (data.getLname() != null && !data.getLname().isEmpty()) {
            if (customerNameBuilder.length() > 0) {
                customerNameBuilder.append(" ");
            }
            customerNameBuilder.append(data.getLname());
        }
        holder.customerNameTextView.setText(customerNameBuilder.toString());

        StringBuilder guardianNameBuilder = new StringBuilder();
        if (data.getfFname() != null && !data.getfFname().isEmpty()) {
            guardianNameBuilder.append(data.getfFname());
        }
        if (data.getfMname() != null && !data.getfMname().isEmpty()) {
            if (guardianNameBuilder.length() > 0) {
                guardianNameBuilder.append(" ");
            }
            guardianNameBuilder.append(data.getfMname());
        }
        if (data.getfLname() != null && !data.getfLname().isEmpty()) {
            if (guardianNameBuilder.length() > 0) {
                guardianNameBuilder.append(" ");
            }
            guardianNameBuilder.append(data.getfLname());
        }
        holder.guardianTV.setText(guardianNameBuilder.toString());

        holder.customerMobileTV.setText(data.getpPh3());
        holder.totalPaymentTV.setText(data.getLoanAmt());
        holder.ficodeTV.setText(String.valueOf(data.getCode()));
        holder.customerAddressTV.setText(data.getAddr());
    }


    @Override
    public int getItemCount() {
        return dataModel.getPendingESignFI().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView customerNameTextView;
        TextView guardianTV;
        TextView customerMobileTV;
        TextView totalPaymentTV;
        TextView ficodeTV;
        TextView customerAddressTV;
        CardView customerCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customerCardView = itemView.findViewById(R.id.customerCardView);
            customerNameTextView = itemView.findViewById(R.id.customerNameTextView);
            guardianTV = itemView.findViewById(R.id.guardianTV);
            customerMobileTV = itemView.findViewById(R.id.customerMobileTV);
            totalPaymentTV = itemView.findViewById(R.id.totalPaymentTV);
            ficodeTV = itemView.findViewById(R.id.smcodeTV);
            customerAddressTV = itemView.findViewById(R.id.customerAddressTV);

            Intent intent = ((Activity) itemView.getContext()).getIntent();
            String id = intent.getStringExtra("keyName");

            customerCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        PendingESignFI dataM = dataModel.getPendingESignFI().get(position);
                        ArrayList<Guarantor> guarantorArrayList=new ArrayList<>();

                        for (Guarantor guarantor:dataModel.getGuarantors()){
                            if (guarantor.getCode() == dataM.getCode() && guarantor.getCreator().equals(dataM.getCreator())) {
                             guarantorArrayList.add(guarantor);
                            }
                        }
                        listener.onItemClick(dataM,guarantorArrayList);

                    }
                }
            });
        }
    }
}
