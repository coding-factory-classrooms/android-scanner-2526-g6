// BarcodeScannerScreen.kt
package com.example.barcodescanner

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BarcodeScannerScreen(
    onBarcodeScanned: (String) -> Unit,
    viewModel: BarcodeViewModel = viewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    LaunchedEffect(Unit) {
        viewModel.startCamera(context, previewView, lifecycleOwner)
    }

    // Observe barcode state
    val barcode by viewModel.barcode.collectAsState()

    // Send scanned barcode once
    LaunchedEffect(barcode) {
        barcode?.let {
            onBarcodeScanned(it)
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize()
    )
}
