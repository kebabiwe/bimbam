package com.example.bimbam

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class MainActivity_settings : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_settings)
        firebaseAuth = FirebaseAuth.getInstance()

        val RelativeLayout = findViewById<RelativeLayout>(R.id.frame_30)
        RelativeLayout.setOnClickListener {
            val intent = Intent(this@MainActivity_settings, MainActivity_about::class.java)
            startActivity(intent)
        }
        val View4 = findViewById<View>(R.id.icon4)
        View4.setOnClickListener {
            val intent = Intent(this@MainActivity_settings, MainActivity_list::class.java)
            startActivity(intent) }

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
        val Logout = findViewById<RelativeLayout>(R.id.frame_29)
        Logout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        val AddADeal = findViewById<View>(R.id.icon3)
        AddADeal.setOnClickListener{
            addDeal()
        }
    }
    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)

        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.custom_design, null)
        builder.setView(view)

        val btnYes = view.findViewById<RelativeLayout>(R.id.frame_25)
        val btnNo = view.findViewById<RelativeLayout>(R.id.frame_26)

        val dialog = builder.create()

        btnYes.setOnClickListener {
            logoutUser()
            dialog.dismiss()
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        // Переносим вызов show() за пределы метода
        dialog.show()
    }

    private fun clearUserToken() {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("user_token")
        editor.apply()
    }
    private fun logoutUser(){
        clearUserToken()
        firebaseAuth.signOut()

        // Заменяем данные в активности MainActivity_you_acc на "Гость" или "Неизвестно"
        val intent = Intent(this@MainActivity_settings, MainActivity_you_acc::class.java).apply {
            putExtra("NAME", "Гость")  // Или "Неизвестно", в зависимости от вашего выбора
            putExtra("SEX", "")
            putExtra("BIRTHDAY", "")
            putExtra("DIAGNOS", "")
        }
        startActivity(intent)

        // Создаем новый стек задачи для MainActivity_startmenu и убираем его из стека задачи текущей активности
        val startMenuIntent = Intent(this@MainActivity_settings, MainActivity_startmenu::class.java)
        startMenuIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(startMenuIntent)

        // Завершаем текущую активность, чтобы предотвратить возврат к предыдущему экрану
        finish()
    }
    private fun addDeal(){
        val builder = AlertDialog.Builder(this)

        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.add_a_deal, null)
        builder.setView(view)
        val add = view.findViewById<View>(R.id.vector1)
        val add1 = view.findViewById<View>(R.id.vector2)
        val add2 = view.findViewById<View>(R.id.vector3)
        val add3 = view.findViewById<View>(R.id.vector4)
        val add4 = view.findViewById<View>(R.id.vector5)
        val dialog = builder.create()
    add.setOnClickListener{
    saveDeal()
    }
        add1.setOnClickListener{
            saveDeal()
        }
        add2.setOnClickListener{
            saveDeal()
        }
        add3.setOnClickListener{
            saveDeal()
        }
        add4.setOnClickListener{
            saveDeal()
        }
        dialog.show()
    }
    private fun saveDeal() {
        val builder = AlertDialog.Builder(this)

        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.custom_design1, null)
        builder.setView(view)

        val nazv = view.findViewById<EditText>(R.id.editTextText)
        val desription = view.findViewById<EditText>(R.id.editTextText2)
        val date = view.findViewById<RelativeLayout>(R.id.date)
        val time = view.findViewById<RelativeLayout>(R.id.time)
        val delete = view.findViewById<RelativeLayout>(R.id.no)
        val save = view.findViewById<RelativeLayout>(R.id.save)
        val dialog = builder.create()
        val selectedDateText = view.findViewById<TextView>(R.id.some_id)
        // Найдите TextView внутри макета custom_design1
        val selectedTimeText = view.findViewById<TextView>(R.id.some_id1)

        date.setOnClickListener {
            showDatePicker(selectedDateText)
        }

        time.setOnClickListener {
            showTimePicker(selectedTimeText)
        }
        delete.setOnClickListener {
            // Просто закрываем AlertDialog
            dialog.dismiss()
        }
        save.setOnClickListener {
            // Получите текст из EditText
            val nazvText = nazv.text.toString()

            // Создайте Intent
            val intent = Intent(this@MainActivity_settings, MainActivity_list::class.java)

            // Передайте текст в другую активность
            intent.putExtra("NAZV_TEXT", nazvText)

            // Запустите другую активность
            startActivity(intent)

            // Закройте текущую активность, если это необходимо
            finish()
        }
        dialog.show()
    }

    private fun showDatePicker(selectedDateText: TextView) {
        val customDatePickerView = layoutInflater.inflate(R.layout.datepickertime, null)
        val datePicker = customDatePickerView.findViewById<DatePicker>(R.id.datePicker1)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        datePicker.init(year, month, day, null)

        // Создать диалог выбора даты
        val datePickerDialog = AlertDialog.Builder(this)
            .setTitle("Выберите дату")
            .setView(customDatePickerView)
            .setPositiveButton("OK") { _, _ ->
                // Обработать нажатие кнопки "OK", установить выбранную дату в TextView
                val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
                selectedDateText.text = selectedDate
            }
            .create()

        // Показать диалог выбора даты
        datePickerDialog.show()
    }
    private fun showTimePicker(selectedTimeText: TextView) {
        // Надуть макет пользовательского выбора времени
        val customTimePickerView = layoutInflater.inflate(R.layout.timepicker, null)

        // Найти NumberPickers в пользовательском макете
        val hoursPicker = customTimePickerView.findViewById<NumberPicker>(R.id.hoursPicker)
        val minutesPicker = customTimePickerView.findViewById<NumberPicker>(R.id.minutesPicker)

        // Инициализировать текущее время в выборах
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        hoursPicker.minValue = 0
        hoursPicker.maxValue = 23
        hoursPicker.value = currentHour
        minutesPicker.minValue = 0
        minutesPicker.maxValue = 59
        minutesPicker.value = currentMinute

        // Создать диалог выбора времени
        val timePickerDialog = AlertDialog.Builder(this)
            .setTitle("Выберите время")
            .setView(customTimePickerView)
            .setPositiveButton("OK") { _, _ ->
                // Обработать нажатие кнопки "OK", установить выбранное время в TextView
                val selectedTime = "${hoursPicker.value}:${String.format("%02d", minutesPicker.value)}"
                selectedTimeText.text = selectedTime
            }
            .create()

        // Показать диалог выбора времени
        timePickerDialog.show()
    }

}
