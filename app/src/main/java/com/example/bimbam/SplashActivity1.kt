package com.example.bimbam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity


class SplashActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash1)

        val imageButton2 = findViewById<RelativeLayout>(R.id.welcomescreen1)
        imageButton2.setOnClickListener {
            val intent = Intent(this@SplashActivity1, MainActivity_privetctvie::class.java)
            startActivity(intent)
        }
    }
}