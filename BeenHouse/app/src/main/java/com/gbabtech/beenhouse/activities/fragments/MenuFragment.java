package com.gbabtech.beenhouse.activities.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gbabtech.beenhouse.R;
import com.gbabtech.beenhouse.activities.MainMenuAdapter;
import butterknife.ButterKnife;

public class MenuFragment extends Fragment {

    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View currentView = getView();
        assert currentView != null;
        RecyclerView menuRecyclerView = currentView.findViewById(R.id.menuRecyclerView);
        initRecyclerView(menuRecyclerView);
    }

    private void initRecyclerView(RecyclerView menuRecyclerView) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        menuRecyclerView.setLayoutManager(layoutManager);

        MainMenuAdapter adapter = new MainMenuAdapter();
        menuRecyclerView.setAdapter(adapter);
    }
}
