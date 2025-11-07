package com.example.scanner.barcode

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barcodescanner.Barcode
import com.example.barcodescanner.BarcodeViewModel
import com.example.scanner.common.ApiCall
import com.example.scanner.common.ApiResponse
import com.example.scanner.products.ProductViewModel
import com.example.scanner.ui.theme.ScannerTheme
import com.google.mlkit.vision.barcode.BarcodeScanning
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class barcodeActivity : ComponentActivity() {

    lateinit var previewView: PreviewView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        previewView = PreviewView(this)

        enableEdgeToEdge()
        setContent {
            ScannerTheme {
                Barcode()
            }
        }
    }
}

fun startCamera(context: Context,
                lifecycleOwner: LifecycleOwner, previewView: PreviewView, scanner: (scanner: com.google.mlkit.vision.barcode.BarcodeScanner,
                                                                                    imageProxy: ImageProxy) -> Unit) {
    val context = context
    val lifecycleOwner = lifecycleOwner


    // Use a coroutine on the Main dispatcher (no viewModelScope here)
    CoroutineScope(Dispatchers.Main).launch {

        try {
            val cameraProvider = ProcessCameraProvider.getInstance(context).get()

            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

            val barcodeScanner = BarcodeScanning.getClient()

            val analysis = ImageAnalysis.Builder().build().apply {
                setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                    scanner(barcodeScanner, imageProxy)
                }
            }

            // Bind use cases to lifecycle
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                analysis
            )
        } catch (e: Exception) {
            Log.e("BARCODE", "Camera bind failed: ${e.message}")
        }
    }
}
