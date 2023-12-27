package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner

class MainActivity_you_acc_edit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_you_acc_edit)

        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc_edit, MainActivity_homePage::class.java)
            startActivity(intent) }

        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc_edit, MainActivity_recommendations::class.java)
            startActivity(intent)}

        val RelativeDateTimeFormatter = findViewById<View>(R.id.icon5)
        RelativeDateTimeFormatter.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc_edit, MainActivity_settings::class.java)
            startActivity(intent)
        }
        val profileView = findViewById<View>(R.id.profile)
        profileView.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc_edit, MainActivity_you_acc::class.java)
            startActivity(intent)
        }
        val View4 = findViewById<View>(R.id.icon4)
        View4.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc_edit, MainActivity_list::class.java)
            startActivity(intent) }


        // Declaring and initializing the Spinner from the layout file
        val mSpinner = findViewById<Spinner>(R.id.spinner)

        // Create a list to display in the Spinner
        val mList = arrayOf<String?>("Мужской", "Женский")

        // Create an adapter as shown below
        val mArrayAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, mList)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list)

        // Set the adapter to the Spinner
        mSpinner.adapter = mArrayAdapter }
}