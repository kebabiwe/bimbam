package com.example.bimbam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class MainActivity_you_acc_edit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_you_acc_edit)

        // Declaring and initializing the Spinner from the layout file
        val mSpinner = findViewById<Spinner>(R.id.spinner)

        // Create a list to display in the Spinner
        val mList = arrayOf<String?>("Мужской","Женский")

        // Create an adapter as shown below
        val mArrayAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, mList)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list)

        // Set the adapter to the Spinner
        mSpinner.adapter = mArrayAdapter

    }
}