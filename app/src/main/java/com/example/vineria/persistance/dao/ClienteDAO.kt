package com.example.vineria.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vineria.modelos.Articulo
import com.example.vineria.modelos.Cliente

@Dao
interface ClienteDAO {
    @Query("SELECT * FROM cliente")
    fun getAll(): MutableList<Cliente>

    @Query("SELECT COUNT(*) FROM cliente")
    fun count(): Int

    @Query("SELECT * FROM cliente WHERE clie_codigo = :codigo")
    fun getByCodigo(codigo: Int): Cliente

    @Query("SELECT clie_nombre FROM cliente WHERE clie_codigo = :codigo")
    fun getDescripcionByCodigo(codigo: Int?): String

    @Insert
    fun insert(cliente: Cliente)

    @Query("DELETE FROM cliente")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(clientes: List<Cliente>)

    @Query("SELECT * FROM cliente WHERE clie_nombre LIKE '%' || :descripcion || '%' LIMIT :offset, 20")
    fun getClientesByDescripcion(offset: Int, descripcion: String) : MutableList<Cliente>
}
