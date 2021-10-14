package com.example.vineria.ui.pedidos

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.vineria.clases.ApplicationViewModel
import com.example.vineria.clases.Tasks
import com.example.vineria.modelos.Pedido
import com.example.vineria.persistance.database.AppDatabase
import com.example.vineria.repository.PedidoRepository
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util.*


class PedidosViewModel(app: Application) : ApplicationViewModel(app) {
    private var pedidoRepository = PedidoRepository(app)
    var id: Int = -1
    var canSave: Boolean = true
    var canSearch: Boolean = true

    var calendar = Calendar.getInstance()
    var simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss aaa z")
    var ddmmyyyy = SimpleDateFormat("yyyy-MM-dd")
    var dateTime = simpleDateFormat.format(calendar.time)
    var fechaHasta: String = ddmmyyyy.format(calendar.time)
    var fechaDesde: String = ddmmyyyy.format(calendar.time)
    var listPedido: MutableLiveData<MutableList<Pedido>>? = MutableLiveData()

    fun getItems() {
        val pedidos = pedidoRepository.getPedidosByFecha(fechaDesde, fechaHasta)

        listPedido?.postValue(pedidos)
    }

    fun enviarTodos() = launchTask(Tasks.SendPedidos) {
        enviarPedidos()
    }

    fun buscar() = launchTask(Tasks.SearchPedidos) {
        change { canSearch = false }
        getItems()
        change { canSearch = true }
    }

    fun enviarPedidos() {
        change { canSave = false }
        if(!isInternetAvailable()) {
            sendToastToActivity("No hay conexión a Internet")
        } else {
            sendToastToActivity("Enviando pedido")
            val pedidos = AppDatabase.getAppDatabase(app).pedidoDAO().getNoEnviados().iterator()
            while (pedidos.hasNext()) {
                val pedido = pedidos.next()
                pedidoRepository.enviarPedido(pedido)
            }
            getItems()
        }
        change { canSave = true }
    }

    fun enviarPedido(pedido: Pedido) = launchTask(Tasks.SendPedido) {
        if(!isInternetAvailable()) {
            sendToastToActivity("No hay conexión a Internet")
        } else {
            sendToastToActivity("Enviando pedido")
            pedidoRepository.enviarPedido(pedido)
            getItems()
        }
    }

    fun isInternetAvailable(): Boolean {
        return try {
            InetAddress.getByName("google.com.ar")
            true
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        private val TAG = this.javaClass.toString()
    }
}
