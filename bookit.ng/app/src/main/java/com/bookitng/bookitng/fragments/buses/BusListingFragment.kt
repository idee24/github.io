package com.bookitng.bookitng.fragments.buses

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bookitng.bookitng.R
import com.bookitng.bookitng.activities.MainMenuActivity
import com.bookitng.bookitng.viewmodels.fragments.buses.BusListingViewModel
import kotlinx.android.synthetic.main.fragment_bus_listing.*


class BusListingFragment : Fragment() {

    private lateinit var viewModel: BusListingViewModel
    private lateinit var context: MainMenuActivity

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(BusListingViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = (activity as MainMenuActivity)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bus_listing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.initRecyclerView(busListingRecyclerView, context)
        backIcon.setOnClickListener { context.onBackPressed() }
    }
}
