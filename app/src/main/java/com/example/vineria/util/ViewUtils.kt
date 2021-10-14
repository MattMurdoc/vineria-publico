package com.example.vineria.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.vineria.R
import com.google.android.material.textfield.TextInputLayout

object ViewUtils {
    private val TAG = OtherUtils.javaClass.toString()

    fun ViewGroup.inflate(layoutResource: Int): View {
        return LayoutInflater.from(this.context).inflate(layoutResource, this, false)
    }

    fun ViewGroup.inflateAndAdd(layoutResource: Int): View {
        val view = this.inflate(layoutResource)
        this.addView(view)
        return view
    }

    fun View.tintBackground(color: Int) {
        var background: Drawable = this.getBackground()
        background = DrawableCompat.wrap(background)
        DrawableCompat.setTint(background, color)
        this.setBackground(background)
    }

    // Devuelve true si la vista está siendo mostrada en pantalla.
    fun View.isVisibleOnScreen(): Boolean {
        if (!this.isShown) {
            return false
        }
        val actualPosition = Rect()
        this.getGlobalVisibleRect(actualPosition)
        val screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels
        val screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels
        val screen = Rect(0, 0, screenWidth, screenHeight)
        return actualPosition.intersect(screen)
    }

    fun View.visible(value: Boolean) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }

    fun View.invisible(value: Boolean) {
        this.visibility = if (value) View.INVISIBLE else View.VISIBLE
    }

    fun ScrollView.setOnScrollListener(listener: () -> Unit) {
        this.getViewTreeObserver().addOnScrollChangedListener(object : ViewTreeObserver.OnScrollChangedListener {
            override fun onScrollChanged() {
                listener()
            }
        })
    }

    fun Boolean.toVisibility(): Int {
        return if (this) View.VISIBLE else View.GONE
    }

    fun TextInputLayout.setAssertionToTextInputLayout(context: Context, asserted: Boolean) {
        val assertedColor = ContextCompat.getColor(context, R.color.background)
        val whiteColor = ContextCompat.getColor(context, R.color.white)
        this.boxBackgroundColor = if (asserted) assertedColor else whiteColor
    }

    fun TextInputLayout.setErrorToTextInputLayout(hasError: Boolean, errorText: String){
        this.isErrorEnabled = hasError
        this.error = if (hasError) errorText else ""
    }

}