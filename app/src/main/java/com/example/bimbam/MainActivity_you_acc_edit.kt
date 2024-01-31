package com.example.bimbam

import Deal
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class MainActivity_you_acc_edit : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var name: EditText
    private lateinit var btn: RelativeLayout
    private lateinit var birthday: EditText
    private lateinit var sex: Spinner
    private lateinit var avatarImageView: ImageView
    private lateinit var storageReference: StorageReference
    private val SELECT_IMAGE_REQUEST = 1
    private var data1: String = ""
    private var data2: String = ""
    private var data3: String = ""
    private var data4: String = ""
    private var imageUrl: String = ""
    private val dbUsers = FirebaseDatabase.getInstance().getReference("Key")
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    private var currentUser: FirebaseUser? = null
    private var nazvText: String? = null
    private var selectedDate: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_you_acc_edit)
        mAuth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        avatarImageView = findViewById(R.id.avatar)
        avatarImageView.layoutParams.width = resources.getDimensionPixelSize(R.dimen.avatar_width)
        avatarImageView.layoutParams.height = resources.getDimensionPixelSize(R.dimen.avatar_height)
        currentUser = FirebaseAuth.getInstance().currentUser

        val View1 = findViewById<View>(R.id.icon1)
        View1.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc_edit, MainActivity_homePage::class.java)
            startActivity(intent)
        }

        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_you_acc_edit, MainActivity_recommendations::class.java)
            startActivity(intent)
        }

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
            startActivity(intent)
        }
        val mSpinner = findViewById<Spinner>(R.id.spinner)
        val mList = arrayOf<String?>("Мужской", "Женский")
        val mArrayAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_list, mList)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list)
        mSpinner.adapter = mArrayAdapter
        val diagnos = findViewById<Spinner>(R.id.spinner1)
        val diagnosAdapter = ArrayAdapter.createFromResource(this, R.array.diagnos_array, android.R.layout.simple_spinner_item)
        diagnosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        diagnos.adapter = diagnosAdapter
        mAuth = FirebaseAuth.getInstance()
        name = findViewById(R.id.childname)
        birthday = findViewById(R.id.childbirthday)
        sex = findViewById(R.id.spinner)

        btn = findViewById(R.id.button)
        btn.setOnClickListener {
            data1 = name.text.toString()
            data2 = birthday.text.toString()
            data3 = sex.selectedItem.toString()
            data4 = diagnos.selectedItem.toString()
            saveData(data1, data2, data3, data4, imageUrl) // Pass imageUrl here
        }
        findViewById<View>(R.id.vector_12).setOnClickListener {
            selectImageFromGallery()
        }
        val AddADeal = findViewById<View>(R.id.icon3)
        AddADeal.setOnClickListener {
            addDeal()
        }
        val dbDeals = FirebaseDatabase.getInstance().getReference("deals")
        selectedDate = intent.getStringExtra("SELECTEDDATE")
        nazvText = intent.getStringExtra("NAZV_TEXT")

    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, SELECT_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            val inputStream = contentResolver.openInputStream(selectedImage!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 164, 164, false)
            val cornerRadius = resources.getDimension(R.dimen.corner_radius) // Corner radius
            val drawable = RoundedBitmapDrawableFactory.create(resources, scaledBitmap)
            drawable.cornerRadius = cornerRadius
            avatarImageView.setImageDrawable(drawable)

            uploadImageToFirebaseStorage(selectedImage)
        }
    }

    private fun uploadImageToFirebaseStorage(selectedImage: Uri?) {
        selectedImage?.let {
            val storageRef = storageReference.child("avatars/${System.currentTimeMillis()}.jpg")
            val uploadTask = storageRef.putFile(selectedImage)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                storageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    imageUrl = downloadUri.toString()
                } else {
                    // Error getting the URL of the uploaded image
                    Toast.makeText(this, "Не удалось загрузить изображение", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveData(name: String, birthday: String, sex: String, diagnos: String, imageUrl: String) {
        val email = mAuth.currentUser?.email
        val user = email?.let { Users(name, birthday, sex, diagnos, imageUrl) }

        FirebaseDatabase.getInstance().getReference("Key").child(mAuth.currentUser!!.uid).setValue(user)
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()
                    sendBroadcast(Intent("updateUserData"))
                    finish()
                } else {
                    Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_SHORT).show()
                }
            })
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

}
