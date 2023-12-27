package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout

class MainActivity_confirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_confirmation)

        val RelativeLayout = findViewById<RelativeLayout>(R.id.button)
        RelativeLayout.setOnClickListener {
            val intent = Intent(this@MainActivity_confirmation, MainActivity_homePage::class.java)
            startActivity(intent)

        }
    }
}