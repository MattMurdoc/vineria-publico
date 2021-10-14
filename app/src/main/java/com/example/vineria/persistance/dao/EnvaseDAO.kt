package com.example.vineria.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vineria.modelos.Envase

@Dao
interface EnvaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(envases: List<Envase>)

    @Query("DELETE FROM envase")
    fun deleteAll()
}