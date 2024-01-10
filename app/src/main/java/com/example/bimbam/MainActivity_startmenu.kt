package com.example.bimbam

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.TextView

class MainActivity_startmenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_startmenu)
        val RelativeLayout = findViewById<RelativeLayout>(R.id.button)
        RelativeLayout.setOnClickListener {
            val intent = Intent(this@MainActivity_startmenu, MainActivity_Login::class.java)
            startActivity(intent)
        }
        val TextView = findViewById<TextView>(R.id.button1)
        TextView.setOnClickListener{
            val intent = Intent(this@MainActivity_startmenu,MainActivity_registrationAcc::class.java)
            startActivity(intent)
        }

        val myButton: RelativeLayout = findViewById(R.id.button)

// Создание ColorStateList для текста
        val textColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf()
            ),
            intArrayOf(
                Color.GREEN, // Зеленый цвет для нажатого состояния
                Color.BLACK // Черный цвет для всех остальных состояний (включая отпущенное)
            )
        )

// Применение ColorStateList к тексту кнопки
        myButton.findViewById<TextView>(R.id.textView).setTextColor(textColors)

// Создание StateListDrawable
        val states = StateListDrawable()

// Добавление состояний (нажато и отпущено)
        states.addState(intArrayOf(android.R.attr.state_pressed), ColorDrawable(Color.GREEN)) // Зеленый цвет для нажатого состояния
        states.addState(intArrayOf(), ColorDrawable(Color.WHITE)) // Белый цвет для всех остальных состояний (включая отпущенное)

// Применение StateListDrawable к кнопке
        myButton.background = states







    }
}