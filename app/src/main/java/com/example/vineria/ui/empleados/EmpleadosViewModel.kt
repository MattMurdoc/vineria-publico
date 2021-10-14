package com.example.vineria.ui.empleados

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import com.example.vineria.clases.ApplicationViewModel
import com.example.vineria.clases.Tasks
import com.example.vineria.modelos.Empleado
import com.example.vineria.persistance.database.AppDatabase
import com.example.vineria.repository.EmpleadoRepository

class EmpleadosViewModel(app: Application) : ApplicationViewModel(app), SharedPreferences.OnSharedPreferenceChangeListener {
    private val perfilRepository = EmpleadoRepository(app)

    var nombreYApellido = ""
    var id: Int = -1
    var canSave: Boolean = false

    var listUsuario: MutableLiveData<MutableList<Empleado>>? = MutableLiveData()

    init {
        PreferenceManager.getDefaultSharedPreferences(app).registerOnSharedPreferenceChangeListener(this);
        update(perfilRepository.getPerfilInstance())
    }

    fun save() = launchTask(Tasks.SavingProfile) {
        perfilRepository.setTelefonoAndSave(nombreYApellido, id)

        getItems()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        update(perfilRepository.getPerfilInstance())
    }

    override fun onCleared() {
        PreferenceManager.getDefaultSharedPreferences(app).unregisterOnSharedPreferenceChangeListener(this)
        super.onCleared()
    }

    fun update(perfil: Empleado) {
        id = perfil.codigo?: -1
        nombreYApellido = perfil.nombre?: ""
        notifyChange()
    }

    fun getItems(){
        val users = AppDatabase.getAppDatabase(app.applicationContext).empleadoDAO().getAll()
        val currentUser = perfilRepository.getPerfilInstance()

        for (usuario in users){
            if(usuario.codigo == currentUser.codigo){
                usuario.seleccionado = true
            }
        }

        listUsuario?.postValue(users)
    }

    companion object {
        private val TAG = "EmpleadosViewModel"
    }
}