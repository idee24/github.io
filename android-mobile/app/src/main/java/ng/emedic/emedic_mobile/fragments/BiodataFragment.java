package ng.emedic.emedic_mobile.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.ServicesActivity;
import ng.emedic.emedic_mobile.networking.models.User;
import ng.emedic.emedic_mobile.utils.PreferenceUtils;

public class BiodataFragment extends Fragment{
    private ServicesActivity activity;
    private FloatingActionButton backButton;
    private FloatingActionButton saveButton;
    private LinearLayout date;
    private TextView dayTextView;
    private TextView monthTextView;
    private TextView yearTextView;
    private Spinner bloodGroupSpinner;
    private Spinner disabilitySpinner;
    private Spinner genotypeSpinner;
    private Spinner maritalStatusSpinner;
    private Spinner weightSpinner;
    private EditText medicalIssuesField;
    private View currentView;
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_biodata, null);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentView = getView();
        if (currentView == null) {
            return;
        }
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_biodata));
        }

        backButton = currentView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = activity.getUser();
                if (validateDate()) {
                    user.setDateOfBirth(getDate());
                }
                user.setDateOfBirth(getDate());
                user.setBloodGroup((String)bloodGroupSpinner.getSelectedItem());
                user.setDisability((String)disabilitySpinner.getSelectedItem());
                user.setGenotype((String)genotypeSpinner.getSelectedItem());
                user.setMaritalStatus((String)maritalStatusSpinner.getSelectedItem());
                user.setWeight((String)weightSpinner.getSelectedItem());
                user.setMedicalIssues(medicalIssuesField.getText().toString().trim());
                activity.loadFragment(new ProfileFragment());
            }
        });
        saveButton = currentView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDate()) {
                    User user = activity.getUser();
                    user.setDateOfBirth(getDate());
                    user.setBloodGroup((String)bloodGroupSpinner.getSelectedItem());
                    user.setDisability((String)disabilitySpinner.getSelectedItem());
                    user.setGenotype((String)genotypeSpinner.getSelectedItem());
                    user.setMaritalStatus((String)maritalStatusSpinner.getSelectedItem());
                    user.setWeight((String)weightSpinner.getSelectedItem());
                    user.setMedicalIssues(medicalIssuesField.getText().toString().trim());
                    activity.updateProfile();
                }
            }
        });
        date = currentView.findViewById(R.id.date);
        dayTextView = currentView.findViewById(R.id.dayTextView);
        monthTextView = currentView.findViewById(R.id.monthTextView);
        yearTextView = currentView.findViewById(R.id.yearTextView);
        medicalIssuesField = currentView.findViewById(R.id.medicalIssuesField);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicesActivity.DatePickerFragment fragment = new ServicesActivity.DatePickerFragment();
                getDate(fragment);
                fragment.show(activity.getSupportFragmentManager(), "datePicker");
            }
        });
        bloodGroupSpinner = currentView.findViewById(R.id.bloodGroupSpinner);
        ArrayAdapter<String> bloodGroupSpinnerAdapter = new ArrayAdapter<String>(activity,
                R.layout.spinner_view, new String[] {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"}){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                // Set the Text color
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                return view;
            }
        };
        bloodGroupSpinnerAdapter.setDropDownViewResource(R.layout.spinner_view);
        bloodGroupSpinner.setAdapter(bloodGroupSpinnerAdapter);

        disabilitySpinner = currentView.findViewById(R.id.disabilitySpinner);
        ArrayAdapter<String> disabilitySpinnerAdapter = new ArrayAdapter<String>(activity,
                R.layout.spinner_view_two, new String[] {"None", "Visual Impairment", "Physical", "Intellectual", "Deaf", "Autism"}){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                // Set the Text color
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                return view;
            }
        };
        disabilitySpinnerAdapter.setDropDownViewResource(R.layout.dropdown_spinner_view);
        disabilitySpinner.setAdapter(disabilitySpinnerAdapter);

        genotypeSpinner = currentView.findViewById(R.id.genotypeSpinner);
        ArrayAdapter<String> genotypeSpinnerAdapter = new ArrayAdapter<String>(activity,
                R.layout.spinner_view, new String[] {"AA", "AS", "SS"}){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                // Set the Text color
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                return view;
            }
        };
        genotypeSpinnerAdapter.setDropDownViewResource(R.layout.spinner_view);
        genotypeSpinner.setAdapter(genotypeSpinnerAdapter);

        maritalStatusSpinner = currentView.findViewById(R.id.maritalStatusSpinner);
        ArrayAdapter<String> maritalStatusSpinnerAdapter = new ArrayAdapter<String>(activity,
                R.layout.spinner_view_two, new String[] {"Single", "Married", "Divorced", "Widowed"}){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                // Set the Text color
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                return view;
            }
        };
        maritalStatusSpinnerAdapter.setDropDownViewResource(R.layout.dropdown_spinner_view);
        maritalStatusSpinner.setAdapter(maritalStatusSpinnerAdapter);

        weightSpinner = currentView.findViewById(R.id.weightSpinner);
        ArrayAdapter<String> weightSpinnerAdapter = new ArrayAdapter<String>(activity,
                R.layout.spinner_view, new String[] {"30+", "50+", "70+","90+"}){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                // Set the Text color
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                return view;
            }
        };
        weightSpinnerAdapter.setDropDownViewResource(R.layout.spinner_view);
        weightSpinner.setAdapter(weightSpinnerAdapter);
        prePopulateInput();
    }

    private void prePopulateInput() {
        User user = activity.getUser();
        if (user != null) {
            if (user.getDateOfBirth() != null && !user.getDateOfBirth().isEmpty()) {
                setDate(user.getDateOfBirth());
            } else {
                setDate();
            }
            if (user.getBloodGroup() != null && !user.getBloodGroup().isEmpty()) {
                bloodGroupSpinner.setSelection(((ArrayAdapter<String>)bloodGroupSpinner.getAdapter())
                        .getPosition(user.getBloodGroup()));
            }

            if (user.getDisability() != null && !user.getDisability().isEmpty()) {
                disabilitySpinner.setSelection(((ArrayAdapter<String>)disabilitySpinner.getAdapter())
                        .getPosition(user.getDisability()));
            }

            if (user.getGenotype() != null && !user.getGenotype().isEmpty()) {
                genotypeSpinner.setSelection(((ArrayAdapter<String>)genotypeSpinner.getAdapter())
                        .getPosition(user.getGenotype()));
            }

            if (user.getMaritalStatus() != null && !user.getMaritalStatus().isEmpty()) {
                maritalStatusSpinner.setSelection(((ArrayAdapter<String>)maritalStatusSpinner.getAdapter())
                        .getPosition(user.getMaritalStatus()));
            }

            if (user.getWeight() != null && !user.getWeight().isEmpty()) {
                weightSpinner.setSelection(((ArrayAdapter<String>)weightSpinner.getAdapter())
                        .getPosition(user.getWeight()));
            }

            if (user.getMedicalIssues() != null) {
                medicalIssuesField.setText(user.getMedicalIssues());
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ServicesActivity) getActivity();
        assert activity != null;
    }

    private String getDate(ServicesActivity.DatePickerFragment... fragments) {
        int day = Integer.valueOf(dayTextView.getText().toString().trim());
        int month = Integer.valueOf(monthTextView.getText().toString().trim());
        int year = Integer.valueOf(yearTextView.getText().toString().trim());
        if (fragments.length > 0) {
            fragments[0].setDate(year, month - 1, day);
        }
        return String.format(Locale.getDefault(), "%02d-%02d-%04d", day, month, year);
    }

    private Calendar getCalendarDate() {
        int day = Integer.valueOf(dayTextView.getText().toString().trim());
        int month = Integer.valueOf(monthTextView.getText().toString().trim());
        int year = Integer.valueOf(yearTextView.getText().toString().trim());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month -1);
        calendar.set(Calendar.YEAR, year);
        return calendar;
    }

    public void setDate(int year, int month, int day) {
        dayTextView.setText(String.format(Locale.getDefault(), "%02d", day));
        monthTextView.setText(String.format(Locale.getDefault(), "%02d", month + 1));
        yearTextView.setText(String.format(Locale.getDefault(), "%04d", year));
        User user = activity.getUser();
        if (user != null) {
            user.setDateOfBirth(getDate());
        }
    }

    private void setDate(long millis) {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        setDate(year, month, day);

    }

    private void setDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        setDate(year, month, day);
    }

    private void setDate(String date) {
        try {
            String[] dates = date.split("-");
            int day = Integer.valueOf(dates[0].trim());
            int month = Integer.valueOf(dates[1].trim()) - 1;
            int year = Integer.valueOf(dates[2].trim());
            setDate(year, month, day);
        } catch (Exception e)  {
            e.printStackTrace();
        }
    }

    private boolean validateDate() {
        final Calendar todaysDate = Calendar.getInstance();
        final Calendar selectedDate = getCalendarDate();
        if (selectedDate.before(todaysDate)) {
            return true;
        } else {
            Toast.makeText(activity, "Please Select an Earlier Date!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
