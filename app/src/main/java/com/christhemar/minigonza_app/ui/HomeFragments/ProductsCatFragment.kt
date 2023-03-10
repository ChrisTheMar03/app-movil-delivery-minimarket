package com.christhemar.minigonza_app.ui.HomeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.databinding.FragmentProductsCatBinding
import com.christhemar.minigonza_app.retrofit.models.Productos
import com.christhemar.minigonza_app.viewmodel.ProductoViewModel
import com.christhemar.minigonza_app.views.adapter.venta.ProductoCatAdapter

class ProductsCatFragment : Fragment() {

    private var _binding:FragmentProductsCatBinding?=null
    private val binding get() = _binding!!

    private lateinit var productoCatAdapter:ProductoCatAdapter

    private lateinit var productoViewModel:ProductoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentProductsCatBinding.inflate(inflater,container,false)
        binding.progressProcat.visibility=View.VISIBLE

        productoViewModel=ViewModelProvider(requireActivity()).get(ProductoViewModel::class.java)

        binding.rvprocat.layoutManager=GridLayoutManager(context,2)

        if(arguments!=null){
            val id = arguments?.getInt("idcat")
            val nombre = arguments?.getString("titulo")
            binding.catTitulo.text = nombre

            productoViewModel.productoxCategoria(id!!)

        }

        productoViewModel.productoCategoria.observe(viewLifecycleOwner, Observer {
            binding.progressProcat.visibility=View.GONE
            productoCatAdapter=ProductoCatAdapter(it,{ pro -> redireccionarDetalle(pro,binding.root) })
            binding.rvprocat.adapter=productoCatAdapter
        })

        return binding.root
    }

    private fun redireccionarDetalle(productos: Productos,view:View){
        val bundle= bundleOf( "idpro" to productos.idPro )
        view.findNavController().navigate(R.id.action_nav_products_cat_to_nav_detalle_producto,bundle)
    }


}