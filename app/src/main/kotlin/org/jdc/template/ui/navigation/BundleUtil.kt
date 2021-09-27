package org.jdc.template.ui.navigation

import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import java.io.Serializable

object BundleUtil {
    fun toBundle(argNameValues: List<Pair<String, Any?>>): Bundle {
        return Bundle().apply {
            argNameValues.forEach {
                val key = it.first
                when (val value = it.second) {
                    null -> {
                        // ignore
                    }
                    is IBinder -> putBinder(key, value)
                    is Bundle -> putBundle(key, value)
                    is Byte -> putByte(key, value)
                    is ByteArray -> putByteArray(key, value)
                    is Char -> putChar(key, value)
                    is CharArray -> putCharArray(key, value)
                    is CharSequence -> putCharSequence(key, value)
                    is Float -> putFloat(key, value)
                    is FloatArray -> putFloatArray(key, value)
                    is Parcelable -> putParcelable(key, value)
                    is Serializable -> putSerializable(key, value)
                    is Short -> putShort(key, value)
                    is ShortArray -> putShortArray(key, value)
                    else -> throw IllegalArgumentException("$value is of a type that is not currently supported")
                }
            }
        }
    }
}

fun <V> Map<String, V>.toBundle(bundle: Bundle = Bundle()): Bundle = bundle.apply {
    forEach {
        val k = it.key
        when (val v = it.value) {
            is IBinder -> putBinder(k, v)
            is Bundle -> putBundle(k, v)
            is Byte -> putByte(k, v)
            is ByteArray -> putByteArray(k, v)
            is Char -> putChar(k, v)
            is CharArray -> putCharArray(k, v)
            is CharSequence -> putCharSequence(k, v)
            is Float -> putFloat(k, v)
            is FloatArray -> putFloatArray(k, v)
            is Parcelable -> putParcelable(k, v)
            is Serializable -> putSerializable(k, v)
            is Short -> putShort(k, v)
            is ShortArray -> putShortArray(k, v)
            else -> throw IllegalArgumentException("$v is of a type that is not currently supported")
        }
    }
}
