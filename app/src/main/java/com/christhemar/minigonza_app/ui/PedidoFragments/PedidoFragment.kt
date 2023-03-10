package com.christhemar.minigonza_app.ui.PedidoFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.databinding.FragmentPedidoBinding
import com.christhemar.minigonza_app.retrofit.models.DetallePedido
import com.christhemar.minigonza_app.retrofit.models.Productos
import com.christhemar.minigonza_app.utils.Formats
import com.christhemar.minigonza_app.utils.SharedPreferencesManejador
import com.christhemar.minigonza_app.viewmodel.ClienteEntityViewModel
import com.christhemar.minigonza_app.viewmodel.PedidoViewModel
import com.christhemar.minigonza_app.views.adapter.venta.DetallePedidoAdapter
import com.christhemar.minigonza_app.views.adapter.venta.PedidoAdapter

class PedidoFragment : Fragment() {

    private var _binding:FragmentPedidoBinding?=null
    private val binding get() = _binding!!

    private lateinit var adapter:PedidoAdapter

    private lateinit var pedidoViewModel: PedidoViewModel
    private lateinit var clienteEntityViewModel: ClienteEntityViewModel
    private lateinit var listaDetallePedido:List<DetallePedido>
    private lateinit var sharedPreferencesManejador: SharedPreferencesManejador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPedidoBinding.inflate(inflater,container,false)
        pedidoViewModel=ViewModelProvider(requireActivity()).get(PedidoViewModel::class.java)
        clienteEntityViewModel=ViewModelProvider(requireActivity()).get(ClienteEntityViewModel::class.java)
        sharedPreferencesManejador= SharedPreferencesManejador()
        sharedPreferencesManejador.init(requireContext())
        binding.notpedido.visibility=View.GONE
        val idCliente = sharedPreferencesManejador.getSomeIntValue("idcliente")
        val pedido = sharedPreferencesManejador.getSomeIntValue("idpedido${idCliente}s")

        binding.rvpedido.layoutManager = LinearLayoutManager(context)

        pedidoViewModel.listarDetallePedidoXpedido(pedido)



        binding.btnseguircomprar.setOnClickListener {
            findNavController().navigate(R.id.action_nav_pedido_to_pagoFragment)
        }

        binding.irCompra.setOnClickListener {

        }


        return binding.root
    }

    fun eliminarPedido(detallePedido: DetallePedido){
        Toast.makeText(context, "${detallePedido.idPro?.nombre}", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        pedidoViewModel.dtpedidoXpedido.observe(requireActivity(), Observer {
            if(!it.isEmpty()){
                adapter= PedidoAdapter(it,{ dt -> eliminarPedido(dt) })
                binding.rvpedido.adapter=adapter
                cargarPedidoCantidad(it)
                binding.pedCard.visibility=View.VISIBLE
            }else{
                binding.rvpedido.visibility=View.GONE
                binding.pedCard.visibility=View.GONE
                binding.notpedido.visibility=View.VISIBLE
            }
        })
    }


    fun cargarPedidoCantidad(lista:List<DetallePedido>){
        var total:Double = 0.0
        var cantidad = 0
        lista.forEach { pe->
            total+= pe.idPro!!.precio * pe.cantidad
            cantidad+=pe.cantidad
        }
        binding.pedTotal.text= "S/. "+Formats.formatearDecimal(total).toString()
        binding.pedCantidad.text=cantidad.toString()
    }

}