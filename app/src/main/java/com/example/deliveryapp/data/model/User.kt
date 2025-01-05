package com.example.deliveryapp.data.model

import java.time.LocalDate

data class User(
    var firstName: String,
    var lastName: String,
    var email: String,
    var dob: LocalDate?,
    var phoneNumber: String?,
    var password: String,
)
