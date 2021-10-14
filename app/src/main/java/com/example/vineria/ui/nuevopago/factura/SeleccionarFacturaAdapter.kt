package com.example.vineria.ui.nuevopago.factura

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.modelos.Cliente
import com.example.vineria.modelos.dto.ImpagoDTO

class SeleccionarFacturaAdapter(private var items: MutableList<ImpagoDTO>, private val itemClick: RecyclerItemClick) :
    RecyclerView.Adapter<SeleccionarFacturaAdapter.RecyclerHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.factura_list_view, parent, false)
        return RecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val item: ImpagoDTO = items[position]
        holder.txtFecha.text = item.fecha.toString()
        holder.txtTipoNro.text = item.descTipoFac + " / " + item.nroFac
        holder.txtTotal.text = item.total.toString()
        holder.txtRestante.text = item.restante.toString()

        holder.itemView.setOnClickListener { view ->
            itemClick.itemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(impagos: MutableList<ImpagoDTO>){
        items.clear()
        items.addAll(impagos)

        notifyDataSetChanged()
    }

    inner class RecyclerHolder(itemView_1: View) : RecyclerView.ViewHolder(itemView_1) {
        val txtFecha: TextView = itemView.findViewById(R.id.txtFecha)
        val txtTipoNro: TextView = itemView.findViewById(R.id.txtTipoNro)
        val txtTotal: TextView = itemView.findViewById(R.id.txtTotal)
        val txtRestante: TextView = itemView.findViewById(R.id.txtRestante)
    }

    interface RecyclerItemClick {
        fun itemClick(item: ImpagoDTO?)
    }
}