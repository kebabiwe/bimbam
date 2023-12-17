package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast

class MainActivity_registrationAcc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registration_acc)
        val login:EditText = findViewById(R.id.login)
        val email:EditText = findViewById(R.id.email)
        val password:EditText = findViewById(R.id.password)
        val repeatpass:EditText = findViewById(R.id.repeatpass)
        val RelativeLayout = findViewById<RelativeLayout>(R.id.sign_up)
        RelativeLayout.setOnClickListener {
            val login = login.text.toString().trim()
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()
            val repeatpass = repeatpass.text.toString().trim()
            if (login == "" || email == "" || password == "" || repeatpass == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else {
            val user = User(login, email, password, repeatpass)
                val intent = Intent(this@MainActivity_registrationAcc, MainActivity_registrationChild::class.java)
                startActivity(intent)
            }
        }
    }
}