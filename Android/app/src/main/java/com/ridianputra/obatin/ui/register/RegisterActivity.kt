package com.ridianputra.obatin.ui.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import com.ridianputra.obatin.databinding.ActivityRegisterBinding
import com.ridianputra.obatin.ui.login.LoginActivity
import com.ridianputra.obatin.utils.ViewModelFactory
import com.ridianputra.obatin.data.Result
import com.ridianputra.obatin.utils.NetworkHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()
        registerAction()

        binding.toLogin.setOnClickListener {
            Intent(this@RegisterActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun registerAction() {
        removeErrorWhenTextChanged()
        binding.registerBtn.setOnClickListener {
            if (NetworkHelper.hasInternetConnection(this@RegisterActivity)) {
                fullNameValidation()
                usernameValidation()
                emailValidation()
                passwordValidation()
                passwordConfirmValidation()

                if (binding.fullnameLayout.error.isNullOrBlank()
                    && binding.usernameLayout.error.isNullOrBlank()
                    && binding.emailLayout.error.isNullOrBlank()
                    && binding.passwordLayout.error.isNullOrBlank()
                    && binding.passwordconfirmLayout.error.isNullOrBlank()
                ) {
                    val fullname = binding.fullnameInput.text.toString().trim()
                    val username = binding.usernameInput.text.toString().trim()
                    val email = binding.emailInput.text.toString().trim()
                    val password = binding.passwordInput.text.toString().trim()
                    val confirm = binding.passwordconfirmInput.text.toString().trim()

                    val objek = JsonObject()
                    objek.addProperty("email", email)
                    objek.addProperty("password", password)
                    objek.addProperty("passwordConfirmation", confirm)
                    objek.addProperty("username", username)
                    objek.addProperty("fullname", fullname)

                    registerViewModel.userRegister(objek).observe(this) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                                is Result.Success -> {
                                    Intent(this@RegisterActivity, LoginActivity::class.java).also {
                                        startActivity(it)
                                    }
                                }
                                is Result.Error -> {
                                    Snackbar.make(
                                        binding.root,
                                        "Username or Email already used",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    binding.progressBar.visibility = View.GONE
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

    private fun removeErrorWhenTextChanged() {
        binding.fullnameInput.doAfterTextChanged {
            binding.fullnameLayout.error = null
            binding.fullnameLayout.isErrorEnabled = false
        }
        binding.usernameInput.doAfterTextChanged {
            binding.usernameLayout.error = null
            binding.usernameLayout.isErrorEnabled = false
        }
        binding.emailInput.doAfterTextChanged {
            binding.emailLayout.error = null
            binding.emailLayout.isErrorEnabled = false
        }
        binding.passwordInput.doAfterTextChanged {
            binding.passwordLayout.error = null
            binding.passwordLayout.isErrorEnabled = false
        }
        binding.passwordconfirmInput.doAfterTextChanged {
            binding.passwordconfirmLayout.error = null
            binding.passwordconfirmLayout.isErrorEnabled = false
        }
    }

    private fun fullNameValidation() {
        if (binding.fullnameInput.text.isNullOrBlank()) {
            binding.fullnameLayout.error = "Full name cannot be empty"
        } else {
            binding.fullnameLayout.error = null
            binding.fullnameLayout.isErrorEnabled = false
        }
    }

    private fun usernameValidation() {
        when {
            binding.usernameInput.text.isNullOrBlank() -> {
                binding.usernameLayout.error = "Username cannot be empty"
            }
            binding.usernameInput.text.toString().contains(" ") -> {
                binding.usernameLayout.error = "Username cannot contain spaces"
            }
            else -> {
                binding.usernameLayout.error = null
                binding.usernameLayout.isErrorEnabled = false
            }
        }
    }

    private fun emailValidation() {
        when {
            binding.emailInput.text.isNullOrBlank() -> {
                binding.emailLayout.error = "Email cannot be empty"
            }
            !Patterns.EMAIL_ADDRESS.matcher(binding.emailInput.text!!).matches() -> {
                binding.emailLayout.error = "Invalid email pattern"
            }
            else -> {
                binding.emailLayout.error = null
                binding.emailLayout.isErrorEnabled = false
            }
        }
    }

    private fun passwordValidation() {
        val regex =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-,.@#$%^&*()_!+=])(?=\\S+$).{4,}$".toRegex()
        when {
            binding.passwordInput.text.isNullOrBlank() -> {
                binding.passwordLayout.error = "Password cannot empty"
            }
            !binding.passwordInput.text.toString().matches(regex) -> {
                binding.passwordLayout.error =
                    "Password must contain at least one uppercase letter, lowercase letter, number, symbol, and no whitespaces"
                binding.passwordconfirmInput.text = null
            }
            else -> {
                binding.passwordLayout.error = null
                binding.passwordLayout.isErrorEnabled = false
            }
        }
    }

    private fun passwordConfirmValidation() {
        when {
            binding.passwordconfirmInput.text.isNullOrBlank() -> {
                binding.passwordconfirmLayout.error = "Password confirmation cannot be empty"
            }
            binding.passwordconfirmInput.text.toString() != binding.passwordInput.text.toString() -> {
                binding.passwordconfirmLayout.error = "Password does not match"
            }
            else -> {
                binding.passwordconfirmLayout.error = null
                binding.passwordconfirmLayout.isErrorEnabled = false
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