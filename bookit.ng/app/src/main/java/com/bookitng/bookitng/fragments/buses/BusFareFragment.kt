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
import com.bookitng.bookitng.viewmodels.fragments.buses.BusFareFragmentViewModel
import kotlinx.android.synthetic.main.fragment_bus_fare.*


class BusFareFragment : Fragment() {

    private lateinit var viewModel: BusFareFragmentViewModel
    private lateinit var context: MainMenuActivity

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(BusFareFragmentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = (activity as MainMenuActivity)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bus_fare, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        proceedToPayButton.setOnClickListener {
            val fragment = context.viewModel.getFragmentByKey(Constants.BUS_PAYMENT_FRAGMENT).fragment
            context.viewModel.loadFragment(fragment, context)
        }

        backIcon.setOnClickListener { context.onBackPressed() }
    }
}
