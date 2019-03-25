package com.gbabtech.beenhouse.activities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gbabtech.beenhouse.R;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder> {

    public MainMenuAdapter () {}

    @NonNull
    @Override
    public MainMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.main_menu_item, viewGroup, false);
        return new MainMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuViewHolder mainMenuViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MainMenuViewHolder extends RecyclerView.ViewHolder {

        MainMenuViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
