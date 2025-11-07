package com.example.scanner.productDetail


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
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
                productDetailSuccessBody(s.product, productId, vm, Pvm)
            }

        }


}

@Composable
private fun productDetailSuccessBody(product: Product, productId: Int , vm: ProductDetailViewModel = viewModel(), pvm: ProductViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    var textInput by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf(product.product_name)}
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton( {showDialog = true}) {
                Icon(Icons.Filled.Edit,
                    contentDescription = "Edit",
                )
            }
        }
    ) { innerPadding ->
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
            Text(productName, fontSize = 32.sp)
            Text("marque " + product.brands)
            Spacer(Modifier.height(32.dp))
            Button(onClick = {
                val shareIntent = vm.createShareIntent(product)
                context.startActivity(shareIntent)
            })
            { Text("Share button") }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {}

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Enter text") },
                text = {
                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        label = { Text("Your text") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        if(!pvm.updateProduct(productId, textInput)) {
                            Toast.makeText(context, "Impossible", Toast.LENGTH_SHORT).show()
                        }

                        productName = textInput
                        textInput = ""
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        textInput = ""
                        showDialog = false
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun ProductDetailScreenPreview () {
    ScannerTheme {
//        productDetailSuccessBody(fakeProduct)
    }
}