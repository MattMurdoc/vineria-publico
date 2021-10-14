package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity
data class Factura (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "fact_codigo") val codigo: Int,
    @ColumnInfo(name = "fact_numero") val numero: String?,
    @ColumnInfo(name = "fact_numeron") val numeron: Int?,
    @ColumnInfo(name = "fact_punto") val punto: Int?,
    @ColumnInfo(name = "fact_fecha") val fecha: Date,
    @ColumnInfo(name = "fact_estado") val estado: Int,
    @ColumnInfo(name = "clie_codigo") val codCliente: Int,
    @ColumnInfo(name = "fact_descuento") val descuento: Char,
    @ColumnInfo(name = "fact_total") val total: BigDecimal?,
    @ColumnInfo(name = "fact_estadocobro") val estadoCobro: Char?,
    @ColumnInfo(name = "fact_fechavencimiento") val fechaVencimiento: Date?,
    @ColumnInfo(name = "fact_iva") val iva: Int,
    @ColumnInfo(name = "fact_tipo") val tipo: Char,
    @ColumnInfo(name = "fact_fechaA") val fechaAlta: Date?,
    @ColumnInfo(name = "fact_emplA") val empleadoAlta: Int?,
    @ColumnInfo(name = "empl_codigo") val codEmpleado: Int?,
    @ColumnInfo(name = "fact_estacion") val estacion: Char?,
    @ColumnInfo(name = "fact_cliente") val descCliente: String?,
    @ColumnInfo(name = "fact_impresora") val impresora: String?
)