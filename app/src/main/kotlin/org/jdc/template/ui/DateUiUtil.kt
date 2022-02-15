package org.jdc.template.ui

import android.content.Context
import android.text.format.DateUtils
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime

object DateUiUtil {
    fun getLocalDateText(context: Context, localDate: LocalDate?): String {
        localDate ?: return ""

        val millis = OffsetDateTime.now().with(localDate).toInstant().toEpochMilli()
        return DateUtils.formatDateTime(context, millis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR)
    }

    fun getLocalTimeText(context: Context, localTime: LocalTime?): String {
        localTime ?: return ""

        val millis = OffsetDateTime.now().with(localTime).toInstant().toEpochMilli()
        return DateUtils.formatDateTime(context, millis, DateUtils.FORMAT_SHOW_TIME)
    }
}