package com.christhemar.minigonza_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.christhemar.minigonza_app.repository.PedidoRepository
import com.christhemar.minigonza_app.retrofit.models.DetallePedido
import com.christhemar.minigonza_app.retrofit.models.Envio
import com.christhemar.minigonza_app.retrofit.models.Pedido

class PedidoViewModel :ViewModel() {

    var pedidoresponse:LiveData<Pedido>
    var detallePedidoresponse:LiveData<Boolean>
    var detallePedidoXcliente:LiveData<List<DetallePedido>>
    var envioresponse:LiveData<Envio>
    var updateEnvio:LiveData<Envio>
    var envioobtener:LiveData<Envio>
    var dtpedidoXpedido:LiveData<List<DetallePedido>>
    private val repo = PedidoRepository()

    init {
        pedidoresponse=repo.pedidoResponse
        detallePedidoresponse=repo.detallePedidoAgregado
        detallePedidoXcliente=repo.detallePedidoXClienteResponse
        envioresponse=repo.envioresponse
        updateEnvio=repo.updateEnvio
        envioobtener=repo.obtenerEnvioCliente
        dtpedidoXpedido = repo.detallePedidoXpedido
    }

    fun creaPedido(pedido: Pedido){
        pedidoresponse = repo.agregarPedido(pedido)
    }

    fun agregarDetallePedido(detallePedido: DetallePedido){
        detallePedidoresponse = repo.agregarDetallePedido(detallePedido)
    }

    fun detallePedidoXcliente(id:Int){
        detallePedidoXcliente = repo.detallePedidoXCliente(id)
    }

    fun agregarEnvio(envio: Envio){
        envioresponse = repo.agregarEnvio(envio)
    }

    fun actualizarPedido(envio: Envio){
        updateEnvio=repo.actualizarEnvio(envio)
    }

    fun obtenerEnvioCliente(id:Int){
        envioobtener=repo.obtenerPedidoXCliente(id)
    }

    fun listarDetallePedidoXpedido(id:Int){
        dtpedidoXpedido = repo.detallePedidoXpedido(id)
    }

}