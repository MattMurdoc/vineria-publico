package com.example.vineria.modelos

import java.math.BigDecimal

class Impagos(
    var facturasPorPagar: MutableList<Pair<Factura, BigDecimal>>,
    var presupuestosPorPagar: MutableList<Pair<Presupuesto, BigDecimal>>
)