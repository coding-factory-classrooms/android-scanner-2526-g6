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
    suspend fun getProduct(@Path("barcode") barcode: String) : ProductResponse; // suspend = async fun
}

sealed class ApiResponse() {
    data class Success(val product: Product) : ApiResponse()
    data class Failed(val message: String): ApiResponse()
}

suspend fun fetchProduct(service: ProductService, barcode: String) : ApiResponse {
    return try {
        ApiResponse.Success(service.getProduct(barcode).product)
    } catch (e: Exception){
        ApiResponse.Failed("Problème avec l'API")
    }
}

fun ApiCall(barcode: String) : ApiResponse = runBlocking { // execution blocked until received response
    val BASE_URL = "https://world.openfoodfacts.net/";
    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(ProductService::class.java)
    var product: ApiResponse = fetchProduct(service, barcode)

    return@runBlocking product;
}