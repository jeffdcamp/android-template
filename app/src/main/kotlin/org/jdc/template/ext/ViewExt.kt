package org.jdc.template.ext

import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(@LayoutRes res: Int, attachToParent: Boolean = false): View = LayoutInflater.from(context).inflate(res, this, attachToParent)
fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(context)