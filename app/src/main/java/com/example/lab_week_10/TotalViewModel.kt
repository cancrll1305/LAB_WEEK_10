package com.example.lab_week_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab_week_10.database.TotalObject
import java.text.SimpleDateFormat
import java.util.*

class TotalViewModel : ViewModel() {

    private val _total = MutableLiveData<TotalObject>()
    val total: LiveData<TotalObject> = _total

    init {
        // Initialize with default values
        _total.value = TotalObject(value = 0, date = getCurrentDate())
    }

    fun incrementTotal() {
        val currentTotal = _total.value?.value ?: 0
        val updatedTotal = currentTotal + 1
        _total.value = TotalObject(value = updatedTotal, date = getCurrentDate())
    }

    fun setTotal(newTotal: TotalObject) {
        _total.value = newTotal
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
