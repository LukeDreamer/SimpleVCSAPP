package com.example.simplevcsapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmployeeEntity(
    @PrimaryKey val empId: Long,
    @ColumnInfo(name = "employee_name") val employeeName: String,
    @ColumnInfo(name = "employee_salary") val employeeSalary: String,
    @ColumnInfo(name = "employee_age") val employeeAge: String,
    @ColumnInfo(name = "back_color") val backColor: Int
)