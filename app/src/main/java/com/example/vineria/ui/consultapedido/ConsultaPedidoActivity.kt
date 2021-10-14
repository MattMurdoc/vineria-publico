package com.example.vineria.ui.consultapedido

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.modelos.Item
import kotlinx.android.synthetic.main.activity_consultas.*

class ConsultaPedidoActivity : ApplicationActivity(R.layout.activity_consultas){
    private val viewModel: ConsultasPedidoViewModel by viewModels()

    private lateinit var adapter: ItemPedidoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val codPedido: Long = java.lang.Long.parseLong(intent.getStringExtra("PEDIDOID")!!)
        viewModel.getConsultaById(codPedido)

        val manager = LinearLayoutManager(this)

        rvItems.layoutManager = manager
        adapter = ItemPedidoAdapter(mutableListOf())
        rvItems.adapter = adapter

        viewModel.observe(this) {
            txtTipoFac.text = viewModel.pedido.tipoComprobante + " / " + viewModel.pedido.nroPedido
            txtFecha.text = viewModel.pedido.fechaHora
            txtCliente.text = viewModel.pedido.descCliente
            txtProducto1.text = viewModel.pedido.itemDTO!!.size.toString()
            txtTotal.text = "$ " + viewModel.pedido.total.toString()

            adapter.updateUsuarios(viewModel.pedido.itemDTO!!)
        }
    }
}