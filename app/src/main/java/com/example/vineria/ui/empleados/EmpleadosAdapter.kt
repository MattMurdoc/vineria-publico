package com.example.vineria.ui.empleados

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.modelos.Empleado
import com.google.android.material.snackbar.Snackbar
import java.util.*
import java.util.stream.Collectors


class EmpleadosAdapter(private var items: MutableList<Empleado>, private val itemClick: RecyclerItemClick) :
    RecyclerView.Adapter<EmpleadosAdapter.RecyclerHolder>() {

    private var originalItems: MutableList<Empleado>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.empleados_list_view, parent, false)
        return RecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val item: Empleado = items[position]
        holder.tvNombreApellido.text = item.nombre

        holder.itemView.setOnClickListener { view ->
            Snackbar.make(view,item.nombre+ " seleccionado.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            itemClick.itemClick(item)
        }

        if (item.seleccionado == true){
            holder.itemView.setBackgroundColor(Color.parseColor("#737373"))
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        }

    }

    fun updateUsuarios(users: MutableList<Empleado>){
        items.clear()
        items.addAll(users)
        originalItems.clear()
        originalItems.addAll(users)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun filter(strSearch: String) {
        if (strSearch.isEmpty()) {
            items.clear()
            items.addAll(originalItems)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                items.clear()
                val collect: List<Empleado> = originalItems.stream()
                    .filter { i: Empleado ->
                        i.nombre!!.toLowerCase(Locale.ROOT).contains(strSearch)
                    }
                    .collect(Collectors.toList())
                items.addAll(collect)
            } else {
                items.clear()
                for (i in originalItems) {
                    if (i.nombre!!.toLowerCase(Locale.ROOT).contains(strSearch)) {
                        items.add(i)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

    inner class RecyclerHolder(itemView_1: View) : RecyclerView.ViewHolder(itemView_1) {
        val tvNombreApellido: TextView = itemView.findViewById(R.id.tvNombreApellido)
    }

    interface RecyclerItemClick {
        fun itemClick(item: Empleado?)
    }

    init {
        originalItems = ArrayList()
        originalItems.addAll(items)
    }
}