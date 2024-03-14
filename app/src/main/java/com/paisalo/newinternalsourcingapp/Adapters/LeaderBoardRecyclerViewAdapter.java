package com.paisalo.newinternalsourcingapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardEntry entry = leaderboardEntries.get(position);
        holder.bind(entry);

        if (entry.getsno().equals("9")) {
            int whiteColor = context.getResources().getColor(R.color.white);
            int transparentWhite = Color.argb(150, Color.red(whiteColor), Color.green(whiteColor), Color.blue(whiteColor));

            holder.linearlayout.setBackgroundColor(transparentWhite);

        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return leaderboardEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearlayout;
        private TextView snoTextView;
        private TextView nameTextView;
        private TextView scoreTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearlayout = itemView.findViewById(R.id.itemlayout);
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
