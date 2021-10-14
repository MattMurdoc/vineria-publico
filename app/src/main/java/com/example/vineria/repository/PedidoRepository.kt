package com.example.vineria.repository

import android.content.Context
import com.example.vineria.BuildConfig
import com.example.vineria.modelos.Item
import com.example.vineria.modelos.Pedido
import com.example.vineria.modelos.dto.ArticuloItemDTO
import com.example.vineria.modelos.dto.ConsultaPedidoDTO
import com.example.vineria.modelos.dto.ItemDTO
import com.example.vineria.persistance.database.AppDatabase
import com.example.vineria.util.GMailSender
import com.example.vineria.util.PedidoUtils
import org.joda.time.format.DateTimeFormat
import java.io.File
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class PedidoRepository(private val context: Context?) {

    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")

    fun crearPedido(codCliente: Int?, descCliente: String, codEmpleado: Int, tipoComprobante: String, observaciones: String, items: MutableList<ArticuloItemDTO> ) {
        val pedidoinsert = Pedido(
            fechaHora = Date(),
            codCliente = codCliente,
            descCliente = descCliente,
            codEmpleado = codEmpleado,
            tipoComprobante = if(tipoComprobante.equals("Presupuesto")) "PS" else if(tipoComprobante.equals("Factura A")) "A" else "B",
            observaciones = observaciones,
            enviado = false,
            body = ""
        )

        val idPed = AppDatabase.getAppDatabase(context).pedidoDAO().insert(pedidoinsert)

        items.forEach {
            val itemInsert = Item(
                idPedido = idPed,
                codArticulo = it.idArticulo,
                cantidad = it.cantidad,
                importeUnitario = it.importeUnitario,
                importeTotal = it.importeTotal,
                listaPrecios = it.listaSeleccionada
            )
            AppDatabase.getAppDatabase(context).itemDAO().insert(itemInsert)
        }
    }

    fun getPedidosByFecha(fechaDesde: String, fechaHasta: String): MutableList<Pedido> {
        val df = DateTimeFormat.forPattern("yyyy-MM-dd")
        val desde = df.parseDateTime(fechaDesde)
        desde.withTimeAtStartOfDay()
        val hasta = df.parseDateTime(fechaHasta).plusDays(1)
        hasta.withTimeAtStartOfDay()
        val fechaDesde1 = Date(desde.millis)
        val fechaHasta1 = Date(hasta.millis)
        val pedidos: MutableList<Pedido> = AppDatabase.getAppDatabase(context).pedidoDAO().getPedidosByFecha(fechaDesde1, fechaHasta1)

        pedidos.forEach {
            val items: List<Item> = AppDatabase.getAppDatabase(context).itemDAO().getAllByIdPedido(it.id)
            var importeTotal = BigDecimal.ZERO
            items.forEach {
                importeTotal = importeTotal.add(it.importeTotal)
            }

            it.total = importeTotal
            it.descCliente = if (it.codCliente != null) AppDatabase.getAppDatabase(context).clienteDAO().getDescripcionByCodigo(it.codCliente) else it.descCliente
        }

        return pedidos
    }

    fun getConsultaPedido(idPedido: Long): ConsultaPedidoDTO {

        val pedido: Pedido = AppDatabase.getAppDatabase(context).pedidoDAO().getPedidoById(idPedido)
        val items: List<Item> =
            AppDatabase.getAppDatabase(context).itemDAO().getAllByIdPedido(idPedido)
        val itemsDto: MutableList<ItemDTO> = mutableListOf()
        var importeTotal = BigDecimal.ZERO

        items.forEach {
            val nombreArticulo = AppDatabase.getAppDatabase(context).articuloDAO()
                .getDescripcionArticuloById(it.codArticulo)
            val itDto = ItemDTO(
                descArticulo = nombreArticulo,
                cantidad = it.cantidad,
                importeUnitario = it.importeUnitario,
                importeTotal = it.importeTotal,
                listaPrecios = it.listaPrecios
            )
            importeTotal = importeTotal.add(it.importeTotal)
            itemsDto.add(itDto)
        }
        return ConsultaPedidoDTO(
            nroPedido = pedido.id,
            fechaHora = simpleDateFormat.format(pedido.fechaHora),
            descCliente = if (pedido.codCliente != null) AppDatabase.getAppDatabase(context).clienteDAO().getDescripcionByCodigo(pedido.codCliente) else pedido.descCliente,
            tipoComprobante = if (pedido.tipoComprobante.equals("PS")) "Presupuesto" else "Factura " + pedido.tipoComprobante,
            total = importeTotal,
            itemDTO = itemsDto
        )
    }

    fun enviarPedido(pedido: Pedido) {
        val items = AppDatabase.getAppDatabase(context).itemDAO().getAllByIdPedido(pedido.id)
        val file: File = PedidoUtils.generarArchivo(pedido, items, context!!.filesDir)
        if (enviarPedido(pedido.id, pedido.codEmpleado, file)) {
            pedido.enviado = true
            AppDatabase.getAppDatabase(context).pedidoDAO().update(pedido)
        }
        file.delete()
    }


    private fun enviarPedido(nroPedido: Long, nroEmpleado: Int, adjunto: File): Boolean {
        return GMailSender.sendMail(
            "${BuildConfig.MAIL_FROM}",
            "${BuildConfig.MAIL_TO}",
            "${BuildConfig.MAIL_PASSWORD}",
            "Pedido$nroPedido - E" + nroEmpleado,
            "", adjunto
        )
    }
}

