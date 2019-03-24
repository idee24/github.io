package momatechsoftwares.rest_demo.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import momatechsoftwares.rest_demo.R;
import momatechsoftwares.rest_demo.networking.models.Statistics;

public class StatisticsExpandableListAdapter implements ExpandableListAdapter {

    private Context context;
    private List<Statistics> statisticsObjects;
    private List<String> statisticsHeaders;

    public StatisticsExpandableListAdapter(final Context context,
                                           final List<Statistics> statisticsObjects,
                                           final List<String> statisticsHeaders) {
        this.context = context;
        this.statisticsObjects = statisticsObjects;
        this.statisticsHeaders = statisticsHeaders;

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return statisticsHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return statisticsObjects.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return statisticsObjects.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null){
            System.out.println("22 Gbab Group");
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.statistics_header, null);
            convertView.setHorizontalScrollBarEnabled(true);
        }
        System.out.println("Gbab: Headers 3  \n\n" + statisticsHeaders.size());
        String statisticsHeader = statisticsHeaders.get(groupPosition);
        TextView statisticsHeaderTextView = convertView.findViewById(R.id.statisticsHeaderTextView);
        statisticsHeaderTextView.setText(statisticsHeader);
        statisticsHeaderTextView.setTypeface(null, Typeface.BOLD);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null){
            System.out.println("11 Gbaab Child");
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.statistics_group_child, null);
        }

        RecyclerView statisticsRecyclerView = convertView.findViewById(R.id.statisticsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        statisticsRecyclerView.setLayoutManager(layoutManager);

        StatisticsAdapter adapter = new StatisticsAdapter(statisticsObjects, context);
        statisticsRecyclerView.setAdapter(adapter);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
