package com.paisalo.newinternalsourcingapp.Adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.Activities.ActivityCollection;
import com.paisalo.newinternalsourcingapp.Activities.BorrowerListActivity;
import com.paisalo.newinternalsourcingapp.Activities.EsignListActivity;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;

import com.paisalo.newinternalsourcingapp.R;

import com.paisalo.newinternalsourcingapp.RoomDatabase.ManagerListDataClass;

import java.util.List;

public class ManagerListAdapter extends RecyclerView.Adapter<ManagerListAdapter.MyViewHolder> {
    private List<ManagerListDataClass> dataList;
    ManagerListDataClass data;
    private Context context;
    String foCode,areaCode,creator,id;
    SharedPreferences sharedPreferences1;
    String branchStatus,branchDisbursement,branchCrifScore,branchFunctions;
    public ManagerListAdapter(Context context, List<ManagerListDataClass> dataList,String moduleName) {
        this.context = context;
        this.dataList = dataList;
        this.id=moduleName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.managerlist_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        data = dataList.get(position);

        holder.nameTextView.setText(data.getFoName());
        holder.placeGroupCodeTextView.setText(data.getAreaName()+"/"+data.getAreaCd());
        holder.branchCreatorTextView.setText(data.getFoCode()+"/"+data.getCreator());

        holder.managerCardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (position != RecyclerView.NO_POSITION) {
                    ManagerListDataClass dataModel = dataList.get(position);

                    foCode = dataModel.getFoCode();
                    creator = dataModel.getCreator();
                    areaCode = dataModel.getAreaCd();
                    openActivity(id,foCode,creator,areaCode);
                }
            }
        });

        if (position % 2 == 1) {
            holder.managerCardView.setBackgroundResource(R.drawable.managerlist_gradientgrey);
        } else {
            holder.managerCardView.setBackgroundResource(R.drawable.managerlist_gradient);
        }
    }


    private void openActivity(String id,String foCode,String creator,String areaCode) {
        if (id.equals("KYC")) {
            Log.d("Kyc","kkk"+id);
            Intent intent = new Intent(context, KYCActivity.class);
            intent.putExtra("foCode", foCode);
            intent.putExtra("creator", creator);
            intent.putExtra("areaCode", areaCode);
            context.startActivity(intent);
        }else if (id.equals("Application")) {
            Log.d("Application", "kkk" + id);
            Intent intent = new Intent(context, BorrowerListActivity.class);
            intent.putExtra("keyName", "Application");
            intent.putExtra("foCode", foCode);
            intent.putExtra("creator", creator);
            intent.putExtra("areaCode", areaCode);
            context.startActivity(intent);
        }
        else if (id.equals("Esign")) {
            showPopup();

        }else if (id.equals("HVisit")) {
            Log.d("Hvisit","kkk"+id);
            Intent intent = new Intent(context, BorrowerListActivity.class);
            intent.putExtra("keyName", "HVisit");
            intent.putExtra("foCode", foCode);
            intent.putExtra("creator", creator);
            intent.putExtra("areaCode", areaCode);
            context.startActivity(intent);
        }else if (id.equals("Collection")) {
            Log.d("Collection","kkk"+id);
            Intent intent = new Intent(context, ActivityCollection.class);
            intent.putExtra("foCode", foCode);
            intent.putExtra("creator", creator);
            intent.putExtra("areaCode", areaCode);
            context.startActivity(intent);
        }

    }




    public void showPopup() {

        sharedPreferences1 = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        branchStatus = sharedPreferences1.getString("branch_status", "DefaultStatus");
        branchDisbursement = sharedPreferences1.getString("branch_disbursement", "DefaultDisbursement");
        branchCrifScore = sharedPreferences1.getString("branch_crifScore", "DefaultCrifScore");
        branchFunctions = sharedPreferences1.getString("branch_functions", "DefaultFunctions");

        Log.d("TAG", "Branch Status: " + branchStatus);
        Log.d("TAG", "Branch Disbursement: " + branchDisbursement);
        Log.d("TAG", "Branch CrifScore: " + branchCrifScore);
        Log.d("TAG", "Branch Functions: " + branchFunctions);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View alertView = LayoutInflater.from(context).inflate(R.layout.loanpopup, null);
        builder.setView(alertView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Optionally, set the size of the alert dialog window
        // alertDialog.getWindow().setLayout(600, 600);

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Handle dialog dismiss if needed
            }
        });

        // Use alertView to find the CardView
        CardView application = alertView.findViewById(R.id.loanApplication);
      //  if (application != null) {
            application.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (branchFunctions.equals("1")) {
                        new androidx.appcompat.app.AlertDialog.Builder(context)
                                .setTitle("")
                                .setMessage("This branch is under trigger.\\nPlease contact to your VP")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (context instanceof Activity) {
                                            ((Activity) context).finish();
                                        }
                                    }
                                })
                                .show();
                    } else {


                        Intent intent = new Intent(context, EsignListActivity.class);
                        intent.putExtra("keyName", "FEsign");
                        intent.putExtra("foCode", foCode);
                        intent.putExtra("creator", creator);
                        intent.putExtra("areaCode", areaCode);
                        context.startActivity(intent);
                        alertDialog.dismiss();
                    }
                }
            });
        /*} else {
            Log.e("ManagerListAdapter", "CardView loanApplication not found");
        }*/

        CardView documentation = alertView.findViewById(R.id.loanDocumentation);
        if (documentation != null) {
            documentation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (branchFunctions.equals("2")) {
                        new androidx.appcompat.app.AlertDialog.Builder(context)
                                .setTitle("")
                                .setMessage("This branch is under trigger.\\nPlease contact to your VP")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (context instanceof Activity) {
                                            ((Activity) context).finish();
                                        }
                                    }
                                })
                                .show();
                    } else {

                        Intent intent = new Intent(context, EsignListActivity.class);
                        intent.putExtra("keyName", "SEsign");
                        intent.putExtra("foCode", foCode);
                        intent.putExtra("creator", creator);
                        intent.putExtra("areaCode", areaCode);
                        context.startActivity(intent);
                        alertDialog.dismiss();
                    }
                }
            });
        } else {
            Log.e("ManagerListAdapter", "CardView loanDocumentation not found");
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
        }




    }
}