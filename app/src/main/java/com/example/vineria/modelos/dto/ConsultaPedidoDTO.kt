package com.example.vineria.modelos.dto

import java.math.BigDecimal

class ConsultaPedidoDTO(
    var nroPedido: Long,
    var fechaHora: String,
    var descCliente: String,
    var tipoComprobante: String,
    var total: BigDecimal,
    var itemDTO: MutableList<ItemDTO>?
)


