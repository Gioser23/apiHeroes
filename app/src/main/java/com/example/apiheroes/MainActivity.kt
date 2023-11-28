package com.example.apiheroes

import AdapterHeroes
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apiheroes.databinding.ActivityMainBinding
import com.example.superheroapp.modelo.conexion.apiconectar
import com.example.superheroapp.modelo.interfaces.Heroes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),
    AdapterHeroes.OnDeleteClickListener,
    AdapterHeroes.OnAddClickListener {
    private lateinit var binding: ActivityMainBinding
    private var contadorId: Long = 1 // Contador para el ID, inicializado en 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        cargarHeroes()
    }
    private fun setupRecyclerView() {
        val adapter = AdapterHeroes(mutableListOf(), this, this) { heroe, action ->
            if (action == AdapterHeroes.ACTION.DELETE) {
                eliminarHeroe(heroe.id.toString())
            }
        }
        binding.recicler.adapter = adapter
    }
    private fun cargarHeroes() {
        apiconectar.apiService.obtenerHeroes().enqueue(object : Callback<List<Heroes>> {
            override fun onResponse(call: Call<List<Heroes>>, response: Response<List<Heroes>>) {
                if (response.isSuccessful) {
                    val heroes = response.body()?.toMutableList()
                    val adapter = binding.recicler.adapter as? AdapterHeroes
                    adapter?.actualizarLista(heroes)
                } else {
                    mostrarMensaje("Error al cargar héroes")
                }
            }
            override fun onFailure(call: Call<List<Heroes>>, t: Throwable) {
                mostrarMensaje("Error de red al cargar héroes")
            }
        })
    }
    override fun onDeleteClick(heroId: String) {
        eliminarHeroe(heroId)
    }
    override fun onAddButtonClick() {
        agregarHeroeBaseDatos()
    }
    private fun agregarHeroeBaseDatos() {
        val nuevoHeroe = generarNuevoHeroe()
        apiconectar.apiService.guardarHeroe(nuevoHeroe).enqueue(object : Callback<Heroes> {
            override fun onResponse(call: Call<Heroes>, response: Response<Heroes>) {
                if (response.isSuccessful) {
                    cargarHeroes()
                    mostrarMensaje("Héroe añadido exitosamente")
                } else {
                    mostrarMensaje("Error al añadir el héroe")
                }
            }

            override fun onFailure(call: Call<Heroes>, t: Throwable) {
                mostrarMensaje("Error de red al añadir el héroe")
            }
        })
    }
    private fun eliminarHeroe(id: String) {
        apiconectar.apiService.borrarheroes(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    val adapter = binding.recicler.adapter as? AdapterHeroes
                    adapter?.eliminarHeroePorId(id)
                    mostrarMensaje("Héroe eliminado exitosamente")
                } else {
                    mostrarMensaje("Error al eliminar el héroe")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                mostrarMensaje("Error de red al eliminar el héroe")
            }
        })
    }
    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
    private fun generarNuevoHeroe(): Heroes {
        val id = contadorId
        contadorId++ //INGREMETADOR
        val nombreHeroe = "Heroe $id"
        val anoInicio = "2023"
        val planetaId = "Planeta $id"
        val password = "password$id"
        val nivel = (1..10).random()
        return Heroes(
            id,
            nombreHeroe,
            anoInicio,
            planetaId,
            password,
            nivel
        )
    }
}




