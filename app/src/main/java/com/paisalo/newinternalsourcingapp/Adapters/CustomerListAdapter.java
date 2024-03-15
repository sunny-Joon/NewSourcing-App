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
import com.paisalo.newinternalsourcingapp.R;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> {
    private List<CustomerModel> dataList;
    private Context context;
    private CustomerListAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View itemView);
    }

    public CustomerListAdapter(Context context, List<CustomerModel> dataList, CustomerListAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataList = dataList;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public CustomerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customerlist_item, parent, false);
        return new CustomerListAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.MyViewHolder holder, int position) {
        CustomerModel data = dataList.get(position);

        holder.customerNameTextView.setText(data.getCustomerName());
        holder.guardianTV.setText(data.getGuardian());
        holder.customerMobileTV.setText(data.getCustomerMobile());
        holder.totalPaymentTV.setText(data.getTotalPayment());
        holder.smcodeTV.setText(data.getSmcode());
        holder.customerAddressTV.setText(data.getCustomerAddress());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView customerNameTextView;
        TextView guardianTV;
        TextView customerMobileTV;
        TextView totalPaymentTV;
        TextView smcodeTV;
        TextView customerAddressTV;
        CardView customerCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customerCardView = itemView.findViewById(R.id.customerCardView);
            customerNameTextView = itemView.findViewById(R.id.customerNameTextView);
            guardianTV = itemView.findViewById(R.id.guardianTV);
            customerMobileTV = itemView.findViewById(R.id.customerMobileTV);
            totalPaymentTV = itemView.findViewById(R.id.totalPaymentTV);
            smcodeTV = itemView.findViewById(R.id.smcodeTV);
            customerAddressTV = itemView.findViewById(R.id.customerAddressTV);

            Intent intent = ((Activity) itemView.getContext()).getIntent();
            String id = intent.getStringExtra("keyName");

            customerCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, itemView);
                    }
                }
            });
        }
    }
}
