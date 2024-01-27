import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Deal(
    var nazvText: String? = null,
    var descriptionText: String? = null,
    var selectedDate: String? = null,
    var selectedTime: String? = null
)
