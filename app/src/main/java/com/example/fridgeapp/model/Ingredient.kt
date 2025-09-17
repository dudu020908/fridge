package com.example.fridgeapp.model

data class Ingredient(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val quantity: Int,
    val expiryDate: String,
    val category: String
)