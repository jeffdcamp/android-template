package org.jdc.template.ux.directory

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.jdc.template.R
import org.jdc.template.databinding.ListItemBinding
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.ui.recycleview.BindingViewHolder

class DirectoryAdapter(
        private val viewModel: DirectoryViewModel
) : ListAdapter<DirectoryItem, DirectoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            binding.viewModel = viewModel
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.directoryItem = getItem(position)
    }

    class ViewHolder(parent: ViewGroup) : BindingViewHolder<ListItemBinding>(R.layout.list_item, parent)

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<DirectoryItem> = object : DiffUtil.ItemCallback<DirectoryItem>() {
            override fun areItemsTheSame(oldItem: DirectoryItem, newItem: DirectoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DirectoryItem, newItem: DirectoryItem): Boolean {
                return oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
            }
        }
    }
}
