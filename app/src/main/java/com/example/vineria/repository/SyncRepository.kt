package com.example.vineria.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.vineria.modelos.*
import com.example.vineria.persistance.database.AppDatabase
import com.example.vineria.persistance.sqlserver.DbConnection
import com.example.vineria.persistance.sqlserver.Queries
import java.sql.ResultSet

class SyncRepository(private val context: Context) {

    companion object {
        private val TAG = "SyncRepository"
    }

    @WorkerThread
    suspend fun deleteWork() {
        val appDatabase = AppDatabase.getAppDatabase(context)
        appDatabase.clienteDAO().deleteAll()
        appDatabase.empleadoDAO().deleteAll()
        appDatabase.articuloDAO().deleteAll()
        appDatabase.envaseDAO().deleteAll()
        appDatabase.facturaDAO().deleteAll()
        appDatabase.detalleFacturaDAO().deleteAll()
        appDatabase.pagoPorPresupuestoDAO().deleteAll()
        appDatabase.presupuestoDAO().deleteAll()
        appDatabase.detallePresupuestoDAO().deleteAll()
        appDatabase.pagoPorPresupuestoDAO().deleteAll()
    }

    @WorkerThread
    suspend fun clienteWork() {
        val clientes = DbConnection().SELECT(Queries.SELECT_CLIENTES, ::mapSQLToCliente)
        AppDatabase.getAppDatabase(context).clienteDAO().insertAll(clientes)
    }

    private fun mapSQLToCliente(result: ResultSet): Cliente {
        return Cliente (codigo = result.getInt("clie_codigo"),
            nombre = result.getString("clie_nombre"),
            estado = result.getInt("esta_codigo"),
            nroDocumento = result.getString("clie_Ndocumento"),
            iva = result.getInt("tiva_codigo"),
            cuit = result.getString("clie_CUIT"))
    }

    @WorkerThread
    suspend fun empleadoWork() {
        val empleados = DbConnection().SELECT(Queries.SELECT_EMPLEADOS, ::mapSQLToEmpleado)
        AppDatabase.getAppDatabase(context).empleadoDAO().insertAll(empleados)
    }

    private fun mapSQLToEmpleado(result: ResultSet): Empleado {
        return Empleado (codigo = result.getInt("empl_codigo"),
            nombre = result.getString("empl_nombre"))
    }

    @WorkerThread
    suspend fun articuloWork() {
        val articulos = DbConnection().SELECT(Queries.SELECT_ARTICULOS, ::mapSQLToArticulo)
        AppDatabase.getAppDatabase(context).articuloDAO().insertAll(articulos)
    }

    private fun mapSQLToArticulo(result: ResultSet): Articulo {
        return Articulo (codigo = result.getInt("arti_codigo"),
            nombre = result.getString("arti_nombre"),
            imagen = result.getString("arti_imagen"),
            precioCompra = result.getBigDecimal("arti_precioCompra"),
            precioLista1 = result.getBigDecimal("arti_precioLista1"),
            precioLista2 = result.getBigDecimal("arti_precioLista2"),
            precioLista3 = result.getBigDecimal("arti_precioLista3"),
            porcLista1 = result.getDouble("arti_porcLista1"),
            porcLista2 = result.getDouble("arti_porcLista2"),
            porcLista3 = result.getDouble("arti_porcLista3"),
            vigente = result.getString("arti_vigente")[0],
            codBarra = result.getString("arti_codigoBarra"),
            envases = getChar(result.getString("arti_envases")),
            codUnidad = result.getInt("unid_codigo"),
            codPrev = result.getInt("prve_codigo"),
            inhiL1 = getChar(result.getString("arti_inhiL1")),
            inhiL2 = getChar(result.getString("arti_inhiL2")),
            inhiL3 = getChar(result.getString("arti_inhiL3")),
            codEnvase = result.getInt("enva_codigo"),
            modificado = getChar(result.getString("arti_modificado")))
    }

    @WorkerThread
    suspend fun envaseWork() {
        val envases = DbConnection().SELECT(Queries.SELECT_ENVASES, ::mapSQLToEnvase)
        AppDatabase.getAppDatabase(context).envaseDAO().insertAll(envases)
    }

    private fun mapSQLToEnvase(result: ResultSet): Envase {
        return Envase (codigo = result.getInt("enva_codigo"),
            nombre = result.getString("enva_nombre"))
    }

    @WorkerThread
    suspend fun facturaWork() {
        val facturas = DbConnection().SELECT(Queries.SELECT_FACTURAS, ::mapSQLToFactura)
        AppDatabase.getAppDatabase(context).facturaDAO().insertAll(facturas)
        for(factura in facturas) {
            val detalles = DbConnection().SELECT(String.format(Queries.SELECT_FACTURA_DETALLE, factura.codigo), ::mapSQLToDetalleFactura)
            AppDatabase.getAppDatabase(context).detalleFacturaDAO().insertAll(detalles)
            val pagos = DbConnection().SELECT(String.format(Queries.SELECT_PAGO_X_FACTURA, factura.codigo), ::mapSQLToPagoPorFactura)
            AppDatabase.getAppDatabase(context).pagoPorFacturaDAO().insertAll(pagos)
        }
    }

