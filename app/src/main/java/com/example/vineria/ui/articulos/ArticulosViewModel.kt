package com.example.vineria.ui.articulos

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import com.example.vineria.clases.ApplicationViewModel
import com.example.vineria.clases.Tasks
import com.example.vineria.modelos.Articulo
import com.example.vineria.persistance.database.AppDatabase
import com.example.vineria.repository.ArticuloRepository

class ArticulosViewModel(app: Application) : ApplicationViewModel(app),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private val articuloRepository = ArticuloRepository(app)

    var nombre = ""
    var id: Int = -1
    var canSave: Boolean = false
    var primeraVez: Boolean = false

    var listArticulo: MutableLiveData<Pair<MutableList<Articulo>, Boolean>>? = MutableLiveData()

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

    }

    fun getItems(offset: Int, text: String) {

        var articulos = articuloRepository.getArticulosByDescripcion(offset, text)
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
        listArticulo?.postValue(Pair(articulos, clean))
    }

    companion object {
        private val TAG = "ArticulosViewModel"
    }
}