package com.example.scanner.products

import androidx.lifecycle.ViewModel
import com.example.scanner.common.ApiError
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow

sealed class ProductListUiState {
    /* children classes are all possible states depending on :
    - Waiting for response :
        Loading
    - received API response :
        Success (200)
        Error (400, 300, 500)
     */
    data object Initial : ProductListUiState() // mark as object when no attributes // LOADING state
    data class Success(val products: List<ProductResponse>) : ProductListUiState()
    data class Failure(val message: String, val error: ApiError) : ProductListUiState()
}

class ProductViewModel() : ViewModel() {

    val productFlow = MutableStateFlow<ProductListUiState>(ProductListUiState.Initial) // store page state -> ProductListUiState.Loading = initial state

    fun createProduct(product: Product) {
        Paper.book().write("product", product)
    }

    fun getProduct(): Product? {
        return Paper.book().read("product", null)
    }

    fun LoadProduct() {
        productFlow.value = ProductListUiState.Initial;
    }

}
