package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import androidx.room.Room
import com.example.lab_week_10.database.TotalObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var db: TotalDatabase
    private val viewModel by lazy {
        ViewModelProvider(this).get(TotalViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the database
        db = Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        ).allowMainThreadQueries().build()

        initializeValueFromDatabase()

        prepareViewModel()

        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    private fun prepareViewModel() {
        // Observe the LiveData object (total)
        viewModel.total.observe(this, { total ->
            updateText(total.value)
        })
    }

    private fun updateText(value: Int) {
        findViewById<TextView>(R.id.text_total).text = getString(R.string.text_total, value)
    }

    private fun initializeValueFromDatabase() {
        val total = db.totalDao().getTotal(ID)
        if (total.isEmpty()) {
            db.totalDao().insert(Total(id = 1, total = TotalObject(
                value = 0,
                date = getCurrentDate()
            )
            ))
        } else {
            viewModel.setTotal(total.first().total)
        }
    }

    override fun onPause() {
        super.onPause()
        val currentTotal = viewModel.total.value
        if (currentTotal != null) {
            db.totalDao().update(Total(ID, currentTotal))
        }
    }

    override fun onStart() {
        super.onStart()
        val currentTotal = viewModel.total.value
        if (currentTotal != null) {
            Toast.makeText(this, "Last updated on: ${currentTotal.date}", Toast.LENGTH_LONG).show()
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    companion object {
        const val ID: Long = 1
    }
}
