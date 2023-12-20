package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Typeface
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity_registrationChild : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    //In xml we have this edit text to take data input and a button to submit
    private lateinit var name : EditText
    private lateinit var btn : Button
    private lateinit var birthday : EditText
private lateinit var sex : Spinner
private lateinit var diagnos : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registration_child)
        mAuth = FirebaseAuth.getInstance()
        name = findViewById(R.id.childname)
        birthday = findViewById(R.id.childbirthday)
        sex = findViewById(R.id.spinner)
        diagnos = findViewById(R.id.spinner1)

        btn = findViewById(R.id.button)
        btn.setOnClickListener{
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
        //User is LoggedOut send user to mainActivity to Login
        if(mAuth.currentUser == null)
        {
            val intent = Intent(this,MainActivity_Login::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
    }

    //Function to save data in Realtime Database of Firebase
    private fun saveData(data: String) {
        val email = mAuth.currentUser?.email
        //Here UserInfo is a data class which tells DB that we have these columns
        val user = email?.let { Users(it,data) }

        //Remember here we use keyword what we used in our Database i.e "Key"
        //You can put anything at place of Key but same as your DataBase
        val dbUsers = FirebaseDatabase.getInstance().getReference("Key")
        dbUsers.child(mAuth.currentUser!!.uid)
            .setValue(user).addOnCompleteListener(OnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Token Saved", Toast.LENGTH_SHORT).show()
                }
            })
    }
}