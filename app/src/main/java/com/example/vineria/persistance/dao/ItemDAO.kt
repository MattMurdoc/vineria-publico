package com.example.vineria.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vineria.modelos.Item

@Dao
interface ItemDAO {

    @Query("SELECT * FROM item WHERE idPedido = :idPedido")
    fun getAllByIdPedido(idPedido: Long): List<Item>

    @Insert
    fun insertAll(items: List<Item>)

    @Insert
    fun insert(item: Item): Long
}