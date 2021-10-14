package com.example.vineria.util

import com.example.vineria.modelos.Item
import com.example.vineria.modelos.Pedido
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.io.File
import java.text.NumberFormat
import java.util.*

object PedidoUtils {

    var formatter: DateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss")


    fun generarArchivo(pedido: Pedido, items: List<Item>, directory: File): File {
        var fileContent : String
        fileContent = agregarDatosPedido(pedido)
        fileContent += agregarItems(items)
        fileContent += agregarObservaciones(pedido)
        val file = File(directory, pedido.id.toString() + " - E" + pedido.codEmpleado.toString() + ".log")
        file.writeText(fileContent)
        return file
    }

    private fun agregarItems(items: List<Item>): Any {
        var result = ""
        val artIt = items.iterator()
        while (artIt.hasNext()) {
            result += agregarItem(artIt.next())
        }
        return result
    }

    fun agregarItem(item: Item): Any {

        return "\n" + item.codArticulo + ";" +
                item.cantidad + ";" +
                item.importeUnitario.toString().replace(".", ",") + ";" +
                item.importeTotal.toString().replace(".", ",") + ";" +
                item.listaPrecios
    }

    fun agregarObservaciones(pedido: Pedido): Any {
        var obs = ""
        if (pedido.observaciones != null) {
            obs += pedido.observaciones
        }

        var result = "\nObservaciones:"
        if (obs != null && obs.length > 0) {
            while(obs.contains("\n")) {
                obs = obs.replace("\n", "")
            }
            while(obs.contains("\r")) {
                obs = obs.replace("\r", "")
            }
            result += "\n" + obs
        }
        return "$result\n*/*"
    }

    fun agregarDatosPedido(pedido: Pedido): String {
        var tipo = pedido.tipoComprobante
        if (tipo.equals("A") || tipo.equals("B")) {
            tipo = "F" + tipo
        }
        return pedido.id.toString() + ";" +
                LocalDateTime(pedido.fechaHora).toString(formatter) + ";" +
                pedido.codCliente + ";" +
                pedido.codEmpleado  + ";" +
                tipo  + ";"
    }
}