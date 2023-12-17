package com.example.bimbam

import android.os.Bundle
import android.view.Gravity
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity_registrationChild : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registration_child)

        val mainLayout: RelativeLayout = findViewById(R.id.form2)

        mainLayout.setOnClickListener {
            showGenderDialog(mainLayout)
        }
    }

    private fun showGenderDialog(relativeLayout: RelativeLayout) {
        val genderOptions = arrayOf("Мужской", "Женский")

        // Создаем RadioGroup
        val radioGroup = RadioGroup(this)
        radioGroup.orientation = RadioGroup.VERTICAL
        radioGroup.gravity = Gravity.CENTER

        // Добавляем RadioButton для каждого варианта пола
        for (gender in genderOptions) {
            val radioButton = RadioButton(this)
            radioButton.text = gender
            radioGroup.addView(radioButton)
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Выберите пол")
            .setView(radioGroup)
            .setPositiveButton("OK") { dialogInterface, which ->
                // Обработка выбора пола
                val selectedRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                val selectedGender = selectedRadioButton?.text.toString()

                // Обновляем RelativeLayout с выбранным полом
                updateRelativeLayout(relativeLayout, selectedGender)
            }
            .setNegativeButton("Отмена", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun updateRelativeLayout(relativeLayout: RelativeLayout, selectedGender: String) {
        // Здесь вы можете выполнить дополнительные действия в зависимости от выбора
        // например, обновить текст или добавить другие компоненты в RelativeLayout
        // в данном примере, мы просто обновим текст
        relativeLayout.removeAllViews()  // Очищаем все предыдущие компоненты
        val newTextView = androidx.appcompat.widget.AppCompatTextView(this)
        newTextView.text = "Ваш пол: $selectedGender"
        relativeLayout.addView(newTextView)
    }
}
