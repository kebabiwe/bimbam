import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Deal(
    val nazvText: String? = null,
    val description: String? = null,
    val selectedDate: String? = null,
    val selectedTime: String? = null,
    var createdBy: String? = null
)
