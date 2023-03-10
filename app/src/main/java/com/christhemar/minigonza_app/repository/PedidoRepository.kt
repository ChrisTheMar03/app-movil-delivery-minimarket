package com.christhemar.minigonza_app.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.christhemar.minigonza_app.retrofit.AppCliente
import com.christhemar.minigonza_app.retrofit.models.DetallePedido
import com.christhemar.minigonza_app.retrofit.models.Envio
import com.christhemar.minigonza_app.retrofit.models.Pedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidoRepository {

    var pedidoResponse=MutableLiveData<Pedido>()
    var detallePedidoXClienteResponse=MutableLiveData<List<DetallePedido>>()
    var detallePedidoAgregado=MutableLiveData<Boolean>()
    var envioresponse = MutableLiveData<Envio>()
    var updateEnvio=MutableLiveData<Envio>()
    var obtenerEnvioCliente = MutableLiveData<Envio>()
    var detallePedidoXpedido = MutableLiveData<List<DetallePedido>>()

    fun agregarPedido(pedido: Pedido):MutableLiveData<Pedido>{
        val call:Call<Pedido> = AppCliente.retrofitService.agregarPedido(pedido)
        call.enqueue(object:Callback<Pedido>{

            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                pedidoResponse.value = response.body()
            }

            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                Log.e("pedido",t.message.toString())
            }

        })
        return pedidoResponse
    }

    fun detallePedidoXCliente(id:Int):MutableLiveData<List<DetallePedido>>{
        val call:Call<List<DetallePedido>> = AppCliente.retrofitService.detallePedidoXcliente(id)
        call.enqueue(object:Callback<List<DetallePedido>>{
            override fun onResponse(call: Call<List<DetallePedido>>, response: Response<List<DetallePedido>>) {
                detallePedidoXClienteResponse.value = response.body()
            }

            override fun onFailure(call: Call<List<DetallePedido>>, t: Throwable) {
                Log.e("dtpedido",t.message.toString())
            }

        })
        return detallePedidoXClienteResponse
    }

    fun agregarDetallePedido(detallePedido: DetallePedido):MutableLiveData<Boolean>{
        val call:Call<Boolean> = AppCliente.retrofitService.agregarDetallePedido(detallePedido)
        call.enqueue(object:Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                detallePedidoAgregado.value = response.body()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("dtpedidoadd",t.message.toString())
            }

        })
        return detallePedidoAgregado
    }

    fun agregarEnvio(envio: Envio):MutableLiveData<Envio>{
        val call:Call<Envio> = AppCliente.retrofitService.agregarEnvio(envio)
        call.enqueue(object:Callback<Envio>{
            override fun onResponse(call: Call<Envio>, response: Response<Envio>) {
                envioresponse.value = response.body()
            }

            override fun onFailure(call: Call<Envio>, t: Throwable) {
                Log.d("envio",t.message.toString())
            }

        })
        return envioresponse
    }

    fun actualizarEnvio(envio: Envio):MutableLiveData<Envio>{
        val call:Call<Envio> = AppCliente.retrofitService.actualizarEnvio(envio)
        call.enqueue(object:Callback<Envio>{
            override fun onResponse(call: Call<Envio>, response: Response<Envio>) {
                updateEnvio.value = response.body()
            }

            override fun onFailure(call: Call<Envio>, t: Throwable) {
                Log.e("updateenvio",t.message.toString())
            }

        })
        return updateEnvio
    }

    fun obtenerPedidoXCliente(id:Int):MutableLiveData<Envio>{
        val call:Call<Envio> = AppCliente.retrofitService.obtenerEnvioCliente(id)
        call.enqueue(object:Callback<Envio>{
            override fun onResponse(call: Call<Envio>, response: Response<Envio>) {
                obtenerEnvioCliente.value = response.body()
            }

            override fun onFailure(call: Call<Envio>, t: Throwable) {
                Log.e("envioobten",t.message.toString())
            }

        })
        return obtenerEnvioCliente
    }

    fun detallePedidoXpedido(id:Int):MutableLiveData<List<DetallePedido>>{
        val call:Call<List<DetallePedido>> = AppCliente.retrofitService.detallePedidoXpedido(id)
        call.enqueue(object:Callback<List<DetallePedido>>{
            override fun onResponse(
                call: Call<List<DetallePedido>>,
                response: Response<List<DetallePedido>>
            ) {
                detallePedidoXpedido.value = response.body()
            }

            override fun onFailure(call: Call<List<DetallePedido>>, t: Throwable) {
                Log.e("dtpedidoxpedido",t.message.toString())
            }

        })
        return detallePedidoXpedido
    }

}