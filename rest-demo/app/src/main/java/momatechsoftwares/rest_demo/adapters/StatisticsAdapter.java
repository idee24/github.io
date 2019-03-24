package momatechsoftwares.rest_demo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import momatechsoftwares.rest_demo.R;
import momatechsoftwares.rest_demo.networking.models.Statistics;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder>{

    private List<Statistics> statisticsList;
    private Context context;
    private LayoutInflater layoutInflater;

    public StatisticsAdapter(List<Statistics> statisticsList, Context context) {
        this.statisticsList = statisticsList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.statistics_item, parent, false);
        return new StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, int position) {
        Statistics statistics = statisticsList.get(position);
        holder.subjectNameTextView.setText(statistics.getSubjectName());
        holder.classNameTextView.setText(statistics.getClassName());
        holder.percentageTextView.setText(statistics.getPercentage());
        holder.progressBar.setProgress(statistics.getProgress());
    }

    @Override
    public int getItemCount() {
        return statisticsList.size();
    }

    class StatisticsViewHolder extends RecyclerView.ViewHolder{

        private TextView subjectNameTextView;
        private TextView classNameTextView;
        private TextView percentageTextView;
        private ProgressBar progressBar;

        StatisticsViewHolder(View itemView) {
            super(itemView);

            subjectNameTextView = itemView.findViewById(R.id.subjectNameTextView);
            classNameTextView = itemView.findViewById(R.id.classNameTextView);
            percentageTextView = itemView.findViewById(R.id.percentageTextView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
