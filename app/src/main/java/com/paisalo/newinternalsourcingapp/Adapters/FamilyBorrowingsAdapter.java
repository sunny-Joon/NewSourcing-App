package com.paisalo.newinternalsourcingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiFamLoan;
import com.paisalo.newinternalsourcingapp.R;

import java.util.List;


public class FamilyBorrowingsAdapter extends RecyclerView.Adapter<FamilyBorrowingsAdapter.MyViewHolder> {
    private Context context;
    private List<FiFamLoan> familyBorrowingsList;

    public FamilyBorrowingsAdapter(Context context, List<FiFamLoan> familyBorrowingsList) {
        this.context = context;
        this.familyBorrowingsList = familyBorrowingsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fam_borrowings_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FiFamLoan fiFamLoan = familyBorrowingsList.get(position);
        holder.lenderNameTV.setText(fiFamLoan.getLenderName());
        holder.lenderTypeTV.setText(fiFamLoan.getLenderType());
        holder.reasonForLoanTV.setText(fiFamLoan.getLoanReason());
        holder.IsMFITV.setText(fiFamLoan.getIsMFI());
        holder.loneeTV.setText(fiFamLoan.getLoneeName());
        holder.loanAmount.setText(fiFamLoan.getLoanAmount().toString());
        holder.loanEmiTV.setText(fiFamLoan.getLoanEMIAmount().toString());
        holder.balance.setText(fiFamLoan.getLoanBalanceAmount().toString());
    }

    @Override
    public int getItemCount() {
        return familyBorrowingsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView lenderNameTV, lenderTypeTV, reasonForLoanTV, IsMFITV, loneeTV, loanAmount, loanEmiTV,balance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lenderNameTV = itemView.findViewById(R.id.lenderNameTV);
            lenderTypeTV = itemView.findViewById(R.id.lenderTypeTV);
            reasonForLoanTV = itemView.findViewById(R.id.reasonForLoanTV);
            IsMFITV = itemView.findViewById(R.id.IsMFITV);
            loneeTV = itemView.findViewById(R.id.loneeTV);
            loanAmount = itemView.findViewById(R.id.loanAmount);
            loanEmiTV = itemView.findViewById(R.id.loanEmiTV);
            balance = itemView.findViewById(R.id.balance);


        }
    }
}
