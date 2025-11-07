package com.example.scanner.products

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.scanner.common.ApiCall
import com.example.scanner.common.ApiResponse
import com.example.scanner.common.ProductData
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Collections.emptyList

sealed class ProductListUiState {
    data object Initial : ProductListUiState() // mark as object when no attributes // LOADING state
    data class Success(val products: List<Product>?) : ProductListUiState()
    data class Failure(val message: String) : ProductListUiState()
}

class PapersData() : ProductData {

    override fun write(products: List<Product>) {
        Paper.book().write("products", products)
    }

    override fun add(product: Product) {
        val products = readAll()
        products!!.add(product)
        write(products)
    }

    override fun getById(id: Int) : Product{
        val products = readAll()
        return products!!.get(id)
    }

    override fun readAll() : MutableList<Product>? {
        return Paper.book().read("products", mutableListOf<Product>())
    }

    override fun delete(id: Int) {
        val products = readAll()
        products!!.removeAt(id)
        write(products);
    }

    override fun update(id: Int, newName: String) {
        val products = readAll();
        val product = products!!.get(id)
        product.product_name = newName
        write(products)
    }
}

class ProductViewModel(
    private val data: ProductData = PapersData()
) : ViewModel() {

    val productFlow = MutableStateFlow<ProductListUiState>(ProductListUiState.Initial) // store page state -> ProductListUiState.Loading = initial state

    //Create Product
    fun createProduct(barcode: String) : Boolean{
        try {
            val product = (ApiCall(barcode) as ApiResponse.Success).product
            data.add(product)
            LoadProduct()
            return true
        } catch(e: Exception) {
            println(e)
            return false;
        }
    }   

    //Get all product with Key products in bdd local
    fun getProducts(): MutableList<Product>? {
        try {
            productFlow.value = ProductListUiState.Success(data.readAll())
            return data.readAll()
        } catch (e : Exception){
            productFlow.value = ProductListUiState.Failure("Erreur")
            println("erreur")
            return emptyList()
        }
    }

    //Get specified product in local bdd with id
    fun getProductById(id : Int): Product? {
        try {
            return data.getById(id)
        } catch (e : Exception){
            productFlow.value = ProductListUiState.Failure("Erreur")
            println("erreur")
            return null
        }
    }

    //call getProduct and update state of product
    fun LoadProduct() {
        productFlow.value = ProductListUiState.Initial;
        getProducts()
    }

    //Delete product with associated id in bdd local with key products
    fun DeleteProduct(ProductIndex: Int) : Boolean {
        val productList = data.readAll()
        if (productList!!.isEmpty()){
            return false
        }
        data.delete(ProductIndex)
        LoadProduct()
        return true
    }

    fun updateProduct( productIndex: Int, title: String) : Boolean {
        var productList = data.readAll()
        if (productList!!.isEmpty()){
            return false
        }

        data.update(productIndex, title)
        LoadProduct()
        return true
    }

    fun searchProducts(query: String) : List<Product> {
        try {
            val allProduct = data.readAll()
            val filteredList = allProduct!!.filter { product ->
                product.product_name.contains(query.trim(), ignoreCase = true)
            }
            productFlow.value = ProductListUiState.Success(filteredList.toMutableList())
            return filteredList
        }catch (e: Exception){
            val message = "erreur recherche"
            productFlow.value = ProductListUiState.Failure(message)
            return emptyList<Product>()
        }

    }

    // FAVORITES
     fun isFavorite(ProductIndex: Int, context: Context): Boolean {
         var ProductList = Paper.book().read("products", mutableListOf<Product>())!!
         return ProductList[ProductIndex].favorite
     }

     fun toggleFavorite(ProductIndex: Int, context: Context ) {
         var ProductList = Paper.book().read("products", mutableListOf<Product>())!!
         if (ProductList.size == 0) {
             return Toast.makeText(context, "impossible", Toast.LENGTH_SHORT).show()
         }
         ProductList[ProductIndex].favorite = !ProductList[ProductIndex].favorite  // invert previous favorite value
         Paper.book().write("products", ProductList)
         LoadProduct()
     }

}
