package com.christhemar.minigonza_app.retrofit.models

import java.util.*

data class Envio(
    val idEnvio: Int? =0,
    val idPedido: Pedido?=null,
    val fEntrega:String?=null,
    val direccion:String="",
    val referencia:String="",
    val distrito:String="",
    val latitud:String="",
    val longitud:String="",
    val idEmp:Empleados?=null
)
