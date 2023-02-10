package br.com.alura.aluvery.dao

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import br.com.alura.aluvery.model.Product
import br.com.alura.aluvery.sampledata.sampleProducts

class ProductDao {

    companion object{
//        private val products = sampleProducts.toMutableList() // mudamos de mutablelist para mutableStateList
    // mutableStateList tem a capacidade de notificar a mudança dos dados
//        private val products = mutableStateListOf<Product>(*sampleProducts.toTypedArray()) //exemplo passando sample
        private val products = mutableStateListOf<Product>()//lista vazia
    }

    fun products() = products.toList() //imutable data

    fun save(product: Product) {
        products.add(product)
    }


}