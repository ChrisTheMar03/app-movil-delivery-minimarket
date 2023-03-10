package com.christhemar.minigonza_app.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.christhemar.minigonza_app.retrofit.AppCliente
import com.christhemar.minigonza_app.retrofit.models.Categoria
import com.christhemar.minigonza_app.retrofit.models.Productos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoRepository {

    val productosTop = MutableLiveData<List<Productos>>()
    val productosResponse = MutableLiveData<List<Productos>>()
    val productoGet = MutableLiveData<Productos>()
    val categoriasResponse = MutableLiveData<List<Categoria>>()
    val productosCategoriaRes = MutableLiveData<List<Productos>>()

    fun productosTop():MutableLiveData<List<Productos>>{
        val call:Call<List<Productos>> = AppCliente.retrofitService.topProductos()
        call.enqueue(object:Callback<List<Productos>>{
            override fun onResponse(
                call: Call<List<Productos>>,
                response: Response<List<Productos>>
            ) {
                productosTop.value = response.body()
            }

            override fun onFailure(call: Call<List<Productos>>, t: Throwable) {
                Log.e("top",t.message.toString())
            }

        })
        return productosTop
    }

    fun listarProductos():MutableLiveData<List<Productos>>{
        val call:Call<List<Productos>> = AppCliente.retrofitService.listaProductos()
        call.enqueue(object:Callback<List<Productos>>{
            override fun onResponse(
                call: Call<List<Productos>>,
                response: Response<List<Productos>>
            ) {
                productosResponse.value = response.body()
            }

            override fun onFailure(call: Call<List<Productos>>, t: Throwable) {
                Log.e("listado",t.message.toString())
            }

        })
        return productosResponse
    }

    fun obtenerProducto(id:Int):MutableLiveData<Productos>{
        val call:Call<Productos> = AppCliente.retrofitService.obtenerProducto(id)
        call.enqueue(object:Callback<Productos>{
            override fun onResponse(call: Call<Productos>, response: Response<Productos>) {
                productoGet.value = response.body()?:Productos()
            }

            override fun onFailure(call: Call<Productos>, t: Throwable) {
                Log.e("buscar",t.message.toString())
            }

        })
        return productoGet
    }

    fun listarCategorias():MutableLiveData<List<Categoria>>{
        val call:Call<List<Categoria>> = AppCliente.retrofitService.listarCategorias()
        call.enqueue(object:Callback<List<Categoria>>{
            override fun onResponse(
                call: Call<List<Categoria>>,
                response: Response<List<Categoria>>
            ) {
                categoriasResponse.value=response.body()
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                Log.e("categorias",t.message.toString())
            }

        })
        return categoriasResponse
    }

    fun productosXcategoria(id:Int):MutableLiveData<List<Productos>>{
        val call:Call<List<Productos>> = AppCliente.retrofitService.productosXcategoria(id)
        call.enqueue(object:Callback<List<Productos>>{
            override fun onResponse(
                call: Call<List<Productos>>,
                response: Response<List<Productos>>
            ) {
                productosCategoriaRes.value = response.body()
            }

            override fun onFailure(call: Call<List<Productos>>, t: Throwable) {
                Log.e("prodXcat",t.message.toString())
            }

        })
        return productosCategoriaRes
    }

}