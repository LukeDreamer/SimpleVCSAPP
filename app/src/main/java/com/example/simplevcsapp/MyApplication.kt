package com.example.simplevcsapp

import android.app.Application
import androidx.room.Room
import com.example.simplevcsapp.database.AppDatabase

class MyApplication : Application() {

    lateinit var dataBase: AppDatabase

    override fun onCreate() {
        super.onCreate()

        dataBase = Room.databaseBuilder(this, AppDatabase::class.java, "employees")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}