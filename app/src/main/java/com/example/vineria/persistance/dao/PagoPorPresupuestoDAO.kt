package com.example.vineria.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vineria.modelos.PagoPorFactura
import com.example.vineria.modelos.PagoPorPresupuesto
import com.example.vineria.modelos.Pedido

@Dao
interface PagoPorPresupuestoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pagos: List<PagoPorPresupuesto>)

    @Query("DELETE FROM pagoporpresupuesto")
    fun deleteAll()

    @Query("SELECT * FROM pagoporpresupuesto WHERE ppre_codigo = :idPresupuesto")
    fun getPagosPresupuesto(idPresupuesto: Int): List<PagoPorPresupuesto>

    @Query("SELECT * FROM pagoporpresupuesto WHERE ppre_estado = :estado AND ppre_pasado = 48")
    fun getPagosPorEstado(estado: Char): List<PagoPorPresupuesto>

    @Query("SELECT MAX(ppre_codigo) from pagoporpresupuesto")
    fun getId(): Int

    @Query("UPDATE pagoporpresupuesto set ppre_estado=1 AND ppre_pasado=1 WHERE ppre_codigo = :codigo")
    fun updateEstadoAndPasado(codigo: Int)

    @Insert
    fun insert(pagoPorPresupuesto: PagoPorPresupuesto): Long
}