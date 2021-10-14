package com.example.vineria.modelos.dto

import java.math.BigDecimal

class ItemDTO(val descArticulo: String,
              val cantidad: Int,
              val importeUnitario: BigDecimal,
              val importeTotal: BigDecimal,
              val listaPrecios: String)