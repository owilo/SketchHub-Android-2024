package com.example.sketchhub

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

class ClientDataGateway {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    @Serializable
    data class GalleryData(
        val canvasData: List<CanvasData>
    )

    @Serializable
    data class CanvasData(
        val drawingId: Int,
        val title: String,
        val historyId: Int,
        val data: ByteArray
    )

    @Serializable
    data class UpdateDrawingData (
        val drawingId: Int?,
        val title: String,
        val description: String,
        val visibility: Boolean
    )

    suspend fun getGalleryData(): GalleryData {
        return try {
            client.get("http://10.0.2.2:8080/getGallery") {
                contentType(ContentType.Application.Json)
            }.body<GalleryData>()
        } catch (e: Exception) {
            e.printStackTrace()
            GalleryData(listOf())
        }
    }

    suspend fun createCanvas(title: String, description: String, visibility: Boolean): CanvasData? {
        val createCanvasData = UpdateDrawingData(null, title, description, visibility)

        return try {
            client.post("http://10.0.2.2:8080/createCanvas") {
                contentType(ContentType.Application.Json)
                setBody(createCanvasData)
            }.body<CanvasData>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun editCanvas(drawingId: Int, title: String, description: String, visibility: Boolean) {
        val updateCanvasData = UpdateDrawingData(drawingId, title, description, visibility)

        try {
            client.post("http://10.0.2.2:8080/editCanvas") {
                contentType(ContentType.Application.Json)
                setBody(updateCanvasData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}