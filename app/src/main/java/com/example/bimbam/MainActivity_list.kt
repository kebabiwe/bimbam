package com.example.bimbam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity_list : AppCompatActivity() {
    private lateinit var calendar: TextView
    private lateinit var currentDate: Calendar
    private var nazvText: String? = null
    private var selectedDate:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        calendar = findViewById(R.id.some_id)
        currentDate = Calendar.getInstance()
        updateDate()
        val textView = findViewById<TextView>(R.id.textView2)
        textView.text = nazvText
        val textView1 = findViewById<TextView>(R.id.textView3)
        textView1.text= selectedDate

        val View5 = findViewById<View>(R.id.icon5)
        View5.setOnClickListener {
            val intent = Intent(this@MainActivity_list, MainActivity_settings::class.java)
            startActivity(intent)

        }

        val profileView = findViewById<View>(R.id.profile)
        profileView.setOnClickListener {
            val intent = Intent(this@MainActivity_list, MainActivity_you_acc::class.java)
            startActivity(intent)
        }
        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_list, MainActivity_homePage::class.java)
            startActivity(intent)}
        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_list, MainActivity_recommendations::class.java)
            startActivity(intent)}
        val arrowView = findViewById<View>(R.id.arrow)
        arrowView.setOnClickListener {
            // Перейти на предыдущую дату
            currentDate.add(Calendar.DAY_OF_MONTH, -1)
            updateDate()
        }

        val arrow2View = findViewById<View>(R.id.arrow2)
        arrow2View.setOnClickListener {
            // Перейти на следующую дату
            currentDate.add(Calendar.DAY_OF_MONTH, 1)
            updateDate()
        }
        selectedDate = intent.getStringExtra("SELECTEDDATE")
        nazvText = intent.getStringExtra("NAZV_TEXT") // Move this line here
        val icon3View = findViewById<View>(R.id.icon3)
        icon3View.setOnClickListener {
            addDeal()
        }
        // Найдите TextView в макете MainActivity_list и установите текст
        val textView2 = findViewById<TextView>(R.id.textView2)
        textView2.text = nazvText
    }

    private fun updateDate() {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate.time)
        calendar.text = formattedDate
    }

    private fun addDeal() {
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
        add.setOnClickListener {
            saveDeal()
        }
        add1.setOnClickListener {
            saveDeal()
        }
        add2.setOnClickListener {
            saveDeal()
        }
        add3.setOnClickListener {
            saveDeal()
        }
        add4.setOnClickListener {
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
        val description = view.findViewById<EditText>(R.id.editTextText2)
        val selectedDateText = view.findViewById<TextView>(R.id.some_id)
        val selectedTimeText = view.findViewById<TextView>(R.id.some_id1)
        val date = view.findViewById<RelativeLayout>(R.id.date)
        val time = view.findViewById<RelativeLayout>(R.id.time)
        val delete = view.findViewById<RelativeLayout>(R.id.no)
        val save = view.findViewById<RelativeLayout>(R.id.save)
        val dialog = builder.create()

        date.setOnClickListener {
            showDatePicker(selectedDateText)
        }

        time.setOnClickListener {
            showTimePicker(selectedTimeText)
        }
        delete.setOnClickListener {
            dialog.dismiss()
        }
        save.setOnClickListener {
            val nazvText = nazv.text.toString()
            val descriptionText = description.text.toString()
            val selectedDate = "${selectedDateText.text}"
            val selectedTime = "${selectedTimeText.text}"

            // Create a data object
            val deal = Deal(nazvText, descriptionText, selectedDate, selectedTime)

            // Get reference to your Firebase database
            val dbDeals = FirebaseDatabase.getInstance().getReference("deals")

            // Push the deal to the database
            val newDealRef = dbDeals.push()
            newDealRef.setValue(deal)
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Успешно сохранено", Toast.LENGTH_SHORT).show()

                        // Update textView2 with the name of the saved deal
                        val textView2 = findViewById<TextView>(R.id.textView2)
                        textView2.text = nazvText
                        val textView3 = findViewById<TextView>(R.id.textView3)
                        textView3.text = selectedDate
                    } else {
                        Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                    }
                })

            dialog.dismiss()
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

        // Create a date selection dialog
        val datePickerDialog = AlertDialog.Builder(this)
            .setTitle("Выберите дату")
            .setView(customDatePickerView)
            .setPositiveButton("OK") { _, _ ->
                // Handle the "OK" button click, set the selected date to TextView
                val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
                selectedDateText.text = selectedDate
            }
            .create()

        // Show the date selection dialog
        datePickerDialog.show()
    }

    private fun showTimePicker(selectedTimeText: TextView) {
        // Inflate the custom time selection layout
        val customTimePickerView = layoutInflater.inflate(R.layout.timepicker, null)

        // Find NumberPickers in the custom layout
        val hoursPicker = customTimePickerView.findViewById<NumberPicker>(R.id.hoursPicker)
        val minutesPicker = customTimePickerView.findViewById<NumberPicker>(R.id.minutesPicker)

        // Initialize the current time in the pickers
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        hoursPicker.minValue = 0
        hoursPicker.maxValue = 23
        hoursPicker.value = currentHour
        minutesPicker.minValue = 0
        minutesPicker.maxValue = 59
        minutesPicker.value = currentMinute

        // Create a time selection dialog
        val timePickerDialog = AlertDialog.Builder(this)
            .setTitle("Выберите время")
            .setView(customTimePickerView)
            .setPositiveButton("OK") { _, _ ->
                // Handle the "OK" button click, set the selected time to TextView
                val selectedTime = "${hoursPicker.value}:${String.format("%02d", minutesPicker.value)}"
                selectedTimeText.text = selectedTime
            }
            .create()

        // Show the time selection dialog
        timePickerDialog.show()
    }
}