package org.jdc.template.util.time

import android.content.Context
import android.text.format.DateUtils
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn


object TimeFormatUtil {
    fun millisToHoursMinutesSeconds(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        val builder = StringBuilder()
        if (hours > 0) {
            builder.append("${hours}h ")
        }
        if (minutes > 0) {
            builder.append("${minutes}m ")
        }
        if (seconds > 0) {
            builder.append("${seconds}s ")
        }

        return builder.toString()
    }

    fun secondsToHoursMinutesSeconds(totalSeconds: Long): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        val builder = StringBuilder()
        if (hours > 0) {
            builder.append("${hours}h ")
        }
        if (minutes > 0) {
            builder.append("${minutes}m ")
        }
        if (seconds > 0) {
            builder.append("${seconds}s ")
        }

        return builder.toString()
    }

    fun formatMessageTime(context: Context, messageInstant: Instant): String {
        val nowDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val messageDate: LocalDate = messageInstant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val isToday = nowDate == messageDate

        return if (isToday) {
            DateUtils.formatDateTime(context, messageInstant.toEpochMilliseconds(), DateUtils.FORMAT_ABBREV_ALL or DateUtils.FORMAT_SHOW_TIME)
        } else {
            DateUtils.formatDateTime(context, messageInstant.toEpochMilliseconds(), DateUtils.FORMAT_ABBREV_ALL or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE)
        }
    }
}