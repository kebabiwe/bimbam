import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bimbam.MainActivity_privetctvie
import com.example.bimbam.SplashActivity1

class SplashActivityy : AppCompatActivity() {

    private val PREFS_NAME = "MyPrefsFile"
    private val PREF_FIRST_RUN = "firstRun"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isFirstRun: Boolean = prefs.getBoolean(PREF_FIRST_RUN, true)

        if (isFirstRun) {
            showSplashActivity1()
        } else {
            showMainActivity()
        }

        finish()
        updateFirstRunFlag(prefs)
    }

    private fun showSplashActivity1() {
        val splashIntent = Intent(this, SplashActivity1::class.java)
        startActivity(splashIntent)
    }

    private fun showMainActivity() {
        val mainIntent = Intent(this, MainActivity_privetctvie::class.java)
        startActivity(mainIntent)
    }

    private fun updateFirstRunFlag(prefs: SharedPreferences) {
        with(prefs.edit()) {
            putBoolean(PREF_FIRST_RUN, false)
            apply()
        }
    }
}
