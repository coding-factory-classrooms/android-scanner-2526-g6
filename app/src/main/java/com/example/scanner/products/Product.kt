package com.example.scanner.products

data class Product (
    val _id: String, // noted "code" in API response
    var product_name: String,
    val image_url: String,
    val brands: String,
    var favorite: Boolean = false,
//    val ingredients: String,
//    val category: String,
//    val nutritionGrade: String, // nutriscore, can be null?
)
data class ProductResponse(
    val product: Product
)

/*
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
*/

val fakeProduct = Product("123", "Faux Produit tres bon", "https://images.openfoodfacts.net/images/products/405/648/964/1018/front_fr.3.400.jpg", "Test")
val newProduct = Product("123", "Faux Produit tres bon", "https://images.openfoodfacts.net/images/products/405/648/964/1018/front_fr.3.400.jpg", "Test")