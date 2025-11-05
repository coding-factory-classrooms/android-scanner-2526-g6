package com.example.scanner

import com.example.scanner.models.ApiCall
import com.example.scanner.models.ApiResponse
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductScanTest {

    @Test
    fun ProductApiCallNutella() {
        val call = ApiCall("3017624010701") // Nutella awaited

        assertEquals("Nutella", (call as ApiResponse.Success).product.product_name); // cast as success, if not success, code will crash
    }

    @Test
    fun ProductApiCallJus() {
        val call = ApiCall("4056489641018") // jus d'orange awaited

        if(call is ApiResponse.Success) assertEquals("Pur jus d'orange sans pulpe", call.product.product_name);
    }

    @Test
    fun ProductIncorrectBarcode() {
        val call = ApiCall("4")

        assert(call is ApiResponse.Failed)
    }

    @Test
    fun ProductEmptyBarcode() {
        val call = ApiCall("")

        assert(call is ApiResponse.Failed)
    }

}