package ng.emedic.emedic_mobile.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.callbacks.UploadCallback;
import ng.emedic.emedic_mobile.data.storage.FirebaseStorageUtil;
import ng.emedic.emedic_mobile.fragments.AppointmentFragment;
import ng.emedic.emedic_mobile.fragments.BiodataFragment;
import ng.emedic.emedic_mobile.fragments.HistoryFragment;
import ng.emedic.emedic_mobile.fragments.ProfileDisplayFragment;
import ng.emedic.emedic_mobile.fragments.ProfileFragment;
import ng.emedic.emedic_mobile.fragments.ServicesFragment;
import ng.emedic.emedic_mobile.fragments.SettingsFragment;
import ng.emedic.emedic_mobile.networking.generators.ServiceGenerator;
import ng.emedic.emedic_mobile.networking.models.Patient;
import ng.emedic.emedic_mobile.networking.models.Service;
import ng.emedic.emedic_mobile.networking.models.User;
import ng.emedic.emedic_mobile.networking.services.RegistrationService;
import ng.emedic.emedic_mobile.utils.EmedicUtils;
import ng.emedic.emedic_mobile.utils.PreferenceUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ServicesActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        UploadCallback, DatePickerDialog.OnDateSetListener, LoaderManager.LoaderCallbacks<ResponseBody>, TimePickerDialog.OnTimeSetListener {
    private final int PROFILE_UPDATE_LOADER = 713;
    private EmedicUtils.EmedicImageChooser emedicImageChooser;
    private User user;
    private Fragment fragment = null;
    private RelativeLayout emedicLoader;
    private boolean updated;
    private String fragmentTitle = "";
    private Service selectedService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        emedicLoader = findViewById(R.id.emedicLoader);
        user = PreferenceUtils.getUser(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new ServicesFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_services:
                fragment = new ServicesFragment();
                break;
            case R.id.navigation_history:
                fragment = new HistoryFragment();
                break;
            case R.id.navigation_profile:
                fragment = selectProfileFragment();
                break;
            case R.id.navigation_settings:
                fragment = new SettingsFragment();
                break;

        }
        return loadFragment(fragment);
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            this.fragment = fragment;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private Fragment selectProfileFragment() {
        Fragment fragment;
        if (PreferenceUtils.userProfileUpdated(this)) {
            fragment = new ProfileDisplayFragment();
        } else {
            fragment = new ProfileFragment();
        }
        return fragment;
    }

    public void pickImage(CircleImageView imageView, RelativeLayout imageViewHolder,
                          RelativeLayout progressBarHolder, NumberProgressBar progressBar, ScrollView profileContent) {
        emedicImageChooser =
                new EmedicUtils.EmedicImageChooser(this, imageView, imageViewHolder);
        emedicImageChooser.setProgressBarHolder(progressBarHolder);
        emedicImageChooser.setProgressBar(progressBar);
        emedicImageChooser.setProfileContent(profileContent);
        emedicImageChooser.showImageChooser();
    }

    public EmedicUtils.EmedicImageChooser getImageChooser() {
        return emedicImageChooser;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        emedicImageChooser.getImagePicker().handleActivityResult(resultCode,requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (emedicImageChooser != null) {
            emedicImageChooser.getImagePicker().handlePermission(requestCode, grantResults);
        }
    }

    @Override
    public void onUploaded(String downloadUrl) {
        if (downloadUrl != null) {
            user.setPictureUrl(downloadUrl);
            emedicImageChooser.setDownloadUrl(downloadUrl);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    emedicImageChooser.getProgressBarHolder().setVisibility(View.INVISIBLE);
                    emedicImageChooser.getProfileContent().setVisibility(View.VISIBLE);
                    Toast.makeText(ServicesActivity.this, "Profile Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
            }, 1000);
        } else {
            emedicImageChooser.getProgressBarHolder().setVisibility(View.INVISIBLE);
            emedicImageChooser.getProfileContent().setVisibility(View.VISIBLE);
            if (!FirebaseStorageUtil.currentTaskIsCanceled()) {
                Toast.makeText(this, "Profile Image Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void updateProgress(final int progress) {
        if (emedicImageChooser != null) {
            final NumberProgressBar progressBar = emedicImageChooser.getProgressBar();
            progressBar.setProgress(progress);
        }
    }

    public boolean cancelUploadTask() {
        return FirebaseStorageUtil.cancelCurrentTask();
    }

    public User getUser() {
        return user;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (fragment != null && fragment instanceof BiodataFragment) {
            ((BiodataFragment)fragment).setDate(year, month, dayOfMonth);
        }

        if (fragment != null && fragment instanceof AppointmentFragment) {
            ((AppointmentFragment)fragment).setDate(year, month, dayOfMonth);
        }
    }

    public void updateProfile() {
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<ResponseBody> loader = loaderManager.getLoader(PROFILE_UPDATE_LOADER);
        Bundle bundle = new Bundle();
        if (loader == null) {
            loaderManager.initLoader(PROFILE_UPDATE_LOADER, bundle, ServicesActivity.this);
        } else {
            loaderManager.restartLoader(PROFILE_UPDATE_LOADER, bundle, ServicesActivity.this);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<ResponseBody> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<ResponseBody>(this) {
            @Nullable
            @Override
            public ResponseBody loadInBackground() {
                RegistrationService registrationService =
                        ServiceGenerator.createService(RegistrationService.class, ServicesActivity.this);
                Call<ResponseBody> call = registrationService.updateProfile(user.getPatientId(), user);
                Response<ResponseBody> response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response != null) {
                    return response.body();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                if (args == null) {
                    return;
                }

                showProgress();

                if (updated) {
                    hideProgress();
                    completeUpdate();
                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(@Nullable ResponseBody data) {
                updated = data != null;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ResponseBody> loader, ResponseBody data) {
        if (data != null) {
            updated = true;
        }
        hideProgress();
        if (updated) {
            completeUpdate();
        } else {
            Toast.makeText(ServicesActivity.this, "Failed to Update Profile", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    private void completeUpdate() {
        Patient patient = PreferenceUtils.getPatient(this);
        if (patient != null) {
            getUser().updatePatientDetails(patient);
        }
        PreferenceUtils.savePatient(patient, this);
        PreferenceUtils.saveUser(getUser(), this);
        updated = false;
        loadFragment(new ProfileDisplayFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showProgress() {
        emedicLoader.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        emedicLoader.setVisibility(View.GONE);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (fragment != null && fragment instanceof AppointmentFragment) {
            ((AppointmentFragment)fragment).setTime(hourOfDay, minute);
        }
    }

    public static class TimePickerFragment extends DialogFragment {
        private int hour;
        private int minutes;
        private String meridian;
        private int hourOfTheDay;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new TimePickerDialog(getActivity(), (ServicesActivity)getActivity(),
                    hourOfTheDay, minutes, false);
        }

        public void setTime(int hour, int minutes, String meridian) {
            this.hour = hour;
            this.minutes = minutes;
            this.meridian = meridian;
            if (meridian.equals("24")) {
                hourOfTheDay = hour;
            } else {
                hourOfTheDay = EmedicUtils.getHourOfTheDay(hour, meridian);
            }
        }
    }

    public static class DatePickerFragment extends DialogFragment {
        private int year;
        private int month;
        private int day;
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), (ServicesActivity)getActivity(), year, month, day);
        }

        public void setDate(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.ic_action_logout) {
            PreferenceUtils.logout(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }

    public String getFragmentTitle() {
        return fragmentTitle;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }
}
