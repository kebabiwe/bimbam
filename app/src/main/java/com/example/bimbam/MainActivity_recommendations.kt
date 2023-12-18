package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity_recommendations : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_recommendations)

        val View5 = findViewById<View>(R.id.icon5)
        View5.setOnClickListener {
            val intent = Intent(this@MainActivity_recommendations, MainActivity_settings::class.java)
            startActivity(intent)
        }

        val profileView = findViewById<View>(R.id.profile)
        profileView.setOnClickListener {
            val intent = Intent(this@MainActivity_recommendations, MainActivity_you_acc::class.java)
            startActivity(intent)
        }
        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_recommendations, MainActivity_homePage::class.java)
            startActivity(intent)}
    }
    }
