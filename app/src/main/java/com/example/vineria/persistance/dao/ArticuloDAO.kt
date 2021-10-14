package com.example.vineria.persistance.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vineria.modelos.Articulo;

@Dao
interface ArticuloDAO {

    @Query("SELECT * FROM articulo")
    fun getAll(): List<Articulo>

    @Query("SELECT COUNT(*) FROM articulo")
    fun count(): Int

    @Insert
    fun insert(articulo: Articulo)

    @Query("DELETE FROM articulo")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articulos: List<Articulo>)

    @Query("SELECT * FROM articulo LIMIT :offset, 20")
    fun selectWithOffset(offset: Int) : MutableList<Articulo>

    @Query("SELECT * FROM articulo WHERE arti_nombre LIKE '%' || :descripcion || '%' LIMIT :offset, 20")
    fun getArticulosByDescripcion(offset: Int, descripcion: String) : MutableList<Articulo>

    @Query("SELECT arti_nombre FROM articulo WHERE arti_codigo = :idArticulo ")
    fun getDescripcionArticuloById(idArticulo: Int): String

}
