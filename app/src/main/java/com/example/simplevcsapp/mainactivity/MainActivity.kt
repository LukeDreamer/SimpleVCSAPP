package com.example.simplevcsapp.mainactivity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplevcsapp.MyApplication
import com.example.simplevcsapp.data.DetailedEmployee
import com.example.simplevcsapp.database.EmployeeDao
import com.example.simplevcsapp.databinding.ActivityMainBinding
import com.example.simplevcsapp.detailedemployee.DetailedActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private val random = java.util.Random()

    private lateinit var baseDao: EmployeeDao

    private val recyclerAdapter = MainRecyclerAdapter()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseDao = (application as MyApplication).dataBase.employeeDao()

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        observeDataChanges()

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        mainViewModel.loadEmployeesInfo()

        recyclerAdapter.itemClickListener = { position ->
            val info = baseDao.get(position.toLong())
            DetailedActivity.start(
                this,
                DetailedEmployee(
                    info.employeeName,
                    info.employeeSalary,
                    info.employeeAge,
                    info.backColor
                )
            )
        }
    }

    private fun observeDataChanges() {
        mainViewModel.getEmployeesLiveData().observe(this, Observer {
            val dataToPass = List(it.data.size) { ind ->
                DetailedEmployee(
                    it.data[ind].employeeName,
                    it.data[ind].employeeSalary,
                    it.data[ind].employeeAge,
                    Color.argb(
                        255,
                        random.nextInt(100),
                        random.nextInt(100),
                        random.nextInt(100)
                    )
                )
            }
            recyclerAdapter.setMainRecyclerData(dataToPass)
            mainViewModel.loadIntoDatabase(dataToPass, baseDao)
        })

        mainViewModel.getEmployeesErrorLiveData().observe(this, Observer {
            Log.e("EmployeesErrorLiveData", it.message.toString())
        })

        mainViewModel.getEmployeesProgressLiveData().observe(this, Observer { progress ->
            if (progress) binding.progressBar.visibility =
                View.VISIBLE else binding.progressBar.visibility = View.GONE
        })
    }
}
