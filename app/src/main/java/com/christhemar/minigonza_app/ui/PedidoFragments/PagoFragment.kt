package com.christhemar.minigonza_app.ui.PedidoFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.databinding.FragmentPagoBinding
import com.christhemar.minigonza_app.retrofit.models.DetallePedido
import com.christhemar.minigonza_app.utils.Formats
import com.christhemar.minigonza_app.utils.SharedPreferencesManejador
import com.christhemar.minigonza_app.viewmodel.PedidoViewModel
import com.christhemar.minigonza_app.views.adapter.venta.DetallePedidoAdapter
import com.christhemar.minigonza_app.views.adapter.venta.PedidoAdapter


class PagoFragment : Fragment() {

    private var _binding:FragmentPagoBinding?=null
    private val binding get() = _binding!!

    private lateinit var pedidoViewModel: PedidoViewModel
    private lateinit var sharedPreferencesManejador: SharedPreferencesManejador
    private lateinit var listaPedidoDetalle:List<DetallePedido>
    private lateinit var detallePedidoAdapter: DetallePedidoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPagoBinding.inflate(inflater,container,false)
        pedidoViewModel=ViewModelProvider(requireActivity()).get(PedidoViewModel::class.java)
        sharedPreferencesManejador= SharedPreferencesManejador()
        sharedPreferencesManejador.init(requireContext())
        val idcliente = sharedPreferencesManejador.getSomeIntValue("idcliente")
        val pedido = sharedPreferencesManejador.getSomeIntValue("idpedido${idcliente}s")
        binding.rvpago.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        pedidoViewModel.obtenerEnvioCliente(idcliente)
        pedidoViewModel.listarDetallePedidoXpedido(pedido)

        pedidoViewModel.envioobtener.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                binding.pgClienteNumero.text = "${it.idPedido!!.idCli!!.nombre} ${it.idPedido!!.idCli!!.apellidos} - ${it.idPedido!!.idCli!!.fono}"
                binding.pgHubicacion.text = "${it.direccion} - ${it.referencia}"
                binding.pgDistrito.text = it.distrito
                binding.pagoCompra.isEnabled=true
            }else{
                binding.pgClienteNumero.text = ""
                binding.pgHubicacion.text = "No indica hubicacion"
                binding.pgDistrito.text = "No indica distrito"
                binding.pagoCompra.isEnabled=false
            }
        })



        pedidoViewModel.dtpedidoXpedido.observe(requireActivity(), Observer {
            if(!it.isEmpty()){
                listaPedidoDetalle = it
                detallePedidoAdapter= DetallePedidoAdapter(it)
                binding.rvpago.adapter=detallePedidoAdapter
                cargarDatosAdicionales(it)
            }
        })

        binding.pagoHubi.setOnClickListener {
            findNavController().navigate(R.id.action_pagoFragment_to_envioFragment)
        }

        /*binding.pagoTarjeta.setOnClickListener {
            val dialogo= Dialogo()
            dialogo.show(parentFragmentManager,"dialogo")
        }*/

        binding.pagoCompra.setOnClickListener {
            irPago()
        }

        return binding.root
    }

    fun irPago(){
        findNavController().navigate(R.id.action_pagoFragment_to_ventaFragment)
    }

    fun cargarDatosAdicionales(lista:List<DetallePedido>){
        var suma = 0.0
        var cantidad = 0
        lista.map {
            suma += it.cantidad * it.idPro!!.precio
            cantidad+=it.cantidad
        }
        binding.pgSubtotal.text = "S/. ${Formats.formatearDecimal(suma)}"
        binding.pedCantidad.text=cantidad.toString()
    }

}