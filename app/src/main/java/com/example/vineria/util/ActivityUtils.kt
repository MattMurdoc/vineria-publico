package com.example.vineria.util

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.example.vineria.R
import com.example.vineria.util.OtherUtils.safely


object ActivityUtils {
    fun Activity.toast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    fun Activity.hideKeyboard() {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            // Por las dudas, vamos a darle y quitarle foco a una vista cualquiera.
            // Y luego vamos a cerrar el keyboard de vuelta.
            try {
                val view2 = findViewById<LinearLayout>(R.id.layout_to_prevent_auto_focus)
                view2.requestFocus()
                imm.hideSoftInputFromWindow(view2.windowToken, 0)
            } catch (e: Exception) {
                Log.e(TAG, "Ocurrio un error inesperado al re-focusear una vista.")
            }
        }
    }

    fun Activity.requestFocusAndShowKeyboard(editText: View) = safely {
        editText.requestFocus()
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    val TAG = "ActivityUtils"
}