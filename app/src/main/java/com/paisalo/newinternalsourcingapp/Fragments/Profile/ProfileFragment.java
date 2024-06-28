package com.paisalo.newinternalsourcingapp.Fragments.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.paisalo.newinternalsourcingapp.Activities.CollectionReportActivity;
import com.paisalo.newinternalsourcingapp.Activities.QrPayments;
import com.paisalo.newinternalsourcingapp.Activities.ReferAndEanActivity;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.databinding.FragmentProfileBinding;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    TextView username,creator,id,address;
    CardView cvCollectionReport,cvQrPayments;
    Button buttonprofile;
    ConstraintLayout referAndEarn;
    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ProfileViewModel profileViewModel=new ViewModelProvider(this).get(ProfileViewModel.class);
        binding=FragmentProfileBinding.inflate(inflater,container,false);
        View root=binding.getRoot();

        buttonprofile = root.findViewById(R.id.buttonprofile);
        referAndEarn = root.findViewById(R.id.referAndEarn);
        username = root.findViewById(R.id.username);
        creator = root.findViewById(R.id.creator);
        id = root.findViewById(R.id.id);
        address = root.findViewById(R.id.address);
        cvQrPayments = root.findViewById(R.id.cvQrPayments);
        cvCollectionReport = root.findViewById(R.id.cvCollectionReport);



        username.setText(GlobalClass.UserName);
        creator.setText(GlobalClass.Creator);
        id.setText(GlobalClass.Id);
        address.setText(GlobalClass.Address);


        cvQrPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentQrPayments = new Intent(getActivity(), QrPayments.class);
                intentQrPayments.putExtra("UserID",GlobalClass.Id);
                startActivity(intentQrPayments);
            }
        });

        cvCollectionReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentcollectionreport = new Intent(getActivity(), CollectionReportActivity.class);
                //intentcollectionreport.putExtra("UserID",IglPreferences.getPrefString(this, SEILIGL.USER_ID, ""));
                startActivity(intentcollectionreport);
            }
        });

        buttonprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReferAndEanActivity.class);
                startActivity(intent);
                // fragmentManager.beginTransaction().replace(R.id.frameLayout, new ReferAndEarnFragment(fragmentManager))
                //   .commit();
            }
        });

        return root;
    }
}