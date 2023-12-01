package com.paisalo.newinternalsourcingapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.Modelclasses.LeaderboardEntry;
import com.paisalo.newinternalsourcingapp.R;

import java.util.List;

public class LeaderBoardRecyclerViewAdapter extends RecyclerView.Adapter<LeaderBoardRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<LeaderboardEntry> leaderboardEntries;

    public LeaderBoardRecyclerViewAdapter(Context context, List<LeaderboardEntry> leaderboardEntries) {
        this.context = context;
        this.leaderboardEntries = leaderboardEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leaderboard_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardEntry entry = leaderboardEntries.get(position);
        holder.bind(entry);

        // Check if the current item's sno is 9 and change the background color
        if (entry.getsno().equals("9")) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.green)); // Use your color resource here
        } else {
            // Reset the background color for other items
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return leaderboardEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView snoTextView;
        private TextView nameTextView;
        private TextView scoreTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            snoTextView = itemView.findViewById(R.id.Snumber);
            nameTextView = itemView.findViewById(R.id.name);
            scoreTextView = itemView.findViewById(R.id.points);
        }

        public void bind(LeaderboardEntry entry) {
            snoTextView.setText(entry.getsno());
            nameTextView.setText(entry.getuserName());
            scoreTextView.setText(entry.getpoints());
        }
    }
}
