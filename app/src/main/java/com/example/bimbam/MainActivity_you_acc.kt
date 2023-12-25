package com.example.bimbam

import android.content.Intent
import android.icu.text.RelativeDateTimeFormatter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity_you_acc : AppCompatActivity() {
    val dbUsers = FirebaseDatabase.getInstance().getReference("Key")
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_you_acc)

        val RelativeDateTimeFormatter = findViewById<View>(R.id.icon5)
        RelativeDateTimeFormatter.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_settings::class.java)
            startActivity(intent)
        }

        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_homePage::class.java)
            startActivity(intent)
        }
        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_recommendations::class.java)
            startActivity(intent)
        }

        val nameTextView = findViewById<TextView>(R.id.NAME)
        val sexTextView = findViewById<TextView>(R.id.SEX)
        val birthdayTextView = findViewById<TextView>(R.id.BIRTHDAY)
        val diagnosTextView = findViewById<TextView>(R.id.DIAGNOS)

        // Получите данные из Intent
        val name = intent.getStringExtra("NAME")
        val sex = intent.getStringExtra("SEX")
        val birthday = intent.getStringExtra("BIRTHDAY")
        val diagnos = intent.getStringExtra("DIAGNOS")

        // Установите данные в TextView
        nameTextView.text = " $name"
        sexTextView.text = " $sex"
        birthdayTextView.text = " $birthday"
        diagnosTextView.text = " $diagnos"

    }
}