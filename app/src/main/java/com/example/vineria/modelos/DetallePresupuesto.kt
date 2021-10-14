package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class DetallePresupuesto (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "deps_codigo") val codigo: Int,
    @ColumnInfo(name = "pres_codigo") val codigoPresupuesto: Int,
    @ColumnInfo(name = "arti_codigo") val codArticulo: Int,
    @ColumnInfo(name = "deps_cantidad") val cantidad: Int,
    @ColumnInfo(name = "deps_importeST") val importeSubtotal: BigDecimal?,
    @ColumnInfo(name = "deps_importeU") val importeUnitario: BigDecimal?,
    @ColumnInfo(name = "deps_Lista") val lista: String?
)