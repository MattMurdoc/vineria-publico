package com.example.vineria.ui.articulos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.modelos.Articulo
import com.google.android.material.snackbar.Snackbar
import java.util.*

class ArticulosAdapter(context: Context, private val itemClick: RecyclerItemClick) :
    RecyclerView.Adapter<ArticulosAdapter.ViewHolder>() {

    private val dataset: MutableList<Articulo>
    private val context: Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.articulos_list_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val a: Articulo = dataset[i]
        viewHolder.tvNombreArticulo.text = a.nombre

        viewHolder.itemView.setOnClickListener { view ->
            Snackbar.make(view,a.nombre+ " seleccionado.", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
            itemClick.itemClick(a)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun adicionarListaArticulo(listaArticulo: MutableList<Articulo>?, clean: Boolean) {
        if(clean){
            dataset.clear()
        }
        dataset.addAll(listaArticulo!!)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreArticulo: TextView

        init {
            tvNombreArticulo = itemView.findViewById(R.id.tvNombreArticulo)
        }
    }

    init {
        this.context = context
        dataset = ArrayList()
    }

    interface RecyclerItemClick {
        fun itemClick(item: Articulo?)
    }
}
