package com.christhemar.minigonza_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.christhemar.minigonza_app.repository.ProductoRepository
import com.christhemar.minigonza_app.retrofit.models.Categoria
import com.christhemar.minigonza_app.retrofit.models.Productos

class ProductoViewModel :ViewModel() {

    private val repo = ProductoRepository()

    var productoObtenido:LiveData<Productos>
    var productoCategoria:LiveData<List<Productos>>

    init {
        productoObtenido=repo.productoGet
        productoCategoria=repo.productosCategoriaRes
    }

    fun listadoProductosTop():LiveData<List<Productos>>{
        return repo.productosTop()
    }

    fun listarProductos():LiveData<List<Productos>>{
        return repo.listarProductos()
    }

    fun obtenerProducto(id:Int){
        productoObtenido=repo.obtenerProducto(id)
    }

    fun listarCategorias():LiveData<List<Categoria>>{
        return repo.listarCategorias()
    }

    fun productoxCategoria(id:Int){
        productoCategoria=repo.productosXcategoria(id)
    }

}