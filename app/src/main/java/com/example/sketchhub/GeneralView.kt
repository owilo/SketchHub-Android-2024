package com.example.sketchhub

import android.app.Activity
import android.app.FragmentManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.sketchhub.fragments.Authentification
import com.example.sketchhub.fragments.Browse
import com.example.sketchhub.fragments.Gallery
import com.example.sketchhub.fragments.History
import com.example.sketchhub.fragments.Import
import com.example.sketchhub.fragments.Premium
import com.example.sketchhub.fragments.Profile
import com.example.sketchhub.fragments.Register
import com.example.sketchhub.fragments.UserAvatar
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.plugins.websocket.ws
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.runBlocking

interface PageChangeListener {
    fun onPageChanged(title: String)

}

interface SearchFilterListener {

    fun filterSearch(title: String)

}

class GeneralView : Activity(), SearchFilterListener, Gallery.GalleryListener, Browse.BrowseListener, History.HistoryListener, Import.ImportListener, UserAvatar.UserAvatarListener, Profile.ProfileListener, Authentification.AuthentificationListener, Register.RegisterListener, PageChangeListener {

    private lateinit var authentificationService: AuthClient

    private lateinit var clientDataGateway: ClientDataGateway

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.general)

        startService(Intent(this, AuthClient::class.java))
        authentificationService = AuthClient()

        startService(Intent(this, ClientDataGateway::class.java))
        clientDataGateway = ClientDataGateway()

        val fragment = when (intent.getStringExtra("fragmentId")) {
            "gallery" -> Gallery()
            "browse" -> Browse()
            "history" -> History()
            "import" -> Import()
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

    override fun onCanvasCreate(title: String, description: String, visibility: Boolean) {
        runBlocking { clientDataGateway.createCanvas(title, description, visibility) }
        startActivity(Intent(this, DrawView::class.java))
    }

    override fun getGalleryData(): ClientDataGateway.GalleryData {
        return runBlocking { clientDataGateway.getGalleryData() }
    }

    override fun launchCanvas(drawingId: Int) {
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

    override fun onAuthenticate(username: String, password: String) {
        runBlocking {
            if (authentificationService.authenticate(
                    username,
                    password
            )) {
                fragmentManager.beginTransaction()
                    .replace(R.id.content, Gallery())
                    .commit()
            }
        }
    }

    override fun onNoAccountClick() {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, Register())
            .commit()
    }

    override fun onSimpleRegister(username: String, email: String, password: String, surname: String?, city: String?, premium: Boolean) {
        runBlocking {
            if (authentificationService.register(
                    username,
                    email,
                    password,
                    surname,
                    city,
                    premium
            )) {
                fragmentManager.beginTransaction()
                    .replace(R.id.content, Gallery())
                    .commit()
            }
        }
    }

    override fun onPremiumRegister() {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, Premium())
            .commit()
    }

    override fun onPageChanged(title: String) {
        findViewById<TextView>(R.id.pageTitle).text = title
    }

    override fun filterSearch(title: String) {
        val fragment = fragmentManager.findFragmentById(R.id.content)
        if (fragment is SearchFilterListener) {
            fragment.filterSearch(title)
        }
    }

}