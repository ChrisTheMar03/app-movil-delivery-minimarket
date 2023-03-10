package com.christhemar.minigonza_app.ui.PedidoFragments

import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.christhemar.minigonza_app.R
import com.christhemar.minigonza_app.utils.SharedPreferencesManejador

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapaFragment : Fragment() , GoogleMap.OnMarkerDragListener,GoogleMap.OnMyLocationClickListener {

    private lateinit var nMap:GoogleMap
    private lateinit var sharedPreferencesManejador: SharedPreferencesManejador

    var latitud = -12.075503
    var longitud = -76.952399

    var latLng=LatLng(latitud,longitud)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManejador= SharedPreferencesManejador()
        sharedPreferencesManejador.init(requireContext())
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipula el mapa una vez disponible.
         * Esta devolución de llamada se activa cuando el mapa está listo para usarse.
         * Aquí es donde podemos añadir marcadores o líneas, añadir oyentes o mover la cámara.
         * En este caso, solo agregamos un marcador cerca de Sydney, Australia.
         * Si los servicios de Google Play no están instalados en el dispositivo, se le pedirá al usuario que
         * instálelo dentro de SupportMapFragment. Este método solo se activará una vez que el
         * el usuario instaló los servicios de Google Play y volvió a la aplicación.
         */

        nMap = googleMap
        nMap.isMyLocationEnabled=true
        val id = sharedPreferencesManejador.getSomeIntValue("idcliente")
        if(sharedPreferencesManejador.getSomeStringValue("lat${id}s")!="" && sharedPreferencesManejador.getSomeStringValue("lon${id}s")!=""){
            latLng= LatLng(sharedPreferencesManejador.getSomeStringValue("lat${id}s").toDouble(),sharedPreferencesManejador.getSomeStringValue("lon${id}s").toDouble())
        }
        nMap.setOnMarkerDragListener(this)
        nMap.setOnMyLocationClickListener(this)
        nMap.addMarker(MarkerOptions()
            .position(latLng)
            .draggable(true))

        nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.0F))
        /*googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mapa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMarkerDrag(p0: Marker) {
        p0.title="Hubicacion Definida"
        p0.showInfoWindow()
        nMap.animateCamera(CameraUpdateFactory.newLatLng(p0.position))
    }

    override fun onMarkerDragEnd(p0: Marker) {
        p0.showInfoWindow()
        latLng=p0.position
        val posicion=p0.position
        Log.d("DATOMAPA","Ultima Parada: ${posicion.longitude} - ${posicion.latitude}")
    }

    override fun onMarkerDragStart(p0: Marker) {
        var position=p0.position
        p0.snippet=position.latitude.toString()+" - "+position.longitude.toString()
        p0.showInfoWindow()
        nMap.animateCamera(CameraUpdateFactory.newLatLng(position))
    }

    override fun onMyLocationClick(p0: Location) {
        latitud = p0.latitude
        longitud = p0.longitude
        val id = sharedPreferencesManejador.getSomeIntValue("idcliente")
        sharedPreferencesManejador.setSomeStringValue("lat${id}s", p0.latitude.toString())
        sharedPreferencesManejador.setSomeStringValue("lon${id}s",p0.longitude.toString())

        verificarHubicacion()

    }

    fun verificarHubicacion(){
        findNavController().popBackStack()
        Toast.makeText(context, "Hubicacion Guarda", Toast.LENGTH_SHORT).show()
    }

}