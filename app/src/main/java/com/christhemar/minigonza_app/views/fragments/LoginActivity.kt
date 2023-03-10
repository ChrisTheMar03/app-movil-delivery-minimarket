package com.christhemar.minigonza_app.views.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.christhemar.minigonza_app.MainActivity
import com.christhemar.minigonza_app.databinding.ActivityLoginBinding
import com.christhemar.minigonza_app.db.entity.ClienteEntity
import com.christhemar.minigonza_app.retrofit.models.Clientes
import com.christhemar.minigonza_app.utils.SharedPreferencesManejador
import com.christhemar.minigonza_app.viewmodel.ClienteEntityViewModel
import com.christhemar.minigonza_app.viewmodel.ClienteViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding

    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var clienteEntityViewModel: ClienteEntityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clienteViewModel=ViewModelProvider(this).get(ClienteViewModel::class.java)
        clienteEntityViewModel=ViewModelProvider(this).get(ClienteEntityViewModel::class.java)
        binding.progressLogin.visibility=View.GONE

        binding.btnregistro.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                redireccionar()
            }
        })

        binding.loginbtn.setOnClickListener { v->
            if(validarDatos()){
                binding.loginbtn.isEnabled=false
                binding.progressLogin.visibility=View.VISIBLE
                clienteViewModel.iniciarSesion(binding.txtCorreo.text.toString().trim(),binding.txtclave.text.toString().trim())
            }
        }

        clienteViewModel.responseLogin.observe(this, Observer {
            obtenerCliente(it)
        })

    }

    private fun obtenerCliente(clientes: Clientes){
        if(clientes.nombre.isNotEmpty()){
            val client = ClienteEntity(clientes.idCli,clientes.nombre,clientes.apellidos,clientes.correo,clientes.fono,clientes.clave,clientes.ban)
            clienteEntityViewModel.insertar(client)
            irMenu()
            binding.progressLogin.visibility=View.GONE
            binding.loginbtn.isEnabled=true
        }else{
            Toast.makeText(this, "Usuario no encontrado, registrese", Toast.LENGTH_SHORT).show()
            binding.progressLogin.visibility=View.GONE
            binding.loginbtn.isEnabled=true
        }
    }


    private fun redireccionar(){
        startActivity(Intent(this,RegisterActivity::class.java))
    }

    private fun irMenu(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private fun validarDatos():Boolean{
        if(validarCorreo()&& validarClave()){
            return true
        }
        return false
    }

    private fun validarCorreo():Boolean{
        if(binding.txtCorreo.text.toString().trim().isEmpty()){
            binding.inputcorreo.error="Campo Correo Necesario"
            return false
        }
        if(!validarCampoEmail()){
            binding.inputcorreo.error="Correo no valido"
            return false
        }
        binding.inputcorreo.error=""
        return true
    }

    private fun validarClave():Boolean{
        if(binding.txtclave.text.toString().trim().isEmpty()){
            binding.inputclave.error="Campo Clave Necesario"
            return false
        }
        if(binding.txtclave.text.toString().length<6){
            binding.inputclave.error="Contraseña debde ser mayor o igual 6 digitos"
            return false
        }
        return true
    }

    private fun validarCampoEmail():Boolean{
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(binding.txtCorreo.text.toString()).matches()
    }

}