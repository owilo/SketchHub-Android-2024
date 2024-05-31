package com.example.sketchhub

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DrawingManager : Service() {

    private val url = "10.0.2.2"
    private val port = 8080
    private val client = HttpClient {
        install(WebSockets)
    }
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            while (isActive) {
                sendHelloWorld()
                delay(1000)
            }
        }
        return START_STICKY
    }

    private suspend fun sendHelloWorld() {
        try {
            client.webSocket(method = HttpMethod.Get, host = url, port = port, path = "/draw") {
                try {
                    send(Frame.Text("Hello world"))
                    val message = incoming.receive()
                    if (message is Frame.Text) {
                        println("Response: ${message.readText()}")
                    }
                } catch (e: Throwable) {
                    println("Connection failed: ${e.message}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        client.close()
    }

}