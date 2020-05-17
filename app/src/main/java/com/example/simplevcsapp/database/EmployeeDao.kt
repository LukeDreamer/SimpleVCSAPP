package com.example.simplevcsapp.database

import androidx.room.*

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM EmployeeEntity")
    fun getAll(): List<EmployeeEntity>

    @Query("SELECT * FROM EmployeeEntity WHERE empId IN (:empId)")
    fun get(empId: Long): EmployeeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg employee: EmployeeEntity)

    @Delete
    fun delete(employee: EmployeeEntity)
}