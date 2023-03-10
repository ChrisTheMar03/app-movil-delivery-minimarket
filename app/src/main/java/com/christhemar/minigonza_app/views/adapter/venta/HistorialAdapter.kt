package com.christhemar.minigonza_app.views.adapter.venta

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.retrofit.models.DetallePedido

class HistorialAdapter(private val lista:List<DetallePedido>) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    inner class HistorialViewHolder(view:View):RecyclerView.ViewHolder(view){

        private val direccion:TextView
        private val fecha:TextView
        private val estado:TextView

        init {
            direccion=view.findViewById(R.id.h_direccion)
            fecha=view.findViewById(R.id.h_fecha)
            estado=view.findViewById(R.id.h_estado)
        }

        fun binding(detallePedido: DetallePedido){
            direccion.text = detallePedido.idPedido!!.idCli!!.correo
            fecha.text = detallePedido.idPedido.fechaPedido
            estado.text = "Pendiente"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historial,parent,false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        val item = lista.get(position)
        holder.binding(item)
    }

    override fun getItemCount(): Int = lista.size

}