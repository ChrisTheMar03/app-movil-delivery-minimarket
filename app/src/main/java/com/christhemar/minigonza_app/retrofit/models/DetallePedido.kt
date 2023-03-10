package com.christhemar.minigonza_app.retrofit.models

data class DetallePedido(
    val idDetallePedido: Int? =0,
    val idPro:Productos?=null,
    val idPedido: Pedido?=null,
    val cantidad:Int=0
)
