package com.christhemar.minigonza_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.christhemar.minigonza_app.repository.ClienteRepository
import com.christhemar.minigonza_app.retrofit.models.Clientes

class ClienteViewModel :ViewModel() {

    var responseLogin :LiveData<Clientes>
    var responseRegistro:LiveData<Int>
    private val respository = ClienteRepository()

    init {
        responseLogin=respository.clienteResponse
        responseRegistro=respository.registroRsponse
    }

    fun iniciarSesion(correo:String,clave:String){
        responseLogin=respository.ininciarSesion(correo,clave)
    }

    fun registrarUsuario(clientes: Clientes){
        responseRegistro=respository.registrarCliente(clientes)
    }

}