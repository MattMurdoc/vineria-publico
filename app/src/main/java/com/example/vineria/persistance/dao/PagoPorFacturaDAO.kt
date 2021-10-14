package com.example.vineria.persistance.dao

import androidx.room.*
import com.example.vineria.modelos.PagoPorFactura
import com.example.vineria.modelos.PagoPorPresupuesto

@Dao
interface PagoPorFacturaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pagos: List<PagoPorFactura>)

    @Query("DELETE FROM pagoporfactura")
    fun deleteAll()

    @Query("SELECT * FROM pagoporfactura WHERE fact_codigo = :idFactura")
    fun getPagosFactura(idFactura: Int): List<PagoPorFactura>

    @Query("SELECT * FROM pagoporfactura WHERE pfac_estado = :estado AND pfac_pasado = 48")
    fun getPagosPorEstado(estado: Char): List<PagoPorFactura>

    @Query("SELECT MAX(pfac_codigo) from pagoporfactura")
    fun getId(): Int

    @Query("UPDATE pagoporfactura set pfac_estado=1 AND pfac_pasado=1 WHERE pfac_codigo = :codigo")
    fun updateEstadoAndPasado(codigo: Int)

    @Insert
    fun insert(pagoPorFactura: PagoPorFactura): Long
}