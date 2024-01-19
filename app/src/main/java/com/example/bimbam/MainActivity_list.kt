package com.example.bimbam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity_list : AppCompatActivity() {
    private lateinit var calendar: TextView
    private lateinit var currentDate: Calendar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        calendar = findViewById(R.id.some_id)
        currentDate = Calendar.getInstance()
        updateDate()
        val View5 = findViewById<View>(R.id.icon5)
        View5.setOnClickListener {
            val intent = Intent(this@MainActivity_list, MainActivity_settings::class.java)
            startActivity(intent)

        }

        val profileView = findViewById<View>(R.id.profile)
        profileView.setOnClickListener {
            val intent = Intent(this@MainActivity_list, MainActivity_you_acc::class.java)
            startActivity(intent)
        }
        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_list, MainActivity_homePage::class.java)
            startActivity(intent)}
        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_list, MainActivity_recommendations::class.java)
            startActivity(intent)}
        val arrowView = findViewById<View>(R.id.arrow)
        arrowView.setOnClickListener {
            // Перейти на предыдущую дату
            currentDate.add(Calendar.DAY_OF_MONTH, -1)
            updateDate()
        }

        val arrow2View = findViewById<View>(R.id.arrow2)
        arrow2View.setOnClickListener {
            // Перейти на следующую дату
            currentDate.add(Calendar.DAY_OF_MONTH, 1)
            updateDate()
        }
    }
    private fun updateDate() {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate.time)
        calendar.text = formattedDate
    }
    }