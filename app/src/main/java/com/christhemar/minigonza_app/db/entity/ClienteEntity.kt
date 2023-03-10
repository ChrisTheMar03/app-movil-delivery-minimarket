package com.christhemar.minigonza_app.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cliente")
data class ClienteEntity(
    @PrimaryKey()
    val idCli: Int? =0,
    val nombre:String="",
    val apellidos:String="",
    val correo:String="",
    val fono:String="",
    val clave:String="",
    val ban:String=""
){
    override fun toString(): String {
        return "ClienteEntity(idCli=$idCli, nombre='$nombre', apellidos='$apellidos', correo='$correo', fono='$fono', clave='$clave', ban='$ban')"
    }
}
