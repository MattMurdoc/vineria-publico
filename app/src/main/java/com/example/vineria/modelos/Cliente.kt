package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cliente(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "clie_codigo") val codigo: Int,
    @ColumnInfo(name = "clie_nombre") val nombre: String,
    @ColumnInfo(name = "esta_codigo") val estado: Int?,
    @ColumnInfo(name = "clie_Ndocumento") val nroDocumento: String?,
    @ColumnInfo(name = "tiva_codigo") val iva: Int?,
    @ColumnInfo(name = "clie_CUIT") val cuit: String?
)