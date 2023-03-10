package com.christhemar.minigonza_app.ui.HomeFragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.christhemar.minigonza_app.databinding.FragmentDetalleProductoBinding
import com.christhemar.minigonza_app.db.entity.ClienteEntity
import com.christhemar.minigonza_app.retrofit.models.Clientes
import com.christhemar.minigonza_app.retrofit.models.DetallePedido
import com.christhemar.minigonza_app.retrofit.models.Pedido
import com.christhemar.minigonza_app.retrofit.models.Productos
import com.christhemar.minigonza_app.utils.Formats
import com.christhemar.minigonza_app.utils.SharedPreferencesManejador
import com.christhemar.minigonza_app.viewmodel.ClienteEntityViewModel
import com.christhemar.minigonza_app.viewmodel.PedidoViewModel
import com.christhemar.minigonza_app.viewmodel.ProductoViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class DetalleProductoFragment : Fragment() {

    private var _binding:FragmentDetalleProductoBinding?=null
    private val binding get() = _binding!!
    private lateinit var pedidoViewModel: PedidoViewModel
    private lateinit var sharedPreferencesManejador: SharedPreferencesManejador

    private lateinit var productoViewModel: ProductoViewModel
    private lateinit var clienteEntityViewModel: ClienteEntityViewModel
    var id:Int?=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentDetalleProductoBinding.inflate(inflater,container,false)
        pedidoViewModel=ViewModelProvider(requireActivity()).get(PedidoViewModel::class.java)
        productoViewModel = ViewModelProvider(requireActivity()).get(ProductoViewModel::class.java)
        clienteEntityViewModel=ViewModelProvider(requireActivity()).get(ClienteEntityViewModel::class.java)
        sharedPreferencesManejador= SharedPreferencesManejador()
        sharedPreferencesManejador.init(requireContext())

        binding.progressBar.visibility=View.VISIBLE

        if(arguments!=null){
            id= arguments?.getInt("idpro")!!
            productoViewModel.obtenerProducto(id!!)
        }

        productoViewModel.productoObtenido.observe(viewLifecycleOwner, Observer {
            cargarProducto(it)
            binding.progressBar.visibility=View.GONE
        })

        obtenerCantidad()

        binding.btnagregarped.setOnClickListener {
            cargarPedido()
        }

        return binding.root
    }

    private fun retroceder(){
        Snackbar.make(binding.root,"Agregado a su carrito",Snackbar.LENGTH_LONG).show()
        findNavController().popBackStack()
    }

    fun cargarProducto(productos: Productos){
        binding.detNombre.text=productos.nombre
        binding.detCat.text=productos.idCat?.nombre
        binding.detMarca.text=productos.idMarca?.nombre
        binding.detPrecio.text="S/. ${Formats.formatearDecimal(productos.precio)}"
        Glide.with(binding.root.context).load(productos.imagen).into(binding.imgDetalle)
    }

    private fun obtenerCantidad(){
        var cantidad = binding.detailCantidad.text.toString().toInt()
        binding.btnPos.setOnClickListener {
            if(cantidad<=40){
                cantidad+=1
            }
            binding.detailCantidad.text=cantidad.toString()
        }
        binding.btnNeg.setOnClickListener {
            if(cantidad>1){
                cantidad-=1
            }
            binding.detailCantidad.text=cantidad.toString()
        }
    }

    private fun cargarPedido(){
        clienteEntityViewModel.obtener().observe(viewLifecycleOwner, Observer {
            cargarPedido(it)
        })
    }

    private fun cargarPedido(it: ClienteEntity){
        val cliente = Clientes(it.idCli,it.nombre,it.apellidos,it.correo,it.fono,it.clave,it.ban)
        val fecha = fechaActual()
        val pedido=Pedido(null,cliente,fecha )

        if(sharedPreferencesManejador.getSomeIntValue("ped${cliente.idCli}s")==0 ){
            sharedPreferencesManejador.setSomeIntValue("ped${cliente.idCli}s",1)
            //Creando el pedido
            pedidoViewModel.creaPedido(pedido)
            Log.d("didos",pedido.toString())

            //Observado el resultado del pedido
            pedidoViewModel.pedidoresponse.observe(viewLifecycleOwner
            ) { t ->
                if (t != null) {
                    sharedPreferencesManejador.setSomeIntValue("idpedido${cliente.idCli}s", t.idPedido!!)

                    val detallePedido = DetallePedido(
                        null,
                        Productos(id!!),
                        t,
                        binding.detailCantidad.text.toString().toInt()
                    )
                    Log.d("idpedido${cliente.idCli}s", t.idPedido.toString())
                    pedidoViewModel.agregarDetallePedido(detallePedido)

                } else {
                    Toast.makeText(context, "Ocurrio un errror con el pedido", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }else{
            //No es nuevo pedido aun
            if(sharedPreferencesManejador.getSomeIntValue("idpedido${cliente.idCli}s")!=0){
                val idPedido = sharedPreferencesManejador.getSomeIntValue("idpedido${cliente.idCli}s")
                val detallePedido=DetallePedido(null,Productos(id!!),Pedido(idPedido),binding.detailCantidad.text.toString().toInt())
                pedidoViewModel.agregarDetallePedido(detallePedido)
            }else{
                Toast.makeText(context, "Hubo un error de servidor", Toast.LENGTH_SHORT).show()
            }
        }

        pedidoViewModel.detallePedidoresponse.observe(viewLifecycleOwner, Observer {
            if(it){
                Log.d("dt",it.toString())
                retroceder()
            }
        })


    }

    fun fechaActual():String{
        val formater = SimpleDateFormat("yyyy-MM-dd")
        return formater.format(Date())
    }

}

