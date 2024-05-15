package com.paisalo.newinternalsourcingapp.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.paisalo.newinternalsourcingapp.Activities.BorrowerListActivity;
import com.paisalo.newinternalsourcingapp.Activities.CustomerListActivity;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.RoomDatabase.ManagerListDataClass;

import java.util.List;

public class ManagerListAdapter extends RecyclerView.Adapter<ManagerListAdapter.MyViewHolder> {
    private List<ManagerListDataClass> dataList;
    private Context context;
    String foCode,areaCode,creator;

    public ManagerListAdapter(Context context, List<ManagerListDataClass> dataList) {
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
        ManagerListDataClass data = dataList.get(position);

        holder.nameTextView.setText(data.getFoName());
        holder.placeGroupCodeTextView.setText(data.getAreaName()+"/"+data.getAreaCd());
        holder.branchCreatorTextView.setText(data.getFoCode()+"/"+data.getCreator());

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

            managerCardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ManagerListDataClass dataModel = dataList.get(position);
                        Intent intent = ((Activity) itemView.getContext()).getIntent();
                        String id = intent.getStringExtra("keyName");
                        foCode = dataModel.getFoCode();
                        creator = dataModel.getCreator();
                        areaCode = dataModel.getAreaCd();
                        openActivity(id,foCode,creator,areaCode);
                    }
                }
            });
        }

        private void openActivity(String id,String foCode,String creator,String areaCode) {
            if (id.equals("KYC")) {
                Log.d("Kyc","kkk"+id);
                Intent intent = new Intent(itemView.getContext(), KYCActivity.class);
                intent.putExtra("foCode", foCode);
                intent.putExtra("creator", creator);
                intent.putExtra("areaCode", areaCode);
                itemView.getContext().startActivity(intent);
            }else if (id.equals("Application")) {
                Log.d("Application", "kkk" + id);
                Intent intent = new Intent(itemView.getContext(), BorrowerListActivity.class);
                intent.putExtra("keyName", "Application");
                intent.putExtra("foCode", foCode);
                intent.putExtra("creator", creator);
                intent.putExtra("areaCode", areaCode);
                itemView.getContext().startActivity(intent);
            }
            else if (id.equals("Esign")) {
                showPopup();

            }else if (id.equals("HVisit")) {
                Log.d("Hvisit","kkk"+id);
                Intent intent = new Intent(itemView.getContext(), BorrowerListActivity.class);
                intent.putExtra("keyName", "HV");
                intent.putExtra("foCode", foCode);
                intent.putExtra("creator", creator);
                intent.putExtra("areaCode", areaCode);
                itemView.getContext().startActivity(intent);
            }else if (id.equals("Collection")) {
                Log.d("Collection","kkk"+id);
                Intent intent = new Intent(itemView.getContext(), CustomerListActivity.class);
                itemView.getContext().startActivity(intent);
            }

        }
        public void showPopup() {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setView(R.layout.loanpopup);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            //   alertDialog.getWindow().setLayout(600, 600);

            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                }
            });

            CardView application = alertDialog.findViewById(R.id.loanApplication);
            application.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), BorrowerListActivity.class);
                    intent.putExtra("keyName", "FEsign");
                    intent.putExtra("foCode", foCode);
                    intent.putExtra("creator", creator);
                    intent.putExtra("areaCode", areaCode);
                    itemView.getContext().startActivity(intent);
                    alertDialog.dismiss();
                }
            });

            CardView documentation = alertDialog.findViewById(R.id.loanDocumentation);
            documentation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Intent intent = new Intent(itemView.getContext(), SecondEsignActivity.class);
                    Intent intent = new Intent(itemView.getContext(), BorrowerListActivity.class);
                    intent.putExtra("keyName", "SEsign");
                    intent.putExtra("foCode", foCode);
                    intent.putExtra("creator", creator);
                    intent.putExtra("areaCode", areaCode);
                    itemView.getContext().startActivity(intent);
                    itemView.getContext().startActivity(intent);
                    alertDialog.dismiss();
                }
            });
        }

    }
}