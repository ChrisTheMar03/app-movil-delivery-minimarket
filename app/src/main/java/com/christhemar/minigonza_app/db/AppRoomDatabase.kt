package com.christhemar.minigonza_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.christhemar.minigonza_app.db.dao.ClienteDao
import com.christhemar.minigonza_app.db.entity.ClienteEntity

@Database(entities = [ClienteEntity::class], version = 1)
abstract class AppRoomDatabase  : RoomDatabase(){

    abstract fun clienteDao():ClienteDao

    companion object{
        //Ya que va a estar involucrada en subprocesos
        //Devolvera los cambios que tenga inmediatamente a los subprocesos
        //Patron singleton
        @Volatile
        private var INSTACE:AppRoomDatabase?=null

        fun getDatabase(context:Context):AppRoomDatabase{
            val tempInstace = INSTACE
            if(tempInstace!=null){
                return tempInstace
            }
            //Crear un hilo para que la instancia se cree
            //Aparte de crear el hilo, si hubiece otra operacion lo colocara en cola hasta que espere
            //el hilo que se encuentra en proceso
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,AppRoomDatabase::class.java,"minimarketBD")
                    .build()
                INSTACE=instance
                return instance
            }
        }

    }

}