package org.jdc.template.ui.compose.util

import android.content.Context
import android.text.format.DateUtils
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateUiUtil {
    fun getLocalDateText(
        context: Context,
        localDate: LocalDate?,
        dateUtilsFlags: Int = DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
    ): String {
        localDate ?: return ""

        localDate.atStartOfDayIn(TimeZone.currentSystemDefault())

        val millis = localDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        return DateUtils.formatDateTime(context, millis, dateUtilsFlags)
    }

    fun getLocalTimeText(
        context: Context,
        localTime: LocalTime?,
        dateUtilsFlags: Int = DateUtils.FORMAT_SHOW_TIME
    ): String {
        localTime ?: return ""
        val millis = LocalDateTime(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date, localTime).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        return DateUtils.formatDateTime(context, millis, dateUtilsFlags)
    }
}
