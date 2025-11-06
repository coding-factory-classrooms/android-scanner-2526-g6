package com.example.scanner.products

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.scanner.common.ApiCall
import com.example.scanner.common.ApiResponse
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow

sealed class ProductListUiState {
    data object Initial : ProductListUiState() // mark as object when no attributes // LOADING state
    data class Success(val products: MutableList<Product>?) : ProductListUiState()
    data class Failure(val message: String) : ProductListUiState()
}

class ProductViewModel() : ViewModel() {

    val productFlow = MutableStateFlow<ProductListUiState>(ProductListUiState.Initial) // store page state -> ProductListUiState.Loading = initial state

    //Create Product
    fun createProduct(product: Product) {
        var ProductList = Paper.book().read("products", mutableListOf<Product>())
        ProductList!!.add(product)
        Paper.book().write("products", ProductList)
    }

    //Create Product by default without cam
    fun CreateDefaultProduct () {

        val response = ApiCall("3017624010701")
        if (response is ApiResponse.Success) {
            createProduct(response.product)
            println("database ${getProducts()}")
        } else {
            println("failed")
        }

    }

    //Get all product with Key products in bdd local
    fun getProducts(): List<Product> {
        try {
            productFlow.value = ProductListUiState.Success(Paper.book().read("products", mutableListOf<Product>()))
            return Paper.book().read("products", mutableListOf<Product>())!!
        } catch (e : Exception){
            productFlow.value = ProductListUiState.Failure("Erreur")
            println("erreur")
            return emptyList()
        }
    }

    //Get specified product in local bdd with id
    fun getProductById(id : Int): Product? {
        try {
            productFlow.value = ProductListUiState.Success(Paper.book().read("products", mutableListOf<Product>()))
            val t = Paper.book().read("products", mutableListOf<Product>())!!
            return t.get(id)
        } catch (e : Exception){
            productFlow.value = ProductListUiState.Failure("Erreur")
            println("erreur")
            return null
        }
    }

    //call getProduct and update state of product
    fun LoadProduct() {
        productFlow.value = ProductListUiState.Initial;
        getProducts()
    }

    //Delete product with associated id in bdd local with key products
    fun DeleteProduct(ProductIndex: Int, context: Context ) {
        var ProductList = Paper.book().read("products", mutableListOf<Product>())!!
        if (ProductList.size == 0){
            return Toast.makeText(context, "impossible", Toast.LENGTH_SHORT).show()
        }
        ProductList.removeAt(ProductIndex)
        Paper.book().write("products", ProductList)
        LoadProduct()
    }

}
