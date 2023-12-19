package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.util.Log
import android.widget.TextView

class MainActivity_registrationAcc : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registration_acc)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val loginEditText: EditText = findViewById(R.id.login)
        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val repeatPasswordEditText: EditText = findViewById(R.id.repeatpass)
        val registrationButton: RelativeLayout = findViewById(R.id.buttonreg)
        val signupText: TextView = findViewById(R.id.title)

        signupText.setOnClickListener {
            val intent = Intent(this, MainActivity_registrationChild::class.java)
            startActivity(intent)
        }

        registrationButton.setOnClickListener {
            Log.d("ButtonClickListener", "Button clicked")
            val login = loginEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val repeatPassword = repeatPasswordEditText.text.toString().trim()

            when {
                login.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() -> {
                    Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                }
                !email.matches(emailPattern.toRegex()) -> {
                    Toast.makeText(this, "Некорректный формат E-mail", Toast.LENGTH_LONG).show()
                }
                repeatPassword != password -> {
                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG).show()
                }
                password.length < 6 -> {
                    Toast.makeText(this, "Пароль должен содержать не менее 6 символов", Toast.LENGTH_LONG).show()
                }
                else -> {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val usersRef = database.reference.child("users").child(auth.currentUser!!.uid)
                            val user = Users(login, email, auth.currentUser!!.uid)
                            usersRef.setValue(user).addOnCompleteListener { innerTask ->
                                if (innerTask.isSuccessful) {
                                    val intent = Intent(this, MainActivity_registrationChild::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    handleFirebaseError(innerTask.exception?.message)
                                }
                            }
                        } else {
                            handleFirebaseError(task.exception?.message)
                        }
                    }
                }
            }
        }
    }

    private fun handleFirebaseError(errorMessage: String?) {
        Toast.makeText(this, "Что-то пошло не так: $errorMessage", Toast.LENGTH_LONG).show()
        Log.e("Firebase", "Authentication failed: $errorMessage")
    }
}
