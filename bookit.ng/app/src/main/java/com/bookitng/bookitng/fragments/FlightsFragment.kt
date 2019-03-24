package com.bookitng.bookitng.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bookitng.bookitng.R
import com.bookitng.bookitng.viewmodels.fragments.FlightsFragmentViewModel


class FlightsFragment : Fragment() {

    private lateinit var viewModel: FlightsFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_flights, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FlightsFragmentViewModel::class.java)
    }

}
