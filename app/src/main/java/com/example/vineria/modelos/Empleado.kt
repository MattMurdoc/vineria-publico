package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Empleado(
    @ColumnInfo(name = "empl_nombre") val nombre: String,
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "empl_codigo") val codigo: Int
) {
    @ColumnInfo(name = "seleccionado") @Ignore var seleccionado: Boolean? = false
}