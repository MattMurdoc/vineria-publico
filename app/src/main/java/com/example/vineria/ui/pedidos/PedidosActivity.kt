package com.example.vineria.ui.pedidos

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.modelos.Item
import com.example.vineria.modelos.PEDIDO_ACCIONES
import com.example.vineria.modelos.Pedido
import com.example.vineria.persistance.database.AppDatabase
import com.example.vineria.ui.nuevopedido.NuevoPedidoActivity
import com.example.vineria.util.ActivityUtils.toast
import kotlinx.android.synthetic.main.activity_pedidos.*
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList


class PedidosActivity : ApplicationActivity(R.layout.activity_pedidos),
    PedidosAdapter.RecyclerItemClick {
    private val viewModel: PedidosViewModel by viewModels()

    private var rvLista: RecyclerView? = null
    private lateinit var adapter: PedidosAdapter
    private var items: List<Pedido>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initValues()

        val rvUsuario: RecyclerView = findViewById(R.id.rvListaPedidos)
        val rlTextView: RelativeLayout = findViewById(R.id.rlTextViewPedidos)

        viewModel.listPedido?.observe(this, {

            if (it.isNullOrEmpty()) {
                rvUsuario.visibility = View.INVISIBLE
                rlTextView.visibility = View.VISIBLE
            } else {
                rvUsuario.visibility = View.VISIBLE
                rlTextView.visibility = View.GONE
            }

            adapter.updatePedidos(it)
        })
        Locale.setDefault(Locale.forLanguageTag("es-AR"))

        val c: Calendar = Calendar.getInstance()
        val fechaHora = DateTime(c.time)
        val fmt: DateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy")
        txtFechaDesde.text = fechaHora.toString(fmt)
        txtFechaHasta.text = fechaHora.toString(fmt)

        fechaDesde.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->

                    viewModel.fechaDesde = year.toString() + "-" + (monthOfYear +1).toString() + "-" + dayOfMonth.toString()

                    var dia = dayOfMonth.toString()
                    if (dia.length == 1) {
                        dia = "0" + dia
                    }

                    var mes = (monthOfYear + 1).toString()
                    if (mes.length == 1) {
                        mes = "0" + mes
                    }

                    txtFechaDesde.text = (dia + "/" + mes + "/" + year)
                },
                mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }


        fechaHasta.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->

                    viewModel.fechaHasta = year.toString() + "-" + (monthOfYear +1).toString() + "-" + dayOfMonth.toString()

                    var dia = dayOfMonth.toString()
                    if (dia.length == 1) {
                        dia = "0" + dia
                    }

                    var mes = (monthOfYear + 1).toString()
                    if (mes.length == 1) {
                        mes = "0" + mes
                    }

                    txtFechaHasta.text = (dia + "/" + mes + "/" + year)
                },
                mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        ivEnviar.setOnClickListener {
            it.let {
                val builder = AlertDialog.Builder(it.context)
                builder.apply {
                    setTitle("¿Desea enviar todos los pedidos?")
                    setPositiveButton("Aceptar") { dialog, id ->
                        viewModel.enviarTodos()
                    }
                    setNegativeButton("Cancelar") { _, _ ->  }
                }
                builder.create()
            }.show()
        }

        btnBuscar.setOnClickListener {
            viewModel.buscar()
        }

        viewModel.getItems()

        fabNuevoPedido.setOnClickListener {
            startActivity(Intent(this, NuevoPedidoActivity::class.java))
        }

        viewModel.observe(this) {
            onViewModelChange()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getItems()
    }

    private fun onViewModelChange() {
        ivEnviar.isEnabled = viewModel.canSave
        btnBuscar.isEnabled = viewModel.canSearch
    }

    private fun initViews() {
        rvLista = findViewById(R.id.rvListaPedidos)
    }

    private fun initValues() {
        val manager = LinearLayoutManager(this)

        rvLista!!.layoutManager = manager
        adapter = PedidosAdapter(items as MutableList<Pedido>, this)
        rvLista!!.adapter = adapter

    }

    override fun itemClick(item: Pedido?) {

        val pedidosDialogFragment = PedidosDialogFragment(pedido = item!!, listener =
        {
            viewModel.enviarPedido(it)
        })
        pedidosDialogFragment.show(supportFragmentManager, "pedidosDialog")
    }

    override fun getMyNavButtonId() = R.id.nav_pedidos
}