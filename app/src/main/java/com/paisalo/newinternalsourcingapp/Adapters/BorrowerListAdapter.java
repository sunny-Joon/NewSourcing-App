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
import com.paisalo.newinternalsourcingapp.Activities.BorrowerListActivity;
import com.paisalo.newinternalsourcingapp.Activities.DownloadDocumentActivity;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;

import com.paisalo.newinternalsourcingapp.R;

public class BorrowerListAdapter extends RecyclerView.Adapter<BorrowerListAdapter.ViewHolder>{

    private Context context;
    private String userName, fatherOrSpouse, fiCode, mobile, creator, address;

    public BorrowerListAdapter(Context context, String userName, String fatherOrSpouse, String fiCode, String mobile, String creator, String address, String shimla) {
        this.context = context;
        this.userName = userName;
        this.fatherOrSpouse = fatherOrSpouse;
        this.fiCode = fiCode;
        this.mobile = mobile;
        this.creator = creator;
        this.address = address;
    }

    @Override
    public BorrowerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.application_form_list_item, parent, false);
        return new BorrowerListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BorrowerListAdapter.ViewHolder holder, int position) {

        holder.userNameTextView.setText(userName);
        holder.fatherOrSpouseTextView.setText(fatherOrSpouse);
        holder.fiCodeTextView.setText(fiCode);
        holder.mobileTextView.setText(mobile);
        holder.creatorTextView.setText(creator);
        holder.addressTextView.setText(address);
    }

    @Override
    public int getItemCount() {
        return 3;
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

            Intent intent = ((Activity) itemView.getContext()).getIntent();
            String id = intent.getStringExtra("keyName");

            borrowerCardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    openActivity(id);
                }
            });

            /*borrowerCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ApplicationFormActivityMenu.class);
                    itemView.getContext().startActivity(intent);
                }
            });*/
        }

        private void openActivity(String id) {
            if (id.equals("Esign")) {
                Log.d("Kyc", "kkk" + id);
                Intent intent = new Intent(itemView.getContext(), DownloadDocumentActivity.class);
                itemView.getContext().startActivity(intent);
            } else if (id.equals("Application")) {
                Log.d("Application", "kkk" + id);
                Intent intent = new Intent(itemView.getContext(), ApplicationFormActivityMenu.class);
                intent.putExtra("keyName", "Application");
                itemView.getContext().startActivity(intent);
            }
        }
    }
}
