package com.christhemar.minigonza_app.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Formats {

    companion object{

        val API_KEY = "AIzaSyBSYQETeFPFiCGoaDiUGCNaHq_xGIG_r6s"

        fun formatearDecimal(numero:Double): String {
            val decimal=DecimalFormat("#.00")
            return decimal.format(numero)
        }



    }

}