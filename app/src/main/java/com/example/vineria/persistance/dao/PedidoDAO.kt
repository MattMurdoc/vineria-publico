package com.example.vineria.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vineria.modelos.Pedido
import java.util.*

@Dao
interface PedidoDAO {

    @Query("SELECT * FROM pedido WHERE enviado != 1")
    fun getNoEnviados(): List<Pedido>

    @Query("SELECT id, fechaHora, codCliente, descCliente, codEmpleado, tipoComprobante, observaciones, enviado, body, (SELECT SUM(i.importeTotal) FROM item i WHERE idPedido = p.id) as total FROM pedido p")
    fun getAll(): MutableList<Pedido>

    @Insert
    fun insert(pedido: Pedido): Long

    @Update
    fun update(pedido: Pedido)

    @Query("SELECT * FROM pedido WHERE fechaHora BETWEEN :fechaDesde AND :fechaHasta")
    fun getPedidosByFecha(fechaDesde: Date?, fechaHasta: Date?): MutableList<Pedido>

    @Query("SELECT * FROM pedido WHERE id = :idPedido")
    fun getPedidoById(idPedido: Long) : Pedido
}