    private fun mapSQLToFactura(result: ResultSet): Factura {
        return Factura (codigo = result.getInt("fact_codigo"),
            numero = result.getString("fact_numero"),
            numeron = result.getInt("fact_numeron"),
            punto = result.getInt("fact_punto"),
            fecha = result.getDate("fact_fecha"),
            estado = result.getInt("fact_estado"),
            codCliente = result.getInt("clie_codigo"),
            descuento = result.getString("fact_descuento")[0],
            total = result.getBigDecimal("fact_total"),
            estadoCobro = getChar(result.getString("fact_estadocobro")),
            fechaVencimiento = result.getDate("fact_fechavencimiento"),
            iva = result.getInt("fact_iva"),
            tipo = result.getString("fact_tipo")[0],
            fechaAlta = result.getDate("fact_fechaA"),
            empleadoAlta = result.getInt("fact_emplA"),
            codEmpleado = result.getInt("empl_codigo"),
            estacion = getChar(result.getString("fact_estacion")),
            descCliente = result.getString("fact_cliente"),
            impresora = result.getString("fact_impresora"))
    }

    private fun mapSQLToDetalleFactura(result: ResultSet): DetalleFactura {
        return DetalleFactura (codigo = result.getInt("deta_codigo"),
            codigoFactura = result.getInt("fact_codigo"),
            codArticulo = result.getInt("arti_codigo"),
            cantidad = result.getInt("deta_cantidad"),
            importeSubtotal = result.getBigDecimal("deta_importeST"),
            importeUnitario = result.getBigDecimal("deta_importeU"),
            lista = result.getString("deta_Lista"))
    }

    private fun mapSQLToPagoPorFactura(result: ResultSet): PagoPorFactura {
        return PagoPorFactura (codigo = result.getInt("pfac_codigo"),
            codigoFactura = result.getInt("fact_codigo"),
            fecha = result.getDate("pfac_fecha"),
            codigoForm = result.getInt("form_codigo"),
            importe = result.getBigDecimal("pfac_importe"),
            empleado = result.getInt("pfac_empleado"),
            banco = result.getString("pfac_banco"),
            nroTransferencia = result.getString("pfac_Ntransferencia"),
            observaciones = result.getString("pfac_observaciones"),
            fechaVencimiento = result.getDate("pfac_fechavenc"),
            nroCheque = result.getString("pfac_Ncheque"),
            tarjetaCodigo = result.getInt("tarj_codigo"),
            nroTarjeta = result.getString("pfac_Ntarjeta"),
            nroLote = result.getString("pfac_Nlote"),
            estado = getChar(result.getString("pfac_estado")),
            fechaAsiento = result.getDate("pfac_fechaAsiento"),
            pasado = getChar(result.getString("pfac_pasado")),
            tablet = getChar(result.getString("pfac_tablet")),
            fechaPaso = result.getDate("pfac_fechaPaso"))
    }

    @WorkerThread
    suspend fun presupuestoWork() {
        val presupuestos = DbConnection().SELECT(Queries.SELECT_PRESUPUESTOS, ::mapSQLToPresupuesto)
        AppDatabase.getAppDatabase(context).presupuestoDAO().insertAll(presupuestos)
        for(presupuesto in presupuestos) {
            val detalles = DbConnection().SELECT(String.format(Queries.SELECT_PRESUPUESTO_DETALLE, presupuesto.codigo), ::mapSQLToDetallePresupuesto)
            AppDatabase.getAppDatabase(context).detallePresupuestoDAO().insertAll(detalles)
            val pagos = DbConnection().SELECT(String.format(Queries.SELECT_PAGO_X_PRESUPUESTO, presupuesto.codigo), ::mapSQLToPagoPorPresupuesto)
            AppDatabase.getAppDatabase(context).pagoPorPresupuestoDAO().insertAll(pagos)
        }
    }

    private fun mapSQLToPresupuesto(result: ResultSet): Presupuesto {
        return Presupuesto (codigo = result.getInt("pres_codigo"),
            fecha = result.getDate("pres_fecha"),
            cliente = result.getString("pres_cliente"),
            total = result.getBigDecimal("pres_total"),
            fechaVencimiento = result.getDate("pres_fechaVencimiento"),
            observaciones = result.getString("pres_Observaciones"),
            estado = getChar(result.getString("pres_estado")),
            estadoCobro = getChar(result.getString("pres_estadoCobro")),
            codCliente = result.getInt("clie_codigo"))
    }

    private fun mapSQLToDetallePresupuesto(result: ResultSet): DetallePresupuesto {
        return DetallePresupuesto (codigo = result.getInt("deps_codigo"),
            codigoPresupuesto = result.getInt("pres_codigo"),
            codArticulo = result.getInt("arti_codigo"),
            cantidad = result.getInt("deps_cantidad"),
            importeSubtotal = result.getBigDecimal("deps_importeST"),
            importeUnitario = result.getBigDecimal("deps_importeU"),
            lista = result.getString("deps_Lista"))
    }

    private fun mapSQLToPagoPorPresupuesto(result: ResultSet): PagoPorPresupuesto {
        return PagoPorPresupuesto (codigo = result.getInt("ppre_codigo"),
            codigoPresupuesto = result.getInt("pres_codigo"),
            fecha = result.getDate("ppre_fecha"),
            codigoForm = result.getInt("form_codigo"),
            importe = result.getBigDecimal("ppre_importe"),
            empleado = result.getInt("empl_codigo"),
            observaciones = result.getString("ppre_observaciones"),
            estado = getChar(result.getString("ppre_estado")),
            fechaAsiento = result.getDate("ppre_fechaAsiento"),
            pasado = getChar(result.getString("ppre_pasado")),
            tablet = getChar(result.getString("ppre_tablet")),
            fechaPaso = result.getDate("ppre_fechaPaso"))
    }

    internal fun getChar(value: String?): Char? {
        return if(value != null) value[0] else null
    }

}