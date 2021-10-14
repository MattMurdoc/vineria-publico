package com.example.vineria.ui.nuevopago

import android.app.Application
import com.example.vineria.clases.ApplicationViewModel
import com.example.vineria.clases.Tasks
import com.example.vineria.modelos.Empleado
import com.example.vineria.modelos.TIPO_FAC
import com.example.vineria.repository.EmpleadoRepository
import com.example.vineria.repository.PagoRepository
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import java.util.*

class NuevoPagoViewModel(app: Application) : ApplicationViewModel(app) {
    private val pagoRepository = PagoRepository(app)
    private val empleadoRepository = EmpleadoRepository(app)

    var cliente: Int = -1
    var oldCliente: Int = -1
    lateinit var descCliente: String
    var factura: Int = -1
    var oldFactura: Int = -1
    lateinit var descFactura: String
    var tipoFac: TIPO_FAC = TIPO_FAC.NONE
    var importe: BigDecimal = BigDecimal.ZERO
    var deuda: BigDecimal = BigDecimal.ZERO
    var restante: BigDecimal = BigDecimal.ZERO
    var banco: String? = null
    var nroCheque: String? = null
    var titularCheque: String? = null
    var vencCheque: Date? = null

    var btnClienteEnabled: Boolean = true
    var btnFacturaEnabled: Boolean = false
    var importeEnabled: Boolean = false
    var bancoEnabled: Boolean = false
    var nroChequeEnabled: Boolean = false
    var titularEnabled = false
    var vencimientoEnabled = false
    var formaPagoEnabled = false
    var canCreate: Boolean = true
    lateinit var snackbar: Snackbar
    var finish: Boolean = false
    lateinit var formaPago: String

    fun crearPago() = launchTask(Tasks.SavePago) {
        canCreate = false
        notifyChange()
        if (cliente < 0) {
            sendError("Debe seleccionar un cliente")
        }

        if (factura < 0 || tipoFac.equals(TIPO_FAC.NONE)) {
            sendError("Debe seleccionar una factura")
        }

        if (importe.compareTo(BigDecimal.ZERO) <= 0) {
            sendError("Debe colocar un importe")
        }

        if (importe.compareTo(deuda) > 0) {
            sendError("La deuda de ${if (tipoFac.equals(TIPO_FAC.FACTURA)) "la factura" else "el presupuesto"} es: ${deuda}, el importe no puede ser mayor")
        }

        val empleado: Empleado = empleadoRepository.getPerfilInstance()
        if (formaPago.equals("Cheque") && (banco.isNullOrBlank() || nroCheque.isNullOrBlank() ||
                    titularCheque.isNullOrBlank() || vencCheque == null)
        ) {
            sendError("Todos los datos del cheque son requeridos")
        }

        if(formaPago.equals("Efectivo")) {
            banco = null
            nroCheque = null
            titularCheque = null
            vencCheque = null
        }

        pagoRepository.crearPago(
            factura,
            tipoFac,
            importe,
            empleado.codigo,
            banco,
            nroCheque,
            titularCheque,
            vencCheque
        )

        snackbar.dismiss()
        sendToastToActivity("Pago creado con éxito")
        finish = true
        notifyChange()
    }

    private fun sendError(mensaje: String) {
        sendToastToActivity(mensaje)
        snackbar.dismiss()
        canCreate = true
        notifyChange()
        throw Exception()
    }

}