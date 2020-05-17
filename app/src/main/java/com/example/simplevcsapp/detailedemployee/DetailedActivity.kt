package com.example.simplevcsapp.detailedemployee

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.simplevcsapp.R
import com.example.simplevcsapp.data.DetailedEmployee
import com.example.simplevcsapp.databinding.ActivityDetailedBinding
import com.example.simplevcsapp.detailedemployee.preferences.PreferenceProvider

class DetailedActivity : AppCompatActivity() {

    companion object {
        private const val ADDED = "Employee has been add to favorites"
        private const val REMOVED = "Employee has been removed from favorites"

        private const val KEY = "KEY"


        fun start(context: Context, data: DetailedEmployee) {
            val intent = Intent(context, DetailedActivity::class.java)
            intent.putExtra(KEY, data)
            context.startActivity(intent)
        }
    }

    private lateinit var preferenceProvider: PreferenceProvider

    private lateinit var binding: ActivityDetailedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceProvider = PreferenceProvider(this)

        val data = intent.getParcelableExtra<DetailedEmployee>(KEY)
        binding.ageNTV.text = data!!.employeeAge
        binding.nameTV.text = data.employeeName
        binding.salaryNTV.text = data.employeeSalary
        (binding.background.background as ColorDrawable).color = data.backColor

        if (preferenceProvider.getEmployee(data.employeeName) == null)
            binding.starImage.setImageWithGlide(R.drawable.ic_unfilled_star)
        else binding.starImage.setImageWithGlide(R.drawable.ic_filled_star)

        binding.backArrowImage.setOnClickListener { onBackPressed() }

        binding.starImage.setOnClickListener {
            if (preferenceProvider.getEmployee(data.employeeName) == null) {
                binding.starImage.setImageWithGlide(R.drawable.ic_filled_star)
                preferenceProvider.saveEmployee(data.employeeName)
                Toast.makeText(applicationContext, ADDED, Toast.LENGTH_LONG)
                    .show()
            } else {
                binding.starImage.setImageWithGlide(R.drawable.ic_unfilled_star)
                preferenceProvider.removeEmployee(data.employeeName)
                Toast.makeText(applicationContext, REMOVED, Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    private fun ImageView.setImageWithGlide(image: Int) {
        Glide.with(this)
            .load(image)
            .into(this)
    }
}
