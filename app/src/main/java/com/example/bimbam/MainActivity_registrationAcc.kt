package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth  // Add this import statement
import com.google.firebase.database.FirebaseDatabase
import android.util.Log



class MainActivity_registrationAcc : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private  lateinit var database: FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registration_acc)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val login: EditText = findViewById(R.id.login)
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)
        val repeatpass: EditText = findViewById(R.id.repeatpass)
        val RelativeLayout = findViewById<RelativeLayout>(R.id.sign_up)
        RelativeLayout.setOnClickListener {
            val login = login.text.toString().trim()
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()
            val repeatpass = repeatpass.text.toString().trim()

            if (login == "" || email == "" || password == "" || repeatpass == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else if(!email.matches(emailPattern.toRegex())){
                    Toast.makeText(this,"Некорректный формат E-mail",Toast.LENGTH_LONG).show()
            }
            else if(repeatpass == "" || repeatpass != password){
                        Toast.makeText(this,"Пароли не совпадают", Toast.LENGTH_LONG).show()}
            else if(password.length < 6)
                Toast.makeText(this,"Пароль должен содержать не менее 6 символов",Toast.LENGTH_LONG).show()
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val database =
                            database.reference.child("users").child(auth.currentUser!!.uid)
                        val users:Users = Users(login,email,auth.currentUser!!.uid)
                        database.setValue(users).addOnCompleteListener {
                            if(it.isSuccessful){
                                val intent = Intent(this@MainActivity_registrationAcc,MainActivity_registrationChild::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "Что-то пошло не так: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                                Log.e("Firebase", "Authentication failed: ${it.exception?.message}")
                            }
                        }
                    }
                }

            }

        }
    }
}
