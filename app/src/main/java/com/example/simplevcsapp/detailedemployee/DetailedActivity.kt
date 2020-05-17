package com.example.simplevcsapp.detailedemployee

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simplevcsapp.data.DetailedEmployee
import com.example.simplevcsapp.databinding.ActivityDetailedBinding

class DetailedActivity : AppCompatActivity() {

    companion object {
        private const val KEY = "KEY"


        fun start(context: Context, data: DetailedEmployee) {
            val intent = Intent(context, DetailedActivity::class.java)
            intent.putExtra(KEY, data)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityDetailedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<DetailedEmployee>(KEY)
        binding.ageNTV.text = data!!.employeeAge
        binding.nameTV.text = data.employeeName
        binding.salaryNTV.text = data.employeeSalary
        (binding.background.background as ColorDrawable).color = data.backColor

        binding.backArrowImage.setOnClickListener { onBackPressed() }
    }
}
