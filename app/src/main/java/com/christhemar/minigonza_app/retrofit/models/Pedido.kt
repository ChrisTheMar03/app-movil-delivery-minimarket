package com.christhemar.minigonza_app.retrofit.models

import java.util.*

data class Pedido(
    val idPedido: Int? =0,
    val idCli:Clientes?=null,
    val fechaPedido:String?=null
){
    override fun toString(): String {
        return "Pedido(idPedido=$idPedido, idCli=$idCli, fechaPedido=$fechaPedido)"
    }
}
