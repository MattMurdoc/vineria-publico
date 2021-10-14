package com.example.vineria.ui.consultapedido

import android.app.Application
import com.example.vineria.clases.ApplicationViewModel
import com.example.vineria.modelos.dto.ConsultaPedidoDTO
import com.example.vineria.repository.PedidoRepository

class ConsultasPedidoViewModel(app: Application) : ApplicationViewModel(app) {
    private val pedidoRepository = PedidoRepository(app)

    lateinit var pedido: ConsultaPedidoDTO

    fun getConsultaById(codPedido: Long) {
        pedido = pedidoRepository.getConsultaPedido(codPedido)
        notifyChange()
    }
}