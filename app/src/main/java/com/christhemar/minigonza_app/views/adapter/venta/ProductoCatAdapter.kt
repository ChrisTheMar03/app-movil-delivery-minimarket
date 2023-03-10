package com.christhemar.minigonza_app.views.adapter.venta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.retrofit.models.Productos
import com.christhemar.minigonza_app.utils.Formats
import com.google.android.material.button.MaterialButton

class ProductoCatAdapter(private val lista:List<Productos>,
                        private val setOnClickListener:(Productos)->Unit) :RecyclerView.Adapter<ProductoCatAdapter.ProductoCatViewHolder>() {

    inner class ProductoCatViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private val imagen:ImageView
        private val nombre:TextView
        private val precio:TextView
        private val btnObtener:MaterialButton

        init {
            imagen=view.findViewById(R.id.catpro_img)
            nombre=view.findViewById(R.id.catpro_nombre)
            precio=view.findViewById(R.id.catpro_precio)
            btnObtener=view.findViewById(R.id.catpro_btn)

        }

        fun binding(productos: Productos,setOnClickListener: (Productos) -> Unit){
            precio.text="S/. ${Formats.formatearDecimal(productos.precio)}"
            nombre.text=productos.nombre
            Glide.with(itemView.context).load(productos.imagen).error(R.drawable.ic_baseline_broken_image_24).into(imagen)

            btnObtener.setOnClickListener {
                setOnClickListener(productos)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoCatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prod_cat,parent,false)
        return ProductoCatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoCatViewHolder, position: Int) {
        val item = lista.get(position)
        holder.binding(item,setOnClickListener)
    }

    override fun getItemCount(): Int = lista.size

}