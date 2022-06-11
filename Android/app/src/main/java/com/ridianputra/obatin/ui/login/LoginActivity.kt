package com.ridianputra.obatin.ui.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import com.ridianputra.obatin.data.local.TokenDataStore
import com.ridianputra.obatin.data.local.TokenViewModel
import com.ridianputra.obatin.data.local.TokenViewModelFactory
import com.ridianputra.obatin.databinding.ActivityLoginBinding
import com.ridianputra.obatin.ui.main.MainActivity
import com.ridianputra.obatin.ui.register.RegisterActivity
import com.ridianputra.obatin.utils.NetworkHelper
import com.ridianputra.obatin.utils.ViewModelFactory
import com.ridianputra.obatin.data.Result
import com.ridianputra.obatin.ui.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_datastore")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var tokenViewModel: TokenViewModel

    private var accessToken = ""
    private var refreshToken = ""
    private var userData = "" //username or email

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadToken()
        hideSystemUI()
        intentToRegisterActivity()
        loginAction()
    }

    private fun loadToken() {
        tokenViewModel = ViewModelProvider(
            this,
            TokenViewModelFactory(TokenDataStore.getInstance(dataStore))
        )[TokenViewModel::class.java]

        tokenViewModel.loadWelcomeKey().observe(this) { welcomeKey ->
            if (welcomeKey == "0") {
                Intent(this@LoginActivity, WelcomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
                finish()
            }
        }

        tokenViewModel.loadToken().observe(this) { result ->
            if (result.accessToken.isNotBlank() && result.refreshToken.isNotBlank()) {
                Intent(this@LoginActivity, MainActivity::class.java).also {
                    it.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
                finish()
            }
        }
    }

    private fun loginAction() {
        binding.loginBtn.setOnClickListener {
            if (NetworkHelper.hasInternetConnection(this@LoginActivity)) {
                if (binding.userInput.text.isNullOrBlank()
                    || binding.passwordInput.text.isNullOrBlank()
                ) {
                    Snackbar.make(it, "Input field cannot be empty", Snackbar.LENGTH_LONG).show()
                } else {
                    val user = binding.userInput.text.toString().trim()
                    val pass = binding.passwordInput.text.toString().trim()

                    val objek = JsonObject()
                    objek.addProperty("username", user)
                    objek.addProperty("password", pass)

                    loginViewModel.userLogin(objek).observe(this) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                                is Result.Success -> {
                                    accessToken = result.data.accessToken
                                    refreshToken = result.data.refreshToken
                                    userData = user
                                    getUserData()
                                }
                                is Result.Error -> {
                                    binding.progressBar.visibility = View.GONE
                                    Snackbar.make(it, "Wrong credentials", Snackbar.LENGTH_LONG)
                                        .show()
                                }
                            }
                        }
                    }
                }
            } else {
                Snackbar.make(binding.root, "Network Error", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun getUserData() {
        loginViewModel.getUserData(accessToken, userData, userData).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Result.Success -> {
                        tokenViewModel.saveDataUser(
                            result.data.fullname,
                            result.data.username,
                            result.data.email
                        )
                        tokenViewModel.saveToken(
                            accessToken,
                            refreshToken
                        )
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Snackbar.make(binding.root, "Login failed, try again", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun intentToRegisterActivity() {
        binding.toRegister.setOnClickListener {
            Intent(this@LoginActivity, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}