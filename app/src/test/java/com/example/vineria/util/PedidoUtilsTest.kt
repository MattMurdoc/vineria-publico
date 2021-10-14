package com.example.vineria.util

import com.example.vineria.modelos.Item
import com.example.vineria.modelos.Pedido
import junit.framework.TestCase
import org.joda.time.LocalDateTime
import java.math.BigDecimal
import java.util.*

class PedidoUtilsTest : TestCase() {

    fun testAgregarItem() {
        val item = Item(11L, 22, 3, BigDecimal.valueOf(12.5), BigDecimal.valueOf(23.6), "list")
        val result = PedidoUtils.agregarItem(item)
        assertEquals("\n22;3;12,5;23,6;list", result)
    }

    fun testAgregarObservaciones() {
        val pedidoObsNull = Pedido(Date(), 1, "descCliente", 1, "tipo", null)
        assertEquals("\nObservaciones:\n*/*", PedidoUtils.agregarObservaciones(pedidoObsNull))
        val pedidoObsVacio = Pedido(Date(), 1, "descCliente", 1, "tipo", "")
        assertEquals("\nObservaciones:\n*/*", PedidoUtils.agregarObservaciones(pedidoObsNull))
        val pedidoObsNotNull = Pedido(Date(), 1, "descCliente", 1, "tipo", "observacion")
        assertEquals("\nObservaciones:\nobservacion\n*/*", PedidoUtils.agregarObservaciones(pedidoObsNotNull))
        val pedidoObsEnters = Pedido(Date(), 1, "descCliente", 1, "tipo", "\n\n\n\n\nobservacion\n\n\n\n")
        assertEquals("\nObservaciones:\nobservacion\n*/*", PedidoUtils.agregarObservaciones(pedidoObsEnters))
        val pedidoObsEntersAndSlashR = Pedido(Date(), 1, "descCliente", 1, "tipo", "\r\n\r\n\r\n\r\n\r\nobservacion\r\n\r\n\r\n\r\n")
        assertEquals("\nObservaciones:\nobservacion\n*/*", PedidoUtils.agregarObservaciones(pedidoObsEntersAndSlashR))
    }

    fun testAgregarDatosPedido() {
        val pedidoTipoPS = Pedido(Date(), 1, "descCliente", 1, "PS", null)
        val expected = "0;" + LocalDateTime().toString(PedidoUtils.formatter) + ";1;1;"
        assertEquals(expected + "PS;", PedidoUtils.agregarDatosPedido(pedidoTipoPS))
        val pedidoTipoA = Pedido(Date(), 1, "descCliente", 1, "A", null)
        assertEquals(expected + "FA;", PedidoUtils.agregarDatosPedido(pedidoTipoA))
        val pedidoTipoB = Pedido(Date(), 1, "descCliente", 1, "B", null)
        assertEquals(expected + "FB;", PedidoUtils.agregarDatosPedido(pedidoTipoB))
    }
}