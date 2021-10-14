package com.example.vineria.ui.nuevopago

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.clases.ApplicationViewModel.Companion.change
import com.example.vineria.modelos.Cliente
import com.example.vineria.modelos.TIPO_FAC
import com.example.vineria.modelos.dto.ImpagoDTO
import com.example.vineria.ui.nuevopago.clientes.SeleccionarClienteActivity
import com.example.vineria.ui.nuevopago.factura.SeleccionarFacturaActivity
import com.example.vineria.util.ActivityUtils.toast
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_nuevo_pago.*
import kotlinx.android.synthetic.main.activity_pedidos.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_nuevo_pago.txtImporte as txtImporte1

class NuevoPagoActivity: ApplicationActivity(R.layout.activity_nuevo_pago) {
    private val viewModel: NuevoPagoViewModel by viewModels()

    companion object {
        private val TAG = "NuevoPagoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnSeleccionarCliente.setOnClickListener({
            startActivityForResult(Intent(this, SeleccionarClienteActivity::class.java), 10)
        })

        btnSeleccionarFactura.setOnClickListener({
            startActivityForResult(Intent(this, SeleccionarFacturaActivity::class.java).apply {
                putExtra("codCliente", viewModel.cliente.toString())
                putExtra("descCliente", viewModel.descCliente)
            }, 20)
        })

        createSnackbar(btnSeleccionarFactura.rootView)

        txtImporte1.doAfterTextChanged {
            if(!txtImporte1.text.isNullOrEmpty()) {
                viewModel.importe = BigDecimal(txtImporte1.text.toString()).setScale(2, RoundingMode.HALF_UP)
            } else {
                viewModel.importe = BigDecimal.ZERO
            }
            viewModel.restante = viewModel.deuda.minus(viewModel.importe)
            txtDeuda.setText(viewModel.restante.toString())
        }

        txtBanco.doAfterTextChanged {
            viewModel.banco = txtBanco.text.toString()
        }

        txtNroCheque.doAfterTextChanged {
            viewModel.nroCheque = txtNroCheque.text.toString()
        }

        txtTitular.doAfterTextChanged {
            viewModel.titularCheque = txtTitular.text.toString()
        }

        btnCrearPago.setOnClickListener({
            viewModel.snackbar.show()
            viewModel.crearPago()
        })

        val list: MutableList<String> = ArrayList()
        list.add("Efectivo")
        list.add("Cheque")
        val adp1: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        formaPago.setAdapter(adp1)
        formaPago.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val itemSelected =  parent?.getSelectedItem().toString()
                viewModel.formaPago = itemSelected
                if (itemSelected.equals("Cheque")) {
                    banco.visibility = View.VISIBLE
                    nroCheque.visibility = View.VISIBLE
                    titular.visibility = View.VISIBLE
                    vencimiento.visibility = View.VISIBLE
                } else {
                    banco.visibility = View.GONE
                    nroCheque.visibility = View.GONE
                    titular.visibility = View.GONE
                    vencimiento.visibility = View.GONE
                }
            }
        }

        Locale.setDefault(Locale.forLanguageTag("es-AR"))

        vencimiento.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->

                    viewModel.vencCheque = Date(year, monthOfYear, dayOfMonth)

                    var dia = dayOfMonth.toString()
                    if (dia.length == 1) {
                        dia = "0" + dia
                    }

                    var mes = (monthOfYear + 1).toString()
                    if (mes.length == 1) {
                        mes = "0" + mes
                    }

                    txtVencimiento.text = (dia + "/" + mes + "/" + year)
                },
                mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        viewModel.observe(this) {
            onViewModelChange()
        }
        formaPago.isEnabled = false
    }

    private fun onViewModelChange() {
        if(viewModel.finish) {
            finish()
        }

        if(!viewModel.canCreate) {
            btnSeleccionarCliente.isEnabled = false
            btnSeleccionarFactura.isEnabled = false
            formaPago.isEnabled = false
            txtImporte1.isEnabled = false
            btnCrearPago.isEnabled = false
            txtBanco.isEnabled = false
            txtNroCheque.isEnabled = false
            txtTitular.isEnabled = false
            vencimiento.isEnabled = false
        }

        if(viewModel.cliente != viewModel.oldCliente && viewModel.oldCliente != -1) {
            viewModel.factura = -1
            viewModel.descFactura = ""
            btnSeleccionarFactura.text = "SELECCIONE FACTURA"
            viewModel.importe = BigDecimal.ZERO
            viewModel.deuda = BigDecimal.ZERO
            viewModel.tipoFac = TIPO_FAC.NONE
        }

        if(viewModel.cliente > 0 && viewModel.descCliente != "") {
            btnSeleccionarCliente.text = viewModel.descCliente
            viewModel.btnFacturaEnabled = true
            viewModel.importeEnabled = false
            viewModel.oldCliente = viewModel.cliente
        }

        if(viewModel.factura != viewModel.oldFactura && viewModel.oldFactura != -1) {
            viewModel.importe = BigDecimal.ZERO
        }

        if(viewModel.factura > 0 && viewModel.descFactura != "") {
            btnSeleccionarFactura.text = viewModel.descFactura
            viewModel.importeEnabled = true
            viewModel.oldFactura = viewModel.factura
            viewModel.bancoEnabled = true
            viewModel.nroChequeEnabled = true
            viewModel.titularEnabled = true
            viewModel.vencimientoEnabled = true
            viewModel.formaPagoEnabled = true
        }

        if(viewModel.importe.equals(BigDecimal.ZERO)) {
            txtImporte1.setText("", TextView.BufferType.EDITABLE)
            txtDeuda.setText(viewModel.deuda.toString())
        }

        if(viewModel.canCreate) {
            btnSeleccionarCliente.isEnabled = viewModel.btnClienteEnabled
            btnSeleccionarFactura.isEnabled = viewModel.btnFacturaEnabled
            txtImporte1.isEnabled = viewModel.importeEnabled
            btnCrearPago.isEnabled = viewModel.canCreate
            formaPago.isEnabled = viewModel.formaPagoEnabled
            txtBanco.isEnabled = viewModel.bancoEnabled
            txtNroCheque.isEnabled = viewModel.nroChequeEnabled
            txtTitular.isEnabled = viewModel.titularEnabled
            vencimiento.isEnabled = viewModel.vencimientoEnabled
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult" + "RequestCode: ${requestCode}, ResultCode: ${resultCode}, Data: ${data}")

        if(requestCode == 10 && resultCode == 10) {
            val cliente: Cliente = Gson().fromJson(data?.getStringExtra("cliente"), Cliente::class.java);
            viewModel.change {
                this.cliente = cliente.codigo
                this.descCliente = cliente.nombre
            }
        }

        if(requestCode == 20 && resultCode == 10) {
            val impagoDTO: ImpagoDTO = Gson().fromJson(data?.getStringExtra("factura"), ImpagoDTO::class.java);
            viewModel.change {
                this.factura = impagoDTO.id
                this.descFactura = impagoDTO.descTipoFac + " / " + impagoDTO.nroFac
                this.deuda = impagoDTO.restante
                this.tipoFac = impagoDTO.tipoFac
            }
        }
    }

    fun createSnackbar(view: View){
        val snackbar: Snackbar = Snackbar.make(view, "Guardando", Snackbar.LENGTH_INDEFINITE)
        val viewGroup = snackbar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text).parent as ViewGroup
        viewGroup.addView(ProgressBar(this))
        viewModel.snackbar = snackbar
    }
}