package com.example.simplevcsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [EmployeeEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}