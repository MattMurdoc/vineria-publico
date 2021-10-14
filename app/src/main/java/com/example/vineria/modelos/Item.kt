package com.example.vineria.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class Item(
    var idPedido: Long,
    var codArticulo: Int,
    var cantidad: Int,
    var importeUnitario: BigDecimal,
    var importeTotal: BigDecimal,
    var listaPrecios: String,
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
