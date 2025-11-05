package com.example.scanner.products

enum class Category {
}

data class Product(
    val id: Int, // noted "code" in API response
    val name: String,
    val brand: String,
    val ingredients: String,
    val category: String,
    val nutritionGrade: String, // nutriscore, can be null?
)