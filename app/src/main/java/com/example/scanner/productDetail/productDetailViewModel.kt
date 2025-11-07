package com.example.scanner.productDetail

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.scanner.products.Product
import com.example.scanner.products.ProductViewModel
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

    //
    fun createShareIntent(product: Product): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Partager un produit")
        intent.putExtra(Intent.EXTRA_TEXT, "Découvre ce produit : ${product.product_name}")
        return Intent.createChooser(intent, "Partager via")
    }
}