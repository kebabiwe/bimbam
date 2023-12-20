import android.content.Intent
import android.os.Bundle
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.bimbam.databinding.ActivityMainRegistrationChildBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import com.example.bimbam.MainActivity_confirmation
import com.example.bimbam.R
import com.example.bimbam.Users

class MainActivity_registrationChild : AppCompatActivity() {
    private lateinit var binding: ActivityMainRegistrationChildBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private var userUid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainRegistrationChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        userUid = intent.getStringExtra("userUid") // Получаем UID пользователя

        val mSpinner = findViewById<Spinner>(R.id.spinner)
        val mList = arrayOf("Мужской", "Женский")
        val mArrayAdapter = ArrayAdapter(this, R.layout.spinner_list, mList)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list)
        mSpinner.adapter = mArrayAdapter

        val dSpinner = findViewById<Spinner>(R.id.spinner1)
        val dList = arrayOf(
            "Синдром дефицита внимания и гиперактивности", "Аутизм",
            "Синдром диссоциации движений и говора", "Синдром Туретта", "Синдром Дауна",
            "Синдром Аспергера", "ДЦП", "Дислексия", "Синдром Ретта"
        )
        val dArrayAdapter = ArrayAdapter(this, R.layout.spinner_list2, dList)
        dArrayAdapter.setDropDownViewResource(R.layout.spinner_list2)
        dSpinner.adapter = dArrayAdapter

        binding.button.setOnClickListener {
            val name = binding.childname.text.toString()
            val sex = binding.spinner.selectedItem.toString()
            val birthday = binding.childbirthday.text.toString()
            val diagnosis = binding.spinner1.selectedItem.toString()

            userUid?.let { uid ->
                database = FirebaseDatabase.getInstance().getReference("Users")
                val userChildReference = database.child(uid).child("childInfo")

                val userChild = Users(name, sex, birthday, diagnosis)

                userChildReference.setValue(userChild).addOnSuccessListener {
                    binding.childname.text.clear()
                    binding.childbirthday.text.clear()
                    val intent = Intent(this, MainActivity_confirmation::class.java)
                    Toast.makeText(this, "Данные успешно сохранены", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener {
                    Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show()
                }
            } ?: run {

                Toast.makeText(this, "User UID is null", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
