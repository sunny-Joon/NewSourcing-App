package com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.paisalo.newinternalsourcingapp.Adapters.KycRecyclerViewAdapter;
import com.paisalo.newinternalsourcingapp.Modelclasses.KYCScanningModel;
import com.paisalo.newinternalsourcingapp.R;

import java.util.ArrayList;
import java.util.List;

public class KycScanningFragment extends Fragment {

    Context context;

    private KycRecyclerViewAdapter kycRecyclerViewAdapter;

    public KycScanningFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<KYCScanningModel> kycScanningList = new ArrayList<>();
        kycScanningList.add(new KYCScanningModel("John Doe", "Personal", "Aadhaar","Front"));
        kycScanningList.add(new KYCScanningModel("Jane Doe", "Business", "Aadhaar", "Back"));
        kycScanningList.add(new KYCScanningModel("John Doe", "Personal", "Voter Id","front"));
        kycScanningList.add(new KYCScanningModel("Jane Doe", "Business", "VoterId", "Back"));
        kycScanningList.add(new KYCScanningModel("John Doe", "Personal", "PassBook","First Page"));
        kycScanningList.add(new KYCScanningModel("John Doe", "Personal", "Passbook","Last Page"));
        kycScanningList.add(new KYCScanningModel("Jane Doe", "Business", "Co-Borrower Aadhaar", "Front"));
        kycScanningList.add(new KYCScanningModel("John Doe", "Personal", "Co-Borrower Aadhaar","Back"));

        kycRecyclerViewAdapter = new KycRecyclerViewAdapter(requireContext(),kycScanningList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kyc_scanning, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.kycScanningRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(kycRecyclerViewAdapter);

        return view;
    }
}
