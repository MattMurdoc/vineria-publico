package com.example.vineria.util

import android.util.Log
import android.view.View

object OtherUtils {
    val Any.TAG: String
        get() {
            val tag = javaClass.simpleName
            return if (tag.length <= 23) tag else tag.substring(0, 23)
        }

    private fun Any.log(logFuncId: Char, vararg objectsToPrint: Any?) {
        val s = objectsToPrint.joinToString(" ")
        when(logFuncId) {
            'e' -> Log.e(this.TAG, s)
            'v' -> Log.v(this.TAG, s)
            'i' -> Log.i(this.TAG, s)
        }
    }

    fun Any.logv(vararg objectsToPrint: Any?) = this.log('v', *objectsToPrint)

    fun Any.loge(vararg objectsToPrint: Any?) = this.log('e', *objectsToPrint)

    fun Any.logi(vararg objectsToPrint: Any?) = this.log('i', *objectsToPrint)

    fun Boolean.toVisibility(): Int {
        return if (this) View.VISIBLE else View.GONE
    }

    inline fun Any.safely(function: () -> Unit) {
        try {
            function()
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }
}
