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
import com.christhemar.minigonza_app.retrofit.models.Pedido
import com.christhemar.minigonza_app.utils.Formats
import com.google.android.material.button.MaterialButton

class PedidoAdapter(private val lista:List<DetallePedido>,
                    private val setOnClickListener: (DetallePedido)->Unit) : RecyclerView.Adapter<PedidoAdapter.PedidoRecyclerView>() {

    inner class PedidoRecyclerView(view:View) :RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoRecyclerView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pedido,parent,false)
        return PedidoRecyclerView(view)
    }

    override fun onBindViewHolder(holder: PedidoRecyclerView, position: Int) {
        with(holder){
            with(lista.get(position)){
                val nombre = itemView.findViewById<TextView>(R.id.dt_nombre)
                val precio = itemView.findViewById<TextView>(R.id.dt_precio)
                val imagen = itemView.findViewById<ImageView>(R.id.dt_img)
                val boton = itemView.findViewById<MaterialButton>(R.id.dt_btn)

                nombre.text = "${idPro?.nombre} (${cantidad})"
                precio.text = "S/. ${Formats.formatearDecimal(idPro!!.precio)}"
                Glide.with(itemView.context).load(idPro.imagen).error(R.drawable.ic_baseline_broken_image_24).into(imagen)

                boton.setOnClickListener {
                    setOnClickListener(this)
                }

            }
        }
    }

    override fun getItemCount(): Int = lista.size

}
