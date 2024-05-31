package com.example.sketchhub.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.sketchhub.PageChangeListener
import com.example.sketchhub.R

class Register : Fragment() {

    interface RegisterListener {
        fun onSimpleRegister(username: String, email: String, password: String, surname: String?, city: String?, premium: Boolean)
        fun onPremiumRegister()
    }

    private lateinit var registerListener: RegisterListener

    private var pageChangeListener: PageChangeListener? = null

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
        pageChangeListener = context as PageChangeListener
    }

    override fun onDetach() {
        super.onDetach()

        pageChangeListener = null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pageChangeListener?.onPageChanged(getString(R.string.register))

        val seekJobCheckBox = view?.findViewById<CheckBox>(R.id.registerSeekJob)

        seekJobCheckBox?.setOnCheckedChangeListener { _, isChecked ->
            val visibility = if (isChecked) View.VISIBLE else View.GONE
            view.findViewById<TextView>(R.id.registerTextSurname).visibility = visibility
            view.findViewById<EditText>(R.id.registerSurname).visibility = visibility
            view.findViewById<TextView>(R.id.registerTextCity).visibility = visibility
            view.findViewById<EditText>(R.id.registerCity).visibility = visibility
        }

        view?.findViewById<Button>(R.id.registerButton)?.setOnClickListener {
            val seekJob = seekJobCheckBox?.isChecked
            registerListener.onSimpleRegister(
                view.findViewById<EditText>(R.id.registerUsername).text.toString(),
                view.findViewById<EditText>(R.id.registerEmail).text.toString(),
                view.findViewById<EditText>(R.id.registerPassword).text.toString(),
                if (seekJob == true) view.findViewById<EditText>(R.id.registerSurname).text.toString() else null,
                if (seekJob == true) view.findViewById<EditText>(R.id.registerCity).text.toString() else null,
                false
            )
        }

        view?.findViewById<Button>(R.id.premiumRegisterButton)?.setOnClickListener {
            registerListener.onPremiumRegister()
        }
    }

}