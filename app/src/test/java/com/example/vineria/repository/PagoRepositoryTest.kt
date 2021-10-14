package com.example.vineria.repository

import com.example.vineria.modelos.Factura
import com.example.vineria.modelos.PagoPorFactura
import com.example.vineria.modelos.PagoPorPresupuesto
import com.example.vineria.modelos.Presupuesto
import com.example.vineria.modelos.dto.PagoDTO
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import java.util.*

class PagoRepositoryTest {

    val pagoRepository = PagoRepository(null)

    @Test
    fun procesarDeudaFacturas_sin_pagos() {

        val factura = crearFactura()

        assertEquals(
            BigDecimal.valueOf(150),
            pagoRepository.procesarDeudaFacturas(factura, listOf<PagoPorFactura>().iterator())
        )
    }

    @Test
    fun procesarDeudaFacturas_con_pago_menor() {

        val factura = crearFactura()
        val pago = crearPagoFactura(BigDecimal.valueOf(50))
        val pagos: List<PagoPorFactura> = listOf(pago)
        assertEquals(
            BigDecimal.valueOf(100),
            pagoRepository.procesarDeudaFacturas(factura, pagos.iterator())
        )
    }

    @Test
    fun procesarDeudaFacturas_con_pago_total() {

        val factura = crearFactura()
        val pago1 = crearPagoFactura(BigDecimal.valueOf(50))
        val pago2 = crearPagoFactura(BigDecimal.valueOf(80))
        val pago3 = crearPagoFactura(BigDecimal.valueOf(20))
        val pagos: List<PagoPorFactura> = listOf(pago1, pago2, pago3)
        assertEquals(
            BigDecimal.valueOf(0),
            pagoRepository.procesarDeudaFacturas(factura, pagos.iterator())
        )
    }

    @Test
    fun procesarDeudaPresupuestos_sin_pagos() {

        val presupuesto = crearPresupuesto()

        assertEquals(
            BigDecimal.valueOf(150),
            pagoRepository.procesarDeudaPresupuestos(presupuesto, listOf<PagoPorPresupuesto>().iterator())
        )
    }

    @Test
    fun procesarDeudaPresupuestos_con_pago_menor() {

        val presupuesto = crearPresupuesto()
        val pago = crearPagoPresupuesto(BigDecimal.valueOf(50))
        val pagos: List<PagoPorPresupuesto> = listOf(pago)
        assertEquals(
            BigDecimal.valueOf(100),
            pagoRepository.procesarDeudaPresupuestos(presupuesto, pagos.iterator())
        )
    }

    @Test
    fun procesarDeudaPresupuestos_con_pago_total() {

        val presupuesto = crearPresupuesto()
        val pago1 = crearPagoPresupuesto(BigDecimal.valueOf(50))
        val pago2 = crearPagoPresupuesto(BigDecimal.valueOf(80))
        val pago3 = crearPagoPresupuesto(BigDecimal.valueOf(20))
        val pagos: List<PagoPorPresupuesto> = listOf(pago1, pago2, pago3)
        assertEquals(
            BigDecimal.valueOf(0),
            pagoRepository.procesarDeudaPresupuestos(presupuesto, pagos.iterator())
        )
    }

    private fun crearPagoFactura(monto: BigDecimal): PagoPorFactura {
        val pago = PagoPorFactura(
            codigo = 1,
            codigoFactura = 1,
            fecha = Date.from(Instant.now()),
            codigoForm = 1,
            importe = monto,
            empleado = 1,
            banco = "",
            nroTransferencia = "",
            observaciones = "",
            fechaVencimiento = Date.from(Instant.now()),
            nroCheque = "",
            tarjetaCodigo = 1,
            nroTarjeta = "",
            nroLote = "",
            estado = '1',
            fechaAsiento = Date.from(Instant.now()),
            pasado = '1',
            tablet = '1',
            fechaPaso = Date.from(Instant.now())
        )
        return pago
    }

    private fun crearFactura(): Factura {
        val factura = Factura(
            codigo = 1, numero = "1", numeron = 1, punto = 1,
            fecha = Date.from(Instant.now()), estado = 1, codCliente = 1,
            descuento = '1', total = BigDecimal.valueOf(150),
            estadoCobro = '1', fechaVencimiento = Date.from(Instant.now()),
            iva = 1, tipo = 'A', fechaAlta = Date.from(Instant.now()),
            empleadoAlta = 1, codEmpleado = 1, estacion = '1', descCliente = "d",
            impresora = "imp"
        )
        return factura
    }

    private fun crearPresupuesto(): Presupuesto {
        val presupuesto = Presupuesto(
            codigo = 1,
            fecha = Date.from(Instant.now()), cliente = "1", total = BigDecimal.valueOf(150),
            estadoCobro = '1', fechaVencimiento = Date.from(Instant.now()),
            observaciones = "", estado = '1', codCliente = 1
        )
        return presupuesto
    }

    private fun crearPagoPresupuesto(monto: BigDecimal): PagoPorPresupuesto {
        val pago = PagoPorPresupuesto(
            codigo = 1,
            codigoPresupuesto = 1,
            fecha = Date.from(Instant.now()),
            codigoForm = 1,
            importe = monto,
            empleado = 1,
            observaciones = "",
            estado = '1',
            fechaAsiento = Date.from(Instant.now()),
            pasado = '1',
            tablet = '1',
            fechaPaso = Date.from(Instant.now())
        )
        return pago
    }

    @Test
    fun cargarPagoConFactura() {
        val factura = crearFactura()
        val pago = crearPagoFactura(BigDecimal.valueOf(150.10))
        val pagoDTO = pagoRepository.cargarPagoConFactura(pago, factura)
        assertEquals( "Factura A", pagoDTO.tipoFactura)
        assertEquals("1", pagoDTO.nroFactura)
        assertEquals("150.1", pagoDTO.importe)
        assertEquals("150", pagoDTO.total)
    }

    @Test
    fun cargarPagoConPresupuesto() {
        val presupuesto = crearPresupuesto()
        val pago = crearPagoPresupuesto(BigDecimal.valueOf(150.10))
        val pagoDTO = pagoRepository.cargarPagoConPresupuesto(pago, presupuesto)
        assertEquals( "Presupuesto", pagoDTO.tipoFactura)
        assertEquals("1", pagoDTO.nroFactura)
        assertEquals("150.1", pagoDTO.importe)
        assertEquals("150", pagoDTO.total)
    }

    @Test
    fun ordenarPorFechaDesc() {
        val pago1 = PagoDTO()
        val pago2 = PagoDTO()
        val pago3 = PagoDTO()
        val pago4 = PagoDTO()

        pago1.fechaDate = Date(2021,1,3,20,0)
        pago2.fechaDate = Date(2021,6,3,20,0)
        pago3.fechaDate = Date(2021,3,2,20,0)
        pago4.fechaDate = Date(2021,6,3,10,0)
        val pagos: MutableList<PagoDTO> = mutableListOf(pago1,pago2,pago3,pago4)
        pagoRepository.ordenarPorFechaDesc(pagos)
        assertEquals(pago2, pagos[0])
        assertEquals(pago4, pagos[1])
        assertEquals(pago3, pagos[2])
        assertEquals(pago1, pagos[3])
    }
}