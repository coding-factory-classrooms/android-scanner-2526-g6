package com.example.scanner.models

import com.example.scanner.products.Product
import com.example.scanner.products.ProductResponse
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("api/v2/product/{barcode}")
    suspend fun getProduct(@Path("barcode") barcode: String) : ProductResponse;
}

suspend fun fetchProduct(service: ProductService, barcode: String) : Product? {
    return try {
        val response = service.getProduct(barcode)
        response.product
    } catch (e: Exception){
        null
    }
}

fun ApiCall(barcode: String) : Product? = runBlocking {
    val BASE_URL = "https://world.openfoodfacts.net/";
    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(ProductService::class.java)
    var product: Product? = fetchProduct(service, barcode)

    println(product)

    return@runBlocking product;
}