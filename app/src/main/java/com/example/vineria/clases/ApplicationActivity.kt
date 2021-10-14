package com.example.vineria.clases

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.vineria.R
import com.example.vineria.components.HeaderView
import com.example.vineria.ui.empleados.EmpleadosActivity

abstract class ApplicationActivity(private val layoutId: Int?) : AppCompatActivity() {
    var navigationBar: NavigationBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customOnCreate()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        customOnCreate()
    }

    private fun customOnCreate() {
        // Usamos el layout para inflar la actividad
        if (layoutId != null) {
            setContentView(layoutId)
        }

        // Por las dudas, en caso de estar usando la action bar
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // En caso de que la actividad tenga un drawer layout...
        findViewById<DrawerLayout>(R.id.drawer_layout)?.let { drawer ->
            navigationBar = NavigationBar(this, drawer)
        }

        // En caso de que la actividad tenga un header...
        findViewById<HeaderView>(R.id.header_view)?.let { header ->
            header.setOnBackClickListener {
                onBackPressed()
            }

            header.setOnBurguerClickListener {
                navigationBar?.open()
            }
        }
    }

    // Implemenetar esto en cada activity que tenga un drawer
    open fun getMyNavButtonId() = R.id.nav_pedidos

    open fun goToMyTravels() {
//        val intent = Intent(this, MyTravelsActivity::class.java)
//        startActivity(intent)
    }

    open fun goToMyAccounts() {
//        startActivity(Intent(this, MyAccountsActivity::class.java))
    }

    open fun goToProfile() {
        startActivity(Intent(this, EmpleadosActivity::class.java))
    }
}