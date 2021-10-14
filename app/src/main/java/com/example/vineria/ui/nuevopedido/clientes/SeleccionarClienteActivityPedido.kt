package com.example.vineria.ui.nuevopedido.clientes

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.modelos.Cliente
import com.example.vineria.modelos.dto.ClienteDTO
import com.example.vineria.ui.nuevopago.clientes.SeleccionarClienteAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_seleccionar_cliente.*
import kotlinx.android.synthetic.main.activity_seleccionar_cliente_pedido.*

class SeleccionarClienteActivityPedido : ApplicationActivity(R.layout.activity_seleccionar_cliente_pedido), SearchView.OnQueryTextListener, SeleccionarClienteAdapter.RecyclerItemClick {
    private val viewModel: SeleccionarClienteViewModelPedido by viewModels()
    private var offset = 0
    private var aptoParaCargar = false
    private var recyclerView: RecyclerView? = null
    private var listaClientesAdapter: SeleccionarClienteAdapter? = null
    private var scrollClientesListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val linearLayoutManager: LinearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                if (aptoParaCargar) {
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        aptoParaCargar = false
                        offset += 20
                        viewModel.getItems(offset, "")
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView = findViewById(R.id.rvSeleccionarClientePedido)
        listaClientesAdapter = SeleccionarClienteAdapter(this,this)
        recyclerView?.setAdapter(listaClientesAdapter)
        recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.addOnScrollListener(scrollClientesListener)

        svSearchClientePedido!!.setOnQueryTextListener(this)

        aptoParaCargar = true
        offset = 0
        viewModel.getItems(offset, "")

        btnNuevoCliente.setOnClickListener{
            it.let {
                val builder = AlertDialog.Builder(it.context)
                builder.apply {
                    setTitle("Escriba del nuevo nombre del cliente")
                    val input = EditText(it.context)
                    input.inputType = InputType.TYPE_CLASS_TEXT
                    builder.setView(input)
                    setPositiveButton("Aceptar") { dialog, id ->
                        val descCliente = input.text.toString()
                        val cliente = ClienteDTO(codCliente = null, descCliente = descCliente, tipoIVA = null)
                        val resultIntent = Intent()
                        resultIntent.putExtra("cliente", Gson().toJson(cliente))
                        setResult(10, resultIntent)
                        finish()
                    }
                    setNegativeButton("Cancelar") { _, _ ->  }
                }
                builder.create()
            }.show()
        }


        viewModel.listCliente?.observe(this) {
            listaClientesAdapter!!.adicionarListaCliente(it.first,it.second)
            aptoParaCargar = true
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        offset = 0
        if (newText == "") {
            viewModel.getItems(offset, "")
            recyclerView?.clearOnScrollListeners()
            recyclerView?.addOnScrollListener(scrollClientesListener)
        } else {
            viewModel.getItems(offset, newText)
            recyclerView?.clearOnScrollListeners()
            recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {})
        }

        return false
    }

    override fun itemClick(item: Cliente?) {
        val svSearch: SearchView = findViewById(R.id.svSearchClientePedido)
        svSearch.clearFocus()

        val cliente = ClienteDTO(codCliente = item?.codigo, item!!.nombre, item.iva)
        val resultIntent = Intent()
        resultIntent.putExtra("cliente", Gson().toJson(cliente))
        setResult(10, resultIntent)
        finish()
    }
}