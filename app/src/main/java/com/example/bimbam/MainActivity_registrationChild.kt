import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bimbam.R
import android.graphics.Typeface
class MainActivity_registrationChild : AppCompatActivity() {

    private lateinit var relativeLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registration_child)

        // Найдите RelativeLayout в вашем макете
        relativeLayout = findViewById(R.id.form2)

        // Получите выбранный пол из интента
        val selectedGender = intent.getStringExtra("selectedGender")

        // Проверьте, существует ли RelativeLayout и выбранный пол не является пустым
        if (relativeLayout != null && selectedGender != null) {
            // Создайте TextView и установите его текст в выбранный пол
            val textView = TextView(this)
            textView.text = "$selectedGender"
            textView.textSize = 15f // размер текста в sp
            textView.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL) // установить стандартный шрифт с жирным начертанием
            // Добавьте TextView в RelativeLayout
            relativeLayout.addView(textView)
        }
    }
}
