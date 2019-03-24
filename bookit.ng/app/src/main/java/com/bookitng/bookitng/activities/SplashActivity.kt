package com.bookitng.bookitng.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import com.bookitng.bookitng.R
import com.bookitng.bookitng.extensions.errorMessage
import com.bookitng.bookitng.extensions.toast
import com.bookitng.bookitng.models.LoginModel
import com.bookitng.bookitng.models.LoginPayload
import com.bookitng.bookitng.models.UserModel
import com.bookitng.bookitng.networking.generateService
import com.bookitng.bookitng.networking.services.LoginService
import com.bookitng.bookitng.utils.Constants
import com.bookitng.bookitng.utils.isLoggedIn
import com.bookitng.bookitng.utils.persistModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            splashTransition()
        }, Constants.SPLASH_DELAY_CONFIG.toLong())
    }

    private fun splashTransition() {
        if (isLoggedIn(this)) {
            renewBearerToken()
        }
        else {
            transitionToLogin()
        }
    }

    private fun renewBearerToken()  {

        val json = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(UserModel::class.java.simpleName, null)
        val currentUser = Gson().fromJson(json, UserModel::class.java)

        val loginModel = LoginModel(currentUser.username, currentUser.password)
        val call = generateService(LoginService::class.java).initiateLogin(loginModel)
        var bearerToken : LoginPayload

        call.enqueue(object : Callback<LoginPayload> {

            override fun onResponse(call: Call<LoginPayload>, response: Response<LoginPayload>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        bearerToken = it
                        currentUser.bearerToken = bearerToken.token
                        transitionToMainMenu(currentUser)
                    }
                }
                else {
                    transitionToLogin()
                }
            }

            override fun onFailure(call: Call<LoginPayload>, t: Throwable) {
                toast("Error Login 2 ${t.stackTrace}")
                transitionToLogin()
            }

        })
    }

    private fun transitionToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun transitionToMainMenu(user: UserModel) {
        persistModel(user, this, UserModel::class.java.simpleName)
        startActivity(Intent(this, MainMenuActivity::class.java))
    }

}
