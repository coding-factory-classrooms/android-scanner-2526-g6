package com.example.scanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.scanner.ui.theme.ScannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> // crash here :/
                    GreetingText(
                        message = "Android",
                        from = "hello there",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) { // modifier is recommended?
    Column (modifier = modifier) { // give modifier to children in @Composable functions
        Text(
            text = message
            // fontSize = 100.sp, // scale-independent pixels (adapted to user preferences)
            // dp -> density-independent pixels
            // lineHeight = 116.sp
        )
        Text(
            text = from
            // fontSize = 36.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScannerTheme {
        GreetingText("Welcome to KTT2!", "from the KTTeam")
    }
}