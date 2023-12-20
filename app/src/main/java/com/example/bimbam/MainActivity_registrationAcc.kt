import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.example.bimbam.MainActivity_registrationChild
import com.example.bimbam.databinding.ActivityMainRegistrationAccBinding

class MainActivity_registrationAcc : AppCompatActivity() {
    private lateinit var binding: ActivityMainRegistrationAccBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainRegistrationAccBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonreg.setOnClickListener {
            val login = binding.login.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val repeatpass = binding.repeatpass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && login.isNotEmpty() && repeatpass.isNotEmpty()) {
                if (password == repeatpass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            val uid = user?.uid

                            // Переход на форму регистрации ребенка
                            val intent = Intent(this, MainActivity_registrationChild::class.java)
                            intent.putExtra("userUid", uid) // Передаем UID пользователя в следующую активность
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Пароль не совпадает", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Поля не могут быть пустыми", Toast.LENGTH_LONG).show()
            }
        }
    }
}
