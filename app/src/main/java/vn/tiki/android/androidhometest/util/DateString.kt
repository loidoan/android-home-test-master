package vn.tiki.android.androidhometest.util

import java.text.SimpleDateFormat
import java.util.*



fun  dateConvert(startedDate: Date, endDate: Date) : Long {
    val dateFormat = SimpleDateFormat("dd-MM-yyy HH:mm:ss")
    dateFormat.timeZone = TimeZone.getTimeZone("GMT")

    val endDate = dateFormat.format(endDate)
    val startDate = dateFormat.format(startedDate)

    val futureDate = dateFormat.parse(endDate)
    val currentDate = dateFormat.parse(startDate)
    if (!currentDate.after(futureDate)) {
        var diff = futureDate.time - currentDate.time
        return diff
    } else {
        return 0
    }
}