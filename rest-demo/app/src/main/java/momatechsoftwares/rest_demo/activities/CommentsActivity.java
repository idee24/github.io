package momatechsoftwares.rest_demo.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import momatechsoftwares.rest_demo.R;

public class CommentsActivity extends AppCompatActivity {

    private EditText numberTextField;
    private RadioGroup commentRadioGroup;
    private int selectedItem;
    Calendar calendar = Calendar.getInstance();
    String myFormat = "MM/dd/yy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
    String selectedDate;
    DatePickerDialog.OnDateSetListener dateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Button showRadioButton = findViewById(R.id.showRadioButton);
        numberTextField = findViewById(R.id.numberTextField);
        commentRadioGroup = findViewById(R.id.commentsRadioGroup);
        showRadioButton.setOnClickListener(v -> {
            int number = Integer.parseInt(numberTextField.getText().toString().trim());
            showRadio(number);
        });
    }

    private void showRadio(int number) {

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,20);

        int radioButtonId = 0;
        String radio;
        for (int row = 1; row <= number; row++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setTag(radioButtonId);
            radioButtonId++;

            radioButton.setWidth(400);
            radioButton.setHeight(300);

            radioButton.setBackgroundResource(R.drawable.comments_selector);
            radioButton.setButtonDrawable(R.color.transparent);
            radio = "Radio: " + radioButtonId;
            radioButton.setText(radio);
            radioButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            radioButton.setTextColor(Color.BLACK);
            radioButton.setTextSize(20);

            commentRadioGroup.addView(radioButton, layoutParams);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CommentsActivity.this, MovieListActivity.class);
        startActivity(intent);
        finish();
    }

    public void showDialog(View view) {
        String[] concepts = {"Oblivion", "Singularity", "Relativity", "Infinity"};
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("ISS");
        dialogBuilder.setSingleChoiceItems(concepts, selectedItem,
                (dialogInterface, which) -> selectedItem = which);
        dialogBuilder.setPositiveButton("Done", (dialog, which) -> showToast());
        dialogBuilder.create().show();
    }

    private void showToast() {
        Toast.makeText(this, "Dialog Touched", Toast.LENGTH_SHORT).show();
    }

    public void showDatePicker(View v) {

        dateListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            selectedDate = dateFormat.format(calendar.getTime());
            System.out.println("Gbab Date: " + selectedDate);
        };

        DatePickerDialog dialog = new DatePickerDialog(this, dateListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
