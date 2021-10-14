package com.example.vineria.ui.consultapedido

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.modelos.dto.ItemDTO

class ItemPedidoAdapter(private var items: MutableList<ItemDTO>) :
        RecyclerView.Adapter<ItemPedidoAdapter.RecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPedidoAdapter.RecyclerHolder {
        val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.consultas_list_view, parent, false)
        return RecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: ItemPedidoAdapter.RecyclerHolder, position: Int) {
        val item: ItemDTO = items[position]
        holder.tvCantidad.text = item.cantidad.toString()
        holder.tvNombreProducto.text = item.descArticulo
        holder.tvPrecioUnitario.text = "$ " + item.importeUnitario.toString() + " - " + item.listaPrecios
        holder.tvSubtotal.text = "$ " + item.importeTotal.toString()
    }

    fun updateUsuarios(i: MutableList<ItemDTO>){
        items.clear()
        items.addAll(i)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class RecyclerHolder(itemView_1: View) : RecyclerView.ViewHolder(itemView_1) {
        val tvCantidad: TextView = itemView.findViewById(R.id.txtCantidad)
        val tvNombreProducto: TextView = itemView.findViewById(R.id.txtDescripcion)
        val tvPrecioUnitario: TextView = itemView.findViewById(R.id.txtPrecioLista)
        val tvSubtotal: TextView = itemView.findViewById(R.id.txtSubTotal)
    }
}