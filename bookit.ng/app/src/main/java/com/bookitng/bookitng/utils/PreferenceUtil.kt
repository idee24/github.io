package com.bookitng.bookitng.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import com.bookitng.bookitng.models.TodayMovies
import com.bookitng.bookitng.models.UserModel
import com.google.gson.Gson

    fun <T> persistModel(model: T, context: Context, key: String): Boolean {

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = preferences.edit()
        val gson = Gson()
        val json = gson.toJson(model)
        prefsEditor.putString(key, json)
        prefsEditor.apply()
        return true
    }

    fun setLoggedIn(context: Context, loggedIn: Boolean) {

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        prefsEditor.putBoolean(Constants.LOGGED_IN, loggedIn)
        prefsEditor.apply()
    }

    fun isLoggedIn(context: Context): Boolean {

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean(Constants.LOGGED_IN, false)

    }