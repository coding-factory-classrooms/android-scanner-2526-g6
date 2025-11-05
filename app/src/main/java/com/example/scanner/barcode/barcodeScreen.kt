package com.example.scanner.barcode

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@Composable
fun BarcodeScannerScreen(
    onBarcodeScanned: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val provider = ProcessCameraProvider.getInstance(context).get()
        cameraProvider = provider
    }

    AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

    LaunchedEffect(cameraProvider) {
        cameraProvider?.let { provider ->
            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

            val barcodeScanner = BarcodeScanning.getClient()
            val analysis = ImageAnalysis.Builder().build().apply {
                setAnalyzer(
                    ContextCompat.getMainExecutor(context)
                ) { imageProxy ->
                    processImageProxy(barcodeScanner, imageProxy, onBarcodeScanned)
                }
            }

            try {
                provider.unbindAll()
                provider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    analysis
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

private fun processImageProxy(
    scanner: com.google.mlkit.vision.barcode.BarcodeScanner,
    imageProxy: ImageProxy,
    onBarcodeScanned: (String) -> Unit
) {
    val mediaImage = imageProxy.image ?: return imageProxy.close()
    val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

    scanner.process(image)
        .addOnSuccessListener { barcodes ->
            for (barcode in barcodes) {
                barcode.rawValue?.let {
                    onBarcodeScanned(it)
                }
            }
        }
        .addOnFailureListener { e ->
            Log.e("BARCODE", "Error: ${e.message}")
        }
        .addOnCompleteListener {
            imageProxy.close()
        }
}
