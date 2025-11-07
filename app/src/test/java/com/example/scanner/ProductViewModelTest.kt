package com.example.scanner

import androidx.compose.foundation.rememberOverscrollEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.common.ProductData
import com.example.scanner.products.Product
import com.example.scanner.products.ProductViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Collections.emptyList


class MockData : ProductData {
    var List = mutableListOf<Product>()

    override fun write(products: List<Product>) {
        TODO("Not yet implemented")
    }

    override fun add(product: Product) {
        List.add(product)
    }

    override fun getById(id: Int): Product {
        return List.get(id)
    }

    override fun readAll(): MutableList<Product>? {
        return List
    }

    override fun delete(id: Int) {
        List.removeAt(id)
    }

    override fun update(id: Int, newName: String) {
        List.get(id).product_name = newName
    }
}

class ProductViewModelTest {

    private lateinit var mockData: MockData
    private lateinit var viewModel: ProductViewModel

    @Before
    fun setup() {
        mockData = MockData()
        viewModel = ProductViewModel(mockData)
    }

    @Test
    fun TestProductCreate() {
        assertTrue(
                viewModel.createProduct("3017624010701")
        )
    }

    @Test
    fun TestProductCreateEmpty() {
        assertFalse(
            viewModel.createProduct("")
        )
    }

    @Test
    fun TestGetProductEmpty() {
        assertEquals(emptyList<Product>() ,viewModel.getProducts())
    }

    @Test
    fun TestGetProduct() {
        viewModel.createProduct("3017624010701")
        assert(!viewModel.getProducts()!!.isEmpty())
    }

    @Test
    fun TestGetProductById() {
        viewModel.createProduct("3017624010701")
        assertEquals("Nutella", viewModel.getProductById(0)!!.product_name)
    }

    @Test
    fun TestDeleteProduct(){
        viewModel.createProduct("3017624010701")
        viewModel.DeleteProduct(0)
        assertEquals(emptyList<Product>() ,viewModel.getProducts())
    }

    @Test
    fun TestUpdateProduct(){
        viewModel.createProduct("3017624010701")
        viewModel.updateProduct(0, "test")
        assertEquals("test", viewModel.getProductById(0)!!.product_name)
    }

    @Test
    fun TestSearchProduct(){
        viewModel.createProduct("3017624010701")
        assert(!viewModel.searchProducts("Nut").isEmpty())
    }

    @Test
    fun TestSearchProductNull(){
        viewModel.createProduct("3017624010701")
        assert(viewModel.searchProducts("BO").isEmpty())
    }
}