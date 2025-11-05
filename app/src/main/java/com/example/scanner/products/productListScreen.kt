package com.example.scanner.products

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.GreetingText

@Preview
@Composable
fun ProductListScreen(vm: ProductViewModel = viewModel()) {

    val state by vm.productFlow.collectAsState();

    LaunchedEffect(Unit) {
        vm.LoadProduct()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> // crash here :/
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(samplesMovies) { movie ->
                MovieCard(movie)
            }
        }
    }
}
