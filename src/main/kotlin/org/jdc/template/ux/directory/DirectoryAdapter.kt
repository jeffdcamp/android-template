package org.jdc.template.ux.directory

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.jdc.template.BR
import org.jdc.template.databinding.ListItemBinding
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.ui.recycleview.RecyclerViewDiffAdapter

class DirectoryAdapter(private val viewModel: DirectoryViewModel) : RecyclerViewDiffAdapter<IndividualDao.DirectoryListItem, DirectoryAdapter.ViewHolder>() {

    var itemClickListener: (IndividualDao.DirectoryListItem) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            binding.setVariable(BR.viewModel, viewModel)

//            setOnClickListener { position -> itemClickListener(items[position]) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val individual = items[position]

        holder.binding.setVariable(BR.directoryItem, individual)
        holder.binding.executePendingBindings()
    }

    override fun areItemsTheSame(oldItem: IndividualDao.DirectoryListItem, newItem: IndividualDao.DirectoryListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IndividualDao.DirectoryListItem, newItem: IndividualDao.DirectoryListItem): Boolean {
        return oldItem.id == newItem.id && oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
    }

    class ViewHolder(parent: ViewGroup, val binding: ListItemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)) : RecyclerView.ViewHolder(binding.listItemLayout)
}
