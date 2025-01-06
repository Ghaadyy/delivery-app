package com.example.deliveryapp.ui.fragments.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.HomeActivity
import com.example.deliveryapp.R
import com.example.deliveryapp.ui.viewModel.UserViewModel
import com.example.deliveryapp.token.TokenManager

class SignupFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        val firstNameEditText = view.findViewById<EditText>(R.id.firstNameEditText)
        val lastNameEditText = view.findViewById<EditText>(R.id.lastNameEditText)
        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val signUpButton = view.findViewById<Button>(R.id.signUpButton)

        userViewModel.loginSuccess.observe(viewLifecycleOwner) { success ->
            if (success) navigateToHomeScreen()
        }

        userViewModel.error.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        }

        signUpButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                userViewModel.signup(firstName, lastName, email, password)
            }
        }
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}