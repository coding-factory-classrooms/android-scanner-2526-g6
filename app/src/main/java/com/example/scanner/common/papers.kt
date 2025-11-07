package com.example.scanner.common

import com.example.scanner.products.Product
import io.paperdb.Paper

interface ProductData {
    fun write(products: MutableList<Product>)
    fun add(product: Product)
    fun getById(id: Int) : Product
    fun readAll(): MutableList<Product>?
    fun delete(id: Int)
    fun update(id: Int, newName: String)
}

