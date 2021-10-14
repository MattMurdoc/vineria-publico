package com.example.vineria.ui.nuevopago.clientes

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.modelos.Articulo
import com.example.vineria.modelos.Cliente
import com.example.vineria.ui.articulos.ArticulosAdapter
import com.example.vineria.ui.articulos.ArticulosViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_articulos.*
import kotlinx.android.synthetic.main.activity_seleccionar_cliente.*


class SeleccionarClienteActivity : ApplicationActivity(R.layout.activity_seleccionar_cliente), SearchView.OnQueryTextListener, SeleccionarClienteAdapter.RecyclerItemClick {
    private val viewModel: SeleccionarClienteViewModel by viewModels()
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
        recyclerView = findViewById(R.id.rvSeleccionarCliente)
        listaClientesAdapter = SeleccionarClienteAdapter(this,this)
        recyclerView?.setAdapter(listaClientesAdapter)
        recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.addOnScrollListener(scrollClientesListener)

        svSearchCliente!!.setOnQueryTextListener(this)

        aptoParaCargar = true
        offset = 0
        viewModel.getItems(offset, "")
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
        val svSearch: SearchView = findViewById(R.id.svSearchCliente)
        svSearch.clearFocus()

        val resultIntent = Intent()
        resultIntent.putExtra("cliente", Gson().toJson(item))
        setResult(10, resultIntent)
        finish()
    }
}
