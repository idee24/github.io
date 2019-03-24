package com.bookitng.bookitng.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bookitng.bookitng.R
import com.bookitng.bookitng.activities.MainMenuActivity
import com.bookitng.bookitng.fragments.*
import com.bookitng.bookitng.fragments.buses.BusFareFragment
import com.bookitng.bookitng.fragments.buses.BusListingFragment
import com.bookitng.bookitng.fragments.buses.BusPaymentFragment
import com.bookitng.bookitng.fragments.buses.BusesFragment
import com.bookitng.bookitng.fragments.entertainment.EntertainmentFragment
import com.bookitng.bookitng.models.BKTFragment
import com.bookitng.bookitng.models.BusesForRoute
import com.bookitng.bookitng.models.TodayMovies
import com.bookitng.bookitng.utils.Constants

class MainMenuViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var currentFragment: Fragment
    lateinit var todayMovies : TodayMovies
    private var backStack = LinkedHashMap<String, BKTFragment>()
    var fragmentMap = LinkedHashMap<Fragment, BKTFragment>()
    lateinit var busesForRoute: BusesForRoute

    fun setUpNavListener(navigationMenu: BottomNavigationView, context: MainMenuActivity) {

        var fragment: Fragment
        navigationMenu.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_flights -> {
                    fragment = getFragmentByKey(Constants.FLIGHTS_FRAGMENT).fragment
                    navigationMenu.menu.findItem(R.id.navigation_flights).isChecked = true
                    loadFragment(fragment, context)
                }
                R.id.navigation_hotels -> {
                    fragment = getFragmentByKey(Constants.HOTELS_FRAGMENT).fragment
                    navigationMenu.menu.findItem(R.id.navigation_hotels).isChecked = true
                    loadFragment(fragment, context)
                }
                R.id.navigation_entertainment -> {
                    fragment = getFragmentByKey(Constants.ENTERTAINMENT_FRAGMENT).fragment
                    navigationMenu.menu.findItem(R.id.navigation_entertainment).isChecked = true
                    loadFragment(fragment, context)
                }
                R.id.navigation_buses -> {
                    fragment = getFragmentByKey(Constants.BUSES_FRAGMENT).fragment
                    navigationMenu.menu.findItem(R.id.navigation_buses).isChecked = true
                    loadFragment(fragment, context)
                }
                R.id.navigation_vacations -> {
                    fragment = getFragmentByKey(Constants.VACATIONS_FRAGMENT).fragment
                    navigationMenu.menu.findItem(R.id.navigation_vacations).isChecked = true
                    loadFragment(fragment, context)
                }
            }
            false
        }
    }

    fun setUpNavigationStructure(activity: AppCompatActivity) {

        val mainMenuFragment = BKTFragment(activity, Constants.MAIN_MENU_FRAGMENT,
            MenuFragment(), null, null)
        backStack[Constants.MAIN_MENU_FRAGMENT] = mainMenuFragment
        fragmentMap[mainMenuFragment.fragment] = mainMenuFragment

        val userProfileFragment = BKTFragment(activity, Constants.USER_PROFILE_FRAGMENT,
            UserProfileFragment(), null, mainMenuFragment)
        backStack[Constants.USER_PROFILE_FRAGMENT] = userProfileFragment
        fragmentMap[userProfileFragment.fragment] = userProfileFragment

        val flightsFragment = BKTFragment(activity, Constants.FLIGHTS_FRAGMENT,
            FlightsFragment(), null, mainMenuFragment)
        backStack[Constants.FLIGHTS_FRAGMENT] = flightsFragment
        fragmentMap[flightsFragment.fragment] = flightsFragment

        val hotelsFragment = BKTFragment(activity, Constants.HOTELS_FRAGMENT,
            HotelsFragment(), null, mainMenuFragment)
        backStack[Constants.HOTELS_FRAGMENT] = hotelsFragment
        fragmentMap[hotelsFragment.fragment] = hotelsFragment

        val vacationsFragment = BKTFragment(activity, Constants.VACATIONS_FRAGMENT,
            VacationFragment(), null, mainMenuFragment)
        backStack[Constants.VACATIONS_FRAGMENT] = vacationsFragment
        fragmentMap[vacationsFragment.fragment] = vacationsFragment

        val busesFragment = BKTFragment(activity, Constants.BUSES_FRAGMENT,
                BusesFragment(), null, mainMenuFragment)
        backStack[Constants.BUSES_FRAGMENT] = busesFragment
        fragmentMap[busesFragment.fragment] = busesFragment

        val busListingFragment = BKTFragment(activity, Constants.BUS_LISTING_FRAGMENT,
                BusListingFragment(), null, busesFragment)
        backStack[Constants.BUS_LISTING_FRAGMENT] = busListingFragment
        fragmentMap[busListingFragment.fragment] = busListingFragment

        val busFareFragment = BKTFragment(activity, Constants.BUS_FARE_FRAGMENT,
                BusFareFragment(), null, busListingFragment)
        backStack[Constants.BUS_FARE_FRAGMENT] = busFareFragment
        fragmentMap[busFareFragment.fragment] = busFareFragment

        val busPaymentFragment = BKTFragment(activity, Constants.BUS_PAYMENT_FRAGMENT,
                BusPaymentFragment(), null, busFareFragment)
        backStack[Constants.BUS_PAYMENT_FRAGMENT] = busPaymentFragment
        fragmentMap[busPaymentFragment.fragment] = busPaymentFragment

        val entertainmentFragment = BKTFragment(activity, Constants.ENTERTAINMENT_FRAGMENT,
            EntertainmentFragment(), null, mainMenuFragment)
        backStack[Constants.ENTERTAINMENT_FRAGMENT] = entertainmentFragment
        fragmentMap[entertainmentFragment.fragment] = entertainmentFragment
    }

    fun loadFragment(fragment: Fragment, context: MainMenuActivity) {

        val transaction = context.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.container, fragment)
        transaction.commit()
        context.getBottomNav().visibility = View.VISIBLE
        currentFragment = fragment
    }

    fun getFragmentByKey(key: String): BKTFragment {
        return backStack[key] ?: backStack[Constants.MAIN_MENU_FRAGMENT]!!
    }

}