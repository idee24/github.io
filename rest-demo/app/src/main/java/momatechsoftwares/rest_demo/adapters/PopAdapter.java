package momatechsoftwares.rest_demo.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import momatechsoftwares.rest_demo.R;
import momatechsoftwares.rest_demo.activities.CustomActivity;
import momatechsoftwares.rest_demo.networking.models.ItemModel;

public class PopAdapter extends RecyclerView.Adapter<PopAdapter.PopViewHolder> {

    private CustomActivity activity;
    private List<ItemModel> itemModels;

    public PopAdapter(CustomActivity activity,
                      List<ItemModel> itemModels) {
        this.activity = activity;
        this.itemModels = itemModels;
    }

    @NonNull
    @Override
    public PopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pop_item, parent, false);
        return new PopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopViewHolder holder, int position) {
        String title = "Item " + position;
        holder.itemTitleTextView.setText(title);

        holder.headerLayout.setOnClickListener(view -> {
            if (holder.childLayout.getVisibility() == View.GONE) {
                holder.childLayout.setVisibility(View.VISIBLE);
            } else {
                holder.childLayout.setVisibility(View.GONE);
            }

        });

        holder.deleteIcon.setOnClickListener(view -> activity.popRecyclerItem(position));
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    class PopViewHolder extends RecyclerView.ViewHolder{

        private ImageView deleteIcon;
        private ConstraintLayout childLayout, headerLayout;
        private TextView itemTitleTextView;

        PopViewHolder(View itemView) {
            super(itemView);
            headerLayout = itemView.findViewById(R.id.headerLayout);
            itemTitleTextView = itemView.findViewById(R.id.itemTitleTextView);
            deleteIcon = itemView.findViewById(R.id.deleteIconImageView);
            childLayout = itemView.findViewById(R.id.childLayout);
        }
    }
}
