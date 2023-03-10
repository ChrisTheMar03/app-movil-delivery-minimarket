package com.christhemar.minigonza_app.views.fragments

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.databinding.ActivityRegisterBinding
import com.christhemar.minigonza_app.retrofit.models.Clientes
import com.christhemar.minigonza_app.viewmodel.ClienteViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding

    private lateinit var clienteViewModel:ClienteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clienteViewModel = ViewModelProvider(this).get(ClienteViewModel::class.java)

        binding.regBtnVolver.setOnClickListener {
            volver()
        }
        
        binding.regBtnReg.setOnClickListener { 
            if(validarDatos()){
                val cliente = Clientes(null,binding.regTxtNombre.text.toString(),binding.regTxtApellido.text.toString(),
                    binding.regTxtEmail.text.toString(),binding.regTxtCelular.text.toString(),binding.regTxtPassword.text.toString(),"0")

                clienteViewModel.registrarUsuario(cliente)

                clienteViewModel.responseRegistro.observe(this, Observer { bool->
                    if(bool!=0){
                        Snackbar.make(binding.root,"Usuario exitosamente Registrado",Snackbar.LENGTH_SHORT).setTextColor(Color.WHITE)
                            .setBackgroundTint(Color.GRAY).show()
                        finish()
                    }
                })

            }

        }

    }

    fun volver(){
        finish()
    }

    fun validarDatos():Boolean{
        if(validarNombre() && validarApellido() && validarEmail() && validarCelular() && validarPassword()){
            return true
        }
        return false
    }

    fun validarNombre():Boolean{
        if(binding.regTxtNombre.text.toString().isEmpty()){
            binding.regInputNombre.error="Ingrese Campo Nombre"
            return false
        }
        if(binding.regTxtNombre.text.toString().length < 2){
            binding.regInputNombre.error="Ingrese un Nombre valido"
            return false
        }

        binding.regInputNombre.error=""
        return true
    }

    fun validarApellido():Boolean{
        if(binding.regTxtApellido.text.toString().isEmpty()){
            binding.regInputApellido.error="Ingrese Campo Appelido"
            return false
        }
        if(binding.regTxtApellido.text.toString().length < 3){
            binding.regInputApellido.error="Ingrese un Apellido valido"
            return false
        }

        binding.regInputApellido.error=""
        return true
    }

    fun validarEmail():Boolean{
        if(binding.regTxtEmail.text.toString().isEmpty()){
            binding.regInputEmail.error="Ingrese Campo Correo"
            return false
        }
        if(!validarCorreo()){
            binding.regInputEmail.error="Correo no valido"
            return false
        }
        binding.regInputEmail.error=""
        return true
    }

    fun validarCelular():Boolean{
        if(binding.regTxtCelular.text.toString().isEmpty()){
            binding.regInputCelular.error="Ingrese Campo Celular"
            return false
        }
        if(binding.regTxtCelular.text.toString().length<9){
            binding.regInputCelular.error="Numero de celular no valido"
            return false
        }
        binding.regInputCelular.error=""
        return true
    }

    fun validarPassword():Boolean{
        if(binding.regTxtPassword.text.toString().isEmpty()){
            binding.regInputPass1.error="Ingrese Campo Contraseña"
            return false
        }
        if(binding.regTxtPassword.text.toString().length<6){
            binding.regInputPass1.error="Contraseña debde ser mayor o igual 6 digitos"
            return false
        }
        binding.regInputPass1.error=""
        return true
    }
    
    fun validarCorreo():Boolean{
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(binding.regTxtEmail.text.toString()).matches()
    }
    
}