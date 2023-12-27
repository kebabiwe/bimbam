package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.net.Uri;

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
            startActivity(intent)
        }

        val RelativeLayout = findViewById<RelativeLayout>(R.id.img_1)
        RelativeLayout.setOnClickListener {
            val intent = Intent(this@MainActivity_recommendations, MainActivity_game_together::class.java)
            startActivity(intent)
        }


        val RelativeLayout_2 = findViewById<View>(R.id.frame_2)
        RelativeLayout_2.setOnClickListener {
            val intent = Intent(this@MainActivity_recommendations, MainActivity_pitanie::class.java)
            startActivity(intent)
        }

        val RelativeLayout_3 = findViewById<View>(R.id.frame_3)
        RelativeLayout_3.setOnClickListener {
            val intent = Intent(this@MainActivity_recommendations, MainActivity_emotions::class.java)
            startActivity(intent)
        }
        val View4 = findViewById<View>(R.id.icon4)
        View4.setOnClickListener {
            val intent = Intent(this@MainActivity_recommendations, MainActivity_list::class.java)
            startActivity(intent) }
    }}

