package com.example.vineria.ui.pagos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.modelos.dto.PagoDTO
import java.util.*

class PagoAdapter(private var items: MutableList<PagoDTO>): RecyclerView.Adapter<PagoAdapter.RecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagoAdapter.RecyclerHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.pagos_list_view, parent, false)
        return RecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val item: PagoDTO = items[position]
        holder.txtFecha.text = item.fecha
        holder.txtCliente.text = item.nombreCliente
        holder.txtNroFactura.text = item.tipoFactura + " / " + item.nroFactura
        holder.txtTotal.text = "$ " + item.total
        holder.txtImporte.text = "$ " + item.importe
    }

    fun updatePago(pedidos: MutableList<PagoDTO>){
        items.clear()
        items.addAll(pedidos)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class RecyclerHolder(itemView_1: View) : RecyclerView.ViewHolder(itemView_1) {
        val txtFecha: TextView = itemView.findViewById(R.id.txtFecha)
        val txtCliente: TextView = itemView.findViewById(R.id.txtCliente)
        val txtNroFactura: TextView = itemView.findViewById(R.id.txtNroFactura)
        val txtTotal: TextView = itemView.findViewById(R.id.txtTotal)
        val txtImporte: TextView = itemView.findViewById(R.id.txtImporte)
    }

    init {
        items = ArrayList()
        items.addAll(items)
    }
}