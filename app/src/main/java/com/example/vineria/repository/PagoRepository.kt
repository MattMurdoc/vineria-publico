package com.example.vineria.repository

import android.content.Context
import com.example.vineria.modelos.*
import com.example.vineria.modelos.dto.ImpagoDTO
import com.example.vineria.modelos.dto.PagoDTO
import com.example.vineria.persistance.database.AppDatabase
import com.example.vineria.persistance.sqlserver.DbConnection
import com.example.vineria.persistance.sqlserver.Queries
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class PagoRepository(private val context: Context?) {

    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
    val simpleDateFormatSQL = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    fun getFacturasPresupuestos(codigo: Int): MutableList<ImpagoDTO> {
        val facturaPresupuestos: MutableList<ImpagoDTO> = mutableListOf()
        val impagos: Impagos = getImpagos(codigo)

        impagos.facturasPorPagar.forEach { item ->
            val impago = ImpagoDTO(id = item.first.codigo,
                nroFac = item.first.numero.toString(),
                fecha = simpleDateFormat.format(item.first.fecha),
                fechaDate = item.first.fecha,
                tipoFac = TIPO_FAC.FACTURA,
                descTipoFac = "Factura " + item.first.tipo,
                total = item.first.total!!,
                restante = item.second
            )
            facturaPresupuestos.add(impago)
        }

        impagos.presupuestosPorPagar.forEach { item ->
            val impago = ImpagoDTO(id = item.first.codigo,
                nroFac = item.first.codigo.toString(),
                fecha = simpleDateFormat.format(item.first.fecha),
                fechaDate = item.first.fecha,
                tipoFac = TIPO_FAC.PRESUPUESTO,
                descTipoFac =  "Presupuesto",
                total = item.first.total!!,
                restante = item.second
            )
            facturaPresupuestos.add(impago)
        }

        facturaPresupuestos.sortBy { i -> i.fechaDate }
        return facturaPresupuestos
    }

    fun getImpagos(codigo: Int): Impagos {
        val facturas = AppDatabase.getAppDatabase(context).facturaDAO().getFacturasCliente(codigo).iterator()
        val presupuestos = AppDatabase.getAppDatabase(context).presupuestoDAO().getPresupuestosCliente(codigo).iterator()

        return Impagos(procesarFacturas(facturas), procesarPresupuestos(presupuestos))
    }

    private fun procesarPresupuestos(presupuestos: Iterator<Presupuesto>): MutableList<Pair<Presupuesto, BigDecimal>> {
        val presupuestosPorPagar: MutableList<Pair<Presupuesto, BigDecimal>> = mutableListOf()
        while (presupuestos.hasNext()) {
            val presupuesto = presupuestos.next()
            val pagosPresupesto = AppDatabase.getAppDatabase(context).pagoPorPresupuestoDAO().
            getPagosPresupuesto(presupuesto.codigo).iterator()
            val deuda = procesarDeudaPresupuestos(presupuesto, pagosPresupesto)
            if (deuda!!.compareTo(BigDecimal.ZERO) > 0) {
                val par: Pair<Presupuesto, BigDecimal> = Pair(presupuesto, deuda)
                presupuestosPorPagar.add(par)
            }
        }
        return presupuestosPorPagar
    }

    private fun procesarFacturas(facturas: Iterator<Factura>): MutableList<Pair<Factura, BigDecimal>> {
        val facturasPorPagar: MutableList<Pair<Factura, BigDecimal>> = mutableListOf()
        while (facturas.hasNext()) {
            val factura = facturas.next()
            val pagosFactura = AppDatabase.getAppDatabase(context).pagoPorFacturaDAO().getPagosFactura(factura.codigo).iterator()
            val deuda = procesarDeudaFacturas(factura, pagosFactura)
            if (deuda!!.compareTo(BigDecimal.ZERO) > 0) {
                val par: Pair<Factura, BigDecimal> = Pair(factura, deuda)
                facturasPorPagar.add(par)
            }
        }
        return facturasPorPagar
    }

    fun procesarDeudaPresupuestos(
        presupuesto: Presupuesto,
        pagosPresupesto: Iterator<PagoPorPresupuesto>
    ): BigDecimal? {
        var deuda = BigDecimal.ZERO
        if (presupuesto.total != null) {
            deuda = presupuesto.total
        }
        while (pagosPresupesto.hasNext()) {
            val pagoPresupuesto = pagosPresupesto.next()
            deuda = deuda.minus(pagoPresupuesto.importe)
        }
        return deuda
    }

    fun procesarDeudaFacturas(
        factura: Factura,
        pagosFactura: Iterator<PagoPorFactura>
    ): BigDecimal? {
        var deuda = BigDecimal.ZERO
        if (factura.total != null) {
            deuda = factura.total
        }
        while (pagosFactura.hasNext()) {
            val pagoFactura = pagosFactura.next()
            deuda = deuda.minus(pagoFactura.importe)
        }
        return deuda
    }

    fun getPagos(): MutableList<PagoDTO> {
        val pagos: MutableList<PagoDTO> = mutableListOf()
        val pagosFacturas = AppDatabase.getAppDatabase(context).pagoPorFacturaDAO().getPagosPorEstado('0')
        val iterator = pagosFacturas.listIterator()
        while (iterator.hasNext()) {
            val pagoPorFactura = iterator.next()
            val factura = AppDatabase.getAppDatabase(context).facturaDAO().getFacturaPorCodigoFactura(pagoPorFactura.codigoFactura)
            val pagoDto = cargarPagoConFactura(pagoPorFactura, factura)
            pagoDto.nombreCliente = getNombreCliente(factura.codCliente)
            pagos.add(pagoDto)
        }
        val pagosPresupuestos = AppDatabase.getAppDatabase(context).pagoPorPresupuestoDAO().getPagosPorEstado('0')
        val iterator1 = pagosPresupuestos.listIterator()
        while (iterator1.hasNext()) {
            val pagoPresupuesto = pagosPresupuestos.iterator().next()
            val presupuesto = AppDatabase.getAppDatabase(context).presupuestoDAO().getPresupuestoPorCodigoPresupuesto(pagoPresupuesto.codigoPresupuesto)
            val pagoDto = cargarPagoConPresupuesto(pagoPresupuesto, presupuesto)
            pagoDto.nombreCliente = if (presupuesto.codCliente != null) getNombreCliente(presupuesto.codCliente) else ""
            pagos.add(pagoDto)
        }

        ordenarPorFechaDesc(pagos)
        return pagos
    }

    fun ordenarPorFechaDesc(pagos: MutableList<PagoDTO>) {
        pagos.sortBy { i -> i.fechaDate }
        return pagos.reverse()
    }

    fun cargarPagoConPresupuesto(pagoPresupuesto: PagoPorPresupuesto, presupuesto: Presupuesto): PagoDTO {
        val pagoDto = PagoDTO()
        pagoDto.fechaDate = pagoPresupuesto.fecha
        pagoDto.fecha = simpleDateFormat.format(pagoPresupuesto.fecha)
        pagoDto.nroFactura = pagoPresupuesto.codigoPresupuesto.toString()
        pagoDto.tipoFactura = "Presupuesto"
        pagoDto.importe = pagoPresupuesto.importe.toString()
        pagoDto.total = presupuesto.total.toString()
        return pagoDto
    }

    fun cargarPagoConFactura(
        pagoPorFactura: PagoPorFactura,
        factura: Factura
    ): PagoDTO {
        val pagoDto = PagoDTO()
        if (pagoPorFactura.fecha != null) {
            pagoDto.fecha = simpleDateFormat.format(pagoPorFactura.fecha)
            pagoDto.fechaDate = pagoPorFactura.fecha
        }
        pagoDto.nroFactura = factura.numero ?: ""
        pagoDto.tipoFactura = "Factura " + factura.tipo
        pagoDto.importe = pagoPorFactura.importe.toString()
        pagoDto.total = factura.total.toString()
        return pagoDto
    }

    private fun getNombreCliente(codCliente: Int): String {
        return AppDatabase.getAppDatabase(context).clienteDAO().getByCodigo(codCliente).nombre
    }

    fun crearPago(codFactura: Int, tipoFac: TIPO_FAC, importe: BigDecimal, codEmpleado: Int,
    banco: String?, nroCheque: String?, titular: String?, vencimientoCheque: Date?) {

        if(tipoFac.equals(TIPO_FAC.FACTURA)) {
            val codigo = AppDatabase.getAppDatabase(context).pagoPorFacturaDAO().getId() + 1
            val pagoPorFactura = PagoPorFactura(
                codigo = codigo,
                fecha = Date(),
                codigoFactura = codFactura,
                codigoForm = 3,
                importe = importe,
                empleado = codEmpleado,
                fechaAsiento = Date(),
                estado = '0',
                observaciones = titular,
                fechaPaso = null,
                fechaVencimiento = vencimientoCheque,
                tablet = null,
                pasado = '0',
                nroLote = null,
                nroTarjeta = null,
                tarjetaCodigo = null,
                nroCheque = nroCheque,
                nroTransferencia = null,
                banco = banco
            )
            AppDatabase.getAppDatabase(context).pagoPorFacturaDAO().insert(pagoPorFactura)
        } else if(tipoFac.equals(TIPO_FAC.PRESUPUESTO)) {
            val codigo = AppDatabase.getAppDatabase(context).pagoPorPresupuestoDAO().getId() + 1
            val pagoPorPresupuesto = PagoPorPresupuesto(
                codigo = codigo,
                fecha = Date(),
                empleado = codEmpleado,
                estado = '0',
                fechaAsiento = Date(),
                importe = importe,
                codigoPresupuesto = codFactura,
                codigoForm = 3,
                observaciones = null,
                pasado = '0',
                tablet = null,
                fechaPaso = null
            )
            AppDatabase.getAppDatabase(context).pagoPorPresupuestoDAO().insert(pagoPorPresupuesto)
        }
    }

    fun enviarFacturas(): Boolean {
        val pagosFacturas = AppDatabase.getAppDatabase(context).pagoPorFacturaDAO().getPagosPorEstado('0')
        val it = pagosFacturas.listIterator()
        while (it.hasNext()) {
            val pagoPorFactura = it.next()
            if (DbConnection().INSERT(crearInsertFactura(pagoPorFactura))) {
                AppDatabase.getAppDatabase(context).pagoPorFacturaDAO().updateEstadoAndPasado(pagoPorFactura.codigo)
            } else {
                return false
            }
        }
        return true
    }

    fun crearInsertFactura(pagoPorFactura: PagoPorFactura): String {

        var insert = Queries.INSERT_PAGO_X_FACTURA
        insert = insert.replace(":fact_codigo:", pagoPorFactura.codigoFactura.toString())
        if (pagoPorFactura.fecha != null) {
            insert = insert.replace(":pfac_fecha:", "'" + simpleDateFormatSQL.format(pagoPorFactura.fecha) + "'")
        } else {
            insert = insert.replace(":pfac_fecha:", "null")
        }
        insert = insert.replace(":form_codigo:", pagoPorFactura.codigoForm.toString())
        insert = insert.replace(":pfac_importe:", pagoPorFactura.importe.toString())
        insert = insert.replace(":pfac_empleado:", pagoPorFactura.empleado.toString())
        insert = insert.replace(":pfac_banco:", pagoPorFactura.banco.toString())
        insert = insert.replace(":pfac_Ntransferencia:", pagoPorFactura.nroTransferencia.toString())
        insert = insert.replace(":pfac_observaciones:", pagoPorFactura.observaciones.toString())
        if (pagoPorFactura.fechaVencimiento != null) {
            insert = insert.replace(":pfac_fechavenc:", "'" + simpleDateFormatSQL.format(pagoPorFactura.fechaVencimiento) + "'")
        } else {
            insert = insert.replace(":pfac_fechavenc:", "null")
        }
        insert = insert.replace(":pfac_Ncheque:", pagoPorFactura.nroCheque.toString())
        insert = insert.replace(":tarj_codigo:", pagoPorFactura.tarjetaCodigo.toString())
        insert = insert.replace(":pfac_Ntarjeta:", pagoPorFactura.nroTarjeta.toString())
        insert = insert.replace(":pfac_Nlote:", pagoPorFactura.nroLote.toString())
        insert = insert.replace(":pfac_estado:", pagoPorFactura.estado.toString())
        if (pagoPorFactura.fechaAsiento != null) {
            insert = insert.replace(":pfac_fechaAsiento:", "'" + simpleDateFormatSQL.format(pagoPorFactura.fechaAsiento) + "'")
        } else {
            insert = insert.replace(":pfac_fechaAsiento:", "null")
        }
        insert = insert.replace(":pfac_pasado:", pagoPorFactura.pasado.toString())
        insert = insert.replace(":pfac_tablet:", pagoPorFactura.tablet.toString())
        if (pagoPorFactura.fechaPaso != null) {
            insert = insert.replace(":pfac_fechaPaso:", "'" + simpleDateFormatSQL.format(pagoPorFactura.fechaPaso) + "'")
        } else {
            insert = insert.replace(":pfac_fechaPaso:", "null")
        }
        return insert
    }

    fun enviarPresupuestos(): Boolean {
        val pagosPresupuestos = AppDatabase.getAppDatabase(context).pagoPorPresupuestoDAO().getPagosPorEstado('0')
        val it = pagosPresupuestos.listIterator()
        while (it.hasNext()) {
            val pagoPresupuesto = it.next()
            if (DbConnection().INSERT(crearInsertPresupuesto(pagoPresupuesto))) {
                AppDatabase.getAppDatabase(context).pagoPorPresupuestoDAO().updateEstadoAndPasado(pagoPresupuesto.codigo)
            } else {
                return false
            }
        }
        return true
    }

    private fun crearInsertPresupuesto(pagoPresupuesto: PagoPorPresupuesto): String {
        var insert = Queries.INSERT_PAGO_X_PRESUPUESTO
        insert = insert.replace(":ppre_codigo:", pagoPresupuesto.codigo.toString())
        insert = insert.replace(":pres_codigo:", pagoPresupuesto.codigoPresupuesto.toString())
        if (pagoPresupuesto.fecha != null) {
            insert = insert.replace(":ppre_fecha:", "'" + simpleDateFormatSQL.format(pagoPresupuesto.fecha) + "'")
        } else {
            insert = insert.replace(":ppre_fecha:", "null")
        }
        insert = insert.replace(":form_codigo:", pagoPresupuesto.codigoForm.toString())
        insert = insert.replace(":ppre_importe:", pagoPresupuesto.importe.toString())
        insert = insert.replace(":empl_codigo:", pagoPresupuesto.empleado.toString())
        insert = insert.replace(":ppre_observaciones:", pagoPresupuesto.observaciones.toString())
        insert = insert.replace(":ppre_estado:", pagoPresupuesto.estado.toString())
        if (pagoPresupuesto.fechaAsiento != null) {
            insert = insert.replace(":ppre_fechaAsiento:", "'" + simpleDateFormatSQL.format(pagoPresupuesto.fechaAsiento) + "'")
        } else {
            insert = insert.replace(":ppre_fechaAsiento:", "null")
        }
        insert = insert.replace(":ppre_pasado:", pagoPresupuesto.pasado.toString())
        insert = insert.replace(":ppre_tablet:", pagoPresupuesto.tablet.toString())
        if (pagoPresupuesto.fechaPaso != null) {
            insert = insert.replace(":ppre_fechaPaso:", "'" + simpleDateFormatSQL.format(pagoPresupuesto.fechaPaso) + "'")
        } else {
            insert = insert.replace(":ppre_fechaPaso:", "null")
        }
        return insert
    }

}