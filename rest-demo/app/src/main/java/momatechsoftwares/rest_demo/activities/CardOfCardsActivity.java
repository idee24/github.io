package momatechsoftwares.rest_demo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import momatechsoftwares.rest_demo.R;
import momatechsoftwares.rest_demo.adapters.TimeTableAdapter;
import momatechsoftwares.rest_demo.networking.models.TimeTable;

public class CardOfCardsActivity extends AppCompatActivity {

    List<String> timeTableHeaders;
    LinkedHashMap<String, List<TimeTable>> timeTables;
    TimeTableAdapter timeTableAdapter;
    ExpandableListView timeTableExpandableListView;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_of_cards);

        initTimeTables();
        addButton = findViewById(R.id.addButton);
        timeTableExpandableListView = findViewById(R.id.timetableExpandableListView);
        timeTableAdapter = new TimeTableAdapter(this, timeTableHeaders, timeTables);
        timeTableExpandableListView.setAdapter(timeTableAdapter);

        initAddListener();
    }

    private void initAddListener() {
        addButton.setOnClickListener(view -> {

            timeTables.put("Saturday", getTimeTable().get("Friday"));
            timeTableHeaders.add("Saturday");
            timeTableAdapter = new TimeTableAdapter(this, timeTableHeaders, timeTables);
            timeTableExpandableListView.setAdapter(timeTableAdapter);
        });
    }

    private void initTimeTables() {

        timeTableHeaders = new LinkedList<>();
        timeTables = new LinkedHashMap<>();

        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        timeTableHeaders.addAll(Arrays.asList(weekdays));
        timeTables = getTimeTable();
    }

    public static LinkedHashMap<String, List<TimeTable>> getTimeTable(){

        LinkedHashMap<String, List<TimeTable>> timetableObject = new LinkedHashMap<>();

        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] subjects = {"Chemistry", "Physics", "Mathematics", "Biology", "Further Maths"};
        String[] className = {"SS1-A", "JSS1-A", "SS2-B", "SS1-C", "JSS3-B"};
        String link = "Sciences";
        String[] periods = {"8am-9am", "9am-10am", "10am-11am", "1pm-2pm", "2pm-3pm"};

        List<TimeTable> timeTables = new LinkedList<>();
        for (int i = 0; i <= 4; i++){

            TimeTable timeTable = new TimeTable();
            timeTable.setSubjectName(subjects[i]);
            timeTable.setClassName(className[i]);
            timeTable.setLink(link);
            timeTable.setPeriod(periods[i]);
            timeTables.add(timeTable);
        }

        for (String weekday : weekdays){
            timetableObject.put(weekday, timeTables);
        }
        return timetableObject;
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CardOfCardsActivity.this, MovieListActivity.class);
        startActivity(intent);
        finish();
    }
}
