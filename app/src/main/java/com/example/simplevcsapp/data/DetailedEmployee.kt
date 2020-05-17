package com.example.simplevcsapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailedEmployee(
    val employeeName: String,
    val employeeSalary: String,
    val employeeAge: String,
    val backColor: Int
) : Parcelable