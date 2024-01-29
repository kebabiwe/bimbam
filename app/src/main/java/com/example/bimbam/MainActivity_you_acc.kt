package com.example.bimbam

import Deal
import android.content.*
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.errorprone.annotations.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

const val steps=10
class MainActivity_you_acc : AppCompatActivity() {
    private val dbUsers = FirebaseDatabase.getInstance().getReference("Key")
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var nameTextView: TextView
    private lateinit var sexTextView: TextView
    private lateinit var birthdayTextView: TextView
    private lateinit var diagnosTextView: TextView
    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Обновление данных в MainActivity_you_acc при получении широковещательного сообщения
            updateUserData()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_you_acc)


        SingleLineChartTheme{
            val lineChartData = LineChartData(
                linePlotData = LinePlotData(
                    lines = listOf(
                        Line(
                            dataPoints = pointsList,
                            LineStyle(),
                            IntersectionPoint(),
                            SelectionHighlightPoint(),
                            ShadowUnderLine(),
                            SelectionHighlightPopUp()
                        )
                    ),
                ),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                gridLines = GridLines(),
                backgroundColor = Color.RED
            )

            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartData
            )

        }


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
        val View4 = findViewById<View>(R.id.icon4)
        View4.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc, MainActivity_list::class.java)
            startActivity(intent) }
        val View5 = findViewById<View>(R.id.edit)
        View5.setOnClickListener{
            val intent = Intent(this@MainActivity_you_acc,MainActivity_you_acc_edit::class.java)
            startActivity(intent)
        }
        val icon3View = findViewById<View>(R.id.icon3)
        icon3View.setOnClickListener {
            addDeal()
        }
        val dbDeals = FirebaseDatabase.getInstance().getReference("deals")

        dbDeals.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dealSnapshot in dataSnapshot.children) {
                    val deal = dealSnapshot.getValue(Deal::class.java)
                    if (deal != null) {
                        val newRelativeLayout = createNewDealRelativeLayout(deal.nazvText ?: "", deal.selectedDate ?: "")

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun SingleLineChartTheme(function: () -> Unit) {
        TODO("Not yet implemented")
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
    override fun onResume() {
        super.onResume()
        // Регистрация приемника для обновления данных
        registerReceiver(updateReceiver, IntentFilter("updateUserData"))

        // Обновление данных в MainActivity_you_acc
        updateUserData()
    }

    override fun onPause() {
        super.onPause()
        // Отмена регистрации приемника при уходе из активности
        unregisterReceiver(updateReceiver)
    }

    private fun updateUserData() {
        currentUserUid?.let { uid ->
            dbUsers.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val user = dataSnapshot.getValue(Users::class.java)
                        user?.let {
                            // Обновление отображаемых данных
                            nameTextView.text = " ${it.name ?: ""}"
                            sexTextView.text = " ${it.sex ?: ""}"
                            birthdayTextView.text = " ${it.birthday ?: ""}"


                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Обработка ошибок при чтении данных из Firebase
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

            // Create a data object
            val deal = Deal(nazvText, descriptionText, selectedDate, selectedTime)

            // Get reference to your Firebase database
            val dbDeals = FirebaseDatabase.getInstance().getReference("deals")

            // Push the deal to the database
            val newDealRef = dbDeals.push()
            newDealRef.setValue(deal)
                .addOnCompleteListener(OnCompleteListener { task ->
                    if(nazvText.isNotEmpty() && descriptionText.isNotEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()){
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Успешно сохранено", Toast.LENGTH_SHORT).show()
                            val newRelativeLayout = createNewDealRelativeLayout(nazvText, selectedDate)

                            val dealsContainer = findViewById<LinearLayout>(R.id.dealsContainer)
                            dialog.dismiss()
                        } else {
                            Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                        }
                    }
                })}
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
        layoutParams.setMargins(16, 0, 0, 16) // Отступ между RelativeLayout
        relativeLayout.layoutParams = layoutParams
        relativeLayout.setBackgroundResource(R.drawable.list_presssed) // Фон RelativeLayout

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
        paramsNazv.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        paramsNazv.setMargins(43.dpToPx(), 27, 0, 0)
        textViewNazv.layoutParams = paramsNazv

        val paramsDate = textViewDate.layoutParams as RelativeLayout.LayoutParams
        paramsDate.addRule(RelativeLayout.ALIGN_PARENT_START)
        paramsDate.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        paramsDate.setMargins(370, 0, 0, 17.dpToPx())
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


    val  pointsList = getPointsList()
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color.BLACK)
        .steps(pointsList.size - 1)
        .labelData { i -> i.toString() + "day" }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.BLACK)
        .labelAndAxisLinePadding(20.dp)
        .labelData {
            i -> i.toString()
        }.build()


    fun getPointsList(): ArrayList<Point> {
        val list = ArrayList<Point>()
        for (i in 0..31) {
            list.add(
                Point(
                    i.toFloat(),
                    Random.nextInt(50, 90).toFloat().toFloat()
                )
            )
        }
        return list
    }
}

