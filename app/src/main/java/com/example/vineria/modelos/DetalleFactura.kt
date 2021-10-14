package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class DetalleFactura (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "deta_codigo") val codigo: Int,
    @ColumnInfo(name = "fact_codigo") val codigoFactura: Int,
    @ColumnInfo(name = "arti_codigo") val codArticulo: Int,
    @ColumnInfo(name = "deta_cantidad") val cantidad: Int,
    @ColumnInfo(name = "deta_importeST") val importeSubtotal: BigDecimal,
    @ColumnInfo(name = "deta_importeU") val importeUnitario: BigDecimal,
    @ColumnInfo(name = "deta_Lista") val lista: String?,
)