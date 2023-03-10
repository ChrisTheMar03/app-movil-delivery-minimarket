package com.christhemar.minigonza_app.retrofit

import com.christhemar.minigonza_app.retrofit.models.*
import retrofit2.Call
import retrofit2.http.*

interface AppServices {

    @GET("productos/top")
    fun topProductos():Call<List<Productos>>

    @POST("clientes")
    fun registrar(@Body clientes: Clientes ):Call<Int>

    @GET("productos/{id}")
    fun obtenerProducto(@Path("id") id:Int):Call<Productos>

    @GET("clientes/sesion/{correo}/{clave}")
    fun iniciarSesion(@Path("correo") correo:String,@Path("clave") clave:String):Call<Clientes>

    @GET("productos")
    fun listaProductos():Call<List<Productos>>

    @GET("categorias")
    fun listarCategorias():Call<List<Categoria>>

    //Buscar productos x categorias
    @GET("productos/categoria/{id}")
    fun productosXcategoria(@Path("id") id:Int ):Call<List<Productos>>

    @POST("pedido")
    fun agregarPedido(@Body pedido: Pedido):Call<Pedido>

    @GET("detallepedido/{id}")
    fun detallePedidoXcliente(@Path("id") id:Int):Call<List<DetallePedido>>

    @POST("detallepedido")
    fun agregarDetallePedido(@Body detallePedido: DetallePedido):Call<Boolean>

    @POST("envio")
    fun agregarEnvio(@Body envio: Envio):Call<Envio>

    @PUT("envio")
    fun actualizarEnvio(@Body envio: Envio):Call<Envio>

    @GET("envio/{id}")
    fun obtenerEnvioCliente(@Path("id") id:Int):Call<Envio>

    @GET("detallepedido/pedido/{id}")
    fun detallePedidoXpedido(@Path("id") id:Int):Call<List<DetallePedido>>

}