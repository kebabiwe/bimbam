package com.example.bimbam


import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class SplashActivity1 : AppCompatActivity() {

    lateinit var iv_note: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash1)

        iv_note = findViewById(R.id.welcomescreen1)

        iv_note.alpha = 0f
        iv_note.animate().setDuration(1600).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity_privetctvie::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
