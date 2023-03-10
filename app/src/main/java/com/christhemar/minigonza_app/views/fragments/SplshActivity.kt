package com.christhemar.minigonza_app.views.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.christhemar.minigonza_app.MainActivity
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.databinding.ActivitySplshBinding
import com.christhemar.minigonza_app.viewmodel.ClienteEntityViewModel

class SplshActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplshBinding

    private lateinit var clienteEntityViewModel: ClienteEntityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplshBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clienteEntityViewModel=ViewModelProvider(this).get(ClienteEntityViewModel::class.java)

        clienteEntityViewModel.obtener().observe(this, Observer {
            it.let {
                if (it!=null){
                    Handler().postDelayed({
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    },2800)
                }else{
                    Handler().postDelayed({
                        startActivity(Intent(this,LoginActivity::class.java))
                        finish()
                    },2800)
                }
            }
        })



    }

    fun redireccionar(activity:AppCompatActivity,tiempo:Long){
        Handler().postDelayed({
            startActivity(Intent(this,activity::class.java))
        },tiempo)
    }

}