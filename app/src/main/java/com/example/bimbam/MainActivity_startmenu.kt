package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity_startmenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_startmenu)
        val RelativeLayout = findViewById<RelativeLayout>(R.id.button)
        RelativeLayout.setOnClickListener {
            val intent = Intent(this@MainActivity_startmenu, MainActivity_Login::class.java)
            startActivity(intent)
        }
        val TextView = findViewById<TextView>(R.id.button1)
        TextView.setOnClickListener{
            val intent = Intent(this@MainActivity_startmenu,MainActivity_registrationAcc::class.java)
            startActivity(intent)
        }

    }
}