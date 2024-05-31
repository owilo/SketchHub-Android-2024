package com.example.sketchhub.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.sketchhub.R
import com.example.sketchhub.SearchFilterListener

class Searchbar : Fragment() {



    private lateinit var advancedSearchDialog: AlertDialog

    private lateinit var searchFilterListener: SearchFilterListener

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
        searchFilterListener = context as SearchFilterListener

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

        view?.findViewById<View>(R.id.performSearch)?.setOnClickListener {
            searchFilterListener.filterSearch(view.findViewById<EditText>(R.id.searchBarInput).text.toString().lowercase())
        }
    }

}