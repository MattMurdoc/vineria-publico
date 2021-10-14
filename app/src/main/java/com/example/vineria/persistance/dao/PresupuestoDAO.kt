package com.example.vineria.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vineria.modelos.Presupuesto

@Dao
interface PresupuestoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(presupuestos: List<Presupuesto>)

    @Query("SELECT * FROM presupuesto WHERE clie_codigo = :idCliente")
    fun getPresupuestosCliente(idCliente: Int): List<Presupuesto>

    @Query("DELETE FROM presupuesto")
    fun deleteAll()

    @Query("SELECT * FROM presupuesto WHERE pres_codigo = :codigoPresupuesto")
    fun getPresupuestoPorCodigoPresupuesto(codigoPresupuesto: Int): Presupuesto
}