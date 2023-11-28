package com.example.superheroapp.modelo.interfaces

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface apiheroes {
    @GET("heroes")
    fun obtenerHeroes(): Call<List<Heroes>>

    @POST("heroes")
    fun guardarHeroe(@Body heroes: Heroes): Call<Heroes>

    @DELETE("heroes/{id}")
    fun borrarheroes(@Path("id") id: String): Call<Void>

    @PUT("heroes/{id}")
    fun editarHeroe(@Path("id") id: String, @Body heroes: Heroes): Call<Heroes>
}

data class Heroes(
    val id: Long,
    val nombre_heroe: String,
    val ano_inicio: String,
    val planeta_id: String,
    val password: String,
    val nivel: Int
)
