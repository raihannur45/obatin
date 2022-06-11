package com.ridianputra.obatin.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import com.ridianputra.obatin.R
import com.ridianputra.obatin.data.Result
import com.ridianputra.obatin.data.local.TokenDataStore
import com.ridianputra.obatin.data.local.TokenViewModel
import com.ridianputra.obatin.data.local.TokenViewModelFactory
import com.ridianputra.obatin.databinding.ActivityProfileBinding
import com.ridianputra.obatin.ui.login.LoginActivity
import com.ridianputra.obatin.ui.main.BotViewModel
import com.ridianputra.obatin.utils.NetworkHelper
import com.ridianputra.obatin.utils.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_datastore")

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var tokenViewModel: TokenViewModel
    private lateinit var refreshTokenObject: JsonObject

    private val botViewModel: BotViewModel by viewModels { ViewModelFactory.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        refreshTokenObject = JsonObject()
        setContentView(binding.root)
        supportActionBar?.title = "Profile"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadTokenAndDataUser()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (NetworkHelper.hasInternetConnection(this@ProfileActivity)) {
            when (item.itemId) {
                R.id.logout -> {
                    botViewModel.deleteAccessToken(refreshTokenObject).observe(this) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                                is Result.Success -> tokenViewModel.logout()
                                is Result.Error -> {
                                    Snackbar.make(
                                        binding.root,
                                        "Failed to logout",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    binding.progressBar.visibility = View.GONE
                                }
                            }
                        }
                    }
                }
                android.R.id.home -> finish()
            }
        } else {
            Snackbar.make(binding.root, "Network Error", Snackbar.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadTokenAndDataUser() {
        tokenViewModel = ViewModelProvider(
            this,
            TokenViewModelFactory(TokenDataStore.getInstance(dataStore))
        )[TokenViewModel::class.java]

        tokenViewModel.loadToken().observe(this) { result ->
            if (result.accessToken.isBlank() && result.refreshToken.isBlank()) {
                Intent(this@ProfileActivity, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
                finish()
            } else {
                refreshTokenObject.addProperty("refreshToken", result.refreshToken)
            }
        }

        tokenViewModel.loadDataUser().observe(this) { result ->
            if (result.fullname.isNotBlank()) {
                binding.fullname.text = result.fullname
            } else {
                binding.fullname.text = "Empty data"
            }

            if (result.username.isNotBlank()) {
                binding.usernameData.text = result.username
            } else {
                binding.usernameData.text = "Empty data"
            }

            if (result.email.isNotBlank()) {
                binding.emailData.text = result.email
            } else {
                binding.emailData.text = "Empty data"
            }
        }
    }
}