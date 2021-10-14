package com.example.vineria.repository

import android.content.Context
import com.example.vineria.modelos.Cliente
import com.example.vineria.persistance.database.AppDatabase

class ClienteRepository(private val context: Context) {

    fun getClientesByDescripcion(offset: Int, descripcion: String): MutableList<Cliente> {
        return AppDatabase.getAppDatabase(context).clienteDAO().getClientesByDescripcion(offset, descripcion)
    }

    companion object {
        val TAG = "Cliente Repository"
    }
}