package com.christhemar.minigonza_app.ui.PedidoFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.databinding.FragmentPagoBinding
import com.christhemar.minigonza_app.databinding.FragmentVentaBinding
import com.christhemar.minigonza_app.utils.SharedPreferencesManejador

class VentaFragment : Fragment() {

    private var _binding: FragmentVentaBinding?=null
    private val binding get() = _binding!!
    private lateinit var sharedPreferencesManejador:SharedPreferencesManejador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVentaBinding.inflate(inflater,container,false)
        sharedPreferencesManejador= SharedPreferencesManejador()
        sharedPreferencesManejador.init(requireContext())

        binding.rPago.setOnCheckedChangeListener(object:RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                val id = p0?.checkedRadioButtonId
                if(binding.rTarjeta.id==id){
                    binding.pago.visibility=View.VISIBLE
                    binding.pagoYape.visibility=View.GONE
                }else{
                    binding.pago.visibility=View.GONE
                    binding.pagoYape.visibility=View.VISIBLE
                }
            }

        })

        binding.pagar.setOnClickListener {
            setearDatos()
        }

        return binding.root
    }

    fun setearDatos(){
        val id = sharedPreferencesManejador.getSomeIntValue("idcliente")
        sharedPreferencesManejador.setSomeIntValue("ped${id}s",0)
        sharedPreferencesManejador.setSomeIntValue("idpedido${id}s",0)
        val view = DialogChecked()
        view.show(parentFragmentManager,"cerrar")
    }

}