package com.paisalo.newinternalsourcingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.R;


import java.util.List;

public class BranchCodesAdapter extends RecyclerView.Adapter<BranchCodesAdapter.BranchCodeViewHolder> {
    Context context;
    List<String> branchCodeList;
    public BranchCodesAdapter(Context context, List<String> branchCodeList) {
        this.context=context;
        this.branchCodeList=branchCodeList;
    }

    @NonNull
    @Override
    public BranchCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.branchcode_card,parent,false);
        return new BranchCodeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BranchCodeViewHolder holder, int position) {
        holder.txtBranchCode.setText(branchCodeList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return branchCodeList.size();
    }

    public class BranchCodeViewHolder extends RecyclerView.ViewHolder {
        TextView txtBranchCode;

        public BranchCodeViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBranchCode=itemView.findViewById(R.id.txtBranchCode);
        }
    }
}
