package com.example.sketchhub.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sketchhub.R

class Premium : Fragment() {

    private lateinit var onPurchasePremiumAlertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater?.inflate(R.layout.premium, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        onPurchasePremiumAlertDialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.premiumPurchaseSuccess))
            .setMessage(R.string.premiumPurchased)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.findViewById<Button>(R.id.monthlyPlan)?.setOnClickListener {
            onPurchasePremiumAlertDialog.show()
        }

        view?.findViewById<Button>(R.id.yearlyPlan)?.setOnClickListener {
            onPurchasePremiumAlertDialog.show()
        }

    }

}