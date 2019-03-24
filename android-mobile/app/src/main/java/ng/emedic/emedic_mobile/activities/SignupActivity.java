package ng.emedic.emedic_mobile.activities;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import java.io.IOException;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.networking.generators.ServiceGenerator;
import ng.emedic.emedic_mobile.networking.models.Signup;
import ng.emedic.emedic_mobile.networking.response.AuthResponse;
import ng.emedic.emedic_mobile.networking.services.RegistrationService;
import ng.emedic.emedic_mobile.utils.EmedicUtils;
import ng.emedic.emedic_mobile.utils.PreferenceUtils;
import retrofit2.Call;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<AuthResponse> {

    private CountryCodePicker countryCodePicker;
    private EditText carrierNumber;
    private Spinner genderSpinner;
    private EditText usernameField;
    private EditText emailField;
    private EditText password;
    private EditText confirmPassword;
    private RelativeLayout emedicLoader;

    private final int SIGN_UP_LOADER = 731;
    private AuthResponse authResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emedicLoader = findViewById(R.id.emedicLoader);

        countryCodePicker = findViewById(R.id.countryCodePicker);
        carrierNumber = findViewById(R.id.carrierNumber);
        countryCodePicker.registerCarrierNumberEditText(carrierNumber);

        countryCodePicker.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                if (!isValidNumber) {
                    carrierNumber.setTextColor(getResources().getColor(R.color.error));
                } else {
                    carrierNumber.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });

        genderSpinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_view, new String[] {"F", "M"}){
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
        genderSpinnerAdapter.setDropDownViewResource(R.layout.spinner_view);
        genderSpinner.setAdapter(genderSpinnerAdapter);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        usernameField = findViewById(R.id.usernameField);
        emailField = findViewById(R.id.emailField);
        password = findViewById(R.id.password);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (confirmPassword.getText().toString().equals(s.toString())) {
                    confirmPassword.setTextColor(getResources().getColor(R.color.white));
                } else {
                    confirmPassword.setTextColor(getResources().getColor(R.color.error));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword = findViewById(R.id.confirmPassword);
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().toString().equals(s.toString())) {
                    confirmPassword.setTextColor(getResources().getColor(R.color.white));
                } else {
                    confirmPassword.setTextColor(getResources().getColor(R.color.error));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validity = validateFields();
                if (validity) {
                    LoaderManager loaderManager = getSupportLoaderManager();
                    Loader<AuthResponse> loader = loaderManager.getLoader(SIGN_UP_LOADER);
                    Bundle bundle = new Bundle();
                    if (loader == null) {
                        loaderManager.initLoader(SIGN_UP_LOADER, bundle, SignupActivity.this);
                    } else {
                        loaderManager.restartLoader(SIGN_UP_LOADER, bundle, SignupActivity.this);
                    }
                }
            }
        });
    }

    private boolean validateFields() {
        boolean validity = true;

        if (usernameField.getText().toString().trim().isEmpty()) {
            usernameField.setError("Provide username!");
            validity = false;
        }

        if (emailField.getText().toString().trim().isEmpty()) {
            emailField.setError("Provide an email address!");
            validity = false;
        } else if(!EmedicUtils.isValidEmail(emailField.getText().toString().trim())) {
            emailField.setError("Invalid email address!");
            validity = false;
        }

        if (carrierNumber.getText().toString().trim().isEmpty()) {
            carrierNumber.setError("Provide a phone number");
            validity = false;
        } else if (!countryCodePicker.isValidFullNumber()) {
            carrierNumber.setError("Invalid phone number");
            validity = false;
        }

        if (password.getText().toString().isEmpty()) {
            password.setError("Provide password!");
            validity = false;
        }

        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            password.setError("Password mismatch!");
            validity = false;
        }

        return validity;
    }

    private Signup constructSignup() {
        Signup signup = new Signup();
        signup.setUsername(usernameField.getText().toString().trim());
        signup.setEmail(emailField.getText().toString().trim());
        signup.setPhone(countryCodePicker.getFullNumberWithPlus());
        signup.setGender((String)genderSpinner.getSelectedItem());
        signup.setPassword(password.getText().toString());
        signup.setProfileType("PATIENT");
        signup.setFirstName("");
        signup.setLastName("");
        return signup;
    }

    private void completeSignup() {
        PreferenceUtils.saveToken(authResponse.getToken(), SignupActivity.this);
        authResponse.getUser().setPatientId(authResponse.getPatient().getPatientId());
        PreferenceUtils.saveUser(authResponse.getUser(), SignupActivity.this);
        PreferenceUtils.savePatient(authResponse.getPatient(), SignupActivity.this);
        Toast.makeText(SignupActivity.this, authResponse.getMessage(), Toast.LENGTH_LONG).show();
        finish();
    }

    private void showProgress() {
        emedicLoader.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        emedicLoader.setVisibility(View.GONE);
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<AuthResponse> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<AuthResponse>(this) {
            @Nullable
            @Override
            public AuthResponse loadInBackground() {
                Signup signup = constructSignup();
                RegistrationService registrationService =
                        ServiceGenerator.createService(RegistrationService.class, SignupActivity.this);
                Call<AuthResponse> call = registrationService.register(signup);
                Response<AuthResponse> response = null;
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

                if (authResponse != null) {
                    hideProgress();
                    completeSignup();
                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(@Nullable AuthResponse data) {
                authResponse = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<AuthResponse> loader, AuthResponse data) {
        if (data != null) {
            authResponse = data;
        }
        hideProgress();
        if (authResponse != null) {
            completeSignup();
        } else {
            Toast.makeText(SignupActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<AuthResponse> loader) {

    }
}
