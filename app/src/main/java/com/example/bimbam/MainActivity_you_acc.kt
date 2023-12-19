package com.example.bimbam

import android.content.Intent
import android.icu.text.RelativeDateTimeFormatter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity_you_acc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_you_acc)

        val RelativeDateTimeFormatter = findViewById<View>(R.id.icon5)
        RelativeDateTimeFormatter.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_settings::class.java)
            startActivity(intent)
        }

        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_homePage::class.java)
            startActivity(intent) }
        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_recommendations::class.java)
            startActivity(intent)}

        val View_edit = findViewById<View>(R.id.vector_13)
        View_edit.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_you_acc_edit::class.java)
            startActivity(intent)}
    }
    }
