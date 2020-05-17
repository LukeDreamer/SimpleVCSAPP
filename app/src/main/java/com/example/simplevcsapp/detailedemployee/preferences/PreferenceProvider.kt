package com.example.simplevcsapp.detailedemployee.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PreferenceProvider(context: Context) {

    private val myContext = context

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(myContext)

    fun saveEmployee(employee: String) {
        preference.edit().putString(employee, employee)
            .apply()
    }

    fun getEmployee(employee: String): String? {
        return preference.getString(employee, null)
    }

    fun removeEmployee(employee: String) {
        preference.edit().remove(employee)
            .apply()
    }

}