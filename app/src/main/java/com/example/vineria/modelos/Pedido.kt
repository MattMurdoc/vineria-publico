package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity
data class Pedido (
    @ColumnInfo(name = "fechaHora") var fechaHora: Date,
    @ColumnInfo(name = "codCliente") var codCliente: Int? = null,
    @ColumnInfo(name = "descCliente") var descCliente: String,
    @ColumnInfo(name = "codEmpleado") var codEmpleado: Int,
    @ColumnInfo(name = "tipoComprobante") var tipoComprobante: String,
    @ColumnInfo(name = "observaciones") var observaciones: String?,
    @ColumnInfo(name = "enviado") var enviado: Boolean = false,
    @ColumnInfo(name = "body") var body: String? = "",
) {
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0
    @ColumnInfo(name = "total") var total: BigDecimal? = BigDecimal.ZERO
}