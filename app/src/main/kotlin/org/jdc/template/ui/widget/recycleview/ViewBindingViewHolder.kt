package org.jdc.template.ui.widget.recycleview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")
abstract class ViewBindingViewHolder<T : ViewBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)