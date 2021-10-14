package com.example.vineria.ui.nuevopedido

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.clases.ApplicationViewModel.Companion.change
import com.example.vineria.modelos.dto.ArticuloItemDTO
import com.example.vineria.modelos.dto.ClienteDTO
import com.example.vineria.ui.articulos.ArticulosActivity
import com.example.vineria.ui.nuevopedido.clientes.SeleccionarClienteActivityPedido
import com.example.vineria.util.ActivityUtils.toast
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_nuevo_pedido.*
import java.math.BigDecimal


class NuevoPedidoActivity: ApplicationActivity(R.layout.activity_nuevo_pedido), NuevoPedidoAdapter.RecyclerItemClick {
    private val viewModel: NuevoPedidoViewModel by viewModels()

    companion object {
        private val TAG = "NuevoPedidoActivity"
    }

    private var rvLista: RecyclerView? = null
    private lateinit var adapter: NuevoPedidoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initValues()

        btnAddArticulo.setOnClickListener {
            startActivityForResult(Intent(this, ArticulosActivity::class.java), 20)
        }

        btnSeleccionarCliente.setOnClickListener {
            startActivityForResult(Intent(this, SeleccionarClienteActivityPedido::class.java), 10)
        }

        txtTotal.text = "$ " + viewModel.total.toString()
        txtProducto1.text = viewModel.productos.toString()

        createSnackbar(btnCrearPedido.rootView)

        btnCrearPedido.setOnClickListener {
            val valido: Boolean = viewModel.validarPedido(adapter.getArticulos())
            if(valido) {
                it.let {
                    val builder = AlertDialog.Builder(it.context)
                    builder.apply {
                        setTitle("Observación")
                        val input = EditText(it.context)
                        input.inputType = InputType.TYPE_CLASS_TEXT
                        input.setText(viewModel.observacion)
                        builder.setView(input)
                        setPositiveButton("Aceptar") { dialog, id ->
                            viewModel.observacion = input.text.toString()
                            viewModel.snackbar.show()
                            viewModel.crearPedido(adapter.getArticulos())
                            dialog.dismiss()
                        }
                    }
                    builder.create()
                }.show()
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as String
                viewModel.tipoFac = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        viewModel.observe(this) {
            onViewModelChange()
        }
    }

    private fun onViewModelChange() {
        if(viewModel.finish) {
            finish()
        }

        btnSeleccionarCliente.isEnabled = viewModel.canCreate
        spinner.isEnabled = viewModel.canCreate
        btnAddArticulo.isEnabled = viewModel.canCreate
        btnCrearPedido.isEnabled = viewModel.canCreate

        if(viewModel.descCliente != viewModel.oldDescCliente && !viewModel.descCliente.isEmpty()) {
            viewModel.tipoFac = ""
        }

        if(!viewModel.descCliente.isEmpty()) {
            btnSeleccionarCliente.text = viewModel.descCliente
            viewModel.oldDescCliente = viewModel.descCliente
        }

        if(viewModel.tipoFac.isEmpty()) {
            if(viewModel.tipoIva == null) {
                val list: MutableList<String> = ArrayList()
                list.add("Presupuesto")

                val adp1: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.setAdapter(adp1)
                spinner.setSelection(0)
            } else if(viewModel.tipoIva!! == 1) {
                val list: MutableList<String> = ArrayList()
                list.add("Factura A")
                list.add("Presupuesto")

                val adp1: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.setAdapter(adp1)
                spinner.setSelection(0)
            } else if(viewModel.tipoIva!! > 1) {
                val list: MutableList<String> = ArrayList()
                list.add("Factura B")
                list.add("Presupuesto")

                val adp1: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.setAdapter(adp1)
                spinner.setSelection(0)
            }
        }

        txtTotal.text = "$ " + viewModel.total.toString()
        txtProducto1.text = viewModel.productos.toString()
    }


    private fun initViews() {
        rvLista = findViewById(R.id.rvListaNuevoPedido)
    }

    private fun initValues() {
        val manager = LinearLayoutManager(this)

        rvLista!!.layoutManager = manager
        adapter = NuevoPedidoAdapter(mutableListOf(), this)
        rvLista!!.adapter = adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult" + "RequestCode: ${requestCode}, ResultCode: ${resultCode}, Data: ${data}")

        if(requestCode == 10 && resultCode == 10) {
            val cliente: ClienteDTO = Gson().fromJson(data?.getStringExtra("cliente"), ClienteDTO::class.java);
            viewModel.change {
                this.cliente = cliente.codCliente
                this.descCliente = cliente.descCliente
                this.tipoIva = cliente.tipoIVA
            }
        }

        if(requestCode == 20 && resultCode == 20) {
            val articulo: ArticuloItemDTO = Gson().fromJson(data?.getStringExtra("articulo"), ArticuloItemDTO::class.java)
            val articulos = adapter.getArticulos()
            val arti = articulos.find { it.idArticulo == articulo.idArticulo }
            if(arti != null) {
                toast("El artículo ya fue agregado")
            } else {
                val dialog = ArticuloDialogFragment(articulo = articulo, aceptar =
                {
                    adapter.insertArticulo(it)
                    calcularTotal()
                }, borrar = {
                    adapter.removeArticulo(it)
                    calcularTotal()
                })
                dialog.show(supportFragmentManager, "articuloDialog")
            }
        }
    }

    override fun itemClick(item: ArticuloItemDTO?) {
        val dialog = ArticuloDialogFragment(articulo = item!!, aceptar =
        {
            adapter.replaceArticulo(it)
            calcularTotal()
        }, borrar = {
            adapter.removeArticulo(it)
            calcularTotal()
        })
        dialog.show(supportFragmentManager, "articuloDialog")
    }

    fun calcularTotal() {
        val articulos = adapter.getArticulos()
        var importeTotal = BigDecimal.ZERO
        for(articulo in articulos) {
            importeTotal = importeTotal.add(articulo.importeTotal)
        }

        viewModel.change {
            viewModel.total = importeTotal
            viewModel.productos = articulos.size
        }
    }

    fun createSnackbar(view: View){
        val snackbar: Snackbar = Snackbar.make(view, "Guardando", Snackbar.LENGTH_INDEFINITE)
        val viewGroup = snackbar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text).parent as ViewGroup
        viewGroup.addView(ProgressBar(this))
        viewModel.snackbar = snackbar
    }
}