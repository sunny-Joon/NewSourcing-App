package com.paisalo.newinternalsourcingapp.Fragments.leaderboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paisalo.newinternalsourcingapp.Adapters.LeaderBoardRecyclerViewAdapter;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.databinding.FragmentLeaderBoardBinding;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LeaderBoardFragment extends Fragment {

    private RecyclerView recyclerView;
    String date="";

    private LeaderBoardRecyclerViewAdapter leaderBoardRecyclerViewAdapter;
    FragmentLeaderBoardBinding binding;
    float progress = 0.7f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLeaderBoardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView progressEnd = root.findViewById(R.id.progressEnd);
        recyclerView = root.findViewById(R.id.leaderboardRecyclerView);
        TextView preparingMsg = root.findViewById(R.id.preparingMsg);
        ProgressBar progressBar = root.findViewById(R.id.progresBar);
        CardView progressCardView = root.findViewById(R.id.progressCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String position = sharedPreferences.getString("position", "-1");
        String progresEnd = sharedPreferences.getString("progressEnd", null);

        Log.d("TAG", "onCreateView:zz "+ position);
        Log.d("TAG", "onCreateView:zz "+ progresEnd);

        if(progresEnd != null){
            progressEnd.setText(progresEnd);
        }

        String leaderboardEntriesJson = sharedPreferences.getString("leaderboardEntries", null);
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        if (leaderboardEntriesJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<LeaderboardEntry>>() {}.getType();
            leaderboardEntries = gson.fromJson(leaderboardEntriesJson, type);
        }
        Log.d("TAG", "onCreateView:zz "+ leaderboardEntries);

        leaderBoardRecyclerViewAdapter = new LeaderBoardRecyclerViewAdapter(getActivity(), leaderboardEntries,position);
        recyclerView.setAdapter(leaderBoardRecyclerViewAdapter);
        leaderBoardRecyclerViewAdapter.notifyDataSetChanged();

        View divider = root.findViewById(R.id.divider);
        TextView incentiveTextView = root.findViewById(R.id.IncentiveTextView);

        Calendar calendar = Calendar.getInstance();
        date = new SimpleDateFormat("dd", Locale.getDefault()).format(calendar.getTime());
        Log.d("TAG_date", "onCreateViewDate: " + date);
        if(Integer.parseInt(date)<6){
            Log.d("TAG", "onCreateViewDate: "+Integer.parseInt(date) );
            progressCardView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            preparingMsg.setVisibility(View.VISIBLE);
        }

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
