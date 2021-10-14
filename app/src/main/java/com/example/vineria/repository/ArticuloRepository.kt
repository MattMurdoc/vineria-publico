package com.example.vineria.repository

import android.content.Context
import com.example.vineria.modelos.Articulo
import com.example.vineria.persistance.database.AppDatabase

class ArticuloRepository(private val context: Context) {

    fun getArticulosWithOffset(offset: Int): MutableList<Articulo>? {
        val articulos = getAllArticulos()
        var articulosWithOffset: MutableList<Articulo>? = null
        if (articulos > offset ){
            articulosWithOffset = AppDatabase.getAppDatabase(context).articuloDAO().selectWithOffset(offset)
        }
        return articulosWithOffset
    }

    fun getAllArticulos(): Int {
        val articulos = AppDatabase.getAppDatabase(context).articuloDAO().count()
        return articulos
    }

    fun getArticulosByDescripcion(offset: Int, descripcion: String): MutableList<Articulo> {
        return AppDatabase.getAppDatabase(context).articuloDAO().getArticulosByDescripcion(offset, descripcion)
    }

    companion object {
        val TAG = "Articulo Repository"
    }
}