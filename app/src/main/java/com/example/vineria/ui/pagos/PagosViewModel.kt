package com.example.vineria.ui.pagos

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.vineria.clases.ApplicationViewModel
import com.example.vineria.clases.Tasks
import com.example.vineria.modelos.dto.PagoDTO
import com.example.vineria.persistance.database.AppDatabase
import com.example.vineria.repository.PagoRepository

class PagosViewModel(app: Application) : ApplicationViewModel(app) {
    private val pagoRepository = PagoRepository(app)

    var canSave: Boolean = true

    var pagos: MutableLiveData<MutableList<PagoDTO>>? = MutableLiveData()

    fun getItems() = launchTask(Tasks.GetPagos) {
        pagos?.postValue(pagoRepository.getPagos())
    }

    fun enviarTodos() = launchTask(Tasks.SendPagos) {
        enviarPagos()
    }

    private fun enviarPagos() {
        change { canSave = false }

        var envioFacturasOk = pagoRepository.enviarFacturas()
        var envioPresupuestosOk = pagoRepository.enviarPresupuestos()

        if(!envioFacturasOk || !envioPresupuestosOk) {
            sendToastToActivity("Algunos pagos no pudieron ser enviados")
        } else if (envioFacturasOk && envioPresupuestosOk){
            sendToastToActivity("Pagos enviados correctamente")
        }
        getItems()
        change { canSave = true }
    }





}