package com.example.deliveryapp.data.model

import java.time.LocalDate

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val dob: LocalDate?,
    val phoneNumber: String?,
    val password: String,
)
