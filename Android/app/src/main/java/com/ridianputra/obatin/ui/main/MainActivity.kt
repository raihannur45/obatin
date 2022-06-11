package com.ridianputra.obatin.ui.main

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import com.ridianputra.obatin.R
import com.ridianputra.obatin.data.MainAdapter
import com.ridianputra.obatin.data.Message
import com.ridianputra.obatin.data.local.TokenDataStore
import com.ridianputra.obatin.data.local.TokenViewModel
import com.ridianputra.obatin.data.local.TokenViewModelFactory
import com.ridianputra.obatin.databinding.ActivityMainBinding
import com.ridianputra.obatin.ui.login.LoginActivity
import com.ridianputra.obatin.utils.ViewModelFactory
import com.ridianputra.obatin.data.Result
import com.ridianputra.obatin.ui.profile.ProfileActivity
import com.ridianputra.obatin.ui.welcome.WelcomeActivity
import com.ridianputra.obatin.utils.NetworkHelper

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_datastore")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    private lateinit var tokenViewModel: TokenViewModel
    private lateinit var messageObject: JsonObject
    private lateinit var refreshTokenObject: JsonObject
    private lateinit var medAndLab: JsonObject // medicine and label object
    private lateinit var medicineName: JsonObject
    private lateinit var labelName: JsonObject

    private var accessToken = ""
    private var refreshToken = ""

    private val botViewModel: BotViewModel by viewModels { ViewModelFactory.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Obatin)
        binding = ActivityMainBinding.inflate(layoutInflater)
        messageObject = JsonObject()
        refreshTokenObject = JsonObject()
        medAndLab = JsonObject()
        medicineName = JsonObject()
        labelName = JsonObject()
        setContentView(binding.root)

        supportActionBar?.title = "Chat"

        loadToken()
        setupRecyclerView()

        binding.btnSend.setOnClickListener {
            if (NetworkHelper.hasInternetConnection(this@MainActivity)) {
                val message = binding.messageInput.text.toString()
                if (message.isBlank()) {
                    Snackbar.make(it, "Message cannot be empty", Snackbar.LENGTH_LONG).show()
                } else {
                    binding.messageInput.text = null
                    mainAdapter.insertMessage(Message(1, message))
                    binding.messageRecyclerView.scrollToPosition(mainAdapter.itemCount - 1)
                    messageObject.addProperty("text", message)

                    when {
                        !medAndLab.has("namaobat") && medAndLab.has("label") -> {
                            medAndLab.addProperty("namaobat", message)
                            robotMessageReply()
                        }
                        else -> talkWithBot()
                    }
                }
            } else {
                Snackbar.make(binding.root, "Network Error", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (NetworkHelper.hasInternetConnection(this@MainActivity)) {
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
                R.id.profile -> {
                    Intent(this@MainActivity, ProfileActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
        } else {
            Snackbar.make(binding.root, "Network Error", Snackbar.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadToken() {
        tokenViewModel = ViewModelProvider(
            this,
            TokenViewModelFactory(TokenDataStore.getInstance(dataStore))
        )[TokenViewModel::class.java]

        tokenViewModel.loadWelcomeKey().observe(this) { welcomeKey ->
            if (welcomeKey == "0") {
                Intent(this@MainActivity, WelcomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
                finish()
            }
        }

        tokenViewModel.loadToken().observe(this) { result ->
            if (result.accessToken.isNotBlank() && result.refreshToken.isNotBlank()) {
                accessToken = result.accessToken
                refreshToken = result.refreshToken
                refreshTokenObject.addProperty("refreshToken", result.refreshToken)
            } else {
                Intent(this@MainActivity, LoginActivity::class.java).also {
                    it.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
                finish()
            }
        }
    }

    private fun talkWithBot() {
        botViewModel.robot(accessToken, messageObject).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Result.Success -> {
                        messageObject.remove("text")
                        val obat = result.data.namaobat
                        val label = result.data.label
                        val status = result.data.status

                        when {
                            label.isNotBlank() && obat.isNotBlank() && status == 1 -> {
                                medAndLab.addProperty("label", label)
                                medAndLab.addProperty("namaobat", obat)
                                robotMessageReply()
                            }
                            label.isNotBlank() -> {
                                medAndLab.addProperty("label", label)
                                robotMessage()
                            }
                        }

                        binding.progressBar.visibility = View.GONE
                    }
                    is Result.Error -> updateAccessToken()
                }
            }
        }
    }

    private fun robotMessage() {
        botViewModel.robotMessage(accessToken, medAndLab).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Result.Success -> {
                        if (medAndLab.has("label")) {
                            val label = medAndLab.get("label").asString
                            if (label == "fitur" || label == "pergi" || label == "tidakpaham") {
                                medAndLab.remove("label")
                            }
                        }
                        val botResponse = result.data.jawab.replace("\\n", "\n")
                        mainAdapter.insertMessage(Message(0, botResponse))
                        binding.messageRecyclerView.scrollToPosition(mainAdapter.itemCount - 1)
                        binding.progressBar.visibility = View.GONE
                    }
                    is Result.Error -> {
                        Snackbar.make(
                            binding.root,
                            "There is a problem",
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun robotMessageReply() {
        botViewModel.robotMessageReply(accessToken, medAndLab).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Result.Success -> {
                        medAndLab.remove("label")
                        medAndLab.remove("namaobat")
                        val botResponse = result.data.jawab.replace("\\n", "\n")
                        mainAdapter.insertMessage(Message(0, botResponse))
                        binding.messageRecyclerView.scrollToPosition(mainAdapter.itemCount - 1)
                        binding.progressBar.visibility = View.GONE
                    }
                    is Result.Error -> {
                        Snackbar.make(
                            binding.root,
                            "There is a problem",
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun updateAccessToken() {
        botViewModel.updateAccessToken(refreshTokenObject).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Result.Success -> {
                        tokenViewModel.saveToken(result.data.accessToken, refreshToken)
                        talkWithBot()
                        binding.progressBar.visibility = View.GONE
                    }
                    is Result.Error -> {
                        Snackbar.make(
                            binding.root,
                            "There is a problem",
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        mainAdapter = MainAdapter()
        binding.messageRecyclerView.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
        mainAdapter.insertMessage(
            Message(
                0,
                "Halo, Selamat Datang di aplikasi Obatin! Ada yang bisa saya bantu?"
            )
        )
        binding.messageRecyclerView.scrollToPosition(mainAdapter.itemCount - 1)
    }
}