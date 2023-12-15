package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout

class MainActivity_homePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home_page)

        val View = findViewById<View>(R.id.profile)
        View.setOnClickListener {
            val intent = Intent(this@MainActivity_homePage, MainActivity_you_acc::class.java)
            startActivity(intent)
        }
    }
}