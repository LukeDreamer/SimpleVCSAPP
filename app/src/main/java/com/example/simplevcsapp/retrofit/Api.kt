package com.example.simplevcsapp.retrofit

import com.example.simplevcsapp.model.EmployeesInfo
import io.reactivex.Single
import retrofit2.http.GET

interface Api {
    @GET("api/v1/employees")
    fun getEmployees(): Single<EmployeesInfo>
}