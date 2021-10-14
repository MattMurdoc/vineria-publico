package com.example.vineria.modelos.dto

import java.math.BigDecimal

class ArticuloItemDTO(
    val idArticulo: Int,
    val descArticulo: String,
    var cantidad: Int,
    var importeUnitario: BigDecimal,
    var importeTotal: BigDecimal,
    var listaSeleccionada: String,
    val iniL1: Char?,
    val precioL1: BigDecimal?,
    val iniL2: Char?,
    val precioL2: BigDecimal?,
    val iniL3: Char?,
    val precioL3: BigDecimal?
) {
    var nuevo: Boolean = true
}