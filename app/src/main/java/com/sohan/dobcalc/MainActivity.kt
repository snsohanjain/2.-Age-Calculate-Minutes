package com.sohan.dobcalc

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvDateSelected: TextView? = null
    private var tvTimeInMinutes: TextView? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private var currentDate: LocalDate? = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
        tvDateSelected = findViewById(R.id.tvSelectedDate)
        tvTimeInMinutes = findViewById(R.id.tvTimeMinuts)
//        periodCalculator()
    }

    private fun clickDatePicker() {
        val myCalander = Calendar.getInstance()
        val year = myCalander.get(Calendar.YEAR)
        val month = myCalander.get(Calendar.MONTH)
        val day = myCalander.get(Calendar.DAY_OF_MONTH)
        var dpd = DatePickerDialog(
            this, { view, year, month, day ->
                val selectedDate = "$day/${month + 1}/$year"
                tvDateSelected?.setText(selectedDate)
                //Simple Date Format
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    val currentDateMinutes = currentDate.time / 60000
                    val differenceInMinutes = currentDateMinutes - selectedDateInMinutes
                    tvTimeInMinutes?.text = differenceInMinutes.toString()
                }
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}
