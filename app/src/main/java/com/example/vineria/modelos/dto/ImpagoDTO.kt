package com.example.vineria.modelos.dto

import com.example.vineria.modelos.TIPO_FAC
import java.math.BigDecimal
import java.util.*

class ImpagoDTO(var id: Int,
                var fecha: String,
                var fechaDate: Date,
                var tipoFac: TIPO_FAC,
                var descTipoFac: String,
                var nroFac: String,
                var total: BigDecimal,
                var restante: BigDecimal)