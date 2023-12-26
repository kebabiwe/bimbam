package com.example.bimbam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity_you_acc : AppCompatActivity() {
    private val dbUsers = FirebaseDatabase.getInstance().getReference("Key")
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var nameTextView: TextView
    private lateinit var sexTextView: TextView
    private lateinit var birthdayTextView: TextView
    private lateinit var diagnosTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_you_acc)

        nameTextView = findViewById(R.id.NAME)
        sexTextView = findViewById(R.id.SEX)
        birthdayTextView = findViewById(R.id.BIRTHDAY)
        diagnosTextView = findViewById(R.id.DIAGNOS)

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

        // Получите данные из Intent
        val name = intent.getStringExtra("NAME")
        val sex = intent.getStringExtra("SEX")
        val birthday = intent.getStringExtra("BIRTHDAY")
        val diagnos = intent.getStringExtra("DIAGNOS")

        // Установите данные в TextView
        nameTextView.text = name ?: ""
        sexTextView.text = sex ?: ""
        birthdayTextView.text = birthday ?: ""
        diagnosTextView.text = diagnos ?: ""

        // Загрузите данные из SharedPreferences
        loadDataFromSharedPreferences()
    }

    private fun loadDataFromSharedPreferences() {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val name = sharedPreferences.getString("NAME", "")
        val birthday = sharedPreferences.getString("BIRTHDAY", "")
        val sex = sharedPreferences.getString("SEX", "")
        val diagnos = sharedPreferences.getString("DIAGNOS", "")

        // Установите данные в TextView
        nameTextView.text = name ?: ""
        sexTextView.text = sex ?: ""
        birthdayTextView.text = birthday ?: ""
        diagnosTextView.text = diagnos ?: ""
    }
}
