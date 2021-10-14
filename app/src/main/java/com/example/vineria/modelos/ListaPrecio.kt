package com.example.vineria.modelos

import java.math.BigDecimal

class ListaPrecio(val numeroLista: String, val importe: BigDecimal) {
    override fun toString(): String {
        return numeroLista + " - $ " + importe.toString()
    }
}