package com.example.vineria.ui.articulos

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.modelos.Articulo
import com.example.vineria.modelos.dto.ArticuloItemDTO
import com.example.vineria.modelos.dto.ClienteDTO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_articulos.*
import java.math.BigDecimal

class ArticulosActivity : ApplicationActivity(R.layout.activity_articulos), SearchView.OnQueryTextListener, ArticulosAdapter.RecyclerItemClick {
    private val viewModel: ArticulosViewModel by viewModels()
    private var offset = 0
    private var aptoParaCargar = false
    private var recyclerView: RecyclerView? = null
    private var listaArticulosAdapter: ArticulosAdapter? = null
    private var scrollArticulosListener = object : RecyclerView.OnScrollListener() {
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
        recyclerView = findViewById(R.id.rvListaArticulos)
        listaArticulosAdapter = ArticulosAdapter(this,this)
        recyclerView?.setAdapter(listaArticulosAdapter)
        recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.addOnScrollListener(scrollArticulosListener)

        svSearchArticulo!!.setOnQueryTextListener(this)

        aptoParaCargar = true
        offset = 0
        viewModel.getItems(offset, "")
        viewModel.listArticulo?.observe(this) {
            listaArticulosAdapter!!.adicionarListaArticulo(it.first,it.second)
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
            recyclerView?.addOnScrollListener(scrollArticulosListener)
        } else {
            viewModel.getItems(offset, newText)
            recyclerView?.clearOnScrollListeners()
            recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {})
        }

        return false
    }

    override fun itemClick(item: Articulo?) {
        val arti = ArticuloItemDTO(
            idArticulo = item!!.codigo,
            descArticulo = item.nombre,
            importeUnitario = BigDecimal.ZERO,
            cantidad = 1,
            importeTotal = BigDecimal.ZERO,
            iniL1 = item.inhiL1,
            iniL2 = item.inhiL2,
            iniL3 = item.inhiL3,
            precioL1 = item.precioLista1,
            precioL2 = item.precioLista2,
            precioL3 = item.precioLista3,
            listaSeleccionada = ""
        )
        val resultIntent = Intent()
        resultIntent.putExtra("articulo", Gson().toJson(arti))
        setResult(20, resultIntent)
        finish()
    }

    companion object {
        private const val TAG = "ARTICULOS ACTIVITY"
    }
}
