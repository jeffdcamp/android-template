package org.jdc.template.ui.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jdc.template.datasource.database.converter.ThreeTenFormatter
import org.jdc.template.ui.recycleview.RecyclerViewDiffAdapter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId

object CustomBinders {
    @JvmStatic
    @BindingAdapter("loadThumbnail")
    fun loadThumbnail(view: ImageView, url: String?) {
        Glide
            .with(view.context)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("textDate")
    fun setTextDate(view: TextView, date: LocalDate?) {
        var text = ""
        date?.let {
            ThreeTenFormatter.localDateTimeToLong(it.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())?.let { millis ->
                text = DateUtils.formatDateTime(view.context, millis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR)
            }
        }

        view.text = text
    }

    @JvmStatic
    @BindingAdapter("textTime")
    fun setTextTime(view: TextView, time: LocalTime?) {
        var text = ""
        time?.let {
            ThreeTenFormatter.localDateTimeToLong(it.atDate(LocalDate.now()))?.let { millis ->
                text = DateUtils.formatDateTime(view.context, millis, DateUtils.FORMAT_SHOW_TIME)
            }
        }
        view.text = text
    }

    @JvmStatic
    @BindingAdapter("items")
    fun <T, VH : RecyclerView.ViewHolder> setItems(recyclerView: RecyclerView, items: List<T>?) {
        items ?: return

        @Suppress("UNCHECKED_CAST")
        val adapter = recyclerView.adapter as? RecyclerViewDiffAdapter<T, VH> ?: error("Must use a RecyclerViewDiffAdapter for app:items")
        adapter.items = items
    }

    @JvmStatic
    @BindingAdapter("buildTimeText")
    fun setAppBuildTimeText(view: TextView, millis: Long) {
        val dateText = DateUtils.formatDateTime(view.context, millis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR)
        view.text = dateText
    }
}
