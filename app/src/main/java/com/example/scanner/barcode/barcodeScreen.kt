// BarcodeScannerScreen.kt
package com.example.barcodescanner

import android.app.Activity
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.barcode.startCamera
import com.example.scanner.products.ProductViewModel

@Composable
fun Barcode(Productvm: ProductViewModel = viewModel(), bvm: BarcodeViewModel = viewModel()) {
    var reading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val barcode by bvm.barcode.collectAsState()


    LaunchedEffect(Unit) {
        startCamera(context, lifecycleOwner, previewView, { scanner, imageProxy ->
            bvm.processImage(scanner, imageProxy)
        })
    }

    LaunchedEffect(barcode) {
        barcode?.let {
            if(reading) {
                reading = false

                Productvm.createProduct(it)
                println(Productvm.getProducts())

                (context as Activity?)?.finish();
            }
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize()
    )
}
