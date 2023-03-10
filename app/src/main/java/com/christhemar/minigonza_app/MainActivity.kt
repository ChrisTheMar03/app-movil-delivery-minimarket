package com.christhemar.minigonza_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.christhemar.minigonza_app.databinding.ActivityMainBinding
import com.christhemar.minigonza_app.utils.HeaderEntity
import com.christhemar.minigonza_app.utils.SharedPreferencesManejador
import com.christhemar.minigonza_app.viewmodel.ClienteEntityViewModel
import com.christhemar.minigonza_app.views.fragments.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var clienteEntityViewModel: ClienteEntityViewModel
    private lateinit var sharedPreferencesManejador: SharedPreferencesManejador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferencesManejador= SharedPreferencesManejador()
        sharedPreferencesManejador.init(this)
        clienteEntityViewModel=ViewModelProvider(this).get(ClienteEntityViewModel::class.java)

        setSupportActionBar(binding.appBarMain.toolbar)


        //Cuando presione el boton inferior
//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,R.id.nav_pedido,R.id.nav_historial
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        informacionNav()
        seleccionarMenuItem()
    }

    fun informacionNav(){
        val imagen = binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.imageView)
        val textoHeader = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header)
        val descHeader = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.nav_second_header)
        clienteEntityViewModel.obtener()
            .observe(this, Observer {
                if(it!=null){
                    descHeader.text = it.correo
                    sharedPreferencesManejador.setSomeIntValue("idcliente",it.idCli!!)
                }

            })
        seleccionarHeader()
            .let {
                imagen.setImageResource(it.recurso)
                textoHeader.text=it.nombre
            }
    }

    fun multiArraysIconNav(): List<HeaderEntity> {
        val lista = arrayOf(HeaderEntity(1,R.drawable._01_banana,"Equipo Platano"),
            HeaderEntity(2,R.drawable._02_cherry,"Equipo Cereza"),
            HeaderEntity(3,R.drawable._03_pineapple,"Equipo Piña"),
            HeaderEntity(4,R.drawable._04_strawberry,"Equipo Fresa"),
            HeaderEntity(5,R.drawable._05_bergamot,"Equipo Bergamota"),
            HeaderEntity(6,R.drawable._06_avocado,"Equipo Palta"),
            HeaderEntity(7,R.drawable._07_pitanga,"Equipo Pitanga"),
            HeaderEntity(8,R.drawable._08_lemon,"Equipo Limon")
        )
        return lista.toList()
    }

    fun seleccionarHeader():HeaderEntity{
        return multiArraysIconNav().get((0..7).random())
    }

    fun seleccionarMenuItem(){
        binding.appBarMain.toolbar.setOnMenuItemClickListener { item->
            when(item.itemId){
                R.id.cerrar_sesion ->{
                    cerrarSesion()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private fun cerrarSesion() {
        clienteEntityViewModel.eliminarTodo()
        redireccionar()
    }

    private fun redireccionar(){
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}