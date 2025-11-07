package com.example.scanner

import android.content.Context
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.scanner.products.Product
import com.example.scanner.products.ProductViewModel
import io.paperdb.Paper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var viewModel: ProductViewModel
    private lateinit var context: Context

    @Before
    fun setup() {
        // Get a test context and initialize PaperDB
        context = ApplicationProvider.getApplicationContext()
        Paper.init(context)
        viewModel = ProductViewModel()
    }

    @Test
    fun testCreateAndGetProducts() {
        println("=== testCreateAndGetProducts ===")

        val product = Product("1", "Cristalline", "", "")
        viewModel.createProduct(product)

        val products = viewModel.getProducts()

        assertTrue(products.isNotEmpty())
        println("Product list contains: ${products.map { it.product_name }}")
    }

    @Test
    fun testGetProductById() {
        println("=== testGetProductById ===")

        val p1 = Product("1", "Cristalline", "", "")
        val p2 = Product("2", "Coca-Cola", "", "")

        viewModel.createProduct(p1)
        viewModel.createProduct(p2)

        val result = viewModel.getProductById(1)
        assertNotNull(result)
        println("Found product: ${result?.product_name}")
    }

    @Test
    fun testUpdateProduct() {
        val p1 = Product("1", "Cristalline", "", "")

        viewModel.createProduct(p1)

        viewModel.updateProduct(0, "test", context)

        assertEquals("test", viewModel.getProductById(0)!!.product_name)
    }

    @Test
    fun testDeleteProduct() {
        println("=== testDeleteProduct ===")

        viewModel.createProduct(Product("1", "Cristalline","", ""))
        viewModel.createProduct(Product("2", "Coca-Cola","", ""))

        println("Before delete: ${viewModel.getProducts().map { it.product_name }}")
        viewModel.DeleteProduct(0, context!!)
        println("After delete: ${viewModel.getProducts().map { it.product_name }}")
    }
}