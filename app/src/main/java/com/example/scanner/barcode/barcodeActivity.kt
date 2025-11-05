package com.example.scanner.barcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.scanner.products.ProductListActivity
import com.example.scanner.R
import com.example.scanner.common.ApiCall
import com.example.scanner.common.ApiResponse
import com.example.scanner.products.ProductListScreen
import com.example.scanner.ui.theme.ScannerTheme
import com.google.android.datatransport.runtime.dagger.Component

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
fun Barcode() {
    var reading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    BarcodeScannerScreen(
        {
            if(reading) {
                reading = false
                val product : ApiResponse = ApiCall(it)

                println((product as ApiResponse.Success).product.product_name)

//                val intent: Intent = Intent(context, ProductListActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(intent)
                (context as Activity?)?.finish();
            } else {
                println("already reading")
            }
        }
    )
}