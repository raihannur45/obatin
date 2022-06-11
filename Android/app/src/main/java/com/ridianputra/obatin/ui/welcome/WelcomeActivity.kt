package com.ridianputra.obatin.ui.welcome

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.ridianputra.obatin.data.local.TokenDataStore
import com.ridianputra.obatin.data.local.TokenViewModel
import com.ridianputra.obatin.data.local.TokenViewModelFactory
import com.ridianputra.obatin.databinding.ActivityWelcomeBinding
import com.ridianputra.obatin.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_datastore")

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var tokenViewModel: TokenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()
        loadToken()

        binding.btnAyo.setOnClickListener {
            tokenViewModel.saveWelcomeKey("1")
        }
    }

    private fun loadToken() {
        tokenViewModel = ViewModelProvider(
            this,
            TokenViewModelFactory(TokenDataStore.getInstance(dataStore))
        )[TokenViewModel::class.java]

        tokenViewModel.loadWelcomeKey().observe(this) { welcomeKey ->
            if (welcomeKey == "1") {
                Intent(this@WelcomeActivity, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
                finish()
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