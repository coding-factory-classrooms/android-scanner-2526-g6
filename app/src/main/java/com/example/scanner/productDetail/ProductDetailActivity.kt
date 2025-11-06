package com.example.scanner.productDetail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.scanner.R
import com.example.scanner.products.ProductListScreen
import com.example.scanner.ui.theme.ScannerTheme

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = intent.getStringExtra("id")!!

        setContent {
            ProductDetailScreen(productId)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScannerTheme {
        ProductDetailScreen("123")
    }
}