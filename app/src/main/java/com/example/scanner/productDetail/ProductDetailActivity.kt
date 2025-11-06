package com.example.scanner.productDetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.scanner.ui.theme.ScannerTheme

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = intent.getIntExtra("id", 0)!!

        setContent {
            ProductDetailScreen(productId)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScannerTheme {
        ProductDetailScreen(0)
    }
}