package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity_game_together : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game_together)

        val View5 = findViewById<View>(R.id.icon5)
        View5.setOnClickListener {
            val intent = Intent(this@MainActivity_game_together, MainActivity_settings::class.java)
            startActivity(intent)
        }

        val profileView = findViewById<View>(R.id.profile)
        profileView.setOnClickListener {
            val intent = Intent(this@MainActivity_game_together, MainActivity_you_acc::class.java)
            startActivity(intent)
        }
        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_game_together, MainActivity_homePage::class.java)
            startActivity(intent)}
        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_game_together, MainActivity_recommendations::class.java)
            startActivity(intent)}

        val View_back = findViewById<View>(R.id.arrow)
        View_back.setOnClickListener {
            val intent = Intent(this@MainActivity_game_together, MainActivity_recommendations::class.java)
            startActivity(intent)}
        val View4 = findViewById<View>(R.id.icon4)
        View4.setOnClickListener {
            val intent = Intent(this@MainActivity_game_together, MainActivity_list::class.java)
            startActivity(intent) }
    }
}