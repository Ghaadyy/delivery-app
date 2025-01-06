package com.example.deliveryapp.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.deliveryapp.data.model.menu.MealOption
import androidx.room.TypeConverter
import com.example.deliveryapp.data.model.menu.Meal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "meal_requests")
data class MealRequest (
    val meal: Meal,
    val price: Double,
    val quantity: Int,
    val options: List<MealOption> = emptyList(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

class MealOptionConverters {
    @TypeConverter
    fun fromMealOptionList(mealOptions: List<MealOption>?): String? {
        return Gson().toJson(mealOptions)
    }

    @TypeConverter
    fun toMealOptionList(mealOptionsString: String?): List<MealOption>? {
        val listType = object : TypeToken<List<MealOption>>() {}.type
        return Gson().fromJson(mealOptionsString, listType)
    }

    @TypeConverter
    fun fromMeal(meal: Meal?): String? {
        return Gson().toJson(meal)
    }

    @TypeConverter
    fun toMeal(meal: String?): Meal? {
        val mealType = object : TypeToken<Meal>() {}.type
        return Gson().fromJson(meal, mealType)
    }
}
