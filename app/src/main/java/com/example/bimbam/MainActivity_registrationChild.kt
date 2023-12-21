package com.example.bimbam

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity_registrationChild : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var name: EditText
    private lateinit var btn: RelativeLayout
    private lateinit var birthday: EditText
    private lateinit var sex: Spinner
    private lateinit var diagnos: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registration_child)
        mAuth = FirebaseAuth.getInstance()
        name = findViewById(R.id.childname)
        birthday = findViewById(R.id.childbirthday)
        sex = findViewById(R.id.spinner)
        diagnos = findViewById(R.id.spinner1)
        btn = findViewById(R.id.button)

        // Создайте адаптер для пола
        val sexAdapter = ArrayAdapter.createFromResource(this, R.array.sex_array, android.R.layout.simple_spinner_item)
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sex.adapter = sexAdapter

        // Создайте адаптер для диагноза
        val diagnosAdapter = ArrayAdapter.createFromResource(this, R.array.diagnos_array, android.R.layout.simple_spinner_item)
        diagnosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        diagnos.adapter = diagnosAdapter

        btn.setOnClickListener {
            val data = name.text.toString()
            val data1 = birthday.text.toString()
            val data2 = sex.selectedItem.toString()
            val data3 = diagnos.selectedItem.toString()
            saveData(data)
            saveData(data1)
            saveData(data2)
            saveData(data3)
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null) {
            val intent = Intent(this, MainActivity_Login::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
    }

    private fun saveData(data: String) {
        val email = mAuth.currentUser?.email
        val user = email?.let { Users(it, data) }
        val dbUsers = FirebaseDatabase.getInstance().getReference("Key")
        dbUsers.child(mAuth.currentUser!!.uid)
            .setValue(user).addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Token Saved", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
