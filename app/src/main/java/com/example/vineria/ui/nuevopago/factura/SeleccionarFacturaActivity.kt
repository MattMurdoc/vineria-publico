package com.example.vineria.ui.nuevopago.factura

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.modelos.dto.ImpagoDTO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_seleccionar_factura.*

class SeleccionarFacturaActivity: ApplicationActivity(R.layout.activity_seleccionar_factura), SeleccionarFacturaAdapter.RecyclerItemClick {
    private val viewModel: SeleccionarFacturaViewModel by viewModels()

    private var recyclerView: RecyclerView? = null
    private var svSearch: SearchView? = null
    private lateinit var adapter: SeleccionarFacturaAdapter
    private var items: List<ImpagoDTO>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val codCliente: Int = Integer.parseInt(intent.getStringExtra("codCliente")!!)
        val descCliente: String = intent.getStringExtra("descCliente")!!

        initViews()
        initValues()

        txtCliente.text = descCliente

        viewModel.getItems(codCliente)

        viewModel.impagos?.observe(this,{
            adapter.updateData(it)
        })
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.rvSeleccionarCliente)
        svSearch = findViewById(R.id.svSearchCliente)
    }

    private fun initValues() {
        val manager = LinearLayoutManager(this)

        recyclerView!!.layoutManager = manager
        adapter = SeleccionarFacturaAdapter(items as MutableList<ImpagoDTO>, this)
        recyclerView!!.adapter = adapter

    }

    override fun itemClick(item: ImpagoDTO?) {
        val resultIntent = Intent()
        resultIntent.putExtra("factura", Gson().toJson(item))
        setResult(10, resultIntent)
        finish()
    }
}