package com.example.botanicalexpedition.data.api

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

@Serializable
data class PlantResponse(
    val id: Int,
    val title: String,
    val body: String
)

@Serializable
data class PlantRequest(
    val title: String,
    val body: String
)

interface PlantApiService {
    @GET("posts")
    suspend fun getPlants(): List<PlantResponse>

    @GET("posts/{id}")
    suspend fun getPlant(@Path("id") id: Int): PlantResponse

    @POST("posts")
    suspend fun createPlant(@Body plantRequest: PlantRequest): PlantResponse
}
