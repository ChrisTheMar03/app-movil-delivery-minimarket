package com.christhemar.minigonza_app.ui.HomeFragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.databinding.FragmentHomeBinding
import com.christhemar.minigonza_app.retrofit.models.Categoria
import com.christhemar.minigonza_app.retrofit.models.Productos
import com.christhemar.minigonza_app.viewmodel.ProductoViewModel
import com.christhemar.minigonza_app.views.adapter.venta.CategoriaAdapter
import com.christhemar.minigonza_app.views.adapter.viewpager.ImageAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageAdapter: ImageAdapter
    private lateinit var categoriaAdapter: CategoriaAdapter
    private lateinit var productoViewModel: ProductoViewModel

    private val URL:String="https://api.whatsapp.com/send?phone=51"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.progressCat.visibility=View.VISIBLE
        binding.progressTop.visibility=View.VISIBLE

        productoViewModel = ViewModelProvider(requireActivity()).get(ProductoViewModel::class.java)

        listarTopProductos()

        configurarViewPager()

        redirigirToUrl("936765744")

        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer(object : ViewPager2.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }
        })

        binding.viewpager2.setPageTransformer(transformer)

        binding.rvcat.layoutManager=GridLayoutManager(context,3)

        cargarCategorias()

        return binding.root
    }

    fun redirigirToUrl(numero:String){
        binding.fab.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                val link=Uri.parse(URL+numero)
                val intent=Intent(Intent.ACTION_VIEW,link)
                startActivity(intent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun listarTopProductos() {
        productoViewModel.listadoProductosTop().observe(viewLifecycleOwner, Observer { list ->
            binding.progressCat.visibility=View.GONE
            binding.progressTop.visibility=View.GONE
            imageAdapter = ImageAdapter(list, binding.viewpager2,{ pro -> redireccionar(pro,binding.root) })
            binding.viewpager2.adapter = imageAdapter
            binding.viewpager2.currentItem = 4
        })
    }

    private fun configurarViewPager() {
        binding.viewpager2.offscreenPageLimit = 3
        binding.viewpager2.clipChildren = false
        binding.viewpager2.clipToPadding = false
        binding.viewpager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun redireccionar(productos: Productos,view:View){
        val bundle= bundleOf( "idpro" to productos.idPro )
        view.findNavController().navigate(R.id.action_nav_home_to_detalleProductoFragment,bundle)
    }

    private fun cargarCategorias(){
        productoViewModel.listarCategorias().observe(viewLifecycleOwner, Observer {
            categoriaAdapter= CategoriaAdapter(it,{ cate -> redireccionarToCategoria( cate ,binding.root) })
            binding.rvcat.adapter=categoriaAdapter
        })
    }

    private fun redireccionarToCategoria(categoria: Categoria,view:View){
        val bundle= bundleOf( "idcat" to  categoria.idCat, "titulo" to categoria.nombre)
        view.findNavController().navigate(R.id.action_nav_home_to_nav_products_cat,bundle)
    }

}