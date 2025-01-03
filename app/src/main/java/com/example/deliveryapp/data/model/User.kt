package com.example.deliveryapp.data.model

import java.time.LocalDate

data class User(
    val firstName: String,
    val lastName: String,
    val dob: LocalDate,
    val email: String,
    val phoneNumber: String,
)
