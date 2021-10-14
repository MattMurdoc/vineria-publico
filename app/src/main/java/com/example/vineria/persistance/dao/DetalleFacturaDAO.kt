package com.example.vineria.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vineria.modelos.DetalleFactura

@Dao
interface DetalleFacturaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(detalle: List<DetalleFactura>)

    @Query("DELETE FROM detallefactura")
    fun deleteAll()
}