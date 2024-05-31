package com.example.sketchhub

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

class AuthClient : Service() {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    @Serializable
    data class UserRegistration(
        val username: String,
        val email: String,
        val password: String,
        val surname: String?,
        val city: String?,
        val premium: Boolean
    )

    suspend fun register(username: String, email: String, password: String, surname: String?, city: String?, premium: Boolean) : Boolean {
        val userRegistration = UserRegistration(username, email, password, surname, city, premium)

        return try {
            val response: HttpResponse = client.post("http://10.0.2.2:8080/register") {
                contentType(ContentType.Application.Json)
                setBody(userRegistration)
            }
            response.body<Boolean>()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @Serializable
    data class UserAuthentication(
        val username: String,
        val password: String
    )

    suspend fun authenticate(username: String, password: String) : Boolean {
        val userAuthentication = UserAuthentication(username, password)

        return try {
            val response: HttpResponse = client.post("http://10.0.2.2:8080/authenticate") {
                contentType(ContentType.Application.Json)
                setBody(userAuthentication)
            }
            response.body<Boolean>()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        client.close()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

}