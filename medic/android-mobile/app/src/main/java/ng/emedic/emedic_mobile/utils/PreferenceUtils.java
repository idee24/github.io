package ng.emedic.emedic_mobile.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.LoginActivity;
import ng.emedic.emedic_mobile.networking.models.Patient;
import ng.emedic.emedic_mobile.networking.models.User;

public class PreferenceUtils {
    public static final String TAG = PreferenceUtils.class
            .getSimpleName();

    public static <O> boolean save(O object, Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString(object.getClass().getSimpleName(), json);
        prefsEditor.apply();
        return true;
    }

    public static <O> O read(Class<O> oClass, Context context) {
        Gson gson = new Gson();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = mPrefs.getString(oClass.getSimpleName(), null);
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, oClass);
    }

    public static boolean saveToken(String token, Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("token", token);
        prefsEditor.apply();
        return true;
    }

    public static String getToken(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("token", null);
    }

    public static User getUser(Context context) {
        return read(User.class, context);
    }

    public static void saveUser(User user, Context context) {
        save(user, context);
    }

    public static Patient getPatient(Context context) {
        return read(Patient.class, context);
    }

    public static void savePatient(Patient patient, Context context) {
        save(patient, context);
    }

    public static boolean userProfileUpdated(Context context) {
        User user = getUser(context);
        return user != null && !user.getFirstName().isEmpty() && user.getPictureUrl() != null
                && !user.getPictureUrl().isEmpty();
    }

    public static void logout(AppCompatActivity activity) {
        User user = getUser(activity);
        user.setLoggedIn(false);
        saveUser(user, activity);
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        activity.finish();
    }
}
