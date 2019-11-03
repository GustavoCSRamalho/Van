package gustavo.com.van.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Calendar {
    fun getCalendarFormated():String {
        val date = Date()
        val sdf: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }
}