package com.example.vineria.ui.pedidos

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.vineria.R
import com.example.vineria.modelos.PEDIDO_ACCIONES
import com.example.vineria.modelos.Pedido
import com.example.vineria.ui.consultapedido.ConsultaPedidoActivity


class PedidosDialogFragment(val listener: (Pedido) -> Unit, val pedido: Pedido) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;

            val view: View = inflater.inflate(R.layout.dialog_pedidos, null, false)

            val btnConsultar : View = view.findViewById(R.id.btnConsultar)

            btnConsultar.setOnClickListener {
                val intent = Intent(context, ConsultaPedidoActivity::class.java)
                intent.putExtra("PEDIDOID",pedido.id.toString())
                startActivity(intent)
                getDialog()?.cancel()
            }

            if(!pedido.enviado) {
                val btnEnviar: View = view.findViewById(R.id.btnEnviar)
                btnEnviar.visibility = View.VISIBLE
                btnEnviar.setOnClickListener {
                    listener(pedido)
                    getDialog()?.cancel()
                }
            }

            builder.setView(view)
            builder.setNegativeButton(R.string.cerrar) { _, _ ->
                getDialog()?.cancel()
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}