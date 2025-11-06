package com.example.scanner.barcode

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.barcodescanner.BarcodeScannerScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.common.ApiCall
import com.example.scanner.common.ApiResponse
import com.example.scanner.products.ProductViewModel
import com.example.scanner.ui.theme.ScannerTheme

class barcodeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ScannerTheme {
                Barcode()
            }
        }
    }
}

@Composable
fun Barcode(Productvm: ProductViewModel = viewModel()) {
    var reading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    BarcodeScannerScreen(
        {
            if(reading) {
                reading = false
                val product : ApiResponse = ApiCall(it)

                println((product as ApiResponse.Success).product.product_name)

                Productvm.createProduct(product.product)
                println(Productvm.getProducts())

                (context as Activity?)?.finish();
            }
        }
    )
}