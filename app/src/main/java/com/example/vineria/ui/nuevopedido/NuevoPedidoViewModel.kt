package com.example.vineria.ui.nuevopedido

import android.app.Application
import com.example.vineria.clases.ApplicationViewModel
import com.example.vineria.clases.Tasks
import com.example.vineria.modelos.Empleado
import com.example.vineria.modelos.dto.ArticuloItemDTO
import com.example.vineria.repository.EmpleadoRepository
import com.example.vineria.repository.PagoRepository
import com.example.vineria.repository.PedidoRepository
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal

class NuevoPedidoViewModel(app: Application) : ApplicationViewModel(app) {
    private val pedidoRepository = PedidoRepository(app)
    private val empleadoRepository = EmpleadoRepository(app)

    var cliente: Int? = null
    var oldDescCliente: String = ""
    var descCliente: String = ""
    var tipoIva: Int? = -1
    var tipoFac: String = ""
    var importe: BigDecimal = BigDecimal.ZERO
    var total: BigDecimal = BigDecimal.ZERO
    var productos: Int = 0
    var canCreate: Boolean = true
    lateinit var snackbar: Snackbar
    var observacion: String = ""
    var finish: Boolean = false

    var btnClienteEnabled: Boolean = true
    var btnFacturaEnabled: Boolean = false

    fun validarPedido(articulos: MutableList<ArticuloItemDTO>): Boolean {
        if(descCliente.isEmpty()) {
            sendToastToActivity("Debe seleccionar un cliente")
            return false
        }

        if(tipoFac.isEmpty()) {
            sendToastToActivity("Debe seleccionar un tipo de factura")
            return false
        }

        if(articulos.isEmpty()) {
            sendToastToActivity("Debe agregar artículos")
            return false
        }

        return true
    }

    fun crearPedido(articulos: MutableList<ArticuloItemDTO>) = launchTask(Tasks.SavePedido) {
        canCreate = false
        notifyChange()

        val empleado: Empleado = empleadoRepository.getPerfilInstance()
        pedidoRepository.crearPedido(cliente, descCliente, empleado.codigo, tipoFac, observacion, articulos)

        snackbar.dismiss()
        sendToastToActivity("Pedido creado con éxito")
        finish = true
        notifyChange()
    }

}