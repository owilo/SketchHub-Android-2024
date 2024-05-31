package com.example.sketchhub.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import com.example.sketchhub.PageChangeListener
import com.example.sketchhub.R
import com.example.sketchhub.SearchFilterListener

class History : Fragment(), SearchFilterListener {

    interface HistoryListener {

        fun launchCanvas(drawingId: Int)

    }

    private lateinit var historyListener: HistoryListener

    private var pageChangeListener: PageChangeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater?.inflate(R.layout.grid, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        historyListener = context as HistoryListener
        pageChangeListener = context as PageChangeListener
    }

    override fun onDetach() {
        super.onDetach()

        pageChangeListener = null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pageChangeListener?.onPageChanged(getString(R.string.page_history))

        fragmentManager.beginTransaction()
            .add(R.id.searchBar, Searchbar())
            .commit()

        val layout = view?.findViewById<GridLayout>(R.id.drawingsList)

        for (i in 0 until layout!!.childCount) {
            val childView = layout.getChildAt(i)
            childView.visibility = View.VISIBLE

            if (childView is TextView) {
                childView.setOnClickListener {
                    historyListener.launchCanvas(i)
                }
            }
        }
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