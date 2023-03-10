package com.christhemar.minigonza_app.ui.PedidoFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.christhemar.minigonza_app.R
import com.google.android.material.button.MaterialButton

class Dialogo : DialogFragment() {

    private lateinit var guardar:MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialogo_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        guardar.setOnClickListener {
            this.dialog?.hide()
        }

    }

    fun init(view:View){
        guardar=view.findViewById(R.id.btnGuardar)
    }

}