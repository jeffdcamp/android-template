package org.jdc.template.ux.directory

import android.view.ViewGroup
import org.jdc.template.R
import org.jdc.template.databinding.ListItemBinding
import org.jdc.template.model.db.main.individual.IndividualDao
import org.jdc.template.ui.recycleview.BindingViewHolder
import org.jdc.template.ui.recycleview.RecyclerViewDiffAdapter

class DirectoryAdapter(
        private val viewModel: DirectoryViewModel
) : RecyclerViewDiffAdapter<IndividualDao.DirectoryListItem, DirectoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            binding.viewModel = viewModel
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, item: IndividualDao.DirectoryListItem) {
        holder.binding.directoryItem = item
    }

    override fun areItemsTheSame(oldItem: IndividualDao.DirectoryListItem, newItem: IndividualDao.DirectoryListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IndividualDao.DirectoryListItem, newItem: IndividualDao.DirectoryListItem): Boolean {
        return oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
    }

    class ViewHolder(parent: ViewGroup) : BindingViewHolder<ListItemBinding>(R.layout.list_item, parent)
}
