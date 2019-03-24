package com.bookitng.bookitng.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bookitng.bookitng.R
import com.bookitng.bookitng.extensions.errorMessage
import com.bookitng.bookitng.models.LoginModel
import com.bookitng.bookitng.models.LoginPayload
import com.bookitng.bookitng.models.UserModel
import com.bookitng.bookitng.networking.generateService
import com.bookitng.bookitng.networking.services.LoginService
import com.bookitng.bookitng.utils.persistModel
import com.bookitng.bookitng.utils.setLoggedIn
import com.sriyank.javatokotlindemo.extensions.isValid
import com.bookitng.bookitng.extensions.toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton.setOnClickListener {
//            initiateLogin()
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }

    private fun initiateLogin() {

        if (usernameTextField.isValid(userNameInputLayout) &&
            passwordTextField.isValid(passwordInputLayout)) {

            val userName = usernameTextField.text.toString().trim()
            val password = passwordTextField.text.toString().trim()
            val loginModel = LoginModel(userName, password)
            val call = generateService(LoginService::class.java).initiateLogin(loginModel)
            var bearerToken : LoginPayload
            showLoader()
            call.enqueue(object : Callback<LoginPayload> {

                override fun onResponse(call: Call<LoginPayload>, response: Response<LoginPayload>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            bearerToken = it
                            finaliseLogin(userName, password, bearerToken.token)
                        }
                    }
                    else {
                        errorMessage(this@LoginActivity, loginLayout, getString(R.string.login_error_text))
                    }
                    hideLoader()
                }

                override fun onFailure(call: Call<LoginPayload>, t: Throwable) {
                    errorMessage(this@LoginActivity, loginLayout, getString(R.string.network_error_text))
                    hideLoader()
                }

            })
        }
    }

    private fun finaliseLogin(username: String?, password: String?, bearerToken: String?) {
        val user= UserModel(username, password, bearerToken)
        persistModel(user, this, UserModel::class.java.simpleName)
        setLoggedIn(this, true)
        startActivity(Intent(this, MainMenuActivity::class.java))
    }

    private fun hideLoader() {
        loginButton.visibility = View.VISIBLE
        spinKit.visibility = View.GONE
    }
    private fun showLoader() {
        loginButton.visibility = View.GONE
        spinKit.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }
}
