package momatechsoftwares.rest_demo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import momatechsoftwares.rest_demo.R;
import momatechsoftwares.rest_demo.adapters.StatisticsAdapter;
import momatechsoftwares.rest_demo.adapters.StatisticsExpandableListAdapter;
import momatechsoftwares.rest_demo.networking.models.Statistics;

public class DashboardActivity extends AppCompatActivity {

    private List<String> statisticsHeaders;
    private List<Statistics> statisticsList;
    private StatisticsExpandableListAdapter statisticsExpandableListAdapter;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initStatistics();
        expandableListView = findViewById(R.id.statisticsExpandableListView);

        statisticsExpandableListAdapter = new StatisticsExpandableListAdapter
                (this, statisticsList, statisticsHeaders);
        expandableListView.setAdapter(statisticsExpandableListAdapter);

    }

    private void initStatistics() {

        statisticsHeaders = new LinkedList<>();
        String[] headers = {"Assessments", "Attendance", "Comments"};
        statisticsHeaders.addAll(Arrays.asList(headers));
        statisticsList = getStatistics();
    }

    public List<Statistics> getStatistics() {

        String[] subjectNames = {"Chemistry", "Physics", "Mathematics", "Biology", "Further Maths",
                "Chemistry", "Physics", "Mathematics", "Biology", "Further Maths"};
        String[] classNames = {"SS1-A", "JSS1-A", "SS2-B", "SS1-C", "JSS3-B", "SS1-A",
                "JSS1-A", "SS2-B", "SS1-C", "JSS3-B"};
        String[] percentages = {"58%", "0%", "90%", "100%", "10%", "58%", "0%", "90%", "100%", "10%"};
        int[] progressList = {58, 0, 90, 100, 10, 58, 0, 90, 100, 10};

        LinkedList<Statistics> statisticsLinkedList = new LinkedList<>();

        for (int i = 0; i < subjectNames.length; i++){

            Statistics statistics = new Statistics();
            statistics.setSubjectName(subjectNames[i]);
            statistics.setClassName(classNames[i]);
            statistics.setPercentage(percentages[i]);
            statistics.setProgress(progressList[i]);

            statisticsLinkedList.add(statistics);
        }
        return statisticsLinkedList;
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(DashboardActivity.this, MovieListActivity.class);
        startActivity(intent);
        finish();
    }
}
