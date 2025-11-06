package com.example.scanner.products

import androidx.lifecycle.ViewModel
import com.example.scanner.common.ApiError
import com.example.scanner.common.ApiResponse
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random

sealed class ProductListUiState {
    /* children classes are all possible states depending on :
    - Waiting for response :
        Loading
    - received API response :
        Success (200)
        Error (400, 300, 500)
     */
    data object Initial : ProductListUiState() // mark as object when no attributes // LOADING state
    data class Success(val products: MutableList<Product>?) : ProductListUiState()
    data class Failure(val message: String, val error: ApiError) : ProductListUiState()
}

class ProductViewModel() : ViewModel() {

    val productFlow = MutableStateFlow<ProductListUiState>(ProductListUiState.Initial) // store page state -> ProductListUiState.Loading = initial state

    fun createProduct(product: Product) {

        var ProductList = Paper.book().read("products", mutableListOf<Product>())
        ProductList!!.add(product)
        Paper.book().write("products", ProductList)
    }

    fun getProducts(): List<Product> {
        try {
            productFlow.value = ProductListUiState.Success(Paper.book().read("products", mutableListOf<Product>()))
            return Paper.book().read("products", mutableListOf<Product>())!!
        } catch (e : Exception){
            productFlow.value = ProductListUiState.Failure("Erreur", ApiError.ERROR_500)
            println("erreur")
            return emptyList()
        }
    }

    fun LoadProduct() {
        productFlow.value = ProductListUiState.Initial;
        getProducts()
    }

}
