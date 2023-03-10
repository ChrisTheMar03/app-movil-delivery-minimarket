package com.christhemar.minigonza_app.utils

data class HeaderEntity(
    val id:Int,
    val recurso:Int,
    val nombre:String
){
    override fun toString(): String {
        return "HeaderEntity(id=$id, recurso=$recurso, nombre='$nombre')"
    }
}
