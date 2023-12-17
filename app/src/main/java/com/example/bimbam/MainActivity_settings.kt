package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout

class MainActivity_settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_settings)

        val RelativeLayout = findViewById<RelativeLayout>(R.id.frame_30)
        RelativeLayout.setOnClickListener {
            val intent = Intent(this@MainActivity_settings, MainActivity_about::class.java)
            startActivity(intent)
        }
    }
}