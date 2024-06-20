package com.paisalo.newinternalsourcingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListDataModel;
import com.paisalo.newinternalsourcingapp.R;
import java.util.List;

public class BorrowerListAdapter extends RecyclerView.Adapter<BorrowerListAdapter.ViewHolder> {

    private Context context;
    private List<BorrowerListDataModel> borrowerListDataModel;
    private OnItemClickListener listener;

    public BorrowerListAdapter(Context context, List<BorrowerListDataModel> borrowerListDataModel, OnItemClickListener listener) {
        this.context = context;
        this.borrowerListDataModel = borrowerListDataModel;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.borrower_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BorrowerListDataModel dataModel = borrowerListDataModel.get(position);

        if (dataModel.getMname() == null && dataModel.getLname() == null) {
            holder.userNameTextView.setText(dataModel.getFname());
        } else if (dataModel.getMname() == null) {
            holder.userNameTextView.setText(dataModel.getFname() + " " + dataModel.getLname());
        } else {
            holder.userNameTextView.setText(dataModel.getFname() + " " + dataModel.getMname() + " " + dataModel.getLname());
        }

        if (dataModel.getfMname() == null && dataModel.getfLname() == null) {
            holder.fatherOrSpouseTextView.setText(dataModel.getfFname());
        } else if (dataModel.getfMname() == null) {
            holder.fatherOrSpouseTextView.setText(dataModel.getfFname() + " " + dataModel.getfLname());
        } else {
            holder.fatherOrSpouseTextView.setText(dataModel.getfFname() + " " + dataModel.getfMname() + " " + dataModel.getfLname());
        }

        holder.fiCodeTextView.setText(String.valueOf(dataModel.getCode()) != null ? String.valueOf(dataModel.getCode()) : "N/A");
        holder.mobileTextView.setText(dataModel.getpPh3());
        holder.creatorTextView.setText(dataModel.getCreator());
        holder.addressTextView.setText(dataModel.getAddr());
    }

    @Override
    public int getItemCount() {
        return borrowerListDataModel.size();
    }

    public interface OnItemClickListener {
        void onItemClick(BorrowerListDataModel adapterItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView, fatherOrSpouseTextView, fiCodeTextView, mobileTextView, creatorTextView, addressTextView;
        CardView borrowerCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            borrowerCardView = itemView.findViewById(R.id.borrowerCardView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            fatherOrSpouseTextView = itemView.findViewById(R.id.fatherOrSpouseTextView);
            fiCodeTextView = itemView.findViewById(R.id.fiCodeTextView);
            mobileTextView = itemView.findViewById(R.id.mobileTextView);
            creatorTextView = itemView.findViewById(R.id.creatorTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);

            borrowerCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        BorrowerListDataModel dataModel = borrowerListDataModel.get(position);
                        listener.onItemClick(dataModel);
                    }
                }
            });
        }
    }
}
