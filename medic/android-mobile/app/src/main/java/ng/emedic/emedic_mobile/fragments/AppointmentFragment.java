package ng.emedic.emedic_mobile.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.ServicesActivity;
import ng.emedic.emedic_mobile.networking.generators.ServiceGenerator;
import ng.emedic.emedic_mobile.networking.models.Provider;
import ng.emedic.emedic_mobile.networking.models.Request;
import ng.emedic.emedic_mobile.networking.models.RequestStatus;
import ng.emedic.emedic_mobile.networking.models.User;
import ng.emedic.emedic_mobile.networking.services.DataService;
import ng.emedic.emedic_mobile.utils.EmedicUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentFragment extends Fragment {
    private ServicesActivity activity;
    private View currentView;
    private Button cancelRequestButton;
    private Button sendRequestButton;
    private Spinner specialistSpinner;
    private LinearLayout date;
    private TextView dayTextView;
    private TextView monthTextView;
    private TextView yearTextView;
    private LinearLayout time;
    private TextView hourTextView;
    private TextView minutesTextView;
    private TextView meridianTextView;
    private EditText informationField;
    private RelativeLayout emedicLoader;
    private List<Provider> providers;
    private String[] providerNames;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment, null);
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
            actionBar.setTitle(activity.getFragmentTitle());
        }

        emedicLoader = currentView.findViewById(R.id.emedicLoader);
        informationField = currentView.findViewById(R.id.informationField);
        cancelRequestButton = currentView.findViewById(R.id.cancelRequestButton);
        sendRequestButton = currentView.findViewById(R.id.sendRequestButton);

        cancelRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.loadFragment(new ServicesFragment());
            }
        });

        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emedicLoader.setVisibility(View.VISIBLE);
                sendRequestButton.setEnabled(false);
                cancelRequestButton.setEnabled(false);
                User user = activity.getUser();
                Request request = new Request();
                request.setAdditionalInformation(informationField.getText().toString().trim());
                request.setAppointment(true);
                request.setAppointmentDate(getSelectedDate());
                request.setAppointmentTime(getTime());
                request.setPatientId(user.getPatientId());
                request.setSpecialist(true);
                request.setStatus(RequestStatus.getNumberString(RequestStatus.PENDING));
                request.setServiceId(activity.getSelectedService().getId());
                DataService service = ServiceGenerator.createService(DataService.class, activity);
                Call<ResponseBody> call = service.makeRequest(request);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(activity, "Request made successfully!", Toast.LENGTH_SHORT).show();
                            activity.loadFragment(new ServicesFragment());
                        } else {
                            emedicLoader.setVisibility(View.INVISIBLE);
                            sendRequestButton.setEnabled(true);
                            cancelRequestButton.setEnabled(true);
                            Toast.makeText(activity, "Failed to make request!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        emedicLoader.setVisibility(View.INVISIBLE);
                        sendRequestButton.setEnabled(true);
                        cancelRequestButton.setEnabled(true);
                        Toast.makeText(activity, "Failed to make request!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        specialistSpinner = currentView.findViewById(R.id.specialistSpinner);

        date = currentView.findViewById(R.id.date);
        dayTextView = currentView.findViewById(R.id.dayTextView);
        monthTextView = currentView.findViewById(R.id.monthTextView);
        yearTextView = currentView.findViewById(R.id.yearTextView);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicesActivity.DatePickerFragment fragment = new ServicesActivity.DatePickerFragment();
                getDate(fragment);
                fragment.show(activity.getSupportFragmentManager(), "datePicker");
            }
        });

        time = currentView.findViewById(R.id.time);
        hourTextView = currentView.findViewById(R.id.hourTextView);
        minutesTextView = currentView.findViewById(R.id.minutesTextView);
        meridianTextView = currentView.findViewById(R.id.meridianTextView);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicesActivity.TimePickerFragment fragment = new ServicesActivity.TimePickerFragment();
                setFragmentTime(fragment);
                fragment.show(activity.getSupportFragmentManager(), "timePicker");
            }
        });

        prePopulateInput();
    }

    private void prePopulateInput() {
        emedicLoader.setVisibility(View.VISIBLE);
        sendRequestButton.setEnabled(false);
        DataService service = ServiceGenerator.createService(DataService.class, activity);
        Call<List<Provider>> call = service.getProviders();
        call.enqueue(new Callback<List<Provider>>() {
            @Override
            public void onResponse(@NonNull Call<List<Provider>> call, @NonNull Response<List<Provider>> response) {
                emedicLoader.setVisibility(View.INVISIBLE);
                sendRequestButton.setEnabled(true);
                if (response.isSuccessful()) {
                    providers = response.body();
                    if (providers != null) {
                        providerNames = new String[providers.size()];
                        int index = 0;
                        for (Provider provider : providers) {
                            providerNames[index++] = provider.getName();
                        }
                        ArrayAdapter<String> specialistSpinnerAdapter = new ArrayAdapter<String>(activity,
                                R.layout.spinner_view_two, providerNames){
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
                        specialistSpinnerAdapter.setDropDownViewResource(R.layout.dropdown_spinner_view);
                        specialistSpinner.setAdapter(specialistSpinnerAdapter);
                    } else {
                        Toast.makeText(activity, "Could not acquire, specialists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, "Could not acquire, specialists", Toast.LENGTH_SHORT).show();
                }
                setDate();
                setTime();

            }

            @Override
            public void onFailure(@NonNull Call<List<Provider>> call, @NonNull Throwable t) {
                emedicLoader.setVisibility(View.INVISIBLE);
                sendRequestButton.setEnabled(true);
                Toast.makeText(activity, "Could not acquire, specialists", Toast.LENGTH_SHORT).show();
            }
        });
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

    private Date getSelectedDate() {
        int day = Integer.valueOf(dayTextView.getText().toString().trim());
        int month = Integer.valueOf(monthTextView.getText().toString().trim());
        int year = Integer.valueOf(yearTextView.getText().toString().trim());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    private void setFragmentTime(ServicesActivity.TimePickerFragment fragment) {
        int hour;
        int minutes;
        String meridian;
        if (hourTextView.getText().toString().trim().isEmpty()) {
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minutes = c.get(Calendar.MINUTE);
            fragment.setTime(hour, minutes, "24");
        } else {
            hour = Integer.valueOf(hourTextView.getText().toString().trim());
            minutes = Integer.valueOf(minutesTextView.getText().toString().trim());
            meridian = meridianTextView.getText().toString().trim();
            fragment.setTime(hour, minutes, meridian);
        }
    }

    private void setDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        setDate(year, month, day);
    }

    private void setTime() {
        final Calendar c = Calendar.getInstance();
        int hourOfTheDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        hourTextView.setText(EmedicUtils.get12Hour(hourOfTheDay));
        minutesTextView.setText(String.format(Locale.getDefault(), "%02d",minute ));
        meridianTextView.setText(EmedicUtils.getMeridian(hourOfTheDay));
    }

    public void setTime(int hourOfDay, int minute) {
        hourTextView.setText(EmedicUtils.get12Hour(hourOfDay));
        minutesTextView.setText(String.format(Locale.getDefault(), "%02d",minute ));
        meridianTextView.setText(EmedicUtils.getMeridian(hourOfDay));
    }

    private String getTime() {
        int hour = Integer.valueOf(hourTextView.getText().toString());
        int minutes = Integer.valueOf(minutesTextView.getText().toString());
        String meredian = meridianTextView.getText().toString();
        int hourOfTheDay = EmedicUtils.getHourOfTheDay(hour, meredian);
        return String.format(Locale.getDefault(), "%02d:%02d", hourOfTheDay, minutes);
    }

    public void setDate(int year, int month, int day) {
        dayTextView.setText(String.format(Locale.getDefault(), "%02d", day));
        monthTextView.setText(String.format(Locale.getDefault(), "%02d", month + 1));
        yearTextView.setText(String.format(Locale.getDefault(), "%04d", year));
        /*User user = activity.getUser();
        if (user != null) {
            user.setDateOfBirth(getDate());
        }*/
    }
}
