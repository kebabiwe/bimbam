package com.example.bimbam

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity_you_acc : AppCompatActivity() {
    private val dbUsers = FirebaseDatabase.getInstance().getReference("Key")
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var nameTextView: TextView
    private lateinit var sexTextView: TextView
    private lateinit var birthdayTextView: TextView
    private lateinit var diagnosTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_you_acc)

        nameTextView = findViewById(R.id.NAME)
        sexTextView = findViewById(R.id.SEX)
        birthdayTextView = findViewById(R.id.BIRTHDAY)
        diagnosTextView = findViewById(R.id.DIAGNOS)

        // Попытка получить данные из Intent
        val name = intent.getStringExtra("NAME")
        val sex = intent.getStringExtra("SEX")
        val birthday = intent.getStringExtra("BIRTHDAY")
        val diagnos = intent.getStringExtra("DIAGNOS")

        // Если данные не были переданы через Intent, попытаемся получить из SharedPreferences
        if (name == null || sex == null || birthday == null || diagnos == null) {
            val sharedPreferences: SharedPreferences = getPreferences(MODE_PRIVATE)

            // Если в SharedPreferences есть хотя бы одно значение, используем данные из SharedPreferences
            if (sharedPreferences.contains("NAME") || sharedPreferences.contains("SEX") ||
                sharedPreferences.contains("BIRTHDAY") || sharedPreferences.contains("DIAGNOS")
            ) {
                nameTextView.text = sharedPreferences.getString("NAME", "")
                sexTextView.text = sharedPreferences.getString("SEX", "")
                birthdayTextView.text = sharedPreferences.getString("BIRTHDAY", "")
                diagnosTextView.text = sharedPreferences.getString("DIAGNOS", "")
            } else {
                // Если в SharedPreferences нет данных, оставляем TextView без изменений
            }
        } else {
            // Если данные были переданы через Intent, устанавливаем их в TextView и сохраняем в SharedPreferences
            nameTextView.text = name
            sexTextView.text = sex
            birthdayTextView.text = birthday
            diagnosTextView.text = diagnos

            // Сохранение данных в SharedPreferences
            saveDataToSharedPreferences(name, sex, birthday, diagnos)
        }

        val RelativeDateTimeFormatter = findViewById<View>(R.id.icon5)
        RelativeDateTimeFormatter.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_settings::class.java)
            startActivity(intent)
        }

        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_homePage::class.java)
            startActivity(intent)
        }

        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_recommendations::class.java)
            startActivity(intent)
        }
    }

    private fun saveDataToSharedPreferences(name: String, sex: String, birthday: String, diagnos: String) {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("NAME", name)
        editor.putString("SEX", sex)
        editor.putString("BIRTHDAY", birthday)
        editor.putString("DIAGNOS", diagnos)
        editor.apply()
    }
}
