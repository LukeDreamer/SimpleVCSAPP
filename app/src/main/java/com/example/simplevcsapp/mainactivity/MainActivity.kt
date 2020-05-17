package com.example.simplevcsapp.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplevcsapp.R
import com.example.simplevcsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private val recyclerAdapter = MainRecyclerAdapter()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        observeDataChanges()

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        mainViewModel.loadEmployeesInfo()
    }

    private fun observeDataChanges() {
        mainViewModel.getEmployeesLiveData().observe(this, Observer {
            recyclerAdapter.setMainRecyclerData(it.data)
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
