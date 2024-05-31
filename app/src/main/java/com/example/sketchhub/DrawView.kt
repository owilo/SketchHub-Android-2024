package com.example.sketchhub

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sketchhub.fragments.UserAvatar


class DrawView : Activity(), UserAvatar.UserAvatarListener {

    private lateinit var editCanvasDialog: AlertDialog
    private lateinit var collaborateDialog: AlertDialog
    private lateinit var exportDialog: AlertDialog

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawing)

        startService(Intent(this, DrawingManager::class.java))

        fragmentManager.beginTransaction()
            .add(R.id.profile, UserAvatar())
            .commit()

        val menuButton = findViewById<View>(R.id.menuButton)
        val sideMenu = findViewById<LinearLayout>(R.id.sideMenu)
        val overlay = findViewById<View>(R.id.overlay)

        menuButton.setOnClickListener {
            sideMenu.startAnimation(
                AnimationUtils.loadAnimation(
                    this@DrawView,
                    R.anim.slide_in
                )
            )
            sideMenu.visibility = View.VISIBLE

            overlay.startAnimation(
                AnimationUtils.loadAnimation(
                    this@DrawView,
                    android.R.anim.fade_in
                )
            )
            overlay.visibility = View.VISIBLE
        }

        sideMenu.setOnTouchListener { _, _ -> true }

        overlay.setOnClickListener {
            sideMenu.startAnimation(
                AnimationUtils.loadAnimation(
                    this@DrawView,
                    R.anim.slide_out
                )
            )
            sideMenu.visibility = View.GONE
            overlay.startAnimation(
                AnimationUtils.loadAnimation(
                    this@DrawView,
                    android.R.anim.fade_out
                )
            )
            overlay.visibility = View.GONE
        }

        editCanvasDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.editCanvas))
            .setView(R.layout.create_dialog)
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok, null)
            .create()

        collaborateDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.collaborate))
            .setView(R.layout.collaborate_dialog)
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok, null)
            .create()

        exportDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.exportCanvas))
            .setView(R.layout.export_dialog)
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok, null)
            .create()

        findViewById<TextView>(R.id.editButton).setOnClickListener {
            editCanvasDialog.show()
        }

        findViewById<TextView>(R.id.galleryButton).setOnClickListener {
            goToPage("gallery")
        }

        findViewById<TextView>(R.id.browseButton).setOnClickListener {
            goToPage("browse")
        }

        findViewById<TextView>(R.id.collaborateButton).setOnClickListener {
            collaborateDialog.show()
        }

        findViewById<TextView>(R.id.historyButton).setOnClickListener {
            goToPage("history")
        }

        findViewById<TextView>(R.id.exportButton).setOnClickListener {
            exportDialog.show()
        }

        findViewById<TextView>(R.id.importButton).setOnClickListener {
            goToPage("import")
        }

    }

    override fun onClickProfile() {
        goToPage("profile")
    }

    private fun goToPage(fragment : String) {
        val intent = Intent(this, GeneralView::class.java)
        intent.putExtra("fragmentId", fragment)
        startActivity(intent)
    }
}