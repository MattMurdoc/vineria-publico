package com.example.vineria.ui.nuevopago.factura

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.vineria.clases.ApplicationViewModel
import com.example.vineria.modelos.Cliente
import com.example.vineria.modelos.dto.ImpagoDTO
import com.example.vineria.persistance.database.AppDatabase
import com.example.vineria.repository.PagoRepository

class SeleccionarFacturaViewModel(app: Application) : ApplicationViewModel(app) {
    private val pagoRepository = PagoRepository(app)

    var impagos: MutableLiveData<MutableList<ImpagoDTO>>? = MutableLiveData()

    fun getItems(codCliente: Int){
        impagos?.postValue(pagoRepository.getFacturasPresupuestos(codCliente))
    }
}