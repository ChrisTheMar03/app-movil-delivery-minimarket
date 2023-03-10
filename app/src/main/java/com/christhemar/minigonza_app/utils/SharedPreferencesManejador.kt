package com.christhemar.minigonza_app.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManejador{

    private val UTILS="UTILS"

    private lateinit var sharedPreferences:SharedPreferences

    fun init(context: Context){
        sharedPreferences= context.getSharedPreferences(UTILS, Context.MODE_PRIVATE)
    }

    //guardarInformacion
    fun setSomeIntValue(nombre:String,numero:Int){
        val editor = sharedPreferences.edit()
        editor.putInt(nombre,numero)
        editor.apply()
    }

    fun getSomeIntValue(nombre:String):Int{
        return sharedPreferences.getInt(nombre,0)
    }

    fun setSomeLongValue(nombre:String,numero:Long){
        val editor = sharedPreferences.edit()
        editor.putLong(nombre,numero)
        editor.apply()
    }

    fun getSomeLongValue(nombre:String):Long{
        return sharedPreferences.getLong(nombre,0L)
    }

    fun setSomeStringValue(nombre:String,valor:String){
        val edi = sharedPreferences.edit()
        edi.putString(nombre,valor)
        edi.apply()
    }

    fun getSomeStringValue(nombre:String): String {
        return sharedPreferences.getString(nombre,"")!!
    }

}