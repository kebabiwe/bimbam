package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout

class MainActivity_registrationAcc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registration_acc)
        val RelativeLayout = findViewById<RelativeLayout>(R.id.sign_up)
        RelativeLayout.setOnClickListener {
            val intent = Intent(this@MainActivity_registrationAcc, MainActivity_registrationChild::class.java)
            startActivity(intent)
        }
    }
}