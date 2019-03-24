package ng.emedic.emedic_mobile.activities;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.networking.models.User;
import ng.emedic.emedic_mobile.utils.PreferenceUtils;

public class SplashActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>{

    private ImageView eMedicLogo;
    private TextView eMedicText;
    private boolean loaded;

    private final int SPLASH_LOADER = 137;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        eMedicLogo = findViewById(R.id.eMedicLogo);
        eMedicText = findViewById(R.id.eMedicText);

        initiateLoading();
    }

    public void transitionToNextActivity() {
        User user = PreferenceUtils.getUser(this);
        Intent intent;
        if (user != null && user.isLoggedIn()) {
            intent = new Intent(this, ServicesActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Pair[] pair = new Pair[2];
            pair[0] = new Pair<View, String>(eMedicLogo, "emedic_logo_shared");
            pair[1] = new Pair<View, String>(eMedicText, "emedic_text_shared");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        finish();
    }

    private void initiateLoading() {
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Void> splashLoader = loaderManager.getLoader(SPLASH_LOADER);
        Bundle bundle = new Bundle();
        if (splashLoader == null) {
            loaderManager.initLoader(SPLASH_LOADER, bundle, this);
        } else {
            loaderManager.restartLoader(SPLASH_LOADER, bundle, this);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Void>(this) {
            @Nullable
            @Override
            public Void loadInBackground() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                if (loaded) {
                    return;
                }

                forceLoad();
            }

            @Override
            public void deliverResult(@Nullable Void data) {
                loaded = true;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Void> loader, Void data) {
        transitionToNextActivity();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Void> loader) {

    }
}
