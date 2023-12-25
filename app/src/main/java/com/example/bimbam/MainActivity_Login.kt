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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.title.setOnClickListener {
            // Необходимо исправить этот блок кода, чтобы избежать создания новой активности MainActivity_Login
            // Вместо этого, если пользователь хочет вернуться на экран входа, используйте Intent с текущей активностью
            val intent = Intent(this, MainActivity_Login::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val login = binding.login.text.toString()
            val pass = binding.password.text.toString()
            if (login.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(login, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Переместите этот блок кода сюда, чтобы избежать ненужного перехода при успешном входе
                        val intent = Intent(this, MainActivity_homePage::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Убедитесь, что этот блок кода соответствует вашим требованиям
        if (firebaseAuth.currentUser != null) {
            // Если пользователь уже вошел в систему, может потребоваться предпринять другие действия
        }
    }
}

