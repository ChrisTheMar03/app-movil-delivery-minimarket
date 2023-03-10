package com.christhemar.minigonza_app.views.adapter.venta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.retrofit.models.DetallePedido

class DetallePedidoAdapter(private val lista:List<DetallePedido>) : RecyclerView.Adapter<DetallePedidoAdapter.DetallePedidoViewHolder>() {

    inner class DetallePedidoViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private val nombre:TextView
        private val cantidad:TextView
        val imagen:ImageView

        init {
            nombre = view.findViewById<TextView>(R.id.item_dp_nombre)
            cantidad = view.findViewById<TextView>(R.id.item_dp_cantidad)
            imagen = view.findViewById<ImageView>(R.id.item_dp_imagen)
        }

        fun render(detallePedido: DetallePedido){
            nombre.text = detallePedido.idPro!!.nombre
            cantidad.text = detallePedido.cantidad.toString()
            Glide.with(itemView.context).load(detallePedido.idPro.imagen).error(R.drawable.ic_baseline_broken_image_24).into(imagen)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetallePedidoViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_detalle_pedido,parent,false)
        return DetallePedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetallePedidoViewHolder, position: Int) {
        val item = lista.get(position)
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

}