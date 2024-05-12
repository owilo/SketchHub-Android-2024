package com.example.sketchhub

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.example.sketchhub.fragments.Authentification
import com.example.sketchhub.fragments.Gallery
import com.example.sketchhub.fragments.Premium
import com.example.sketchhub.fragments.Profile
import com.example.sketchhub.fragments.Register
import com.example.sketchhub.fragments.UserAvatar


class GeneralView : Activity(), Gallery.GalleryListener, UserAvatar.UserAvatarListener, Profile.ProfileListener, Authentification.AuthentificationListener, Register.RegisterListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.general)

        val fragment = when (intent.getStringExtra("fragmentId")) {
            "gallery" -> Gallery()
            "browse" -> Gallery()
            "history" -> Gallery()
            "import" -> Gallery()
            "profile" -> Profile()
            else -> Authentification()
        }

        findViewById<View>(R.id.returnButton).setOnClickListener {
            onBackPressed()
        }

        fragmentManager.beginTransaction()
            .add(R.id.content, fragment)
            .commit()

        fragmentManager.beginTransaction()
            .add(R.id.profile, UserAvatar())
            .commit()
    }

    override fun onCanvasCreate() {
        startActivity(Intent(this, DrawView::class.java))
    }

    override fun onClickProfile() {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, Profile())
            .commit()
    }

    override fun onPremiumUpgradeClick() {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, Premium())
            .commit()
    }

    override fun onAuthenticate() {
        fragmentManager.beginTransaction()
            .replace(R.id.content, Gallery())
            .commit()
    }

    override fun onNoAccountClick() {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, Register())
            .commit()
    }

    override fun onSimpleRegister() {
        fragmentManager.beginTransaction()
            .replace(R.id.content, Gallery())
            .commit()
    }

    override fun onPremiumRegister() {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, Premium())
            .commit()
    }

}