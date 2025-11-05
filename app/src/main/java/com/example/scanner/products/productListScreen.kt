package com.example.scanner.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.models.ApiCall
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scanner.ui.theme.ScannerTheme

@Composable
fun ProductListScreen(vm: ProductViewModel = viewModel()) {

    val state by vm.productFlow.collectAsState();

    LaunchedEffect(Unit) { // useEffect -> executed on load once // UNIT -> void
        vm.LoadProduct()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
      Button(onClick = { ApiCall("3017624010701") }) { Text("Button") }
      LazyColumn( // RecyclerView
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(sampleProducts) { product ->
                ProductCard(product)
            }
            
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card {
        Row(Modifier.height(100.dp).fillMaxWidth()) {
            Image(
                painterResource(product.thumbnailId),
                contentDescription = ""
            )
            Column {
                Text(product.id.toString())
                Text(product.name)
            }
            SeeMoreButton()
        }
    }
}

@Composable
fun SeeMoreButton() {
    OutlinedButton(onClick = {TODO()}) {
        Text(text="See more")
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    ScannerTheme {
        ProductListScreen()
    }
}
