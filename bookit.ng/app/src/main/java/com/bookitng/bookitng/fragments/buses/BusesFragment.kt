package com.bookitng.bookitng.fragments.buses

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bookitng.bookitng.R
import com.bookitng.bookitng.activities.MainMenuActivity
import com.bookitng.bookitng.utils.Constants
import com.bookitng.bookitng.viewmodels.fragments.buses.BusesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_buses.*

class BusesFragment : Fragment() {

    private lateinit var viewModel: BusesFragmentViewModel
    private lateinit var context: MainMenuActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = (activity as MainMenuActivity)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_buses, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.retrieveBusesForRoute((activity as MainMenuActivity))

        searchButton.setOnClickListener {
            val fragment = context.viewModel.getFragmentByKey(Constants.BUS_LISTING_FRAGMENT).fragment
            context.viewModel.loadFragment(fragment, context)
        }

        dateLayout.setOnClickListener { viewModel.onDateChanged(context) }
        timeLayout.setOnClickListener { viewModel.onTimeChanged(context) }

        backIcon.setOnClickListener { context.onBackPressed() }

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(BusesFragmentViewModel::class.java)
    }

}
