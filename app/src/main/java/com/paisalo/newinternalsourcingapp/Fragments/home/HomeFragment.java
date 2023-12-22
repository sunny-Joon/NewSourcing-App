package com.paisalo.newinternalsourcingapp.Fragments.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.paisalo.newinternalsourcingapp.Activities.IncentiveAnimatedActivity;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Utils.GlobalClass;
import com.paisalo.newinternalsourcingapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Dialog popupDialog;

    public static Fragment newInstance(int position) {
        HomeFragment homeFragment=new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.backgroundImageView.setVisibility(View.GONE);

        binding.txtCalcIncentive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.backgroundImageView.setVisibility(View.VISIBLE);

                popupDialog = GlobalClass.showBlurredPopup(requireContext(), root, binding.backgroundImageView, R.layout.incentive_popup);

                new CountDownTimer(2000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        startActivity(new Intent(getActivity(), IncentiveAnimatedActivity.class));
                        binding.backgroundImageView.setVisibility(View.GONE);
                        GlobalClass.dismissPopup(popupDialog);                    }
                }.start();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}