package com.example.vineria.ui.pedidos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.modelos.Pedido
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*


class PedidosAdapter(private var items: MutableList<Pedido>, private val itemClick: RecyclerItemClick) :
    RecyclerView.Adapter<PedidosAdapter.RecyclerHolder>() {

    private var originalItems: MutableList<Pedido>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosAdapter.RecyclerHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.pedidos_list_view, parent, false)
        return RecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val item: Pedido = items[position]
        holder.tvNumeroPedido.text = item.id.toString()
        val fechaHora = DateTime(item.fechaHora.time)
        val fmt: DateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy")
        holder.tvFecha.text = fechaHora.toString(fmt)
        holder.tvCliente.text = item.descCliente
        holder.tvTotal.text = "$ ${item.total.toString()}"

        if(item.enviado){
            holder.ivEstadoPedido.setImageResource(R.drawable.ic_baseline_check)
        }else{
            holder.ivEstadoPedido.setImageResource(R.drawable.ic_baseline_access_time)
        }

        holder.itemView.setOnClickListener(){
            notifyDataSetChanged();
            itemClick.itemClick(item)
        }
    }

    fun updatePedidos(pedidos: MutableList<Pedido>){
        items.clear()
        items.addAll(pedidos)
        originalItems.clear()
        originalItems.addAll(pedidos)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class RecyclerHolder(itemView_1: View) : RecyclerView.ViewHolder(itemView_1) {
        val tvNumeroPedido: TextView = itemView.findViewById(R.id.txtNroPedido)
        val tvFecha: TextView = itemView.findViewById(R.id.txtFecha)
        val tvCliente: TextView = itemView.findViewById(R.id.txtCliente)
        val tvTotal: TextView = itemView.findViewById(R.id.txtTotal)
        val ivEstadoPedido: ImageView = itemView.findViewById(R.id.ivEstadoPedido)
    }

    interface RecyclerItemClick {
        fun itemClick(item: Pedido?)
    }

    init {
        originalItems = ArrayList()
        originalItems.addAll(items)
    }
}