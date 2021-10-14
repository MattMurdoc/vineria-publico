package com.example.vineria.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.example.vineria.R
import com.example.vineria.util.ViewUtils.visible
import kotlinx.android.synthetic.main.floating_circular_button_back.view.*
import kotlinx.android.synthetic.main.floating_circular_button_burger.view.*
import kotlinx.android.synthetic.main.horizontal_line.view.*
import kotlinx.android.synthetic.main.layout_header.view.*

class HeaderView(context: Context, attr: AttributeSet?) : FrameLayout(context, attr) {
    init {
        inflate(context, R.layout.layout_header, this)

        context.theme?.obtainStyledAttributes(attr, R.styleable.HeaderView, 0, 0)?.apply {
            horizontal_line.visible(getBoolean(R.styleable.HeaderView_hasLine, false))
            header_text_view.text = getString(R.styleable.HeaderView_title)
            val hasBurguer = getBoolean(R.styleable.HeaderView_hasBurguer, false)
            floating_circular_button_burger.visible(hasBurguer)
            floating_circular_button_back.visible(!hasBurguer)
            recycle()
        }

        // Por alguna razón, no toma el id del layout, así que lo seteamos a mano
        id = R.id.header_view
    }

    fun setOnBurguerClickListener(listener: (View)  -> Unit) {
        floating_circular_button_burger.setOnClickListener(listener)
    }

    fun setOnBackClickListener(listener: (View)  -> Unit) {
        floating_circular_button_back.setOnClickListener(listener)
    }
}