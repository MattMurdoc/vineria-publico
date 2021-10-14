package com.example.vineria.ui.nuevopedido

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.modelos.dto.ArticuloItemDTO

class NuevoPedidoAdapter(private var items: MutableList<ArticuloItemDTO>, private val itemClick: NuevoPedidoAdapter.RecyclerItemClick) :
    RecyclerView.Adapter<NuevoPedidoAdapter.RecyclerHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NuevoPedidoAdapter.RecyclerHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.consultas_list_view, parent, false)
        return RecyclerHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun insertArticulo(articulo: ArticuloItemDTO) {
        items.add(articulo)
        notifyDataSetChanged()
    }

    fun removeArticulo(articulo: ArticuloItemDTO) {
        val removido: Boolean = items.remove(articulo)
        if(removido) {
            notifyDataSetChanged()
        }
    }

    fun replaceArticulo(articulo: ArticuloItemDTO) {
        val i: Int = items.indexOf(articulo)
        items[i] = articulo
        notifyDataSetChanged()
    }

    fun getArticulos(): MutableList<ArticuloItemDTO> {
        return items
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val item: ArticuloItemDTO = items[position]
        holder.tvNombreArticulo.text = item.descArticulo
        holder.tvCantidad.text = item.cantidad.toString()
        holder.tvListaPrecio.text = item.listaSeleccionada + " - $ " +item.importeUnitario
        holder.tvSubTotal.text = "$ " + item.importeTotal.toString()
        holder.itemView.setOnClickListener(){
            itemClick.itemClick(item)
        }
    }

    inner class RecyclerHolder(itemView_1: View) : RecyclerView.ViewHolder(itemView_1) {
        val tvNombreArticulo: TextView = itemView.findViewById(R.id.txtDescripcion)
        val tvListaPrecio: TextView = itemView.findViewById(R.id.txtPrecioLista)
        val tvCantidad: TextView = itemView.findViewById(R.id.txtCantidad)
        val tvSubTotal: TextView = itemView.findViewById(R.id.txtSubTotal)
    }

    interface RecyclerItemClick {
        fun itemClick(item: ArticuloItemDTO?)
    }
}