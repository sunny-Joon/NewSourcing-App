package com.paisalo.newinternalsourcingapp.Fragments.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.Adapters.LeaderBoardRecyclerViewAdapter;
import com.paisalo.newinternalsourcingapp.Modelclasses.LeaderboardEntry;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.databinding.FragmentLeaderBoardBinding;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardFragment extends Fragment {

    private RecyclerView recyclerView;
    private LeaderBoardRecyclerViewAdapter leaderBoardRecyclerViewAdapter;
    FragmentLeaderBoardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LeaderBoardViewModel leaderBoardViewModel = new ViewModelProvider(this).get(LeaderBoardViewModel.class);
        binding = FragmentLeaderBoardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.leaderboardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Example data (replace with your actual data)
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        leaderboardEntries.add(new LeaderboardEntry("1", "Mohit", "3000"));
        leaderboardEntries.add(new LeaderboardEntry("2", "Alice", "2800"));
        leaderboardEntries.add(new LeaderboardEntry("3", "Bob", "2500"));
        leaderboardEntries.add(new LeaderboardEntry("4", "Charlie", "2200"));
        leaderboardEntries.add(new LeaderboardEntry("5", "David", "2000"));
        leaderboardEntries.add(new LeaderboardEntry("6", "Eve", "1800"));
        leaderboardEntries.add(new LeaderboardEntry("7", "Frank", "1600"));
        leaderboardEntries.add(new LeaderboardEntry("8", "Grace", "1400"));
        leaderboardEntries.add(new LeaderboardEntry("9", "Hank", "1200"));
        leaderboardEntries.add(new LeaderboardEntry("10", "Ivy", "1000"));
        leaderboardEntries.add(new LeaderboardEntry("11", "Eve", "1800"));
        leaderboardEntries.add(new LeaderboardEntry("12", "Frank", "1600"));
        leaderboardEntries.add(new LeaderboardEntry("13", "Grace", "1400"));
        leaderboardEntries.add(new LeaderboardEntry("14", "Hank", "1200"));
        leaderboardEntries.add(new LeaderboardEntry("15", "Ivy", "1000"));

        leaderBoardRecyclerViewAdapter = new LeaderBoardRecyclerViewAdapter(requireContext(), leaderboardEntries);
        recyclerView.setAdapter(leaderBoardRecyclerViewAdapter);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) binding.divider.getLayoutParams();

        layoutParams.horizontalBias = 0.8f;

        binding.divider.setLayoutParams(layoutParams);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
