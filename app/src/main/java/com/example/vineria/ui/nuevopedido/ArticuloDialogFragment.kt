package com.example.vineria.ui.nuevopedido

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.vineria.R
import com.example.vineria.modelos.ListaPrecio
import com.example.vineria.modelos.dto.ArticuloItemDTO
import java.math.BigDecimal


class ArticuloDialogFragment(
    val aceptar: (ArticuloItemDTO) -> Unit,
    val borrar: (ArticuloItemDTO) -> Unit,
    val articulo: ArticuloItemDTO
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val view: View = inflater.inflate(R.layout.dialog_articulo, null, false)

            val txtNombre: TextView = view.findViewById(R.id.txtNombre)
            val txtCantidad: TextView = view.findViewById(R.id.txtCantidad)
            val listaPrecio: Spinner = view.findViewById(R.id.listaPrecio)

            txtNombre.text = articulo.descArticulo
            txtCantidad.setText(articulo.cantidad.toString())
            val list: MutableList<ListaPrecio> = ArrayList()
            if (articulo.iniL1 == '0') {
                list.add(ListaPrecio("L1", articulo.precioL1!!))
            }

            if (articulo.iniL2 == '0') {
                list.add(ListaPrecio("L2", articulo.precioL2!!))
            }

            if (articulo.iniL3 == '0') {
                list.add(ListaPrecio("L3", articulo.precioL3!!))
            }

            val adapter: ArrayAdapter<ListaPrecio> =
                ArrayAdapter<ListaPrecio>(it, android.R.layout.simple_list_item_1, list)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            listaPrecio.setAdapter(adapter)

            if(articulo.listaSeleccionada.isEmpty()) {
                listaPrecio.setSelection(0)
            } else {
                for (position in 0 until adapter.getCount()) {
                    if (adapter.getItem(position)!!.numeroLista === articulo.listaSeleccionada) {
                        listaPrecio.setSelection(position)
                        break
                    }
                }
            }

            builder.setView(view)

            builder.setPositiveButton("Aceptar", null)

            builder.setNegativeButton(R.string.cerrar) { _, _ ->
                getDialog()?.cancel()
            }

            if(!articulo.nuevo) {
                builder.setNeutralButton("Borrar") { _, _ ->
                    borrar.invoke(articulo)
                }
            }

            val alert: AlertDialog = builder.create()

            alert.show()

            alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (txtCantidad.text.toString().isEmpty()) {
                    Toast.makeText(it.context, "La cantidad no puede ser vacía", Toast.LENGTH_LONG).show()
                } else if (Integer.parseInt(txtCantidad.text.toString()).compareTo(0) <= 0) {
                    Toast.makeText(it.context,"La cantidad no puede ser menor o igual a 0", Toast.LENGTH_LONG).show()
                } else {
                    val cantidad = Integer.parseInt(txtCantidad.text.toString())
                    articulo.cantidad = cantidad
                    val lista = (listaPrecio.selectedItem as ListaPrecio)
                    articulo.listaSeleccionada = lista.numeroLista
                    articulo.importeUnitario = lista.importe
                    articulo.importeTotal = lista.importe.multiply(BigDecimal(cantidad.toString()))
                    articulo.nuevo = false
                    aceptar.invoke(articulo)
                    alert.dismiss()
                }
            }

            return alert

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

