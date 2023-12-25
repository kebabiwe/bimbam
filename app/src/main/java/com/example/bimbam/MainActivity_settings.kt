package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class MainActivity_settings : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_settings)

        val RelativeLayout = findViewById<RelativeLayout>(R.id.frame_30)
        RelativeLayout.setOnClickListener {
            val intent = Intent(this@MainActivity_settings, MainActivity_about::class.java)
            startActivity(intent)
        }

        val profileView = findViewById<View>(R.id.profile)
        profileView.setOnClickListener {
            val intent = Intent(this@MainActivity_settings, MainActivity_you_acc::class.java)
            startActivity(intent) }
        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_settings, MainActivity_homePage::class.java)
            startActivity(intent)}
        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_settings, MainActivity_recommendations::class.java)
            startActivity(intent)}
    }
    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)

        // Устанавливаем собственный макет для AlertDialog
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.custom_design, null)
        builder.setView(view)

        // Находим кнопки в макете
        val btnYes = view.findViewById<RelativeLayout>(R.id.yes)
        val btnNo = view.findViewById<RelativeLayout>(R.id.no)

        // Устанавливаем обработчики для кнопок
        btnYes.setOnClickListener {
            logoutUser()
            // Закрываем диалог
            builder.create().dismiss()
        }

        btnNo.setOnClickListener {
            // Закрываем диалог
            builder.create().dismiss()
        }

        // Создаем AlertDialog
        val dialog = builder.create()

        // Отображаем AlertDialog
        dialog.show()
    }
    private fun clearUserToken() {
        // Clear the user token from SharedPreferences
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("user_token")
        editor.apply()
    }
    private fun logoutUser(){
        clearUserToken()
        firebaseAuth.signOut()
        val intent = Intent(this@MainActivity_settings, MainActivity_startmenu::class.java)
        startActivity(intent)

        // Finish the current activity to prevent the user from going back to the previous screen
        finish()
    }
}