package com.bookitng.bookitng.extensions

import android.view.View
import com.bookitng.bookitng.activities.MainMenuActivity

fun hideMenuBarz(activity: MainMenuActivity) {

    activity.supportActionBar!!.hide()
    activity.getBottomNav().visibility = View.GONE
}

fun showMenuBarz(activity: MainMenuActivity) {

    activity.supportActionBar!!.show()
    activity.getBottomNav().visibility = View.VISIBLE
}