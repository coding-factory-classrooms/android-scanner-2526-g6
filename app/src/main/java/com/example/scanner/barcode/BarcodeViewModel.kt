// BarcodeScannerViewModel.kt
package com.example.barcodescanner

import android.content.Context
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BarcodeViewModel : ViewModel() {

    private val _barcode = MutableStateFlow<String?>(null)
    val barcode: StateFlow<String?> = _barcode

    //setup camera

    fun processImage(
        scanner: com.google.mlkit.vision.barcode.BarcodeScanner,
        imageProxy: ImageProxy
    ) {
        val mediaImage = imageProxy.image ?: return imageProxy.close()
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                barcodes.firstOrNull()?.rawValue?.let {value ->
                    _barcode.value = value
                }
            }
            .addOnFailureListener { e ->
                Log.e("BARCODE", "Error: ${e.message}")
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}
