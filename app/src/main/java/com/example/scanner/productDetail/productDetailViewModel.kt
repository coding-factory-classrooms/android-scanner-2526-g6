package com.example.scanner.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.products.Product
import com.example.scanner.products.ProductViewModel
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

    fun loadProductDetail(productId: Int, vm: ProductViewModel) {
        uiStateFlow.value = ProductDetailUiState.Initial

        val product = vm.getProductById(productId)

        uiStateFlow.value = ProductDetailUiState.Success(product!!);
    }
}