package com.christhemar.minigonza_app.ui.PedidoFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.databinding.FragmentHistorialPedidoBinding
import com.christhemar.minigonza_app.utils.SharedPreferencesManejador
import com.christhemar.minigonza_app.viewmodel.PedidoViewModel
import com.christhemar.minigonza_app.views.adapter.venta.HistorialAdapter

class HistorialPedidoFragment : Fragment() {

    private var _binding:FragmentHistorialPedidoBinding?=null
    private val binding get() = _binding!!
    private lateinit var sharedPreferencesManejador: SharedPreferencesManejador
    private lateinit var pedidoViewModel: PedidoViewModel
    private lateinit var historialAdapter:HistorialAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHistorialPedidoBinding.inflate(inflater,container,false)
        sharedPreferencesManejador= SharedPreferencesManejador()
        sharedPreferencesManejador.init(requireContext())
        pedidoViewModel= ViewModelProvider(requireActivity())[PedidoViewModel::class.java]
        val idCliente = sharedPreferencesManejador.getSomeIntValue("idcliente")
        binding.rvHistorial.layoutManager=LinearLayoutManager(context)

        pedidoViewModel.detallePedidoXcliente(idCliente)

        pedidoViewModel.detallePedidoXcliente.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                historialAdapter=HistorialAdapter(it)
                binding.rvHistorial.adapter=historialAdapter
                binding.notHave.visibility=View.GONE
            }else{
                binding.notHave.visibility=View.VISIBLE
                binding.rvHistorial.visibility=View.GONE
            }
        })

        return binding.root
    }

}