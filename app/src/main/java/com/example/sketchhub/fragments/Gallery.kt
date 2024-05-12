package com.example.sketchhub.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sketchhub.DrawView
import com.example.sketchhub.GeneralView
import com.example.sketchhub.R

class Gallery : Fragment() {

    interface GalleryListener {

        fun onCanvasCreate()

    }

    private lateinit var galleryListener: GalleryListener

    private lateinit var createCanvasDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater?.inflate(R.layout.gallery, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        galleryListener = context as GalleryListener
        createCanvasDialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.createCanvas))
            .setView(R.layout.create_dialog)
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                galleryListener.onCanvasCreate()
            }
            .create()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentManager.beginTransaction()
            .add(R.id.searchBar, Searchbar())
            .commit()

        view?.findViewById<View>(R.id.newCanvas)?.setOnClickListener {
            createCanvasDialog.show()
        }
    }

}