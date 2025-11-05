package com.example.scanner.products

import com.example.scanner.common.ApiError
import kotlinx.coroutines.flow.MutableStateFlow

sealed class ProductListUiState {
    /* children classes are all possible states depending on :
    - Waiting for response :
        Loading
    - received API response :
        Success (200)
        Error (400, 300, 500)
     */
    data object Loading : ProductListUiState() // mark as object when no attributes
    data class Success(val products: List<Product>) : ProductListUiState()
    data class Failure(val message: String, val error: ApiError) : ProductListUiState()
}

fun LoadProduct() {
    val productFlow = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
}