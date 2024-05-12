package com.example.sketchhub.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sketchhub.R

class Searchbar : Fragment() {

    private lateinit var advancedSearchDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater?.inflate(R.layout.searchbar, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        advancedSearchDialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.advancedSearch))
            .setView(R.layout.advanced_search_dialog)
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.findViewById<View>(R.id.advancedSearch)?.setOnClickListener {
            advancedSearchDialog.show()
        }
    }

}