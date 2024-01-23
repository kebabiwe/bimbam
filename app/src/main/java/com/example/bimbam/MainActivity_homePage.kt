package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity_homePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home_page)
        FirebaseMessaging.getInstance().token
        val profileView = findViewById<View>(R.id.profile)
        profileView.setOnClickListener {
            val intent = Intent(this@MainActivity_homePage, MainActivity_you_acc::class.java)
            startActivity(intent)
        }


        val View = findViewById<View>(R.id.icon5)
        View.setOnClickListener {
            val intent = Intent(this@MainActivity_homePage, MainActivity_settings::class.java)
            startActivity(intent)
        }
        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_homePage, MainActivity_recommendations::class.java)
            startActivity(intent)
        }
        val View4 = findViewById<View>(R.id.icon4)
        View4.setOnClickListener {
            val intent = Intent(this@MainActivity_homePage, MainActivity_list::class.java)
            startActivity(intent) }

    }
}
