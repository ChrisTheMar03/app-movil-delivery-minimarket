package com.christhemar.minigonza_app.ui.PedidoFragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.christhemar.minigonza_app.R

class DialogChecked : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_checked,container,false)

        Handler().postDelayed({
            findNavController().navigate(R.id.action_ventaFragment_to_nav_home)
            this.dialog?.hide()
        },2500)

        return view
    }

}