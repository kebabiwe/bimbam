package com.example.bimbam

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity_you_acc_edit : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var name: EditText
    private val dbUsers = FirebaseDatabase.getInstance().getReference("Key")
    private lateinit var btn: RelativeLayout
    private lateinit var birthday: EditText
    private lateinit var sex: Spinner
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_you_acc_edit)
        mAuth = FirebaseAuth.getInstance()

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
        val mSpinner = findViewById<Spinner>(R.id.spinner)
        val mList = arrayOf<String?>("Мужской", "Женский")
        val mArrayAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, mList)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list)
        mSpinner.adapter = mArrayAdapter

        mAuth = FirebaseAuth.getInstance()
        name = findViewById(R.id.childname)
        birthday = findViewById(R.id.childbirthday)
        sex = findViewById(R.id.spinner)
        btn = findViewById(R.id.frame_36)
        btn.setOnClickListener {
            val data = name.text.toString()
            val data1 = birthday.text.toString()
            val data2 = sex.selectedItem.toString()
            saveData(data, data1, data2)
        }
    }
    private fun saveDataToSharedPreferences(name: String, birthday: String, sex: String, diagnos: String) {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("NAME", name)
        editor.putString("BIRTHDAY", birthday)
        editor.putString("SEX", sex)
        editor.putString("DIAGNOS", diagnos)
        editor.apply()
    }
    private fun saveData(name: String, birthday: String, sex: String) {
        val email = mAuth.currentUser?.email
        val user = email?.let { Users(name, birthday, sex) }

        dbUsers.child(mAuth.currentUser!!.uid).setValue(user)
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()

                    // Отправка широковещательного сообщения для обновления MainActivity_you_acc
                    sendBroadcast(Intent("updateUserData"))

                    // Завершить текущую активность
                    finish()
                } else {
                    Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
