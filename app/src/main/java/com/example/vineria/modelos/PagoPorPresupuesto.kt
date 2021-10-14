package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity
data class PagoPorPresupuesto (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "ppre_codigo") val codigo: Int,
    @ColumnInfo(name = "pres_codigo") val codigoPresupuesto: Int,
    @ColumnInfo(name = "ppre_fecha") val fecha: Date,
    @ColumnInfo(name = "form_codigo") val codigoForm: Int,
    @ColumnInfo(name = "ppre_importe") val importe: BigDecimal,
    @ColumnInfo(name = "empl_codigo") val empleado: Int,
    @ColumnInfo(name = "ppre_observaciones") val observaciones: String?,
    @ColumnInfo(name = "ppre_estado") val estado: Char?,
    @ColumnInfo(name = "ppre_fechaAsiento") val fechaAsiento: Date?,
    @ColumnInfo(name = "ppre_pasado") val pasado: Char?,
    @ColumnInfo(name = "ppre_tablet") val tablet: Char?,
    @ColumnInfo(name = "ppre_fechaPaso") val fechaPaso: Date?,
)