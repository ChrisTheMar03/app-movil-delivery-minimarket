package com.christhemar.minigonza_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.christhemar.minigonza_app.db.AppRoomDatabase
import com.christhemar.minigonza_app.db.entity.ClienteEntity
import com.christhemar.minigonza_app.repository.ClienteEntityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClienteEntityViewModel(application: Application) : AndroidViewModel(application) {

    private val repo:ClienteEntityRepository

    init {
        val app = AppRoomDatabase.getDatabase(application).clienteDao()
        repo= ClienteEntityRepository(app)
    }

    fun insertar(clienteEntity: ClienteEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertar(clienteEntity)
        }

    fun actualizar(clienteEntity: ClienteEntity) = viewModelScope.launch(Dispatchers.IO) {
        repo.actualizar(clienteEntity)
    }

    fun eliminarTodo() = viewModelScope.launch(Dispatchers.IO) {
        repo.eliminarTodo()
    }

    fun obtener():LiveData<ClienteEntity>{
        return repo.obtener()
    }

}