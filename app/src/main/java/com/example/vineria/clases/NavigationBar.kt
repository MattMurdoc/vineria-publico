package com.example.vineria.clases

import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.vineria.R
import com.example.vineria.repository.EmpleadoRepository
import com.example.vineria.ui.pagos.PagosActivity
import com.example.vineria.ui.sincronizacion.SyncActivity
import com.example.vineria.ui.empleados.EmpleadosActivity
import com.example.vineria.ui.pedidos.PedidosActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.navigation_bar_header.view.*

class NavigationBar(private val activity: AppCompatActivity,
                    private val drawerLayout: DrawerLayout
): NavigationView.OnNavigationItemSelectedListener {
    // Constantes:
    private val LAYOUT = R.layout.navigation_bar
    private val HEADER_LAYOUT = R.layout.navigation_bar_header

    private val perfilRepository = EmpleadoRepository(activity)

    // Views
    private val navigationView = drawerLayout.findViewById<NavigationView>(R.id.navigation_bar)
    private val header: View = navigationView.getHeaderView(0)

    init {
        changeName()

        // Esto es para que funcione el onNavigationItemSelected:
        navigationView.setNavigationItemSelectedListener(this)

        navigationView.menu.findItem(applicationActivity.getMyNavButtonId()).isChecked = true
    }


    fun open() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun close() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun isOpen(): Boolean = drawerLayout.isDrawerOpen(GravityCompat.START)

    private fun newIntent(cls: Class<*>?) {
        // Cierra el navigation bar
        drawerLayout.closeDrawer(GravityCompat.START)

        // Abre el nuevo activity
        val intent = Intent(activity, cls)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        activity.startActivity(intent)
        activity.finish()
    }

    fun changeName() {
        val perfil = perfilRepository.getPerfilInstance()
        header.nav_header_title.setText(perfil.nombre)
    }

    companion object {
        private val TAG = "NavigationBar"
    }

//    fun openCallHelpDialog() {
//        safely {
//            val phones = profileRepository.getPerfilInstance().telefonosDeContacto
//            CallHelpDialog(profileRepository.getTelTaaxii(), activity, phones).show()
//        }
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()

        when(id) {
            R.id.nav_pedidos -> {
                Log.e(TAG, "pedidos_event")
                newIntent(PedidosActivity::class.java)
            }
            R.id.nav_pagos -> {
                Log.e(TAG, "pagos_event")
                newIntent(PagosActivity::class.java)
            }
            R.id.nav_sync -> {
                Log.e(TAG, "sync_event")
                newIntent(SyncActivity::class.java)
            }
            R.id.nav_my_user -> {
                Log.e(TAG, "usuario_event")
                newIntent(EmpleadosActivity::class.java)
            }

            else -> { Log.e(TAG, "Item de NavigationBar indefinido.") }
        }

        return activity.onOptionsItemSelected(item)
    }

    private val applicationActivity : ApplicationActivity get() = activity as ApplicationActivity
}