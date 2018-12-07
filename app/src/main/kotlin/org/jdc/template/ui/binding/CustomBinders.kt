package org.jdc.template.ui.binding

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.jdc.template.ui.recycleview.MovableListAdapter
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
    @BindingAdapter("list")
    fun <T, VH : RecyclerView.ViewHolder> setItems(recyclerView: RecyclerView, list: List<T>?) {
        list ?: return

        @Suppress("UNCHECKED_CAST")
        when {
            recyclerView.adapter is ListAdapter<*, *> -> {
                val adapter = recyclerView.adapter as ListAdapter<T, VH>
                adapter.submitList(list)
            }
            recyclerView.adapter is MovableListAdapter<*, *> -> {
                val adapter = recyclerView.adapter as MovableListAdapter<T, VH>
                adapter.submitList(list)
            }
            else -> error("Must use a ListAdapter or MovableListAdapter for app:adapterList")
        }
    }

    @JvmStatic
    @BindingAdapter("buildTimeText")
    fun setAppBuildTimeText(view: TextView, millis: Long) {
        val dateText = DateUtils.formatDateTime(view.context, millis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR)
        view.text = dateText
    }
}
