package com.paisalo.newinternalsourcingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiFamMem;
import com.paisalo.newinternalsourcingapp.R;
import java.util.List;

public class FaimlyMemberIncomeAdapter extends RecyclerView.Adapter<FaimlyMemberIncomeAdapter.ViewHolder>  {

    private Context context;
    private List<FiFamMem> memberincomelist;


    public FaimlyMemberIncomeAdapter(Context context, List<FiFamMem> memberincomelist) {
        this.context = context;
        this.memberincomelist = memberincomelist;
    }

    @NonNull
    @Override
    public FaimlyMemberIncomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fammem_income_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FaimlyMemberIncomeAdapter.ViewHolder viewHolder, int position) {

        FiFamMem fiFamMem = memberincomelist.get(position);
        viewHolder.membername.setText(fiFamMem.getMemName() != null ? fiFamMem.getMemName() : "N/A");
        viewHolder.memgender.setText(fiFamMem.getGender() != null ? fiFamMem.getGender() : "N/A");

        if (fiFamMem.getAge() != null) {
            viewHolder.memage.setText(String.valueOf(fiFamMem.getAge()));
        } else {
            viewHolder.memage.setText("N/A");
        }

        if (fiFamMem.getIncome() != null) {
            viewHolder.memincome.setText(String.valueOf(fiFamMem.getIncome()));
        } else {
            viewHolder.memincome.setText("N/A");
        }

        viewHolder.memeducation.setText(fiFamMem.getEducatioin() != null ? fiFamMem.getEducatioin() : "N/A");
        viewHolder.memschooltype.setText(fiFamMem.getSchoolType() != null ? fiFamMem.getSchoolType() : "N/A");
        viewHolder.membusiness.setText(fiFamMem.getBusiness() != null ? fiFamMem.getBusiness() : "N/A");
        viewHolder.memhealth.setText(fiFamMem.getHealth() != null ? fiFamMem.getHealth() : "N/A");
        viewHolder.memrelationship.setText(fiFamMem.getRelationWBorrower() != null ? fiFamMem.getRelationWBorrower() : "N/A");
        viewHolder.membusinesstype.setText(fiFamMem.getBusinessType() != null ? fiFamMem.getBusinessType() : "N/A");
       // viewHolder.memincome.setText(fiFamMem.getIncome() != null ? fiFamMem.getIncome() : "N/A");
        viewHolder.memincometype.setText(fiFamMem.getIncomeType() != null ? fiFamMem.getIncomeType() : "N/A");
    }

    @Override
    public int getItemCount() {
        return memberincomelist.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView membername, memgender, memage, memeducation, memhealth,
                memschooltype,membusiness,memrelationship,membusinesstype,
                memincome,memincometype;
        CardView femmemincomecardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            femmemincomecardview= itemView.findViewById(R.id.femmemincomecardview);
            membername= itemView.findViewById(R.id.membername);
            memgender= itemView.findViewById(R.id.memgender);
            memage= itemView.findViewById(R.id.memage);
            memeducation= itemView.findViewById(R.id.memeducation);
            memhealth= itemView.findViewById(R.id.memhealth);
            memschooltype= itemView.findViewById(R.id.memschooltype);
            membusiness= itemView.findViewById(R.id.membusiness);
            memrelationship= itemView.findViewById(R.id.memrelationship);
            membusinesstype= itemView.findViewById(R.id.membusinesstype);
            memincome= itemView.findViewById(R.id.memincome);
            memincometype= itemView.findViewById(R.id.memincometype);


        }
    }
}
