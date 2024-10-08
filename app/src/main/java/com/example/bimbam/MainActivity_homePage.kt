package com.example.bimbam

import Deal
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity_homePage : AppCompatActivity() {
    private lateinit var dealsContainer: LinearLayout
    private val addedDealIds = HashSet<String>()
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home_page)

        FirebaseMessaging.getInstance().token

        val profileView = findViewById<View>(R.id.profile)
        dealsContainer = findViewById(R.id.dealsContainer)
        currentUser = FirebaseAuth.getInstance().currentUser
        loadDealsFromDatabase()

        profileView.setOnClickListener {
            val intent = Intent(this@MainActivity_homePage, MainActivity_you_acc::class.java)
            startActivity(intent)
        }

        val View = findViewById<View>(R.id.icon5)
        View.setOnClickListener {
            val intent = Intent(this@MainActivity_homePage, MainActivity_settings::class.java)
            startActivity(intent)
        }

        val View2 = findViewById<View>(R.id.icon2)
        View2.setOnClickListener {
            val intent = Intent(this@MainActivity_homePage, MainActivity_recommendations::class.java)
            startActivity(intent)
        }

        val View4 = findViewById<View>(R.id.icon4)
        View4.setOnClickListener {
            val intent = Intent(this@MainActivity_homePage, MainActivity_list::class.java)
            startActivity(intent)
        }

        val AddADeal = findViewById<View>(R.id.icon3)
        AddADeal.setOnClickListener {
            addDeal()
        }
    }

    private fun loadDealsFromDatabase() {
        val dbDeals = FirebaseDatabase.getInstance().getReference("deals")
        dbDeals.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var dealsCount = 0
                dataSnapshot.children.forEach { userSnapshot ->
                    val userId = userSnapshot.key
                    userSnapshot.children.forEach { dealSnapshot ->
                        val dealId = dealSnapshot.key!!
                        val deal = dealSnapshot.getValue(Deal::class.java)
                        if (deal != null && userId == currentUser?.uid && !addedDealIds.contains(dealId)) {
                            val newRelativeLayout = createNewDealRelativeLayout(dealId, deal)
                            dealsContainer.addView(newRelativeLayout)
                            addedDealIds.add(dealId)
                            dealsCount++
                        }
                    }
                }

                if (dealsCount == 0 && dealsContainer.childCount == 0) {
                    findViewById<TextView>(R.id.title1).visibility = View.VISIBLE
                } else {
                    findViewById<TextView>(R.id.title1).visibility = View.GONE
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun addDeal() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.add_a_deal, null)
        builder.setView(view)

        val dialog = builder.create()

        val add = view.findViewById<View>(R.id.vector1)
        val add1 = view.findViewById<View>(R.id.vector2)
        val add2 = view.findViewById<View>(R.id.vector3)
        val add3 = view.findViewById<View>(R.id.vector4)
        val add4 = view.findViewById<View>(R.id.vector5)

        add.setOnClickListener {
            saveDeal()
            dialog.dismiss()
        }
        add1.setOnClickListener {
            saveDeal()
            dialog.dismiss()
        }
        add2.setOnClickListener {
            saveDeal()
            dialog.dismiss()
        }
        add3.setOnClickListener {
            saveDeal()
            dialog.dismiss()
        }
        add4.setOnClickListener {
            saveDeal()
            dialog.dismiss()
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
        // Implementation of showDatePicker
    }

    private fun showTimePicker(selectedTimeText: TextView) {
        // Implementation of showTimePicker
    }

    private fun createNewDealRelativeLayout(dealId: String, deal: Deal): RelativeLayout {
        val relativeLayout = RelativeLayout(this)
        val layoutParams = RelativeLayout.LayoutParams(
            320.dpToPx(), 56.dpToPx()
        )
        layoutParams.setMargins(16, 0, 0, 16)
        relativeLayout.layoutParams = layoutParams
        relativeLayout.setBackgroundResource(R.drawable.list_presssed)

        // TextView for deal name
        val textViewNazv = TextView(this)
        textViewNazv.text = deal.nazvText
        textViewNazv.id = View.generateViewId()
        textViewNazv.textSize = 15f
        textViewNazv.alpha = 0.5f
        textViewNazv.typeface = ResourcesCompat.getFont(this, R.font.opensans)
        textViewNazv.setTextColor(ContextCompat.getColor(this, android.R.color.black))

        // TextView for deal date
        val textViewDate = TextView(this)
        textViewDate.text = deal.selectedDate
        textViewDate.id = View.generateViewId()
        textViewDate.textSize = 13f
        textViewDate.alpha = 0.2f
        textViewDate.typeface = ResourcesCompat.getFont(this, R.font.opensans)
        textViewDate.setTextColor(ContextCompat.getColor(this, android.R.color.black))

        // RadioButton for deal status
        val radioButton = RadioButton(this)
        radioButton.id = View.generateViewId()
        radioButton.layoutParams = RelativeLayout.LayoutParams(
            32.dpToPx(), 32.dpToPx()
        )
        radioButton.background = ContextCompat.getDrawable(this, R.drawable.list_unpressed)

        // ImageView for editing deal
        val editImageView = ImageView(this)
        editImageView.id = View.generateViewId()
        editImageView.layoutParams = RelativeLayout.LayoutParams(
            18.dpToPx(), 18.dpToPx()
        )
        editImageView.setImageResource(R.drawable.edit)
        editImageView.alpha = 0.5f

        // Adding views to the RelativeLayout
        relativeLayout.addView(textViewNazv)
        relativeLayout.addView(textViewDate)
        relativeLayout.addView(radioButton)
        relativeLayout.addView(editImageView)

        // LayoutParams for textViewNazv
        val paramsNazv = textViewNazv.layoutParams as RelativeLayout.LayoutParams
        paramsNazv.addRule(RelativeLayout.ALIGN_PARENT_START)
        paramsNazv.addRule(RelativeLayout.CENTER_VERTICAL)
        paramsNazv.setMargins(43.dpToPx(), 0, 0, 0)
        textViewNazv.layoutParams = paramsNazv

        // LayoutParams for textViewDate
        val paramsDate = textViewDate.layoutParams as RelativeLayout.LayoutParams
        paramsDate.addRule(RelativeLayout.ALIGN_PARENT_END)
        paramsDate.addRule(RelativeLayout.CENTER_VERTICAL)
        paramsDate.setMargins(0, 0, 50.dpToPx(), 0)
        textViewDate.layoutParams = paramsDate

        // LayoutParams for radioButton
        val paramsRadioButton = radioButton.layoutParams as RelativeLayout.LayoutParams
        paramsRadioButton.addRule(RelativeLayout.ALIGN_PARENT_START)
        paramsRadioButton.addRule(RelativeLayout.CENTER_VERTICAL)
        paramsRadioButton.setMargins(5.dpToPx(), 11.dpToPx(), 0, 0)
        radioButton.layoutParams = paramsRadioButton

        // LayoutParams for editImageView
        val paramsEdit = editImageView.layoutParams as RelativeLayout.LayoutParams
        paramsEdit.addRule(RelativeLayout.ALIGN_PARENT_END)
        paramsEdit.addRule(RelativeLayout.CENTER_VERTICAL)
        paramsEdit.setMargins(0, 20.dpToPx(), 20.dpToPx(), 20.dpToPx())
        editImageView.layoutParams = paramsEdit

        // Set the onCheckedChangeListener for the radioButton
        radioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val dbDeals = FirebaseDatabase.getInstance().getReference("deals")
                dbDeals.child(currentUser?.uid ?: "").child(dealId).removeValue()

                val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
                relativeLayout.startAnimation(animation)
                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationRepeat(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        dealsContainer.removeView(relativeLayout)
                    }
                })
            }
        }

        return relativeLayout
    }

    fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }

    companion object {
        const val TAG = "MainActivity_homePage"
    }
}
