package org.jdc.template.ui.binding

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime

object CustomBinders {
    @JvmStatic
    @BindingAdapter("textDate")
    fun setTextDate(view: TextView, date: LocalDate?) {
        var text = ""
        date?.let {
            val millis = OffsetDateTime.now().with(date).toInstant().toEpochMilli()
            text = DateUtils.formatDateTime(view.context, millis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR)
        }

        view.text = text
    }

    @JvmStatic
    @BindingAdapter("textTime")
    fun setTextTime(view: TextView, time: LocalTime?) {
        var text = ""
        time?.let {
            val millis = OffsetDateTime.now().with(time).toInstant().toEpochMilli()
            text = DateUtils.formatDateTime(view.context, millis, DateUtils.FORMAT_SHOW_TIME)
        }
        view.text = text
    }

    @JvmStatic
    @BindingAdapter("buildTimeText")
    fun setAppBuildTimeText(view: TextView, millis: Long) {
        val dateText = DateUtils.formatDateTime(view.context, millis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR)
        view.text = dateText
    }
}
