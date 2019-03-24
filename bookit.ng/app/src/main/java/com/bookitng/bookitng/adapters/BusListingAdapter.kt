package com.bookitng.bookitng.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bookitng.bookitng.R
import com.bookitng.bookitng.activities.MainMenuActivity
import com.bookitng.bookitng.utils.Constants

class BusListingAdapter(private val activity: MainMenuActivity):
        RecyclerView.Adapter<BusListingAdapter.BusListingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusListingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.bus_listing_item, parent, false)
        return BusListingViewHolder(view)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: BusListingViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            val fragment = activity.viewModel.getFragmentByKey(Constants.BUS_FARE_FRAGMENT).fragment
            activity.viewModel.loadFragment(fragment, activity)
        }
    }

    inner class BusListingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}