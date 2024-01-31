package com.example.bimbam

import Deal
import android.app.Activity
import android.content.*
import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.*


class MainActivity_you_acc : AppCompatActivity() {
    private val REQUEST_CODE_EDIT_PROFILE = 101
    private lateinit var avatarImageView: ImageView
    private val dbUsers = FirebaseDatabase.getInstance().getReference("Key")
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    private var currentUser: FirebaseUser? = null
    // Views
    private lateinit var nameTextView: TextView
    private lateinit var sexTextView: TextView
    private lateinit var birthdayTextView: TextView
    private lateinit var diagnosTextView: TextView
    private var nazvText: String? = null
    private var selectedDate: String? = null
    private var imageUrl: String? = null // Добавлено поле для хранения imageUrl

    // BroadcastReceiver for updating data
    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateUserData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_you_acc)
        currentUser = FirebaseAuth.getInstance().currentUser
        avatarImageView = findViewById(R.id.avatar)
        nameTextView = findViewById(R.id.NAME)
        sexTextView = findViewById(R.id.SEX)
        birthdayTextView = findViewById(R.id.BIRTHDAY)
        diagnosTextView = findViewById(R.id.DIAGNOS)
        val size = resources.getDimensionPixelSize(R.dimen.avatar_size)
        avatarImageView.layoutParams.width = size
        avatarImageView.layoutParams.height = size
        val icon5View = findViewById<View>(R.id.icon5)
        icon5View.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_settings::class.java)
            startActivity(intent)
        }

        val icon1View = findViewById<View>(R.id.icon1)
        icon1View.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_homePage::class.java)
            startActivity(intent)
        }
        val icon2View = findViewById<View>(R.id.icon2)
        icon2View.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_recommendations::class.java)
            startActivity(intent)
        }

        val icon4View = findViewById<View>(R.id.icon4)
        icon4View.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_list::class.java)
            startActivity(intent)
        }

        val editView = findViewById<View>(R.id.edit)
        editView.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_you_acc_edit::class.java)
            intent.putExtra("NAME", nameTextView.text.toString())
            intent.putExtra("SEX", sexTextView.text.toString())
            intent.putExtra("BIRTHDAY", birthdayTextView.text.toString())
            intent.putExtra("DIAGNOS", diagnosTextView.text.toString())
            intent.putExtra("IMAGE_URL", imageUrl) // Передаем imageUrl для редактирования
            startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE)
        }
        val AddADeal = findViewById<View>(R.id.icon3)
        AddADeal.setOnClickListener {
            addDeal()
        }
        val EditHeight = findViewById<View>(R.id.vector_12)
        EditHeight.setOnClickListener {
            editHeight()
        }
        val EditWeight = findViewById<View>(R.id.vector_13)
        EditWeight.setOnClickListener {
            editWeight()
        }
        selectedDate = intent.getStringExtra("SELECTEDDATE")
        nazvText = intent.getStringExtra("NAZV_TEXT")

    }



    override fun onResume() {
        super.onResume()

        registerReceiver(updateReceiver, IntentFilter("updateUserData"))

        updateUserData()
    }

    override fun onPause() {
        super.onPause()

        unregisterReceiver(updateReceiver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            data?.let {
                nameTextView.text = it.getStringExtra("NAME")
                sexTextView.text = it.getStringExtra("SEX")
                birthdayTextView.text = it.getStringExtra("BIRTHDAY")
                diagnosTextView.text = it.getStringExtra("DIAGNOS")
                // Получаем imageUrl из результатов редактирования
                imageUrl = it.getStringExtra("IMAGE_URL")
                // Применяем imageUrl
                if (!imageUrl.isNullOrEmpty()) {
                    Picasso.get().load(imageUrl).into(avatarImageView)
                }
            }
        }
    }
    private fun editHeight() {
        val builder = AlertDialog.Builder(this)

        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.custom_design_for_height, null)
        builder.setView(view)
        val heightEditText = view.findViewById<EditText>(R.id.height)
        val button = view.findViewById<RelativeLayout>(R.id.button)
        val dialog = builder.create()

        button.setOnClickListener {
            val heightString = heightEditText.text.toString().trim()
            if (heightString.isNotEmpty() && heightString.isNumeric()) {
                val heightValue = heightString.toDouble()
                val textView = findViewById<TextView>(R.id.textView2)
                textView.text = heightString
                val weightValue = findViewById<TextView>(R.id.textView3).text.toString() // Получаем текущее значение веса
                if (weightValue.isNotEmpty() && weightValue.isNumeric()) {
                    saveParametresToDatabase(heightValue, weightValue.toDouble())
                }
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun editWeight() {
        val builder = AlertDialog.Builder(this)

        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.custom_design_for_weight, null)
        builder.setView(view)
        val weightEditText = view.findViewById<EditText>(R.id.height)
        val button = view.findViewById<RelativeLayout>(R.id.button)
        val dialog = builder.create()

        button.setOnClickListener {
            val weightString = weightEditText.text.toString().trim()
            if (weightString.isNotEmpty() && weightString.isNumeric()) {
                val weightValue = weightString.toDouble()
                val textView = findViewById<TextView>(R.id.textView3)
                textView.text = weightString
                val heightValue = findViewById<TextView>(R.id.textView2).text.toString() // Получаем текущее значение роста
                if (heightValue.isNotEmpty() && heightValue.isNumeric()) {
                    saveParametresToDatabase(heightValue.toDouble(), weightValue)
                }
                dialog.dismiss()
            }
        }

        dialog.show()
    }



    private fun String.isNumeric(): Boolean {
        return try {
            this.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun saveParametresToDatabase(height: Double, weight: Double) {
        val userId = currentUser?.uid
        val parametres = Parametres(height.toString(), weight.toString())
        val dbParametres = FirebaseDatabase.getInstance().getReference("parametres")
        val newParametresRef = dbParametres.child(userId!!).push()
        newParametresRef.setValue(parametres)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Успешно сохранено", Toast.LENGTH_SHORT).show()
                    // Обновляем соответствующие текстовые поля
                    findViewById<TextView>(R.id.textView2).text = height.toString()
                    findViewById<TextView>(R.id.textView3).text = weight.toString()
                } else {
                    Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUserData() {
        currentUserUid?.let { uid ->
            dbUsers.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val user = dataSnapshot.getValue(Users::class.java)
                        user?.let {
                            nameTextView.text = " ${it.name ?: ""}"
                            sexTextView.text = " ${it.sex ?: ""}"
                            birthdayTextView.text = " ${it.birthday ?: ""}"
                            diagnosTextView.text = " ${it.diagnos ?: ""}"
                            // Получаем imageUrl из базы данных
                            imageUrl = it.imageUrl
                            // Применяем imageUrl с помощью Picasso
                            if (!imageUrl.isNullOrEmpty()) {
                                Picasso.get().load(imageUrl).into(avatarImageView)
                            }
                            // Обновляем рост и вес
                            val userId = currentUser?.uid
                            val dbParametres = FirebaseDatabase.getInstance().getReference("parametres")
                            dbParametres.child(userId!!).addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(parametresSnapshot: DataSnapshot) {
                                    if (parametresSnapshot.exists()) {
                                        val parametres = parametresSnapshot.getValue(Parametres::class.java)
                                        parametres?.let {
                                            findViewById<TextView>(R.id.textView2).text = it.height
                                            findViewById<TextView>(R.id.textView3).text = it.weight
                                        }
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Обработка ошибки, если не удается получить данные
                                }
                            })
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Обработка ошибки, если не удается получить данные
                }
            })
        }
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