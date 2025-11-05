package com.example.scanner.products

import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.moviescompose.movies.ProductListActivity
import com.example.scanner.models.ApiCall
import com.example.scanner.ui.theme.ScannerTheme

@Composable
fun ProductListScreen(vm: ProductViewModel = viewModel()) {

    val state by vm.productFlow.collectAsState();

    LaunchedEffect(Unit) { // useEffect -> executed on load once // UNIT -> void
        vm.LoadProduct()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            item {
                Button(onClick = { ApiCall("3017624010701") }) { Text("Button") }
            }
            /*
            items(samplesMovies) { movie ->
                MovieCard(movie)
            }*/
        }
    }
}

@Preview
@Composable
fun ProductListActivity() {
    ScannerTheme {
        ProductListScreen()
    }
}
