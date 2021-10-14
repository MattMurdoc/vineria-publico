package com.example.vineria.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vineria.modelos.Factura

@Dao
interface FacturaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(facturas: List<Factura>)

    @Query("SELECT * FROM factura WHERE clie_codigo = :idCliente")
    fun getFacturasCliente(idCliente: Int): List<Factura>

    @Query("SELECT * FROM factura WHERE fact_codigo = :codigoFactura")
    fun getFacturaPorCodigoFactura(codigoFactura: Int): Factura

    @Query("DELETE FROM factura")
    fun deleteAll()
}