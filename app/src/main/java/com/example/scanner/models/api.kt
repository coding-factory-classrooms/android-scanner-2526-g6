package com.example.scanner.models

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProductService {
    @GET("api/v2/product/3017624010701")
    fun getProduct() : Call<Product>;
}

fun ApiCall(): Call<Product> {
    val BASE_URL = "https://world.openfoodfacts.net/";
    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(ProductService::class.java)
    val call = service.getProduct()

    call.enqueue(object : Callback<Product> {
        override fun onResponse(call: Call<Product?>, response: Response<Product?>) {
            if(response.isSuccessful) {
                val res = response.body()?.product
                println(res)
            }
        }

        override fun onFailure(
            call: Call<Product?>,
            t: Throwable
        ) {
            println("failed");
        }
    })

    return call
}