package com.example.vineria.ui.pagos

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.modelos.dto.PagoDTO
import com.example.vineria.ui.nuevopago.NuevoPagoActivity
import kotlinx.android.synthetic.main.activity_pagos.*
import kotlinx.android.synthetic.main.activity_pagos.fabNuevoPedido
import kotlinx.android.synthetic.main.activity_pagos.ivEnviar
import kotlinx.android.synthetic.main.activity_pedidos.*

class PagosActivity: ApplicationActivity(R.layout.activity_pagos) {
    private val viewModel: PagosViewModel by viewModels()

    private var rvLista: RecyclerView? = null
    private lateinit var adapter: PagoAdapter
    private var items: List<PagoDTO>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initValues()

        viewModel.pagos?.observe(this,{

            if(it.isNullOrEmpty()){
                rvListaPagos.visibility = View.INVISIBLE
                rlTextViewPagos.visibility = View.VISIBLE
            } else {
                rvListaPagos.visibility = View.VISIBLE
                rlTextViewPagos.visibility = View.GONE
            }

            adapter.updatePago(it)
        })

        viewModel.getItems()

        fabNuevoPedido.setOnClickListener({
            startActivity(Intent(this, NuevoPagoActivity::class.java))
        })

        ivEnviar.setOnClickListener {
            it.let {
                val builder = AlertDialog.Builder(it.context)
                builder.apply {
                    setTitle("¿Desea enviar todos los pagos?")
                    setPositiveButton("Aceptar") { dialog, id ->
                        viewModel.enviarTodos()
                    }
                    setNegativeButton("Cancelar") { _, _ ->  }
                }
                builder.create()
            }.show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getItems()
    }

    private fun initViews() {
        rvLista = findViewById(R.id.rvListaPagos)
    }

    private fun initValues() {
        val manager = LinearLayoutManager(this)

        rvLista!!.layoutManager = manager
        adapter = PagoAdapter(items as MutableList<PagoDTO>)
        rvLista!!.adapter = adapter

    }

    override fun getMyNavButtonId() = R.id.nav_pagos
}