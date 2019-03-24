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

import java.util.Arrays;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.ServicesActivity;
import ng.emedic.emedic_mobile.networking.models.User;

public class ProfileDetailsFragment extends Fragment {
    private View currentView;
    private ServicesActivity activity;
    private TextView addressTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView genderTextView;
    private TextView passwordTextView;
    public ProfileDetailsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_details, container, false);
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
        addressTextView = currentView.findViewById(R.id.addressTextView);
        emailTextView = currentView.findViewById(R.id.emailTextView);
        phoneTextView = currentView.findViewById(R.id.phoneTextView);
        genderTextView = currentView.findViewById(R.id.genderTextView);
        passwordTextView = currentView.findViewById(R.id.passwordTextView);

        loadDetails();
    }

    private void loadDetails() {
        User user = activity.getUser();
        if (user != null) {
            addressTextView.setText(user.getAddress() != null ? user.getAddress() : "N/A");
            emailTextView.setText(user.getEmail() != null ? user.getEmail() : "N/A");
            phoneTextView.setText(user.getPhone() != null ? user.getPhone() : "N/A");
            genderTextView.setText(user.getGender() != null ?
                    user.getGender().equalsIgnoreCase("M") ? "Male" : "Female" : "Male");
            passwordTextView.setText(getCypher("SamplePassword", '*'));

        }
    }

    private String getCypher(String text, char character) {
        char[] chars = new char[text.length()];
        Arrays.fill(chars, character);
        return new String(chars);
    }
}
