package com.example.shoppinglistapp.repository

import android.content.Context
import com.example.shoppinglistapp.database.ShoppingListRoomDatabase
import com.example.shoppinglistapp.model.Product
import com.example.shoppinglistapp.model.ProductDao

class ProductRepository(context: Context) {

    private val productDao: ProductDao

    init {
        val database =
            ShoppingListRoomDatabase.getDatabase(context)
        productDao = database!!.productDao()
    }

    suspend fun getAllProducts(): List<Product> = productDao.getAllProducts()

    suspend fun insertProduct(product: Product) = productDao.insertProduct(product)

    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)

    suspend fun deleteAllProducts() = productDao.deleteAllProducts()

}