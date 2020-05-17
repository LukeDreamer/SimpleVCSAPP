package com.example.simplevcsapp.mainactivity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplevcsapp.R
import com.example.simplevcsapp.model.EmployeeData
import java.util.*


class MainRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val random = Random()

    private var data = emptyList<EmployeeData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_recycler_item_layout, parent, false)

        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EmployeeViewHolder -> holder.bindData(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setMainRecyclerData(data: List<EmployeeData>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTV = itemView.findViewById<TextView>(R.id.nameTV)
        private val ageNTV = itemView.findViewById<TextView>(R.id.ageNTV)
        private val salaryNTV = itemView.findViewById<TextView>(R.id.salaryNTV)

        private val avatar = itemView.findViewById<ImageView>(R.id.avatar)

        private val cardView = itemView.findViewById<CardView>(R.id.cardView)

        fun bindData(position: Int) {
            nameTV.text = data[position].employeeName
            ageNTV.text = data[position].employeeAge
            salaryNTV.text = data[position].employeeSalary

            Glide.with(avatar)
                .load(R.mipmap.ic_launcher)
                .circleCrop()
                .into(avatar)

            val color: Int = Color.argb(
                255,
                random.nextInt(100),
                random.nextInt(100),
                random.nextInt(100)
            )

            cardView.background.setTint(color)
        }
    }
}