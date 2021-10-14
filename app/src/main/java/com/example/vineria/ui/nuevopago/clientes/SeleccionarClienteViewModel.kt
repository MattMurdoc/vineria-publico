package com.example.vineria.ui.nuevopago.clientes

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.vineria.clases.ApplicationViewModel
import com.example.vineria.modelos.Articulo
import com.example.vineria.modelos.Cliente
import com.example.vineria.persistance.database.AppDatabase
import com.example.vineria.repository.ArticuloRepository
import com.example.vineria.repository.ClienteRepository

class SeleccionarClienteViewModel(app: Application) : ApplicationViewModel(app),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private val clienteRepository = ClienteRepository(app)

    var nombre = ""
    var id: Int = -1
    var canSave: Boolean = false
    var primeraVez: Boolean = false

    var listCliente: MutableLiveData<Pair<MutableList<Cliente>, Boolean>>? = MutableLiveData()

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

    }

    fun getItems(offset: Int, text: String) {

        var clientes = clienteRepository.getClientesByDescripcion(offset, text)
        var clean = false
        if (text != "") {
            clean = true
            change {
                primeraVez = true
            }
        } else if (primeraVez) {
            clean = true
            change {
                primeraVez = false
            }
        }
        listCliente?.postValue(Pair(clientes, clean))
    }

    companion object {
        private val TAG = "ClientesViewModel"
    }
}