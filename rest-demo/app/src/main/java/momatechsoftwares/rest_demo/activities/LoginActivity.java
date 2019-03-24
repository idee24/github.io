package momatechsoftwares.rest_demo.activities;

import android.content.Intent;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import momatechsoftwares.rest_demo.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView usernameField;
    private TextView passwordField;

    private static final String PASS = "admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        usernameField = findViewById(R.id.userNameTextField);
        passwordField = findViewById(R.id.passwordTextField);

        VectorDrawableCompat personDrawableCompat= VectorDrawableCompat.create(getResources(),
                R.drawable.ic_person_black_24dp, usernameField.getContext().getTheme());
        usernameField.setCompoundDrawablesRelativeWithIntrinsicBounds(personDrawableCompat, null, null, null);

        VectorDrawableCompat lockDrawableCompat= VectorDrawableCompat.create(getResources(),
                R.drawable.ic_lock_outline_black_24dp, passwordField.getContext().getTheme());
        passwordField.setCompoundDrawablesRelativeWithIntrinsicBounds(lockDrawableCompat, null, null, null);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, MovieListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
