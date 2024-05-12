package com.example.sketchhub.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sketchhub.R

class Register : Fragment() {

    interface RegisterListener {
        fun onSimpleRegister()
        fun onPremiumRegister()
    }

    private lateinit var registerListener: RegisterListener

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater?.inflate(R.layout.registration, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        registerListener = context as RegisterListener
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.findViewById<Button>(R.id.registerButton)?.setOnClickListener {
            registerListener.onSimpleRegister()
        }

        view?.findViewById<Button>(R.id.premiumRegisterButton)?.setOnClickListener {
            registerListener.onPremiumRegister()
        }
    }

}