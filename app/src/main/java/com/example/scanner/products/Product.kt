package com.example.scanner.products

import com.example.scanner.R

enum class Category {
}

data class Product(
    val id: Int, // "code" in API response
    val name: String,
    val thumbnailId: Int,
    /*
    val brand: String,
    val ingredients: String,
    val category: String,
    val nutritionGrade: String, // nutriscore, can be null?
     */
)

val sampleProducts = listOf(
    Product(1, "lo", R.drawable.cristalline),
    Product(2, "uhihih", R.drawable.cristalline),
    Product(3, "lofsdjkfjsl", R.drawable.cristalline),
    Product(4, "lfdnsjkf", R.drawable.cristalline),
    Product(5, "aaaaaaaaaaaaaaaaaaao", R.drawable.cristalline),
)