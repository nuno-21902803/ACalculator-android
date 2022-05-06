package com.example.acalculator

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface OperationDao {

    @Insert
    suspend fun insert(operation : OperationRoom)

    @Insert
    suspend fun insertAll(operations: List<OperationRoom>)

    @Query("SELECT * FROM operation order by timestamp asc")
    suspend fun getAll() : List<OperationRoom>

    @Query("SELECT * FROM operation where uuid = :uuid")
    suspend fun getByID(uuid: String) : OperationRoom

    @Query("DELETE FROM operation ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastOperation(): OperationRoom


    @Query("DELETE FROM operation WHERE uuid = :uuid")
    suspend fun deleteById(uuid: String): Int

    @Query("DELETE FROM operation")
    suspend fun deleteAll(): Int

}