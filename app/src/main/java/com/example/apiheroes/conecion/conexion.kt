package com.example.superheroapp.modelo.conexion

import com.example.superheroapp.modelo.interfaces.apiheroes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object apiconectar {
    var builder = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var apiService = builder.create(apiheroes::class.java)
}