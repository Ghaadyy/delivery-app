package com.example.deliveryapp.data.model

enum class OrderRating(val ratingValue: Int, val label: String) {
    EXCELLENT(5, "Excellent"),
    GOOD(4, "Good"),
    AVERAGE(3, "Average"),
    POOR(2, "Poor"),
    TERRIBLE(1, "Terrible"),
    PENDING(0, "Pending"),
    NOT_APPLICABLE(-1, "Not Applicable");

    companion object {
        fun fromId(ratingValue: Int): OrderRating? {
            return entries.find { it.ratingValue == ratingValue }
        }
    }
}
