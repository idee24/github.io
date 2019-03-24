package momatechsoftwares.rest_demo.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;

import momatechsoftwares.rest_demo.R;
import momatechsoftwares.rest_demo.networking.models.TimeTable;

public class TimeTableAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<String> timeTableHeaders;
    private LinkedHashMap<String, List<TimeTable>> timetables;

    public TimeTableAdapter (final Context context,
                             final List<String> timeTableHeaders,
                             final LinkedHashMap<String, List<TimeTable>> timetables){
        this.context = context;
        this.timeTableHeaders = timeTableHeaders;
        this.timetables = timetables;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return timetables.get(timeTableHeaders.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.time_table_item, null);
        }
            final TimeTable timeTable = (TimeTable) getChild(groupPosition, childPosition);

            TextView subjectTextView = convertView.findViewById(R.id.subjectTextView);
            TextView classNameTextView = convertView.findViewById(R.id.classTextView);
            TextView periodTextView = convertView.findViewById(R.id.periodTextView);
            TextView linkTextView = convertView.findViewById(R.id.linkTextView);

            subjectTextView.setText(timeTable.getSubjectName());
            classNameTextView.setText(timeTable.getClassName());
            periodTextView.setText(timeTable.getPeriod());
            linkTextView.setText(timeTable.getLink());

            return convertView;
    }

    @Override
    public Object getGroup(int groupPosition)  {
        return timeTableHeaders.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {

        String timeTableHeader = timeTableHeaders.get(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.time_table_header, null);
        }
        TextView weekdayTextView = convertView.findViewById(R.id.weekdayTextView);
        weekdayTextView.setText(timeTableHeader);
        weekdayTextView.setTypeface(null, Typeface.BOLD);


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return timetables.get(timeTableHeaders.get(groupPosition)).size();
    }

    @Override
    public int getGroupCount() {
        return timeTableHeaders.size();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
