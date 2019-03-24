package com.bookitng.bookitng.viewmodels.fragments.buses

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.preference.PreferenceManager
import com.bookitng.bookitng.activities.MainMenuActivity
import com.bookitng.bookitng.models.BusesForRoute
import com.bookitng.bookitng.models.UserModel
import com.bookitng.bookitng.networking.Routes
import com.bookitng.bookitng.networking.generateService
import com.bookitng.bookitng.networking.services.BusService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import java.util.Calendar
import android.support.design.widget.FloatingActionButton
import android.support.constraint.ConstraintLayout
import android.view.View


class BusesFragmentViewModel(application: Application) : AndroidViewModel(application) {


    fun onDateChanged(activity: MainMenuActivity) {

        val calendar = Calendar.getInstance()
        val dateListener: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        val dialog = DatePickerDialog(activity, dateListener,
                calendar.get(java.util.Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
        dialog.show()
    }

    fun onTimeChanged(activity: MainMenuActivity) {

        val calendar = Calendar.getInstance()
        val timeListener: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
        }

        val dialog = TimePickerDialog(activity, timeListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
        dialog.show()
    }

    fun retrieveBusesForRoute(activity: MainMenuActivity){

        val json = PreferenceManager.getDefaultSharedPreferences(activity)
            .getString(UserModel::class.java.simpleName, null)
        val bearerToken = Gson().fromJson(json, UserModel::class.java).bearerToken

        val call = generateService(BusService::class.java).getBusesForRoute(Routes.BUSES_ENDPOINT + "abuja/lagos",
            bearerToken, 20, 1)

        call.enqueue(object : Callback<BusesForRoute> {

            override fun onResponse(call: Call<BusesForRoute>, response: Response<BusesForRoute>) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        val busesPayload = it
                        if (!busesPayload.data.isNullOrEmpty()) {
                            activity.viewModel.busesForRoute = busesPayload
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BusesForRoute>, t: Throwable) {

            }
        })
    }
}