import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bimbam.MainActivity_privetctvie
import com.example.bimbam.SplashActivity1

class SplashActivity : AppCompatActivity() {

    private val PREFS_NAME = "MyPrefsFile"
    private val PREF_FIRST_RUN = "firstRun"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isFirstRun: Boolean = prefs.getBoolean(PREF_FIRST_RUN, true)

        if (isFirstRun) {
            // Если приложение запускается впервые, показываем SplashActivity1
            val splashIntent = Intent(this, SplashActivity1::class.java)
            startActivity(splashIntent)
            finish()

            // Дополнительные действия, если нужны

            // После выполнения необходимых действий обновляем флаг
            with(prefs.edit()) {
                putBoolean(PREF_FIRST_RUN, false)
                apply()
            }
        } else {
            // Если приложение запускается не впервые, переходим к MainActivity_privetctvie
            val mainIntent = Intent(this, MainActivity_privetctvie::class.java)
            startActivity(mainIntent)
            finish()
        }
    }
}
