package com.example.deliveryapp.data.model

enum class DriverRating(val ratingValue: Int, val label: String) {
    LIKE(1, "Like"),
    DISLIKE(-1, "Dislike"),
    PENDING(0, "Pending"),
    NOT_APPLICABLE(-2, "Not Applicable");

    companion object {
        fun fromId(ratingValue: Int): DriverRating? {
            return entries.find { it.ratingValue == ratingValue }
        }
    }
}