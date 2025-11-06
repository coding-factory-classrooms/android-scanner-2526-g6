package com.example.scanner.productDetail

import androidx.lifecycle.ViewModel
import com.example.scanner.products.Product
import com.example.scanner.products.fakeProduct
import kotlinx.coroutines.flow.MutableStateFlow


//etats de la page de detail
sealed class ProductDetailUiState {
    data object Initial : ProductDetailUiState()
    data class Success(val product: Product) : ProductDetailUiState()
    data class Failure(val message: String) : ProductDetailUiState()
}
class ProductDetailViewModel : ViewModel() {

    val uiStateFlow = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Initial)

    fun loadProductDetail(productId: String) {
        uiStateFlow.value = ProductDetailUiState.Initial


        uiStateFlow.value = ProductDetailUiState.Success(fakeProduct)

    }
}