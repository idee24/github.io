package momatechsoftwares.rest_demo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import momatechsoftwares.rest_demo.R;
import momatechsoftwares.rest_demo.adapters.PopAdapter;
import momatechsoftwares.rest_demo.networking.models.ItemModel;

public class CustomActivity extends AppCompatActivity {

    public RecyclerView popRecyclerView;
    private List<ItemModel> itemModels = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        popRecyclerView = findViewById(R.id.popRecyclerView);

        initRecyclerView();
    }

    private void initRecyclerView() {
        for (int i = 0; i < 10; i++) {
            itemModels.add(new ItemModel());
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        popRecyclerView.setLayoutManager(layoutManager);

        PopAdapter adapter = new PopAdapter(this, itemModels);

        popRecyclerView.setItemAnimator(new DefaultItemAnimator());
        popRecyclerView.setAdapter(adapter);
    }

    public void popRecyclerItem(int position){

        itemModels.remove(position);
        popRecyclerView.getAdapter().notifyItemRemoved(position);
        popRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
