package com.christhemar.minigonza_app.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.christhemar.minigonza_app.retrofit.AppCliente
import com.christhemar.minigonza_app.retrofit.models.Clientes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClienteRepository {

    val clienteResponse = MutableLiveData<Clientes>()
    val registroRsponse = MutableLiveData<Int>()

    fun ininciarSesion(correo:String,clave:String):MutableLiveData<Clientes>{
        val call:Call<Clientes> = AppCliente.retrofitService.iniciarSesion(correo,clave)
        call.enqueue(object:Callback<Clientes>{
            override fun onResponse(call: Call<Clientes>, response: Response<Clientes>) {
                clienteResponse.value = response.body()?: Clientes()
            }

            override fun onFailure(call: Call<Clientes>, t: Throwable) {
                Log.e("login",t.message.toString())
            }

        })
        return clienteResponse
    }

    fun registrarCliente(clientes: Clientes):MutableLiveData<Int>{
        val call:Call<Int> = AppCliente.retrofitService.registrar(clientes)
        call.enqueue(object:Callback<Int>{
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                registroRsponse.value = response.body()
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("registro",t.message.toString())
            }

        })
        return registroRsponse
    }

}