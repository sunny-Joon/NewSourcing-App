package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.paisalo.newinternalsourcingapp.Activities.ManagerList;
import com.paisalo.newinternalsourcingapp.databinding.FragmentOnBoardBinding;


public class OnBoardFragment extends Fragment {

    FragmentOnBoardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OnBoardViewModel viewModel=new ViewModelProvider(this).get(OnBoardViewModel.class);
        binding=FragmentOnBoardBinding.inflate(inflater,container,false);

        View root=binding.getRoot();

        binding.kyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kycIntent = new Intent(requireActivity(), ManagerList.class);
                kycIntent.putExtra("keyName", "KYC");
                startActivity(kycIntent);
            }
        });

        binding.esign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireActivity(), ManagerList.class);
                intent.putExtra("keyName", "Esign");
                startActivity(intent);

            }
        });
        binding.application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(requireActivity(), ManagerList.class);
                intent.putExtra("keyName", "Application");
                startActivity(intent);
            }
        });

        binding.housevisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), ManagerList.class);
                intent.putExtra("keyName", "HVisit");
                startActivity(intent);
            }
        });

        binding.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), ManagerList.class);
                intent.putExtra("keyName", "Collection");
                startActivity(intent);
            }
        });

        return root;
    }
}