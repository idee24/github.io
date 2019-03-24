package com.bookitng.bookitng.viewmodels.fragments.buses

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bookitng.bookitng.activities.MainMenuActivity
import com.bookitng.bookitng.adapters.BusListingAdapter

class BusListingViewModel(application: Application): AndroidViewModel(application) {

    fun initRecyclerView(listingRecyclerView: RecyclerView,
                         activity: MainMenuActivity) {

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        listingRecyclerView.layoutManager = layoutManager
        listingRecyclerView.adapter = BusListingAdapter(activity)
    }
}