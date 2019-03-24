package com.bookitng.bookitng.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bookitng.bookitng.R
import com.bookitng.bookitng.activities.MainMenuActivity
import com.bookitng.bookitng.utils.Constants
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment : Fragment(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as MainMenuActivity).getBottomNav().visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        flightsLayout.setOnClickListener(this)
        hotelsLayout.setOnClickListener(this)
        vacationsLayout.setOnClickListener(this)
        busesLayout.setOnClickListener(this)
        entertainmentLayout.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.flightsLayout -> (activity as MainMenuActivity).getBottomNav()
                .findViewById<View>(R.id.navigation_flights).performClick()

            R.id.hotelsLayout -> (activity as MainMenuActivity).getBottomNav()
                .findViewById<View>(R.id.navigation_hotels).performClick()

            R.id.vacationsLayout -> (activity as MainMenuActivity).getBottomNav()
                .findViewById<View>(R.id.navigation_vacations).performClick()

            R.id.busesLayout -> (activity as MainMenuActivity).getBottomNav()
                .findViewById<View>(R.id.navigation_buses).performClick()

            R.id.entertainmentLayout -> (activity as MainMenuActivity).getBottomNav()
                .findViewById<View>(R.id.navigation_entertainment).performClick()
        }
    }


}
