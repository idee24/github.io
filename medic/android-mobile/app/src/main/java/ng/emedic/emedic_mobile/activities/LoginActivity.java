package ng.emedic.emedic_mobile.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.io.IOException;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.networking.generators.ServiceGenerator;
import ng.emedic.emedic_mobile.networking.models.Login;
import ng.emedic.emedic_mobile.networking.models.User;
import ng.emedic.emedic_mobile.networking.response.AuthResponse;
import ng.emedic.emedic_mobile.networking.services.LoginService;
import ng.emedic.emedic_mobile.utils.PreferenceUtils;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<AuthResponse> {

    private EditText usernameField;
    private EditText passwordField;
    private RelativeLayout emedicLoader;

    private final int LOGIN_LOADER = 371;
    private AuthResponse authResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        emedicLoader = findViewById(R.id.emedicLoader);

        TextView signUpText = findViewById(R.id.signUpText);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out );
            }
        });

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);

        VectorDrawableCompat personDrawableCompat= VectorDrawableCompat.create(getResources(),
                R.drawable.ic_person_black_24dp, usernameField.getContext().getTheme());
        usernameField.setCompoundDrawablesRelativeWithIntrinsicBounds(personDrawableCompat, null, null, null);

        VectorDrawableCompat lockDrawableCompat= VectorDrawableCompat.create(getResources(),
                R.drawable.ic_lock_outline_black_24dp, passwordField.getContext().getTheme());
        passwordField.setCompoundDrawablesRelativeWithIntrinsicBounds(lockDrawableCompat, null, null, null);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = validateFields();
                if (valid) {
                    LoaderManager loaderManager = getSupportLoaderManager();
                    Loader<AuthResponse> loginLoader = loaderManager.getLoader(LOGIN_LOADER);
                    Bundle bundle = new Bundle();
                    if (loginLoader == null) {
                        loaderManager.initLoader(LOGIN_LOADER, bundle, LoginActivity.this);
                    } else {
                        loaderManager.restartLoader(LOGIN_LOADER, bundle, LoginActivity.this);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = PreferenceUtils.getUser(this);
        if (user != null) {
            usernameField.setText(user.getUsername());
        }
    }

    private boolean validateFields() {
        boolean valid = true;
        if (usernameField.getText().toString().trim().isEmpty()) {
            usernameField.setError("Username field cannot be empty!");
            valid = false;
        }

        if (passwordField.getText().toString().isEmpty()) {
            passwordField.setError("Password field cannot be empty!");
            valid = false;
        }

        return valid;
    }

    private Login constructLogin() {
        Login login = new Login();
        login.setUsername(usernameField.getText().toString().trim());
        login.setPassword(passwordField.getText().toString());
        return login;
    }

    private void completeLogin() {
        PreferenceUtils.saveToken(authResponse.getToken(), LoginActivity.this);
        authResponse.getUser().setLoggedIn(true);
        authResponse.getUser().updateUserDetails(authResponse.getPatient());
        PreferenceUtils.saveUser(authResponse.getUser(), LoginActivity.this);
        PreferenceUtils.savePatient(authResponse.getPatient(), LoginActivity.this);
        Toast.makeText(LoginActivity.this, authResponse.getMessage(), Toast.LENGTH_LONG).show();
        transitionToServicesActivity();
    }

    private void transitionToServicesActivity() {
        Intent intent = new Intent(this, ServicesActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
                Login login = constructLogin();
                LoginService loginService = ServiceGenerator.createService(LoginService.class, LoginActivity.this);
                Call<AuthResponse> call = loginService.login(login);
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
                    completeLogin();
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
            completeLogin();
        } else {
            Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<AuthResponse> loader) {

    }
}
