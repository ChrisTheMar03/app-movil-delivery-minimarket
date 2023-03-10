package com.christhemar.minigonza_app.repository

import androidx.lifecycle.LiveData
import com.christhemar.minigonza_app.db.dao.ClienteDao
import com.christhemar.minigonza_app.db.entity.ClienteEntity

class ClienteEntityRepository(private val clienteDao: ClienteDao) {

    suspend fun insertar(clienteEntity: ClienteEntity){
        clienteDao.insertar(clienteEntity)
    }

    suspend fun actualizar(clienteEntity: ClienteEntity){
        clienteDao.actualizar(clienteEntity)
    }

    suspend fun eliminarTodo(){
        clienteDao.eliminarTodo()
    }

    fun obtener():LiveData<ClienteEntity>{
        return clienteDao.obtener()
    }

}