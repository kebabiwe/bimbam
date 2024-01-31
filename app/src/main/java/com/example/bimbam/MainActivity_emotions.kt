package com.example.bimbam

import Deal
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

import java.util.*

class MainActivity_emotions : AppCompatActivity() {
    private var currentUser: FirebaseUser? = null
    private var nazvText: String? = null
    private var selectedDate: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_emotions)
        currentUser = FirebaseAuth.getInstance().currentUser
        val View5 = findViewById<View>(R.id.icon5)
        View5.setOnClickListener {
            val intent = Intent(this@MainActivity_emotions, MainActivity_settings::class.java)
            startActivity(intent)
        }

        val profileView = findViewById<View>(R.id.profile)
        profileView.setOnClickListener {
            val intent = Intent(this@MainActivity_emotions, MainActivity_you_acc::class.java)
            startActivity(intent)
        }
        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_emotions, MainActivity_homePage::class.java)
            startActivity(intent)}
        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_emotions, MainActivity_recommendations::class.java)
            startActivity(intent)}

        val View_back = findViewById<View>(R.id.arrow)
        View_back.setOnClickListener {
            val intent = Intent(this@MainActivity_emotions, MainActivity_recommendations::class.java)
            startActivity(intent)}
        val View4 = findViewById<View>(R.id.icon4)
        View4.setOnClickListener {
            val intent = Intent(this@MainActivity_emotions, MainActivity_list::class.java)
            startActivity(intent) }
        val AddADeal = findViewById<View>(R.id.icon3)
        AddADeal.setOnClickListener {
            addDeal()
        }
        val dbDeals = FirebaseDatabase.getInstance().getReference("deals")
        selectedDate = intent.getStringExtra("SELECTEDDATE")
        nazvText = intent.getStringExtra("NAZV_TEXT")
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
            val userId = currentUser?.uid
            val deal = Deal(nazvText, descriptionText, selectedDate, selectedTime)
            val dbDeals = FirebaseDatabase.getInstance().getReference("deals")
            val newDealRef = dbDeals.child(userId!!).push()
            newDealRef.setValue(deal)
                .addOnCompleteListener { task ->
                    if (nazvText.isNotEmpty() && descriptionText.isNotEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Успешно сохранено", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
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

        val datePickerDialog = AlertDialog.Builder(this)
            .setTitle("Выберите дату")
            .setView(customDatePickerView)
            .setPositiveButton("OK") { _, _ ->
                // Handle the "OK" button click, set the selected date to TextView
                val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
                selectedDateText.text = selectedDate
            }
            .create()
        datePickerDialog.show()
    }

    private fun showTimePicker(selectedTimeText: TextView) {
        val customTimePickerView = layoutInflater.inflate(R.layout.timepicker, null)
        val hoursPicker = customTimePickerView.findViewById<NumberPicker>(R.id.hoursPicker)
        val minutesPicker = customTimePickerView.findViewById<NumberPicker>(R.id.minutesPicker)
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        hoursPicker.minValue = 0
        hoursPicker.maxValue = 23
        hoursPicker.value = currentHour
        minutesPicker.minValue = 0
        minutesPicker.maxValue = 59
        minutesPicker.value = currentMinute
        val timePickerDialog = AlertDialog.Builder(this)
            .setTitle("Выберите время")
            .setView(customTimePickerView)
            .setPositiveButton("OK") { _, _ ->
                // Handle the "OK" button click, set the selected time to TextView
                val selectedTime = "${hoursPicker.value}:${String.format("%02d", minutesPicker.value)}"
                selectedTimeText.text = selectedTime
            }
            .create()
        timePickerDialog.show()
    }

    private fun createNewDealRelativeLayout(nazvText: String, selectedDate: String): RelativeLayout {
        val relativeLayout = RelativeLayout(this)
        val layoutParams = RelativeLayout.LayoutParams(320.dpToPx(), 56.dpToPx())
        layoutParams.setMargins(16, 0, 0, 16) // Margin between RelativeLayouts
        relativeLayout.layoutParams = layoutParams
        relativeLayout.setBackgroundResource(R.drawable.list_presssed) // Background of RelativeLayout

        val textViewNazv = TextView(this)
        textViewNazv.text = nazvText
        textViewNazv.id = View.generateViewId()
        textViewNazv.textSize = 15f
        textViewNazv.alpha = 0.5f
        textViewNazv.typeface = ResourcesCompat.getFont(this, R.font.opensans)
        textViewNazv.setTextColor(ContextCompat.getColor(this, android.R.color.black))

        val textViewDate = TextView(this)
        textViewDate.text = selectedDate
        textViewDate.id = View.generateViewId()
        textViewDate.textSize = 13f
        textViewDate.alpha = 0.2f
        textViewDate.typeface = ResourcesCompat.getFont(this, R.font.opensans)
        textViewDate.setTextColor(ContextCompat.getColor(this, android.R.color.black))

        val radioButton = RadioButton(this)
        radioButton.id = View.generateViewId()
        radioButton.layoutParams = RelativeLayout.LayoutParams(
            32.dpToPx(), 32.dpToPx()
        )
        radioButton.background = ContextCompat.getDrawable(this, R.drawable.list_unpressed)

        val editImageView = ImageView(this)
        editImageView.id = View.generateViewId()
        editImageView.layoutParams = RelativeLayout.LayoutParams(
            18.dpToPx(), 18.dpToPx()
        )
        editImageView.setImageResource(R.drawable.edit)
        editImageView.alpha = 0.5f

        relativeLayout.addView(textViewNazv)
        relativeLayout.addView(textViewDate)
        relativeLayout.addView(radioButton)
        relativeLayout.addView(editImageView)

        val paramsNazv = textViewNazv.layoutParams as RelativeLayout.LayoutParams
        paramsNazv.addRule(RelativeLayout.ALIGN_PARENT_START)
        paramsNazv.addRule(RelativeLayout.CENTER_VERTICAL) // Выравнивание по вертикали по центру
        paramsNazv.setMargins(43.dpToPx(), 0, 0, 0) // Отступ от левого края
        textViewNazv.layoutParams = paramsNazv

        val paramsDate = textViewDate.layoutParams as RelativeLayout.LayoutParams
        paramsDate.addRule(RelativeLayout.ALIGN_PARENT_END)
        paramsDate.addRule(RelativeLayout.CENTER_VERTICAL) // Выравнивание по вертикали по центру
        paramsDate.setMargins(0, 0, 50.dpToPx(), 0) // Отступ от правого края
        textViewDate.layoutParams = paramsDate

        val paramsRadioButton = radioButton.layoutParams as RelativeLayout.LayoutParams
        paramsRadioButton.addRule(RelativeLayout.ALIGN_PARENT_START)
        paramsRadioButton.addRule(RelativeLayout.CENTER_VERTICAL)
        paramsRadioButton.setMargins(5.dpToPx(), 11.dpToPx(), 0, 0)
        radioButton.layoutParams = paramsRadioButton

        val paramsEdit = editImageView.layoutParams as RelativeLayout.LayoutParams
        paramsEdit.addRule(RelativeLayout.ALIGN_PARENT_END)
        paramsEdit.addRule(RelativeLayout.CENTER_VERTICAL)
        paramsEdit.setMargins(0, 20.dpToPx(), 20.dpToPx(), 20.dpToPx())
        editImageView.layoutParams = paramsEdit
        return relativeLayout
    }

    fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }
}