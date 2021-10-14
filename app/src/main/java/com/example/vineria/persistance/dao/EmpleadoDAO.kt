package com.example.vineria.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vineria.modelos.Articulo
import com.example.vineria.modelos.Empleado

@Dao
interface EmpleadoDAO {

    @Query("SELECT * FROM empleado")
    fun getAll(): MutableList<Empleado>

    @Query("SELECT COUNT(*) FROM empleado")
    fun count(): Int

    @Insert
    fun insert(empleado: Empleado)

    @Query("DELETE FROM empleado")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(empleados: List<Empleado>)

}