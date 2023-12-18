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
        val spinner: Spinner = findViewById(R.id.spinner)

// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            this,
            R.array.sex_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }
}}