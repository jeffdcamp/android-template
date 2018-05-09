package org.jdc.template.ext

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun <T : View> T.setVisible(visible: Boolean, invisibilityMode: Int = View.GONE) {
    visibility = when {
        visible -> View.VISIBLE
        else -> invisibilityMode
    }
}

fun ViewGroup.inflate(@LayoutRes res: Int, attachToParent: Boolean = false): View = LayoutInflater.from(context).inflate(res, this, attachToParent)
fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(context)