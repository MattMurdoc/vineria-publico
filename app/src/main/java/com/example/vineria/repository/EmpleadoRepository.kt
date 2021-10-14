package com.example.vineria.repository

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.example.vineria.modelos.Empleado

class EmpleadoRepository(private val context: Context) {

    fun setTelefonoAndSave(nombreApellido: String?, id: Int) {
        val myprefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = myprefs.edit()
        editor.putString("nombreApellido", nombreApellido)
        editor.putInt("id", id)
        editor.apply()
    }

    fun getPerfilInstance() : Empleado {
        val myprefs = PreferenceManager.getDefaultSharedPreferences(context)
        val empleado = Empleado(nombre = myprefs.getString("nombreApellido", "")!!, codigo = myprefs.getInt("id", -1))
        Log.v(TAG, "Se ha obtenido el perfil desde PreferenceManager.")
        return empleado;
    }

    companion object {
        val TAG = "PerfilRepository"
    }
}
