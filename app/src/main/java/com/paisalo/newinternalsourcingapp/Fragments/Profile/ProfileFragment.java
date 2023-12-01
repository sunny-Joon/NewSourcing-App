package com.paisalo.newinternalsourcingapp.Fragments.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.databinding.FragmentProfileBinding;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ProfileViewModel profileViewModel=new ViewModelProvider(this).get(ProfileViewModel.class);
        binding=FragmentProfileBinding.inflate(inflater,container,false);
        View root=binding.getRoot();


        // Inflate the layout for this fragment
        return root;
    }
}