package com.example.vineria.ui.nuevopago.clientes

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.modelos.Articulo
import com.example.vineria.modelos.Cliente
import com.example.vineria.ui.articulos.ArticulosActivity
import com.google.android.material.snackbar.Snackbar
import java.util.*
import java.util.stream.Collectors

class SeleccionarClienteAdapter(
    context: Context,
    private val itemClick: RecyclerItemClick
) :
    RecyclerView.Adapter<SeleccionarClienteAdapter.ViewHolder>() {

    private val dataset: MutableList<Cliente>
    private val context: Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cliente_list_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val c: Cliente = dataset[i]
        viewHolder.txtNombre.text = c.nombre
        viewHolder.txtDocumento.text = c.cuit

        viewHolder.itemView.setOnClickListener { view ->
            itemClick.itemClick(c)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun adicionarListaCliente(listaCliente: MutableList<Cliente>?, clean: Boolean) {
        if (clean) {
            dataset.clear()
        }
        dataset.addAll(listaCliente!!)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView
        val txtDocumento: TextView

        init {
            txtNombre = itemView.findViewById(R.id.txtNombre)
            txtDocumento = itemView.findViewById(R.id.txtDocumento)
        }
    }

    init {
        this.context = context
        dataset = ArrayList()
    }

    interface RecyclerItemClick {
        fun itemClick(item: Cliente?)
    }
}