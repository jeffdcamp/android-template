package org.jdc.template.ux.directory

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import org.jdc.template.R
import org.jdc.template.databinding.ListItemBinding
import org.jdc.template.model.db.main.individual.IndividualDao
import org.jdc.template.ui.recycleview.BindingViewHolder

class DirectoryAdapter(
        private val viewModel: DirectoryViewModel
) : ListAdapter<IndividualDao.DirectoryListItem, DirectoryAdapter.ViewHolder>(DIFF_CALLBACK) {

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
        val DIFF_CALLBACK: DiffUtil.ItemCallback<IndividualDao.DirectoryListItem> = object : DiffUtil.ItemCallback<IndividualDao.DirectoryListItem>() {
            override fun areItemsTheSame(oldItem: IndividualDao.DirectoryListItem, newItem: IndividualDao.DirectoryListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: IndividualDao.DirectoryListItem, newItem: IndividualDao.DirectoryListItem): Boolean {
                return oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
            }
        }
    }
}
