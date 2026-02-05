package org.jdc.template.shared.util.network

import androidx.core.net.toUri

fun Uri.toAndroidUri(): android.net.Uri {
    return toString().toUri()
}

fun android.net.Uri.toKmpUri(): Uri {
    return Uri.parse(toString())
}
