package com.example.acalculator

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface OperationDao {

    @Insert
    suspend fun insert(operation : OperationRoom)

    @Query("SELECT * FROM operation order by timestamp asc")
    suspend fun getAll() : List<OperationRoom>

    @Query("SELECT * FROM operation where uuid = :uuid")
    suspend fun getByID(uuid: String) : OperationRoom

    @Query("DELETE FROM operation WHERE uuid = :uuid")
    suspend fun deleteById(uuid: String)
}