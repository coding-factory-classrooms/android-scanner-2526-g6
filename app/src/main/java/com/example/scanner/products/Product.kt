package com.example.scanner.products

enum class Category {
}

data class Product (
    val _id: String, // noted "code" in API response
    val product_name: String,
//    val name: String,
//    val brand: String,
//    val ingredients: String,
//    val category: String,
//    val nutritionGrade: String, // nutriscore, can be null?
)
data class ProductResponse(
    val product: Product
)

val sampleProducts = listOf(
    Product("lo","0"),
    Product("uhihih","1"),
    Product("lofsdjkfjsl","2"),
    Product("lfdnsjkf","3"),
    Product("aaaaaaaaaaaaaaaaaaao","4"),
    Product("lo","5"),
    Product("uhihih","6"),
    Product("lofsdjkfjsl","7"),
    Product("lfdnsjkf","8"),
    Product("aaaaaaaaaaaaaaaaaaao","9"),
    )

val fakeProduct = Product("Faux Produit tres bon", "123")