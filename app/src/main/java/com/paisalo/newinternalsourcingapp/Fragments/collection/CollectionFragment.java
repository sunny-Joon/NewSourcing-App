package com.paisalo.newinternalsourcingapp.Fragments.collection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paisalo.newinternalsourcingapp.Adapters.CollectionAdapter;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.databinding.FragmentCollectionBinding;

public class CollectionFragment extends Fragment {

    private CollectionAdapter collectionAdapter;
    private RecyclerView recyclerView;
    FragmentCollectionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create ViewModel
        CollectionViewModel collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);

        // Inflate the binding layout
        binding = FragmentCollectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.applicationFormRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Example data (replace with your actual data)
        String key = "YourKey";
        collectionAdapter = new CollectionAdapter(requireContext(), "David", "Sam", "001", "4545454545", "Mumbai", "NewDelhi");
        recyclerView.setAdapter(collectionAdapter);

        return root;
    }
}
