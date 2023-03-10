package com.christhemar.minigonza_app.retrofit.models

data class Productos(
    val idPro:Int=0,
    val nombre:String="",
    val stock:Int=0,
    val imagen:String="",
    val precio:Double=0.0,
    val estado:String="",
    val idCat:Categoria?=null,
    val idMarca:Marcas?=null
)
