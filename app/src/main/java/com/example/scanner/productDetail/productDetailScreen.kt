package com.example.scanner.productDetail


import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.scanner.R
import com.example.scanner.products.Product
import com.example.scanner.products.ProductViewModel
import com.example.scanner.products.fakeProduct
import com.example.scanner.ui.theme.ScannerTheme


@Composable
fun ProductDetailScreen(productId: Int, vm: ProductDetailViewModel = viewModel(), Pvm: ProductViewModel = viewModel()) {

    val context = LocalContext.current

    val State by vm.uiStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadProductDetail(productId, Pvm)
    }
        when(val s = State){
            is ProductDetailUiState.Initial -> {}
            is ProductDetailUiState.Failure ->{
                Toast.makeText(context, s.message, Toast.LENGTH_SHORT).show()
            }
            is ProductDetailUiState.Success -> {
                productDetailSuccessBody(s.product)
            }

        }


}

@Composable
private fun productDetailSuccessBody(product: Product) {
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = product.image_url,
                contentDescription = product.product_name,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(180.dp)
            )
            Text(product.product_name, fontSize = 32.sp)
            Text(product.brands)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        )
        {
        }
    }
}

@Preview
@Composable
fun ProductDetailScreenPreview () {
    ScannerTheme {
        productDetailSuccessBody(fakeProduct)
    }
}