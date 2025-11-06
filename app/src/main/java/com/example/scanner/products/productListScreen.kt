package com.example.scanner.products

import com.example.scanner.ui.theme.ScannerTheme

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.example.scanner.R
import com.example.scanner.barcode.barcodeActivity
import com.example.scanner.common.ApiCall
import com.example.scanner.common.ApiResponse
import com.example.scanner.productDetail.ProductDetailActivity
import com.example.scanner.ui.theme.ScannerTheme
import com.google.android.material.progressindicator.CircularProgressIndicator

@Composable
fun ProductListScreen(vm: ProductViewModel = viewModel()) {
    val state by vm.productFlow.collectAsState();

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

//    vm.createProduct(Product("bouteille"))
//    val storedProduct = vm.getProducts()
//    println( "storedproduct $storedProduct")

    LaunchedEffect(Unit) { // useEffect -> executed on load once // UNIT -> void
        vm.LoadProduct()
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                vm.LoadProduct()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp // get screen height for Min Box Size

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> // ?
         Box(modifier = Modifier
             .fillMaxWidth()
             .padding(innerPadding)
         ) { // box will help contain floating action button
            when (state) {
                is ProductListUiState.Failure -> Text("failed")
                ProductListUiState.Initial -> CircularProgressIndicator()
                is ProductListUiState.Success -> {

                    LazyVerticalGrid( // RecyclerView
                        columns = GridCells.Adaptive(minSize = 180.dp),
                        contentPadding = PaddingValues(17.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.heightIn(min = screenHeight) // grid should always be screen height
                                                                         // else card elevation looks cut off
                    ) {
                        itemsIndexed((state as ProductListUiState.Success).products!!) { index, product ->
                            ProductCard(product, index, onButtonClick = {
                                val intent = Intent(context, ProductDetailActivity::class.java);
                                intent.putExtra("id", index)
                                context.startActivity(intent)

                            })
                        }
                    }
                }
            }

             Row(  // fit 2 overlayed floating action buttons
                 modifier = Modifier
                     .align(Alignment.BottomEnd)
                     .padding(16.dp),
                 horizontalArrangement = Arrangement.spacedBy(12.dp)
             ) {
                 Button( // Nutella Button uhihi
                     onClick = {
                         val response = ApiCall("3017624010701")
                         if (response is ApiResponse.Success) {
                             vm.createProduct(response.product)
                             println("database ${vm.getProducts()}")
                         } else {
                             println("failed")
                         }
                     },
                     modifier = Modifier
                         .padding(16.dp),
                     shape = RoundedCornerShape(8.dp),
                     colors = ButtonDefaults.buttonColors(
                         containerColor = MaterialTheme.colorScheme.primary
                     )
                 ) {
                     Text("Nutella", color = MaterialTheme.colorScheme.onPrimary)
                 }

                 Button(onClick = {
                    val intent: Intent = Intent(context, barcodeActivity::class.java)
                    context.startActivity(intent)
                 }) { Text("Camera") }
             }

        }
    }
}

@Composable
fun ProductCard(product: Product, index: Int, onButtonClick: () -> Unit, vm: ProductViewModel = viewModel(),) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            onClick = onButtonClick,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()  ,
            elevation = CardDefaults.cardElevation(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )) {
//            Column(Modifier        // adding a column breaks everything so let's just not :/
//                .height(100.dp)
//                .fillMaxWidth())
//            {
                Box (modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF))
                ) { // trying to have a clean white bg for imgs since they're not the standardized
                    AsyncImage(
                        model = product.image_url,
                        contentDescription = product.product_name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .padding(16.dp)
                    )
                }

                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text =product.product_name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                    )
                    Spacer(
                        modifier = Modifier.padding(2.dp)
                    )
                    Text(
                        text = "Lorem ipsum dolo c’est trop bon en plus faut rester hydraté",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp,
                        lineHeight = 14.sp
                        )
                    // SeeMoreButton(onButtonClick); // replaced by the clickable card
                    Button(onClick = {
                        vm.DeleteProduct( index, context)
                    }) {
                        Text("delete", color = MaterialTheme.colorScheme.onSurface)
                    }
                }

//            }

        }
    }
}

@Composable
fun SeeMoreButton(onButtonClick: () -> Unit) {
    OutlinedButton(onClick = onButtonClick) {
        Text(text="See more")
    }
}

@Preview
@Composable
fun ProductListScreenPreview() {
    ScannerTheme {
        ProductListScreen()
    }
}
