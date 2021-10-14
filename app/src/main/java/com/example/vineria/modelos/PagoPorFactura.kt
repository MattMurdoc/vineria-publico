package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity
data class PagoPorFactura (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "pfac_codigo") val codigo: Int,
    @ColumnInfo(name = "fact_codigo") val codigoFactura: Int,
    @ColumnInfo(name = "pfac_fecha") val fecha: Date?,
    @ColumnInfo(name = "form_codigo") val codigoForm: Int,
    @ColumnInfo(name = "pfac_importe") val importe: BigDecimal,
    @ColumnInfo(name = "pfac_empleado") val empleado: Int,
    @ColumnInfo(name = "pfac_banco") val banco: String?,
    @ColumnInfo(name = "pfac_Ntransferencia") val nroTransferencia: String?,
    @ColumnInfo(name = "pfac_observaciones") val observaciones: String?,
    @ColumnInfo(name = "pfac_fechavenc") val fechaVencimiento: Date?,
    @ColumnInfo(name = "pfac_Ncheque") val nroCheque: String?,
    @ColumnInfo(name = "tarj_codigo") val tarjetaCodigo: Int?,
    @ColumnInfo(name = "pfac_Ntarjeta") val nroTarjeta: String?,
    @ColumnInfo(name = "pfac_Nlote") val nroLote: String?,
    @ColumnInfo(name = "pfac_estado") val estado: Char?,
    @ColumnInfo(name = "pfac_fechaAsiento") val fechaAsiento: Date,
    @ColumnInfo(name = "pfac_pasado") val pasado: Char?,
    @ColumnInfo(name = "pfac_tablet") val tablet: Char?,
    @ColumnInfo(name = "pfac_fechaPaso") val fechaPaso: Date?
)