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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.ServicesActivity;
import ng.emedic.emedic_mobile.networking.models.User;
import ng.emedic.emedic_mobile.utils.EmedicUtils;

public class ProfileFragment extends Fragment {
    private ServicesActivity activity;
    private RelativeLayout profileImageHolder;
    private CircleImageView profileImage;
    private RelativeLayout progressBarHolder;
    private ScrollView profileContent;
    private NumberProgressBar progressBar;
    private FloatingActionButton fab;
    private RelativeLayout profileImageLoader;
    private ProgressBar loaderProgressBar;
    private View currentView;
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText otherNamesField;
    private EditText addressField;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentView = getView();
        if (currentView == null) {
            return;
        }
        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_profile));
        }

        profileImageHolder = currentView.findViewById(R.id.profile_image_holder);
        profileImage = currentView.findViewById(R.id.profile_image);
        progressBarHolder = currentView.findViewById(R.id.progressBarHolder);
        progressBar = currentView.findViewById(R.id.progressBar);
        profileContent = currentView.findViewById(R.id.profile_content);
        fab = currentView.findViewById(R.id.fab);
        profileImageLoader = currentView.findViewById(R.id.profile_image_loader);
        loaderProgressBar = currentView.findViewById(R.id.loader_progress_bar);
        firstNameField = currentView.findViewById(R.id.firstNameField);
        lastNameField = currentView.findViewById(R.id.lastNameField);
        otherNamesField = currentView.findViewById(R.id.otherNamesField);
        addressField = currentView.findViewById(R.id.addressField);

        loaderProgressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileContent.getVisibility() == View.VISIBLE) {
                    if (validateInput()) {
                        User user = activity.getUser();
                        user.setFirstName(firstNameField.getText().toString().trim());
                        user.setLastName(lastNameField.getText().toString().trim());
                        user.setOtherNames(otherNamesField.getText().toString().trim());
                        user.setAddress(addressField.getText().toString().trim());
                        activity.loadFragment(new BiodataFragment());
                    }
                }
            }
        });

        profileImageHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateImagePicker();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.pickImage(profileImage, profileImageHolder, progressBarHolder, progressBar, profileContent);
            }
        });

        prePopulateInput();
    }

    private void initiateImagePicker() {
        activity.pickImage(profileImage, profileImageHolder, progressBarHolder, progressBar, profileContent);
    }

    private void prePopulateInput() {
        User user = activity.getUser();
        if (user != null) {
            String downloadUrl = user.getPictureUrl();
            EmedicUtils.displayImage(downloadUrl,  profileImageHolder, profileImageLoader, profileImage);
            firstNameField.setText(user.getFirstName() == null ? "" : user.getFirstName());
            lastNameField.setText(user.getLastName() == null ? "" : user.getLastName());
            otherNamesField.setText(user.getOtherNames() == null ? "" : user.getOtherNames());
            addressField.setText(user.getAddress() == null ? "" : user.getAddress());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ServicesActivity) getActivity();
        assert activity != null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (activity.cancelUploadTask()) {
            Toast.makeText(activity, "Profile Upload Was Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasUploadedProfileImage() {
        User user = activity.getUser();
        return user != null && user.getPictureUrl() != null && !user.getPictureUrl().trim().isEmpty();
    }

    private boolean validateInput() {
        boolean valid = true;
        if (!hasUploadedProfileImage()) {
            valid = false;
            Toast.makeText(activity,  "Profile Image Not Uploaded!", Toast.LENGTH_SHORT).show();
        }

        if (firstNameField.getText().toString().trim().isEmpty()) {
            firstNameField.setError("This field cannot be empty!");
            valid = false;
        }

        if (lastNameField.getText().toString().trim().isEmpty()) {
            lastNameField.setError("This field cannot be empty!");
            valid = false;
        }

        if (addressField.getText().toString().trim().isEmpty()) {
            addressField.setError("This field cannot be empty!");
            valid = false;
        }

        return valid;
    }
}
