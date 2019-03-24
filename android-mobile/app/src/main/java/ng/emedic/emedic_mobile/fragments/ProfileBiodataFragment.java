package ng.emedic.emedic_mobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.ServicesActivity;
import ng.emedic.emedic_mobile.networking.models.User;

public class ProfileBiodataFragment extends Fragment {
    private View currentView;
    private ServicesActivity activity;
    private TextView dobTextView;
    private TextView bloodGroupTextView;
    private TextView genotypeTextView;
    private TextView disabilityTextView;
    private TextView weightTextView;
    private TextView medicalIssuesTextView;
    public ProfileBiodataFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_biodata, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ServicesActivity) getActivity();
        assert activity != null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentView = getView();
        if (currentView == null) {
            return;
        }
        dobTextView = currentView.findViewById(R.id.dobTextView);
        bloodGroupTextView = currentView.findViewById(R.id.bloodGroupTextView);
        genotypeTextView = currentView.findViewById(R.id.genotypeTextView);
        disabilityTextView = currentView.findViewById(R.id.disabilityTextView);
        weightTextView = currentView.findViewById(R.id.weightTextView);
        medicalIssuesTextView = currentView.findViewById(R.id.medicalIssuesTextView);

        loadBiodata();
    }

    private void loadBiodata() {
        User user = activity.getUser();
        if (user != null) {
            dobTextView.setText(user.getDateOfBirth() != null && !user.getDateOfBirth().isEmpty() ? formatDOB(user.getDateOfBirth()) : "N/A");
            bloodGroupTextView.setText(user.getBloodGroup() != null ? user.getBloodGroup() : "N/A");
            genotypeTextView.setText(user.getGenotype() != null ? user.getGenotype() : "N/A");
            disabilityTextView.setText(user.getDisability() != null ? user.getDisability() : "N/A");
            weightTextView.setText(user.getWeight() != null ? user.getWeight() : "N/A");
            medicalIssuesTextView.setText(user.getMedicalIssues() != null ? user.getMedicalIssues() : "N/A");
        }
    }

    private String formatDOB(String dob) {
        String[] splits = dob.split("-");
        String day = splits[0];
        String month = splits[1];
        String year = splits[2];

        String dayString = String.format(Locale.getDefault(), "%d", Integer.valueOf(day)) + getOrdinal(Integer.valueOf(day));
        String monthString = getMonthName(Integer.valueOf(month.trim()) - 1);

        return dayString + " " + monthString + ", " + year;
    }

    private static String getMonthName(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    private String getOrdinal(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

}
