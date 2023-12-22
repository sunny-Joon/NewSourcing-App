package com.paisalo.newinternalsourcingapp.Fragments.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    float progress = 0.7f;

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

       /* ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) binding.divider.getLayoutParams();

        layoutParams.horizontalBias = 0.8f;

        binding.divider.setLayoutParams(layoutParams);*/

        // Assuming you have references to both the divider and TextView
        View divider = binding.divider;
        TextView incentiveTextView = binding.IncentiveTextView;

        ConstraintLayout.LayoutParams dividerLayoutParams = (ConstraintLayout.LayoutParams) divider.getLayoutParams();

        if (dividerLayoutParams == null) {
            dividerLayoutParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
        }

        dividerLayoutParams.horizontalBias = progress;
        divider.setLayoutParams(dividerLayoutParams);
        ConstraintLayout.LayoutParams textViewLayoutParams = (ConstraintLayout.LayoutParams) incentiveTextView.getLayoutParams();
        if (textViewLayoutParams == null) {
            textViewLayoutParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
        }

        textViewLayoutParams.horizontalBias = progress;
        incentiveTextView.setLayoutParams(textViewLayoutParams);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
