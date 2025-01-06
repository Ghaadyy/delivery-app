package com.example.deliveryapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.token.TokenManager
import com.example.deliveryapp.ui.fragments.user.LoginFragment
import com.example.deliveryapp.ui.fragments.user.SignupFragment
import com.example.deliveryapp.ui.viewModel.UserViewModel

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var authSwitch: SwitchCompat

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        TokenManager.initialize(applicationContext)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        if(!TokenManager.isTokenNotValid()){
            navigateToHomeScreen()
        }else{
            TokenManager.clearToken()
        }

        setContentView(R.layout.activity_authentication)

        authSwitch = findViewById(R.id.authSwitch)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.auth_fragment_container, LoginFragment())
                .commit()
        }

        authSwitch.setOnCheckedChangeListener { _, isChecked ->
            userViewModel.clearError()
            val fragment = if (isChecked) SignupFragment() else LoginFragment()
            authSwitch.text = if (isChecked) "Sign Up" else "Log In"
            supportFragmentManager.beginTransaction()
                .replace(R.id.auth_fragment_container, fragment)
                .commit()
        }
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}