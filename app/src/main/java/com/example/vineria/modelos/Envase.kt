package com.example.vineria.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Envase (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "enva_codigo") val codigo: Int,
    @ColumnInfo(name = "enva_nombre") val nombre: String
)