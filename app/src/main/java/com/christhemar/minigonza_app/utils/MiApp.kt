package com.christhemar.minigonza_app.utils

import android.app.Application

class MiApp : Application() {

    companion object{
        private var INSTANCE:MiApp?=null
        fun getInstance() = INSTANCE ?: MiApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE=this
    }


}