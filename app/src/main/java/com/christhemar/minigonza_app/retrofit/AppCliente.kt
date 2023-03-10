package com.christhemar.minigonza_app.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppCliente {

    private const val URL= "http://192.168.0.5:9898/delivery/"

    //Otorgando tiempo de espera a la llamada de la peticion
    private var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1,TimeUnit.MINUTES)
        .readTimeout(30,TimeUnit.MINUTES)
        .writeTimeout(15,TimeUnit.MINUTES)
        .build()

    //Instanciado la clase retrofit
    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Indicando la interfaz que implementara el cliente
    val retrofitService:AppServices by lazy {
        buildRetrofit().create(AppServices::class.java)
    }

}