package com.christhemar.minigonza_app.ui.PedidoFragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.databinding.FragmentEnvioBinding
import com.christhemar.minigonza_app.retrofit.models.Envio
import com.christhemar.minigonza_app.retrofit.models.Pedido
import com.christhemar.minigonza_app.utils.SharedPreferencesManejador
import com.christhemar.minigonza_app.viewmodel.PedidoViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class EnvioFragment : Fragment() {

    private var _binding:FragmentEnvioBinding?=null
    private val binding get() = _binding!!
    private lateinit var sharedPreferencesManejador: SharedPreferencesManejador
    private lateinit var pedidoViewModel: PedidoViewModel
    private var direccion=""
    private var distrito=""
    private var referencia=""
    private var fecha=""
    private var dni=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment - Instancias
        _binding= FragmentEnvioBinding.inflate(inflater,container,false)
        pedidoViewModel=ViewModelProvider(requireActivity()).get(PedidoViewModel::class.java)
        sharedPreferencesManejador= SharedPreferencesManejador()
        sharedPreferencesManejador.init(requireContext())
        val idcli = sharedPreferencesManejador.getSomeIntValue("idcliente")

        //Animaciones
        val animtaion = AnimationUtils.loadAnimation(context,R.anim.radio_anim)
        val correr = AnimationUtils.loadAnimation(context,R.anim.move_anim)
        val regresar = AnimationUtils.loadAnimation(context,R.anim.move_end_anim)
        val animSet=AnimationSet(false)
        animSet.addAnimation(regresar)
        animSet.addAnimation(correr)
        animacionCarga(animtaion)
        binding.entrega.startAnimation(animtaion)
        binding.carrito.startAnimation(animSet)

        //Animacion del carro al dar click
        binding.carrito.setOnClickListener {
            binding.carrito.startAnimation(animSet)
        }

        /*binding.envCargar.setOnClickListener {
            pedidoViewModel.obtenerEnvioCliente(idcli)

            pedidoViewModel.envioobtener.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    binding.envDireccion.append(it.direccion)
                    binding.envDistrito.append(it.distrito)
                    binding.envReferencia.append(it.referencia)
                    binding.envDni.isEnabled=false
                    binding.envDni.append("00000000")
                    binding.linear.visibility=View.VISIBLE
                }
            })

        }*/

        //Mapa es verificado
        cargarCheckMapa()

        //Material Picker
        val constraint=CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Fecha de entrega")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraint.build())
            .build()

        //Entrar al mapa
        binding.mapa.setOnClickListener {
            habilitarLocalizacion()
        }

        //Obtener la fecha picker
        binding.envFecha.setOnClickListener {
            picker.show(parentFragmentManager,"DATE")
            picker.addOnPositiveButtonClickListener {
                val fecha = Date(it)
                val text = convertirFecha(fecha)
                this.fecha = text
                binding.envFecha.text = text
                binding.envFecha.setIconResource(R.drawable.ic_icon_circle_white)
                Log.d("totales","${direccion}-${distrito}-${referencia}-${text}")
                binding.linear.visibility=View.VISIBLE
                binding.linear.startAnimation(animtaion)
            }
        }

        binding.envDireccion.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                direccion = p0.toString()
            }

        })

        binding.envDistrito.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                distrito = p0.toString()
            }
        })

        binding.envReferencia.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                referencia = p0.toString()
            }
        })

        binding.envDni.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                dni = p0.toString()
            }

        })


        binding.envVista.setOnClickListener {
            val ped = sharedPreferencesManejador.getSomeIntValue("idpedido${idcli}s")
            val lat = sharedPreferencesManejador.getSomeStringValue("lat${idcli}s")
            val lon = sharedPreferencesManejador.getSomeStringValue("lon${idcli}s")

            //no envio${idcli}s
            //no idenvio${idcli}s
            if(validarCampos()){
                val envio = Envio(null,
                    Pedido(ped),fecha,direccion,referencia,distrito,lat,lon,null
                )

                pedidoViewModel.agregarEnvio(envio)

                pedidoViewModel.envioresponse.observe(viewLifecycleOwner,Observer{
                    if(it!=null){
                        findNavController().popBackStack()
                    }else{
                        Toast.makeText(context, "No se guardo", Toast.LENGTH_SHORT).show()
                    }
                })

            }

        }

        return binding.root
    }


    private fun animacionCarga(animation: Animation){
        binding.inputEnvDireccion.startAnimation(animation)
        binding.inputEnvDistrito.startAnimation(animation)
        binding.inputEnvReferencia.startAnimation(animation)
    }

    //Devuelve si esta habilitado el permiso
    private fun locationGranted():Boolean{
        return context?.let { ContextCompat.checkSelfPermission(it,Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED
    }

    //Permisos de localizacion
    private fun habilitarLocalizacion(){
        if(locationGranted()){
            //si
            redireecionar()
        }else{
            //no
            pedirPermisos()
        }
    }

    private fun redireecionar(){
        findNavController().navigate(R.id.action_envioFragment_to_mapaFragment)
    }

    private fun pedirPermisos() {
        if(activity?.let { ActivityCompat.shouldShowRequestPermissionRationale(it,Manifest.permission.ACCESS_FINE_LOCATION) } == true){
            Toast.makeText(context, "Ve ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else{
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0) }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            0 -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                redireecionar()
            }else{
                activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0) }
            }
            else -> {}
        }
    }

    fun convertirFecha(date: Date):String{
        val formater = SimpleDateFormat("yyyy-MM-dd")
        return formater.format(date)
    }

    fun validarCampos():Boolean{
        val id=sharedPreferencesManejador.getSomeIntValue("idcliente")
        if(direccion!="" && distrito!="" && referencia!="" && sharedPreferencesManejador.getSomeStringValue("lat${id}s")!="" && sharedPreferencesManejador.getSomeStringValue("lon${id}s")!=""){
            return true
        }
        return false
    }

    fun cargarCheckMapa(){
        val id = sharedPreferencesManejador.getSomeIntValue("idcliente")
        if(sharedPreferencesManejador.getSomeStringValue("lat${id}s")!="" && sharedPreferencesManejador.getSomeStringValue("lon${id}s")!=""){
            binding.envMapaCheck.visibility=View.VISIBLE
        }
    }

    /*fun validarCamposInput(){
        val hijos = binding.contraintLayout.childCount

        for (i in (0..hijos)){
            val input = binding.contraintLayout.getChildAt(i)

            if(input is TextInputEditText){
                val entrada:TextInputEditText = input as TextInputEditText
                verificarVacios(entrada)
            }
        }
    }*/


    /*fun verificarVacios(textInputEditText: TextInputEditText):Boolean{
        if(textInputEditText.text.toString().trim().isEmpty()){
            return false
        }
        Log.d("input",textInputEditText.text.toString())
        return true
    }*/

}