package com.example.simplevcsapp.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplevcsapp.data.DetailedEmployee
import com.example.simplevcsapp.database.EmployeeDao
import com.example.simplevcsapp.database.EmployeeEntity
import com.example.simplevcsapp.model.EmployeesInfo
import com.example.simplevcsapp.retrofit.Api
import com.example.simplevcsapp.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val random = Random()

    private val employeesLiveData = MutableLiveData<EmployeesInfo>()
    private val employeesErrorLiveData = MutableLiveData<Throwable>()
    private val progressLiveData = MutableLiveData<Boolean>()

    private val retroClient by lazy {
        RetrofitClient.retrofit.create(Api::class.java)
    }

    fun getEmployeesLiveData(): LiveData<EmployeesInfo> {
        return employeesLiveData
    }

    fun getEmployeesErrorLiveData(): LiveData<Throwable> {
        return employeesErrorLiveData
    }

    fun getEmployeesProgressLiveData(): LiveData<Boolean> {
        return progressLiveData
    }

    fun loadEmployeesInfo() {
        compositeDisposable.add(
            retroClient.getEmployees()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { progressLiveData.postValue(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        employeesLiveData.value = it
                        progressLiveData.postValue(false)
                    },
                    {
                        employeesErrorLiveData.value = it
                        progressLiveData.postValue(false)
                    })
        )
    }

    fun loadIntoDatabase(data: List<DetailedEmployee>, baseDao: EmployeeDao) {
        val baseData = List(data.size) { ind ->
            EmployeeEntity(
                ind.toLong(),
                data[ind].employeeName,
                data[ind].employeeSalary,
                data[ind].employeeAge,
                data[ind].backColor
            )
        }
        baseDao.insertAll(*baseData.toTypedArray())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}