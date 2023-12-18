package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import android.graphics.Typeface
import android.widget.Spinner
import android.widget.ArrayAdapter
class MainActivity_registrationChild : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registration_child)
        val signupText = findViewById<TextView>(R.id.title)
        signupText.setOnClickListener {
            val intent = Intent(this, MainActivity_registrationChild::class.java)
            startActivity(intent)
        }
        // Declaring and initializing the Spinner from the layout file
        val mSpinner = findViewById<Spinner>(R.id.spinner)

        // Create a list to display in the Spinner
        val mList = arrayOf<String?>("Мужской","Женский")

        // Create an adapter as shown below
        val mArrayAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, mList)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list)

        // Set the adapter to the Spinner
        mSpinner.adapter = mArrayAdapter
        val dSpinner = findViewById<Spinner>(R.id.spinner1)

        // Create a list to display in the Spinner
        val dList = arrayOf<String?>("Синдром дефицита внимания и гиперактивности","Аутизм", "Синдром диссоциации движений и говора", "Синдром Туретта", "Синдром Дауна", "Синдром Аспергера","ДЦП", "Дислексия","Синдром Ретта")

        // Create an adapter as shown below
        val dArrayAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, mList)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list)

        // Set the adapter to the Spinner
        mSpinner.adapter = mArrayAdapter
    }
}