package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class Articulo (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "arti_codigo") val codigo: Int,
    @ColumnInfo(name = "arti_nombre") val nombre: String,
    @ColumnInfo(name = "arti_imagen") val imagen: String?,
    @ColumnInfo(name = "arti_precioCompra") val precioCompra: BigDecimal,
    @ColumnInfo(name = "arti_precioLista1") val precioLista1: BigDecimal?,
    @ColumnInfo(name = "arti_precioLista2") val precioLista2: BigDecimal?,
    @ColumnInfo(name = "arti_precioLista3") val precioLista3: BigDecimal?,
    @ColumnInfo(name = "arti_porcLista1") val porcLista1: Double?,
    @ColumnInfo(name = "arti_porcLista2") val porcLista2: Double?,
    @ColumnInfo(name = "arti_porcLista3") val porcLista3: Double?,
    @ColumnInfo(name = "arti_vigente") val vigente: Char,
    @ColumnInfo(name = "arti_codigoBarra") val codBarra: String?,
    @ColumnInfo(name = "arti_envases") val envases: Char?,
    @ColumnInfo(name = "unid_codigo") val codUnidad: Int?,
    @ColumnInfo(name = "prve_codigo") val codPrev: Int?,
    @ColumnInfo(name = "arti_inhiL1") val inhiL1: Char?,
    @ColumnInfo(name = "arti_inhiL2") val inhiL2: Char?,
    @ColumnInfo(name = "arti_inhiL3") val inhiL3: Char?,
    @ColumnInfo(name = "enva_codigo") val codEnvase: Int?,
    @ColumnInfo(name = "arti_modificado") val modificado: Char?
)