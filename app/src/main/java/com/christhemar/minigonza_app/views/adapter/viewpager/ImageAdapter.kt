package com.christhemar.minigonza_app.views.adapter.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.retrofit.models.Productos
import com.christhemar.minigonza_app.utils.Formats

class ImageAdapter(private val lista:List<Productos>,
                   private val viewPager2: ViewPager2,
                   private val setOnClickListener:(Productos)->Unit) :  RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


    inner class ImageViewHolder(view:View) : RecyclerView.ViewHolder(view){
        private var imageView:ImageView
        private var nombre:TextView
        private var precio:TextView
        init {
            imageView=view.findViewById(R.id.imgpager)
            nombre=view.findViewById(R.id.view_nombre)
            precio=view.findViewById(R.id.view_precio)
        }
        fun render(productos: Productos,setOnClickListener: (Productos) -> Unit){
            Glide.with(itemView.context).load(productos.imagen).into(imageView)
            nombre.text=recortarPalabra(productos.nombre)
            precio.text="S/. ${Formats.formatearDecimal(productos.precio)}"

            itemView.setOnClickListener {
                setOnClickListener(productos)
            }

        }

        fun recortarPalabra(word:String):String{
            return if(word.length>28) "${word.substring(0,28)}..." else word
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_pager,parent,false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = lista.get(position)
        holder.render(item,setOnClickListener)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

}