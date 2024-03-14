package com.paisalo.newinternalsourcingapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.Activities.BorrowerListActivity;
import com.paisalo.newinternalsourcingapp.Activities.DownloadDocumentActivity;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.HouseVisitActivity1;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
import com.paisalo.newinternalsourcingapp.Modelclasses.ManagerModel;
import com.paisalo.newinternalsourcingapp.R;

import java.util.List;

public class ManagerListAdapter extends RecyclerView.Adapter<ManagerListAdapter.MyViewHolder> {
    private List<ManagerModel> dataList;
    private Context context;

    public ManagerListAdapter(Context context, List<ManagerModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.managerlist_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ManagerModel data = dataList.get(position);

        holder.nameTextView.setText(data.getName());
        holder.placeGroupCodeTextView.setText(data.getPlaceGroupCode());
        holder.branchCreatorTextView.setText(data.getBranchCreator());

        if (position % 2 == 1) {
            holder.managerCardView.setBackgroundResource(R.drawable.managerlist_gradientgrey);
        } else {
            holder.managerCardView.setBackgroundResource(R.drawable.managerlist_gradient);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView placeGroupCodeTextView;
        TextView branchCreatorTextView;
        CardView managerCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            managerCardView = itemView.findViewById(R.id.managerCardView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            placeGroupCodeTextView = itemView.findViewById(R.id.placeGroupCode1TextView);
            branchCreatorTextView = itemView.findViewById(R.id.BranchCreator);

            Intent intent = ((Activity) itemView.getContext()).getIntent();
            String id = intent.getStringExtra("keyName");

            managerCardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    openActivity(id);
                }
            });
        }

        private void openActivity(String id) {
            if (id.equals("KYC")) {
                Log.d("Kyc","kkk"+id);
                Intent intent = new Intent(itemView.getContext(), KYCActivity.class);
                itemView.getContext().startActivity(intent);
            }else if (id.equals("Application")) {
                Log.d("Application", "kkk" + id);
                Intent intent = new Intent(itemView.getContext(), BorrowerListActivity.class);
                intent.putExtra("keyName", "Application");
                itemView.getContext().startActivity(intent);
            }
            else if (id.equals("Esign")) {

                showPopup();
                /*Log.d("Esign","kkk"+id);
                Intent intent = new Intent(itemView.getContext(), BorrowerListActivity.class);
                intent.putExtra("keyName", "Esign");
                itemView.getContext().startActivity(intent);*/
            }else if (id.equals("HVisit")) {
                Log.d("Hvisit","kkk"+id);
                Intent intent = new Intent(itemView.getContext(), HouseVisitActivity1.class);
                itemView.getContext().startActivity(intent);
            }

        }
        public void showPopup() {
            View popupView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.loanpopup, null);

            int width = 600;
            int height = 600;

            PopupWindow popupWindow = new PopupWindow(
                    popupView, width, height
            );

            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            CardView application = popupView.findViewById(R.id.loanApplication);
            application.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

           CardView documentation = popupView.findViewById(R.id.loanDocumentation);
            documentation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DownloadDocumentActivity.class);
                    itemView.getContext().startActivity(intent);
                    popupWindow.dismiss();
                }
            });
        }

    }
}