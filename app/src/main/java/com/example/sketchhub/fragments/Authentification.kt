package com.example.sketchhub.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.sketchhub.GeneralView
import com.example.sketchhub.PageChangeListener
import com.example.sketchhub.R

class Authentification : Fragment() {

    interface AuthentificationListener {
        fun onAuthenticate(username: String, password: String)
        fun onNoAccountClick()
    }

    private lateinit var forgotPasswordAlertDialog: AlertDialog

    private lateinit var authentificationListener: AuthentificationListener

    private var pageChangeListener: PageChangeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater?.inflate(R.layout.authentification, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        authentificationListener = context as AuthentificationListener
        pageChangeListener = context as PageChangeListener

        forgotPasswordAlertDialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.passwordForgotten))
            .setView(R.layout.forgot_password_dialog)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    override fun onDetach() {
        super.onDetach()

        pageChangeListener = null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageChangeListener?.onPageChanged(getString(R.string.authentification))

        view?.findViewById<Button>(R.id.authenticate)?.setOnClickListener {
            authentificationListener.onAuthenticate(
                view.findViewById<EditText>(R.id.authenticateUsername).text.toString(),
                view.findViewById<EditText>(R.id.authenticatePassword).text.toString(),
            )
        }

        view?.findViewById<Button>(R.id.noAccount)?.setOnClickListener {
            authentificationListener.onNoAccountClick()
        }

        view?.findViewById<Button>(R.id.forgotPassword)?.setOnClickListener {
            forgotPasswordAlertDialog.show()
        }
    }

}