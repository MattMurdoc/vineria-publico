package com.example.vineria.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vineria.modelos.DetallePresupuesto

@Dao
interface DetallePresupuestoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(detalles: List<DetallePresupuesto>)

    @Query("DELETE FROM detallepresupuesto")
    fun deleteAll()
}