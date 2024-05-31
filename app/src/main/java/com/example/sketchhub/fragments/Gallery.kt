package com.example.sketchhub.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Switch
import android.widget.TextView
import com.example.sketchhub.ClientDataGateway
import com.example.sketchhub.PageChangeListener
import com.example.sketchhub.R
import com.example.sketchhub.SearchFilterListener

class Gallery : Fragment(), SearchFilterListener {

    interface GalleryListener {

        fun onCanvasCreate(title: String, description: String, visibility: Boolean)

        fun getGalleryData(): ClientDataGateway.GalleryData

        fun launchCanvas(drawingId: Int)

    }

    private lateinit var galleryListener: GalleryListener

    private lateinit var createCanvasDialog: AlertDialog

    private var pageChangeListener: PageChangeListener? = null

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
        pageChangeListener = context as PageChangeListener

        createCanvasDialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.createCanvas))
            .setView(R.layout.create_dialog)
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                val alertDialog = dialog as AlertDialog
                galleryListener.onCanvasCreate(
                    alertDialog.findViewById<EditText>(R.id.canvasTitle).text.toString(),
                    alertDialog.findViewById<EditText>(R.id.canvasDescription).text.toString(),
                    alertDialog.findViewById<Switch>(R.id.canvasVisibility).isActivated
                )
            }
            .create()
    }

    override fun onDetach() {
        super.onDetach()

        pageChangeListener = null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pageChangeListener?.onPageChanged(getString(R.string.page_gallery))

        fragmentManager.beginTransaction()
            .add(R.id.searchBar, Searchbar())
            .commit()

        view?.findViewById<View>(R.id.newCanvas)?.setOnClickListener {
            createCanvasDialog.show()
        }

        val layout = view?.findViewById<GridLayout>(R.id.drawingsList)

        for (i in 0 until layout!!.childCount) {
            val childView = layout.getChildAt(i)
            childView.visibility = View.VISIBLE

            if (childView is TextView) {
                childView.setOnClickListener {
                    galleryListener.launchCanvas(i)
                }
            }
        }

        /*val parentLayout = view?.findViewById<GridLayout>(R.id.drawingsList)

        val galleryData = galleryListener.getGalleryData().canvasData

        val layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        layoutParams.setMargins(7, 7, 7, 7)

        for (drawingData in galleryData) {
            val textView = TextView(this.context)
            textView.layoutParams = layoutParams
            textView.setPadding(10, 4, 10, 4)
            textView.setBackgroundResource(R.drawable.canvas_thumbnail)
            textView.text = drawingData.title
            textView.id = drawingData.drawingId
            textView.setOnClickListener {
                galleryListener.launchCanvas(drawingData.drawingId)
            }
            parentLayout?.addView(textView)
        }*/
    }

    override fun filterSearch(title: String) {
        val layout = view?.findViewById<GridLayout>(R.id.drawingsList)

        for (i in 0 until layout!!.childCount) {
            val childView = layout.getChildAt(i)

            if (childView is TextView) {
                childView.visibility = if (title in childView.text.toString().lowercase()) View.VISIBLE else View.GONE
            }
        }
    }

}