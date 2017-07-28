package org.jdc.template.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.StringRes
import android.support.v4.app.NotificationManagerCompat
import org.jdc.template.R

enum class NotificationChannels constructor(val channelId: String,
                                            @StringRes val textResId: Int,
                                            val importance: Int) {
    GENERAL("general_channel", R.string.notification_channel_general, NotificationManagerCompat.IMPORTANCE_DEFAULT);

    companion object {
        fun registerAllChannels(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                NotificationChannels.values().forEach {
                    val channel = NotificationChannel(it.channelId, context.getString(it.textResId), it.importance);
                    notificationManager.createNotificationChannel(channel);
                }
            }
        }
    }
}