package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity
data class Presupuesto (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "pres_codigo") val codigo: Int,
    @ColumnInfo(name = "pres_fecha") val fecha: Date,
    @ColumnInfo(name = "pres_cliente") val cliente: String?,
    @ColumnInfo(name = "pres_total") val total: BigDecimal?,
    @ColumnInfo(name = "pres_fechaVencimiento") val fechaVencimiento: Date?,
    @ColumnInfo(name = "pres_Observaciones") val observaciones: String?,
    @ColumnInfo(name = "pres_estado") val estado: Char?,
    @ColumnInfo(name = "pres_estadoCobro") val estadoCobro: Char?,
    @ColumnInfo(name = "clie_codigo") val codCliente: Int?
)