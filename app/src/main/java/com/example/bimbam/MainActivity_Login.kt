package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.bimbam.databinding.ActivityMainLoginBinding
import com.example.bimbam.databinding.ActivityMainRegistrationAccBinding
import com.example.bimbam.databinding.ActivityMainRegistrationChildBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity_Login : AppCompatActivity() {
    private lateinit var binding: ActivityMainLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var isInLoginProcess: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.title.setOnClickListener {
            val intent = Intent(this, MainActivity_Login::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val login = binding.login.text.toString()
            val pass = binding.password.text.toString()
            if (login.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(login, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity_homePage::class.java)
                        startActivity(intent)
                        saveUserTokenToDevice("уникальный_токен")
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveUserTokenToDevice(token: String) {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_token", token)
        editor.apply()
    }
    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null && !isInLoginProcess) {
            val intent = Intent(this, MainActivity_homePage::class.java)
            startActivity(intent)
            val sharedPreferences = getPreferences(MODE_PRIVATE)
            val userToken = sharedPreferences.getString("user_token", null)

            if (firebaseAuth.currentUser != null && !isInLoginProcess && userToken != null) {
                // Пользователь уже вошел в систему, и токен присутствует
                val intent = Intent(this, MainActivity_homePage::class.java)
                startActivity(intent)
            }
        }
    }

}
