package com.christhemar.minigonza_app.views.adapter.venta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.retrofit.models.Categoria

class CategoriaAdapter(private val lista:List<Categoria>,
                        private val setOnClickListener:(Categoria)->Unit) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    inner  class CategoriaViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private val imagen:ImageView
        private val nombre:TextView

        init {
            imagen=view.findViewById(R.id.item_cat_imagen)
            nombre=view.findViewById(R.id.item_cat_nombre)
        }

        fun render(categoria: Categoria,setOnClickListener: (Categoria) -> Unit){
            nombre.text=categoria.nombre
            Glide.with(itemView.context).load(categoria.imagen).error(R.drawable.ic_baseline_broken_image_24).into(imagen)

            itemView.setOnClickListener {
                setOnClickListener(categoria)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_categoria,parent,false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val item = lista.get(position)
        holder.render(item,setOnClickListener)
    }

    override fun getItemCount(): Int = lista.size

}