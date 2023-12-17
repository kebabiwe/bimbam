import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bimbam.R
import android.content.Intent


class MainActivity_ChooseSex : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_choose_sex)
        val sexLayout = findViewById<RelativeLayout>(R.id.sex)
        val maleButton = findViewById<TextView>(R.id.maleButt)
        val femaleButton = findViewById<TextView>(R.id.femaleButton)

        maleButton.setOnClickListener {
            // Обработка выбора кнопки "Мужской"
            addDataToAnotherClass("Мужской")
        }

        femaleButton.setOnClickListener {
            // Обработка выбора кнопки "Женский"
            addDataToAnotherClass("Женский")
        }
    }

    private fun addDataToAnotherClass(selectedGender: String) {
        val intent = Intent(this, MainActivity_registrationChild::class.java)
        intent.putExtra("selectedGender", selectedGender)
        startActivity(intent)
    }
}
