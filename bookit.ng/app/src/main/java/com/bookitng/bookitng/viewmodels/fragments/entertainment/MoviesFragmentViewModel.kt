package com.bookitng.bookitng.viewmodels.fragments.entertainment

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.preference.PreferenceManager
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bookitng.bookitng.R
import com.bookitng.bookitng.activities.MainMenuActivity
import com.bookitng.bookitng.adapters.TodayMovieAdapter
import com.bookitng.bookitng.extensions.errorMessage
import com.bookitng.bookitng.models.TodayMovies
import com.bookitng.bookitng.models.UserModel
import com.bookitng.bookitng.networking.generateService
import com.bookitng.bookitng.networking.services.MovieService
import com.github.ybq.android.spinkit.SpinKitView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviesFragmentViewModel(application: Application) : AndroidViewModel(application) {

    fun retrieveTodayMovies(activity: MainMenuActivity,
                            todayMovieRecyclerView: RecyclerView,
                            spinKit: SpinKitView,
                            constraintLayout: ConstraintLayout) {

        todayMovieRecyclerView.visibility = View.GONE
        spinKit.visibility = View.VISIBLE

        val json = PreferenceManager.getDefaultSharedPreferences(activity)
            .getString(UserModel::class.java.simpleName, null)
        val currentUser = Gson().fromJson(json, UserModel::class.java)
        var todayMovies : TodayMovies

        val call = generateService(MovieService::class.java).retrieveTodayMovies(currentUser.bearerToken, 20, 1)
        call.enqueue(object : Callback<TodayMovies> {

            override fun onResponse(call: Call<TodayMovies>, response: Response<TodayMovies>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        todayMovies = it
                        if (todayMovies.data.isNullOrEmpty()) {
                            errorMessage(activity, constraintLayout, activity.resources.getString(R.string.movies_error_text))
                        }
                        else {
                            activity.viewModel.todayMovies = todayMovies
                            val layoutManager = LinearLayoutManager(activity)
                            layoutManager.orientation = LinearLayoutManager.VERTICAL
                            todayMovieRecyclerView.layoutManager = layoutManager
                            val adapter = TodayMovieAdapter(activity, todayMovies.data!!)
                            todayMovieRecyclerView.adapter = adapter
                        }
                    }
                }
                todayMovieRecyclerView.visibility = View.VISIBLE
                spinKit.visibility = View.GONE
            }

            override fun onFailure(call: Call<TodayMovies>, t: Throwable) {
                errorMessage(activity, constraintLayout,
                    activity.resources.getString(R.string.network_error_text))
                todayMovieRecyclerView.visibility = View.VISIBLE
                spinKit.visibility = View.GONE
            }
        })
    }
}