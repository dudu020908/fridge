package com.example.fridgeapp.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.fridgeapp.model.Ingredient

class FridgeViewModel : ViewModel() {
    var ingredients by mutableStateOf(listOf<Ingredient>())
        private set

    var selectedCategory by mutableStateOf<String?>(null)

    fun addIngredient(item: Ingredient) {
        ingredients = ingredients + item
    }

    fun getByCategory(category: String): List<Ingredient> {
        return ingredients.filter { it.category == category }
    }
}
