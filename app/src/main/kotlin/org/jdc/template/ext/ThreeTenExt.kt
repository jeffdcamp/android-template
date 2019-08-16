package org.jdc.template.ext

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import java.util.Calendar

fun LocalTime.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.clear()
    calendar.set(0, 0, 0, hour, minute, second)
    return calendar
}

fun Calendar.toLocalTime(): LocalTime {
    return LocalTime.of(get(Calendar.HOUR), get(Calendar.MINUTE), get(Calendar.SECOND))
}

fun LocalDate.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.clear()
    calendar.set(year, monthValue - 1, dayOfMonth)
    return calendar
}

fun Calendar.toLocalDate(): LocalDate {
    return LocalDate.of(get(Calendar.YEAR), get(Calendar.MONTH) + 1, get(Calendar.DATE))
}
