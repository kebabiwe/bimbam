package com.example.bimbam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class SplashActivityy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_activityy)
        val imageButton2 = findViewById<RelativeLayout>(R.id.welcomescreen)
        imageButton2.setOnClickListener {
            val intent = Intent(this@SplashActivityy, SplashActivity1::class.java)
            startActivity(intent)
        }
    }
}
