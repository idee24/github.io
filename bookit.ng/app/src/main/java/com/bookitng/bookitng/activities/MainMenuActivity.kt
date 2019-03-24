package com.bookitng.bookitng.activities

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.internal.BottomNavigationMenu
import android.support.design.widget.BottomNavigationView
import com.bookitng.bookitng.R
import com.bookitng.bookitng.fragments.FlightsFragment
import com.bookitng.bookitng.fragments.MenuFragment
import com.bookitng.bookitng.models.TodayMovies
import com.bookitng.bookitng.utils.Constants
import com.bookitng.bookitng.viewmodels.MainMenuViewModel
import kotlinx.android.synthetic.main.activity_main_menu.*


class MainMenuActivity : AppCompatActivity() {

    lateinit var viewModel: MainMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        initViewModel()
        viewModel.setUpNavigationStructure(this)
        viewModel.setUpNavListener(navigation, this)
        viewModel.loadFragment(viewModel.getFragmentByKey(Constants.MAIN_MENU_FRAGMENT).fragment, this)
    }



    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainMenuViewModel::class.java)
    }

    override fun onBackPressed() {
        val currentFragment = viewModel.currentFragment
        val bktFragment = viewModel.fragmentMap[currentFragment]
        if (bktFragment?.previousFragment == null) {
            this.moveTaskToBack(true)
        }
        else {
            viewModel.loadFragment(bktFragment.previousFragment?.fragment!!, this)
        }
    }

    fun getBottomNav(): BottomNavigationView {
        return navigation
    }
}
