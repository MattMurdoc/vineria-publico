package com.example.vineria.ui.sincronizacion

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.modelos.SyncStatus
import kotlinx.android.synthetic.main.activity_sync.*


class SyncActivity: ApplicationActivity(R.layout.activity_sync) {
    private val viewModel: SyncViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnSync.setOnClickListener {
            viewModel.sync()

        }

        viewModel.observe(this) {
            onViewModelChange()
        }

        initRecyclerView()
    }

    internal fun initRecyclerView() {
        val dataSet: HashMap<Int, Pair<String, MutableLiveData<SyncStatus>>> = HashMap()
        dataSet.put(0, Pair("Cliente", viewModel.clienteWorkStatus))
        dataSet.put(1, Pair("Empleado", viewModel.empleadoWorkStatus))
        dataSet.put(2, Pair("Articulo", viewModel.articuloWorkStatus))
        dataSet.put(3, Pair("Envase", viewModel.envaseWorkStatus))
        dataSet.put(4, Pair("Factura", viewModel.facturaWorkStatus))
        dataSet.put(5, Pair("Presupuesto", viewModel.presupuestoWorkStatus))
        val workerAdapter = WorkerAdapter(dataSet, this)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = workerAdapter
    }

    private fun onViewModelChange() {
    }

    override fun getMyNavButtonId() = R.id.nav_sync
}