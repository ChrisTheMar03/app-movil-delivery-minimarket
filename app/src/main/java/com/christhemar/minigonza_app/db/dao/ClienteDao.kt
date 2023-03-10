package com.christhemar.minigonza_app.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.christhemar.minigonza_app.db.entity.ClienteEntity

@Dao
interface ClienteDao {

    //Se registrar solo una unica persona, solo una vez
    @Insert(onConflict=OnConflictStrategy.IGNORE)
    fun insertar (clienteEntity: ClienteEntity)

    @Update
    fun actualizar(vararg clienteEntity: ClienteEntity)

    @Query("DELETE FROM cliente")
    fun eliminarTodo()

    @Query("SELECT * FROM cliente LIMIT 1")
    fun obtener():LiveData<ClienteEntity>

}