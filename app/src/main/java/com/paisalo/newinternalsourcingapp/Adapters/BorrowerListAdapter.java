package com.paisalo.newinternalsourcingapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.Activities.FirstEsignActivity;
import com.paisalo.newinternalsourcingapp.Activities.SecondEsignActivity;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.HouseVisitActivity1;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListDataModel;
import com.paisalo.newinternalsourcingapp.R;

import java.util.List;

public class BorrowerListAdapter extends RecyclerView.Adapter<BorrowerListAdapter.ViewHolder> {

    private Context context;
    private List<BorrowerListDataModel> borrowerListDataModel;

    public BorrowerListAdapter(Context context, List<BorrowerListDataModel> borrowerListDataModel) {
        this.context = context;
        this.borrowerListDataModel = borrowerListDataModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.application_form_list_item, parent, false);
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
        if(dataModel.getCode() != null){
            holder.fiCodeTextView.setText(dataModel.getCode());
        }else{
            holder.fiCodeTextView.setText(dataModel.getCode());
        }
        holder.mobileTextView.setText(dataModel.getpPh3());
        holder.creatorTextView.setText(dataModel.getCreator());
        holder.addressTextView.setText(dataModel.getAddr());
    }

    @Override
    public int getItemCount() {
        return borrowerListDataModel.size();
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
                        Intent intent = ((Activity) itemView.getContext()).getIntent();
                        String id = intent.getStringExtra("keyName");
                        String fiCode = dataModel.getCode();
                        String creator = dataModel.getCreator();
                        if (id != null) {
                            openActivity(id, fiCode, creator);
                        }
                    }
                }
            });
        }

        public void openActivity(String id, String fiCode, String creator) {
            Intent intent = null;
            switch (id) {
                case "FEsign":
                    intent = new Intent(context, FirstEsignActivity.class);
                    break;
                case "SEsign":
                    intent = new Intent(context, SecondEsignActivity.class);
                    break;
                case "Application":
                    intent = new Intent(context, ApplicationFormActivityMenu.class);
                    intent.putExtra("fiCode", fiCode); // Pass fiCode to the intent
                    intent.putExtra("creator", creator); // Pass creator to the intent
                    break;
                case "HVisit":
                    intent = new Intent(context, HouseVisitActivity1.class);
                    intent.putExtra("fiCode", fiCode); // Pass fiCode to the intent
                    intent.putExtra("creator", creator); // Pass creator to the intent
                    break;
            }

            if (intent != null) {
                context.startActivity(intent);
            } else {
                Log.e("ViewHolder", "Invalid id received: " + id);
            }
        }
    }
}
