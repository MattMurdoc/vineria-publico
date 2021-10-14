package com.example.vineria.ui.empleados

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vineria.R
import com.example.vineria.clases.ApplicationActivity
import com.example.vineria.clases.ApplicationViewModel.Companion.change
import com.example.vineria.modelos.Empleado
import kotlin.collections.ArrayList

class EmpleadosActivity : ApplicationActivity(R.layout.activity_empleados), EmpleadosAdapter.RecyclerItemClick, SearchView.OnQueryTextListener {
    private val viewModel: EmpleadosViewModel by viewModels()

    private var rvLista: RecyclerView? = null
    private var svSearch: SearchView? = null
    private lateinit var adapter: EmpleadosAdapter
    private var items: List<Empleado>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initValues()
        initListener()

        val svSearch: SearchView = findViewById(R.id.svSearchEmpleado)
        val rvUsuario: RecyclerView = findViewById(R.id.rvListaEmpleados)
        val rlTextView: RelativeLayout = findViewById(R.id.rlTextViewEmpleados)


        viewModel.listUsuario?.observe(this,{

            if(it.isNullOrEmpty()){
                svSearch.visibility = View.INVISIBLE
                rvUsuario.visibility = View.INVISIBLE
                rlTextView.visibility = View.VISIBLE
                //Toast.makeText(baseContext,"Sincronizar Usuarios", Toast.LENGTH_SHORT).show()
            }else{
                svSearch.visibility = View.VISIBLE
                rvUsuario.visibility = View.VISIBLE
                rlTextView.visibility = View.GONE
            }

            adapter.updateUsuarios(it)
        })

        viewModel.getItems()
    }

    private fun initViews() {
        rvLista = findViewById(R.id.rvListaEmpleados)
        svSearch = findViewById(R.id.svSearchEmpleado)
    }

    private fun initValues() {
        val manager = LinearLayoutManager(this)

        rvLista!!.layoutManager = manager
        adapter = EmpleadosAdapter(items as MutableList<Empleado>, this)
        rvLista!!.adapter = adapter

    }

    private fun initListener() {
        svSearch!!.setOnQueryTextListener(this)
    }

    override fun itemClick(item: Empleado?) {

        val svSearch: SearchView = findViewById(R.id.svSearchEmpleado)
        svSearch.setQuery("", false);
        svSearch.clearFocus()

        viewModel.change {
            if (item != null) {
                nombreYApellido = item.nombre
            }
        }

        viewModel.change {
            if (item != null) {
                id = item.codigo
            }
        }

        viewModel.save()

        viewModel.observe(this) {
            onViewModelChange()
        }
    }

    private fun onViewModelChange() {
        navigationBar?.changeName()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        adapter.filter(newText)
        return false
    }

    override fun getMyNavButtonId() = R.id.nav_my_user
}
