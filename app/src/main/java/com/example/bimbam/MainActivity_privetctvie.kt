package com.example.bimbam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity_privetctvie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_privetctvie)
        val curruser = FirebaseAuth.getInstance().currentUser
        if(curruser!= null){
            val intent = Intent(this@MainActivity_privetctvie, MainActivity_homePage::class.java)
            startActivity(intent)
            finish()
        }
        else {
            val RelativeLayout = findViewById<RelativeLayout>(R.id.button)
            RelativeLayout.setOnClickListener {
                val intent = Intent(this@MainActivity_privetctvie, MainActivity_startmenu::class.java)
                startActivity(intent)
            }
        }
    }
}